package com.example.exchance_server.registration;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class OrgRegRequest implements RegRequest{
    private final UserRegRequest userBody;
    private final String numTaxpayer;
}
