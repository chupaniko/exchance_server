package com.example.exchance_server.admin;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ManualRequest {
    private final String token;
    private final String version;
    private final String aboutProjConstructor;
    private final String whatsNew;
    private final String aboutDev;
}
