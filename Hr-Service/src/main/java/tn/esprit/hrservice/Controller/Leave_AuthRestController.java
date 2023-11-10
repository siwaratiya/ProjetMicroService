package tn.esprit.hrservice.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.hrservice.Dto.Leave_AuthorizationDto;
import tn.esprit.hrservice.Entity.Leave_Authorization;
import tn.esprit.hrservice.Service.Leave_AuthService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("LeaveAuth")
public class Leave_AuthRestController {

    private final Leave_AuthService leave_authService;

    @PostMapping("/addAndAssignLAToAccount/{idA}")
    @ResponseStatus(HttpStatus.CREATED)
    public Leave_AuthorizationDto addAndAssignLAToAccount(@RequestBody Leave_Authorization leaveAuthorization, @PathVariable("idA") Long idA){
        return leave_authService.addAndAssignLAToAccount(leaveAuthorization, idA);
    }

    @PutMapping("/updateLeaveAuth/{idA}")
    @ResponseStatus(HttpStatus.OK)
    public Leave_AuthorizationDto updateLA(@RequestBody Leave_AuthorizationDto leaveAuthorization, @PathVariable("idA") Long idA){
        return leave_authService.updateLeaveAuth(leaveAuthorization, idA);
    }

    @GetMapping("/retrieveLAByAccountId/{idA}")
    @ResponseStatus(HttpStatus.OK)
    public List<Leave_AuthorizationDto> retrieveLAByAccountId(@PathVariable("idA") Long idA){
        return leave_authService.retrieveLAByAccountId(idA);
    }

    @DeleteMapping("/deleteLeaveAuth/{idLA}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteLA(@PathVariable("idLA") Long idLA){
        leave_authService.deleteLeaveAuth(idLA);
    }

    @GetMapping("/getAllLA")
    public List<Leave_AuthorizationDto> getAllLA(){
        return leave_authService.retrieveAllLeaveAuths();
    }

    @GetMapping("/getLAById/{idLA}")
    public Leave_AuthorizationDto getLAById(@PathVariable("idLA") Long idLA){
        return leave_authService.retrieveLeaveAuthById(idLA);
    }

}
