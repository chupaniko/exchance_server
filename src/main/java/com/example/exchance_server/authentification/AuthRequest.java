package com.example.exchance_server.authentification;

import com.example.exchance_server.appuser.AppUserRole;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AuthRequest {
    private final String email;
    private final String password;
    private final AppUserRole userRole;
}
