package com.example.exchance_server.registration;

import com.example.exchance_server.appuser.AppUser;
import com.example.exchance_server.appuser.AppUserRole;
import com.example.exchance_server.appuser.AppUserService;
import com.example.exchance_server.email.EmailSender;
import com.example.exchance_server.registration.token.ConfirmationToken;
import com.example.exchance_server.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;

    private AppUser buildUserFromRequest(UserRegRequest request, AppUserRole role) {
        boolean isValidEmail = emailValidator.
                test(request.getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

        /*AppUserRole userRole;

        switch (request.getRole()) {
            case "STUDENT":
                userRole = AppUserRole.STUDENT;
                break;
            case "UNIVERSITY_EMPLOYEE":
                userRole = AppUserRole.UNIVERSITY_EMPLOYEE;
                break;
            case "ORGANIZATION":
                userRole = AppUserRole.ORGANIZATION;
            default:
                userRole = AppUserRole.SIMPLE_USER;
                break;
        }*/

        return new AppUser(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                role,
                request.getWebsite(),
                request.getAbout(),
                request.getPhone(),
                request.getRegion(),
                request.getAvatar());
    }

    private void sendConfirmTokenEmail(AppUser user, String link) {
        emailSender.send(
                user.getEmail(),
                buildEmail(user.getName(), link));
    }

    /*public String registerUser(RegRequest regRequest) {
        String link = "";
        String token = "";
        AppUser user = new AppUser();
        if (regRequest instanceof PersonRegRequest personRegRequest) {
            user = buildUserFromRequest(personRegRequest.getUserBody());
            token = appUserService.signUpUser(user, personRegRequest);
            link = "http://localhost:8080/api/v1/registerPerson/confirm?token=" + token;
        }
        else if (regRequest instanceof OrgRegRequest orgRegRequest) {
            user = buildUserFromRequest(orgRegRequest.getUserBody());
            token = appUserService.signUpUser(user, orgRegRequest);
            link = "http://localhost:8080/api/v1/registerOrganization/confirm?token=" + token;
        }

        sendConfirmTokenEmail(user, link);
        return token;
    }*/

    public String registerOrganization(OrgRegRequest orgRegRequest) {
        String link = "";
        String token = "";
        AppUser user = buildUserFromRequest(orgRegRequest.getUserBody(), AppUserRole.ORGANIZATION);
        token = appUserService.signUpUser(user, null, orgRegRequest);
        link = "http://localhost:8080/api/v1/registerOrganization/confirm?token=" + token;
        sendConfirmTokenEmail(user, link);
        return token;
    }

    public String registerPerson(PersonRegRequest personRegRequest) {
        String link = "";
        String token = "";
        AppUserRole userRole;

        switch (personRegRequest.getUserBody().getRole()) {
            case "STUDENT":
                userRole = AppUserRole.STUDENT;
                break;
            case "UNIVERSITY_EMPLOYEE":
                userRole = AppUserRole.UNIVERSITY_EMPLOYEE;
                break;
            default:
                userRole = AppUserRole.SIMPLE_USER;
                break;
        }
        AppUser user = buildUserFromRequest(personRegRequest.getUserBody(), userRole);
        token = appUserService.signUpUser(user, personRegRequest, null);
        link = "http://localhost:8080/api/v1/registerPerson/confirm?token=" + token;
        sendConfirmTokenEmail(user, link);
        return token;
    }

    /*public String registerUser(OrgRegRequest request) {
        AppUser user = buildUserFromRequest(request.getUserBody());
        String token = appUserService.signUpUser(user, null, request);
        String link = "http://localhost:8080/api/v1/registerOrganization/confirm?token=" + token;
        sendConfirmTokenEmail(user, link);
        return token;
    }*/

    /*public String registerPerson(PersonRegRequest request) {
        // TODO: ???????????????? ???????????? ?????????? ???? ???????????????????????????? ?? ?????????
        boolean isValidEmail = emailValidator.
                test(request.getUserBody().getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

        UserRegRequest userRegRequest = request.getUserBody();
        AppUserRole userRole;
        switch (userRegRequest.getRole()) {
            case "STUDENT":
                userRole = AppUserRole.STUDENT;
                break;
            case "UNIVERSITY_EMPLOYEE":
                userRole = AppUserRole.UNIVERSITY_EMPLOYEE;
                break;
            case "ORGANIZATION":
                userRole = AppUserRole.ORGANIZATION;
            default:
                userRole = AppUserRole.SIMPLE_USER;
                break;
        }

        String token = appUserService.signUpUser(
                new AppUser(
                        userRegRequest.getName(),
                        userRegRequest.getEmail(),
                        userRegRequest.getPassword(),
                        userRole,
                        userRegRequest.getWebsite(),
                        userRegRequest.getAbout(),
                        userRegRequest.getPhone(),
                        userRegRequest.getRegion(),
                        userRegRequest.getAvatar()
                ), request
        );

        String link = "http://localhost:8080/api/v1/registerPerson/confirm?token=" + token;
        emailSender.send(
                userRegRequest.getEmail(),
                buildEmail(userRegRequest.getName(), link));

        return token;
    }*/

   /* public String registerOrganization(OrgRegRequest request) {
        boolean isValidEmail = emailValidator.
                test(request.getUserBody().getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }
        UserRegRequest userRegRequest = request.getUserBody();

        String token = appUserService.signUpUser(
                new AppUser(
                        userRegRequest.getName(),
                        userRegRequest.getEmail(),
                        userRegRequest.getPassword(),
                        AppUserRole.ORGANIZATION,
                        userRegRequest.getWebsite(),
                        userRegRequest.getAbout(),
                        userRegRequest.getPhone(),
                        userRegRequest.getRegion(),
                        userRegRequest.getAvatar()
                ), request
        );

        String link = "http://localhost:8080/api/v1/registerOrganization/confirm?token=" + token;
        emailSender.send(
                userRegRequest.getEmail(),
                buildEmail(userRegRequest.getName(), link));

        return token;
    }*/

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(
                confirmationToken.getAppUser().getEmail());
        return "confirmed";
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
