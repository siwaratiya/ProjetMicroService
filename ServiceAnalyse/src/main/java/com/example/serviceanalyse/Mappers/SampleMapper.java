package com.example.serviceanalyse.Mappers;

import com.example.serviceanalyse.Dto.SampleDto;
import com.example.serviceanalyse.Entities.Sample;
import com.example.serviceanalyse.Interfaces.AccountClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SampleMapper {
    AccountClient accountClient;

    public  SampleDto mapToDto(Sample sample){

        SampleDto sampleDto = SampleDto.builder()
                .idSample(sample.getIdSample())
                .date(sample.getDate())
                .numSample(sample.getNumSample())
                .account_id(sample.getIdA())
                .accountDto(accountClient.SelectById(sample.getIdA()).getBody())
                .build();
        return sampleDto;
    }
    public  Sample mapToEntity(SampleDto sampleDto ){
        Sample sample = Sample.builder()
                .idSample(sampleDto.getIdSample())
                .date(sampleDto.getDate())
                .numSample(sampleDto.getNumSample())
                .idA(sampleDto.getAccount_id())
                .build();
        return sample;
    }
}
