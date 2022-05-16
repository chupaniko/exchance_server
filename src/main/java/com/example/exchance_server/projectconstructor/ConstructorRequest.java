package com.example.exchance_server.projectconstructor;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ConstructorRequest {
    private final String token;
    private final String projectName;
    private final String projectDescription;
    private final String projectField;
}
