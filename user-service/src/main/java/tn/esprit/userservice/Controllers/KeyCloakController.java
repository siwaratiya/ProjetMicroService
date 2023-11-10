package tn.esprit.userservice.Controllers;


import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.userservice.Dtos.AuthenticationRequestDto;
import tn.esprit.userservice.Dtos.AuthenticationResponseDto;
import tn.esprit.userservice.Dtos.MsgReponseStatusDto;
import tn.esprit.userservice.Dtos.UserDto;
import tn.esprit.userservice.Services.Interfaces.IKeyCloakService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(path = "keycloak")
public class KeyCloakController {
    private final IKeyCloakService iKeyCloakService;
    @Autowired
    public KeyCloakController(@Qualifier("keycloak-service") IKeyCloakService iKeyCloakService)
    {this.iKeyCloakService = iKeyCloakService;}


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login (
            @RequestBody AuthenticationRequestDto loginrequest)  {
        return iKeyCloakService.login(loginrequest);
    }
    @PostMapping("/logout")
    public ResponseEntity<MsgReponseStatusDto> logout ( HttpServletRequest request) {
        return iKeyCloakService.logout(request);
    }

    @PostMapping
    public String insert(@RequestBody UserDto userDTO){
        iKeyCloakService.insert(userDTO);
        return "User Added Successfully.";
    }
    @PutMapping(path = "/assign/{userName}/to/{role_name}")
    public String assignRealmRoleToUser(@PathVariable("userName") String userName,
                                        @PathVariable("role_name") String role_name){
        iKeyCloakService.assignRealmRoleToUser( userName,  role_name);
        return "User Added Successfully.";
    }
    @GetMapping()
    public List<UserRepresentation> selectAll(){
        List<UserRepresentation> user = iKeyCloakService.selectAll();
        return user;
    }
    @GetMapping(path = "/username/{userName}")
    public List<UserRepresentation> selectByUsername(@PathVariable("userName") String userName){
        List<UserRepresentation> user = iKeyCloakService.selectByUsername(userName);
        return user;
    }
    @GetMapping(path = "/is-correct-username/{userName}")
    boolean isCorrectUserName (@PathVariable("userName") String userName){
          return iKeyCloakService.isCorrectUserName (userName);
    }

    @PutMapping(path = "/{userId}")
    public String update(@PathVariable("userId") String userId, @RequestBody UserDto userDTO){
        iKeyCloakService.update(userId, userDTO);
        return "User Details Updated Successfully.";
    }

    @DeleteMapping(path = "/{userId}")
    public String delete(@PathVariable("userId") String userId){
        iKeyCloakService.delete(userId);
        return "User Deleted Successfully.";
    }

    @GetMapping(path = "/verification-link/{userId}")
    public String sendVerificationLink(@PathVariable("userId") String userId){
        iKeyCloakService.sendVerificationLink(userId);
        return "Verification Link Send to Registered E-mail Id.";
    }

    @GetMapping(path = "/reset-password/{userId}")
    public String sendResetPassword(@PathVariable("userId") String userId){
        iKeyCloakService.sendResetPassword(userId);
        return "Reset Password Link Send Successfully to Registered E-mail Id.";
    }
}
