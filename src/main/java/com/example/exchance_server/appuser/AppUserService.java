package com.example.exchance_server.appuser;

import com.example.exchance_server.organization.Organization;
import com.example.exchance_server.organization.OrganizationRepository;
import com.example.exchance_server.organization.OrganizationService;
import com.example.exchance_server.person.Person;
import com.example.exchance_server.person.PersonService;
import com.example.exchance_server.registration.OrgRegRequest;
import com.example.exchance_server.registration.PersonRegRequest;
import com.example.exchance_server.registration.RegRequest;
import com.example.exchance_server.registration.token.ConfirmationToken;
import com.example.exchance_server.registration.token.ConfirmationTokenService;
import com.example.exchance_server.userproject.UserProject;
import com.example.exchance_server.userproject.UserProjectService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

//specifically for SpringSecurity
@Service
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG =
            "user with email %s not found";

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final PersonService personService;
    private final OrganizationService organizationService;
    private final UserProjectService projectService;
    private final OrganizationRepository organizationRepository;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder,
                          ConfirmationTokenService confirmationTokenService,
                          @Lazy PersonService personService,
                          OrganizationService organizationService,
                          @Lazy UserProjectService projectService,
                          OrganizationRepository organizationRepository) {
        this.appUserRepository = appUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationTokenService = confirmationTokenService;
        this.personService = personService;
        this.organizationService = organizationService;
        this.projectService = projectService;
        this.organizationRepository = organizationRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND_MSG, email)));
    }

    public List<UserProject> getProjectSubscriptions(String token) {
        return getUserByToken(token).getProjectSubscriptions().stream().toList();
    }

    public String subscribeToProject(String token, String name) {
        AppUser user = getUserByToken(token);
        user.getProjectSubscriptions().add(projectService.getProjectByName(name));
        appUserRepository.save(user);
        return "Subscription added";
    }

    public AppUser getUserByToken(String token) {
        return confirmationTokenService.getToken(token).orElseThrow(() ->
                new IllegalStateException("token not found" + token)).getAppUser();
    }

    public Person getPersonByAppUser(AppUser user) {
        return personService.getPersonByAppUser(user);
    }

    public Organization getOrganizationByAppUser(AppUser user) {
        return organizationService.getOrganizationByAppUser(user);
    }

    public AppUser getUserByEmail(String email) {
        return appUserRepository.findByEmail(email).orElseThrow(() -> new IllegalStateException("user not found"));
    }

    //appuser, person
    public String signUpUser(AppUser appUser, PersonRegRequest personRegRequest, OrgRegRequest orgRegRequest) {
        boolean userExists = appUserRepository
                .findByEmail(appUser.getEmail())
                .isPresent();

        if (userExists) {
            // TODO check of attributes are the same and
            // TODO if email not confirmed send confirmation email.

            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder
                .encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);
        if (personRegRequest != null)
        personService.registerPerson(appUser, personRegRequest);
        else organizationService.registerOrganization(appUser, orgRegRequest);


        // TODO: достаем юзера из repository, кидаем ссылку на него в person

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );

        confirmationTokenService.saveConfirmationToken(
                confirmationToken);

        //AppUser savedUser = appUserRepository.findByEmail(appUser.getEmail()).orElse(new AppUser());

        //personService.registerPerson(appUser, request);
//        TODO: SEND EMAIL

        return token;
    }

    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }

    public String authUser(AppUser requestUser) {
        AppUser appUser;
        appUser = appUserRepository.findByEmail(requestUser.getEmail()).orElse(new AppUser("", "", AppUserRole.SIMPLE_USER));

        if (Objects.equals(appUser.getEmail(), requestUser.getEmail()) && bCryptPasswordEncoder.matches(requestUser.getPassword(), appUser.getPassword())) {
            return confirmationTokenService.findTokenByUser(appUser);
        } else return "no id";
    }
}
