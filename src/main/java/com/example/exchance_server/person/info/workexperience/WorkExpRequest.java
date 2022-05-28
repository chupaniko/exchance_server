package com.example.exchance_server.person.info.workexperience;

import com.example.exchance_server.person.info.EmplPeriodRequest;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class WorkExpRequest {
    private final String token;
    private final String institute;
    private final String post;
    private final EmplPeriodRequest emplPeriodBody;
}
