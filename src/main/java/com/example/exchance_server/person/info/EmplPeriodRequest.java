package com.example.exchance_server.person.info;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class EmplPeriodRequest {
    private final String numTaxpayer;
    private final String orgName;
    private final int startYear;
    private final int endYear;
}
