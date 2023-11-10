package com.example.serviceanalyse.Dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@ToString
    @Builder
    @Setter
    @AllArgsConstructor
    @Getter
    public class SampleDto {
        private Integer idSample;
        private Date date;
        private String numSample;
    private  Long account_id;
    private AccountDto accountDto;
    }

