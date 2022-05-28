package com.example.exchance_server.projectconstructor;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AddUsersToProjectRequest {
    private final String token;
    private final String name;
    private final String userEmail;
}
