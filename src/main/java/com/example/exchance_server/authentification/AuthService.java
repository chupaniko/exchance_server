package com.example.exchance_server.authentification;

import com.example.exchance_server.appuser.AppUser;
import com.example.exchance_server.appuser.AppUserRole;
import com.example.exchance_server.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final AppUserService appUserService;

    public String auth(AuthRequest request) {
        return appUserService.authUser(
                new AppUser(
                        request.getEmail(),
                        request.getPassword(),
                        request.getUserRole()
                )
        );
    }
}
