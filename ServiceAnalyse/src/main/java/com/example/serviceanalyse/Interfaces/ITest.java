package com.example.serviceanalyse.Interfaces;

import com.example.serviceanalyse.Entities.Test;

import java.util.List;

public interface ITest {
    Test addOrUpdateTest(Test test);

    void removeSTest(int idTest);

    Test retriveTest(int idTest);

    List<Test> retrieveAllTest();

    Test asigntesTosmp(int idTest, int idSample);
}
