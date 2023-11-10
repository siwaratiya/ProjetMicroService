package com.example.serviceanalyse.Services;

import com.example.serviceanalyse.Dto.SampleDto;
import com.example.serviceanalyse.Entities.Sample;
import com.example.serviceanalyse.Interfaces.AccountClient;
import com.example.serviceanalyse.Interfaces.ISample;
import com.example.serviceanalyse.Mappers.SampleMapper;
import com.example.serviceanalyse.Repository.SampleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SampleService implements ISample {
    AccountClient accountClient;
    SampleMapper leaveAuthorizationMapper;
    private final SampleRepository sampleRepository;


    @Override
    public Sample addOrUpdateSample(Sample sample) {
       return sampleRepository.save(sample);


    }

    @Override
    public void removeSample(int idSample) {
        sampleRepository.deleteById(idSample);
    }

    @Override
    public Sample retriveSample(int idSample) {
        return sampleRepository.findById(idSample).orElse(null);
    }
    @Override
    public List<Sample> retrieveAllSample() {

        return  sampleRepository.findAll();



    }
    @Override
    public SampleDto addAndAssignLAToAccount(Sample la, Long idA) {
        Long idAccount = accountClient.SelectById(idA).getBody().getId();

        Sample leaveAuthorization = sampleRepository.save(la);
        leaveAuthorization.setIdA(idAccount);

        sampleRepository.save(leaveAuthorization);

        return leaveAuthorizationMapper.mapToDto(leaveAuthorization);
    }


}
