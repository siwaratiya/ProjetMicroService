package tn.esprit.userservice.Services.Interfaces;

import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import tn.esprit.userservice.Dtos.AuthenticationRequestDto;
import tn.esprit.userservice.Dtos.AuthenticationResponseDto;
import tn.esprit.userservice.Dtos.MsgReponseStatusDto;
import tn.esprit.userservice.Dtos.UserDto;
import tn.esprit.userservice.Entitys.Account;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IKeyCloakService {
    ResponseEntity<AuthenticationResponseDto> login(AuthenticationRequestDto request);
    ResponseEntity<MsgReponseStatusDto> logout(HttpServletRequest request);

    void insert(UserDto userDto );
    void assignRealmRoleToUser(String userName, String role_name);
    List<UserRepresentation> selectAll();
    List<UserRepresentation> selectByUsername(String userName);
    boolean isCorrectUserName (String userName);
    void update(String userId , UserDto userDto );
    void update(String userId , Account account );
    void update(String userId , UserRepresentation userRepresentation );
    void delete(String userId);
    void sendVerificationLink(String userId);
    void sendResetPassword(String userId);
    UsersResource getInstance();

}
