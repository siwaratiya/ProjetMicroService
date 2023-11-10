package tn.esprit.appointmentservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tn.esprit.appointmentservice.Dto.AppointmentDto;
import tn.esprit.appointmentservice.entities.Appointment;
import tn.esprit.appointmentservice.services.AppointmentService;


import java.util.List;

@Controller
@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    private AppointmentService iAppointmentService;
    @Autowired
    public AppointmentController(@Qualifier("Appointment") AppointmentService iAppointmentService){this.iAppointmentService = iAppointmentService;}
    @GetMapping("/getAll")
    public List<AppointmentDto> SelectAll () {return  iAppointmentService. SelectAll () ;}
    @GetMapping("/get/{id}")
    public ResponseEntity<AppointmentDto> SelectBy (@PathVariable int id) {return ResponseEntity.ok( iAppointmentService.SelectBy ( id)) ;}
     @PostMapping("/add")
     public AppointmentDto Insert(@RequestBody AppointmentDto appointmentDto) {return  iAppointmentService.Insert(   appointmentDto);}
    @PutMapping("/update/{id}")
    public  ResponseEntity<AppointmentDto> update( @PathVariable("id") int id, @RequestBody AppointmentDto appointmentDto){
        return  ResponseEntity.ok(   iAppointmentService.update( id , appointmentDto));

    }


    @DeleteMapping("/delete/{id}")
    public  ResponseEntity<HttpStatus> delete(@PathVariable  Integer id ){   iAppointmentService.delete( id ); return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);}



    @PostMapping ("assignUserToAccount/{idAccount}")
    public AppointmentDto  assignUserToAccount(@RequestBody  Appointment appointment, @PathVariable("idAccount")  Long idAccount){
        return   iAppointmentService.assignAppointmentToAccount(appointment ,  idAccount);
    }
//    @PutMapping ("AddAppointmentAndAssignToAccount/{idAccount}")
//    public AppointmentDto  AddAppointmentAndAssignToAccount(  @PathVariable("idAccount")  Long idAccount , @RequestBody  AppointmentDto appointmentDto){
//        return   iAppointmentService.AddAppointmentAndAssignToAccount( idAccount,  appointmentDto);
//    }


}
