package com.example.serviceanalyse.Controllers;

import com.example.serviceanalyse.Dto.SampleDto;
import com.example.serviceanalyse.Entities.Sample;
import com.example.serviceanalyse.Interfaces.ISample;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Sample")
@RequiredArgsConstructor
public class SampleController {
    private final ISample iSample;
    @PostMapping("/add")
    Sample addSample(@RequestBody Sample sample){
        return iSample.addOrUpdateSample(sample);
    }
    @DeleteMapping("/delete/{idSample}")
    void deleteSample(@PathVariable("idSample") Integer idSample){
        iSample.removeSample(idSample);
    }
    @PutMapping("/update")
    Sample updateSample(@RequestBody Sample e){
        return iSample.addOrUpdateSample(e);
    }
    @GetMapping("/get/{idSample}")
    Sample getSample(@PathVariable("idSample") Integer idSample){
        return iSample.retriveSample(idSample);
    }
    @GetMapping("/all")
    List<Sample> getAllSample(){
        return iSample.retrieveAllSample();
    }
    @PostMapping("/addAndAssignLAToAccount/{idA}")
    @ResponseStatus(HttpStatus.CREATED)
    public SampleDto addAndAssignLAToAccount(@RequestBody Sample leaveAuthorization, @PathVariable("idA") Long idA){
        return iSample.addAndAssignLAToAccount(leaveAuthorization, idA);
    }

}

