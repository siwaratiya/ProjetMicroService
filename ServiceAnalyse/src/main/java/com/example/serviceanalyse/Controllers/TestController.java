package com.example.serviceanalyse.Controllers;

import com.example.serviceanalyse.Entities.Test;
import com.example.serviceanalyse.Interfaces.ITest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Test")
@RequiredArgsConstructor

public class TestController {

    private final ITest iTest;

    @PostMapping("/add")
    Test addtest(@RequestBody Test e){
        return iTest.addOrUpdateTest(e);
    }

    @DeleteMapping("/delete/{idTest}")
    void deletetest(@PathVariable("idTest") int idTest){
        iTest.removeSTest(idTest);
    }

    @PutMapping("/update")
    Test updateTest(@RequestBody Test e){
        return iTest.addOrUpdateTest(e);
    }

    @GetMapping("/get/{idTest}")
    Test affichtest(@PathVariable("idTest") int idTest){
        return iTest.retriveTest(idTest);
    }
    @GetMapping("/all")
    List<Test> getAllTest(){
        return iTest.retrieveAllTest();
    }

    @PutMapping("/asign/{idTest}/{idSample}")
    Test ModifierDep (@PathVariable ("idTest") Integer idTest, @PathVariable("idSample") Integer idSample){
        return iTest.asigntesTosmp(idTest,idSample);

    }
}

