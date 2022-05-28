package com.example.exchance_server.registration;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserRegRequest {
    // TODO: enum
    private final String role;
    private final String website;
    private final String about;
    private final String email;
    private final String password;
    private final byte[] avatar;
    private final String phone;
    private final String region;
    private final String name;
}
