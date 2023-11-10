package com.example.serviceanalyse.Services;

import com.example.serviceanalyse.Entities.Sample;
import com.example.serviceanalyse.Entities.Test;
import com.example.serviceanalyse.Interfaces.ITest;
import com.example.serviceanalyse.Repository.SampleRepository;
import com.example.serviceanalyse.Repository.TestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j

public class TestService implements ITest {

    private final TestRepository testRepository;
    private final SampleRepository sampleRepository;



    @Override
    public Test addOrUpdateTest(Test test) {
        return testRepository.save(test);


    }

    @Override
    public void removeSTest(int idTest) {
        testRepository.deleteById(idTest);
    }

    @Override
    public Test retriveTest(int idTest) {
        return testRepository.findById(idTest).orElse(null);
    }
    @Override
    public List<Test> retrieveAllTest() {

        return  testRepository.findAll();

    }
    @Override
    public Test asigntesTosmp(int idTest, int idSample) {
        Test d = testRepository.findById(idTest).orElse(null);
        Sample e = sampleRepository.findById(idSample).orElse(null);
        d.setSample(e);

        return testRepository.save(d);
    }
}