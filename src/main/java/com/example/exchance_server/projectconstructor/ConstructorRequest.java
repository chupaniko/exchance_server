package com.example.exchance_server.projectconstructor;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ConstructorRequest {
    private final String token;

    private final String name;
    private final String launchReason;
    private final LocalDateTime createdAt;
    private final String need;
    private final String goalsResCriteria;
    private final String tasks;
    private final String orgBorders;
    private final String budget;
    private final String time;
    private final String restrictions;
    private final String risks;
    private final String customer;
    private final String leader;
}
