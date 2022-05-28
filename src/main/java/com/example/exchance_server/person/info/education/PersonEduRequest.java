package com.example.exchance_server.person.info.education;

import com.example.exchance_server.person.info.EmplPeriodRequest;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PersonEduRequest {
    private String token;
    private final String facultyName;
    private final String specialization;
    private final String degree;
    private final EmplPeriodRequest emplPeriodBody;
}
