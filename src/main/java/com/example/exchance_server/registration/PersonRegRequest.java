package com.example.exchance_server.registration;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PersonRegRequest implements RegRequest{
    private final UserRegRequest userBody;
    private final String surname;
    private final String patronymic;
    private final boolean gender;
    private final String citizenship;
    private final LocalDateTime birthday;
}
