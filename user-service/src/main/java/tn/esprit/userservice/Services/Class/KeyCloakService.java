package tn.esprit.userservice.Services.Class;


import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import tn.esprit.userservice.Configures.KeycloakConfig;
import tn.esprit.userservice.Dtos.*;
import tn.esprit.userservice.Entitys.Account;
import tn.esprit.userservice.Entitys.Roles;
import tn.esprit.userservice.Services.Interfaces.IKeyCloakService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


@Service("keycloak-service")
public class KeyCloakService implements IKeyCloakService {
    private final KeycloakConfig keycloakConfig;
    private final RestTemplate restTemplate;
    @Autowired
    public KeyCloakService(KeycloakConfig keycloakConfig,
                           RestTemplate restTemplate)
    {   this.keycloakConfig = keycloakConfig;
        this.restTemplate = restTemplate;}


    @Override
    public void insert(UserDto userDto ){
        CredentialRepresentation credential = Credentials
                .createPasswordCredentials(userDto.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstname());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setCredentials(Collections.singletonList(credential));
        user.setEnabled(userDto.isEnabled());
        UsersResource instance = getInstance();
        instance.create(user);
        if (userDto.getRole() != null) {this.assignRealmRoleToUser(userDto.getUsername(), Roles.Patient.toString());}
    }

    private String getRoleIdByName(String roleName) {
        List<RoleRepresentation> realmRoles = keycloakConfig.getKeycloak()
                .realm(keycloakConfig.realm)
                .roles()
                .list();
        for (RoleRepresentation role : realmRoles) {
            System.out.println(role.getName());
        }
        for (RoleRepresentation role : realmRoles) {
            if (role.getName().equals(roleName)) {
                return role.getId();
            }
        }
        return null;
    }
    @Override
    public void assignRealmRoleToUser(String userName, String role_name){
        String client_id = getRealmResource().clients().findByClientId(keycloakConfig.clientId).get(0).getId();
        String userId = getRealmResource().users().search(userName).get(0).getId();
        UserResource user = getRealmResource().users().get(userId);
        List<RoleRepresentation> roleToAdd = new LinkedList<>();
        roleToAdd.add(getRealmResource().clients().get(client_id).roles().get(role_name).toRepresentation());
        user.roles().clientLevel(client_id).add(roleToAdd);
    }
    @Override
    public List<UserRepresentation> selectByUsername(String userName){
        UsersResource usersResource = getInstance();
        List<UserRepresentation> user = usersResource.search(userName, true);
        return user;
    }
    @Override
    public boolean isCorrectUserName (String userName){
        List<UserRepresentation> user = this.selectByUsername( userName);
        if ( user.size()>0){return true;}
        return false;
    }
    @Override
    public List<UserRepresentation> selectAll(){
        UsersResource usersResource = getInstance();
        List<UserRepresentation> users = usersResource.list();
        return users;
    }
    @Override
    public void update(String userId , UserDto userDto ){
        CredentialRepresentation credential = Credentials
                .createPasswordCredentials(userDto.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstname());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setCredentials(Collections.singletonList(credential));
        UsersResource usersResource = getInstance();
        usersResource.get(userId).update(user);
    }
    @Override
    public void update(String userId , Account account ){
        CredentialRepresentation credential = Credentials
                .createPasswordCredentials(account.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(account.getUsername());
        user.setFirstName(account.getFirstname());
        user.setLastName(account.getLastname());
        user.setEmail(account.getEmail());
        user.setCredentials(Collections.singletonList(credential));
        UsersResource usersResource = getInstance();
        usersResource.get(userId).update(user);
    }
    @Override
    public void update(String userId , UserRepresentation userRepresentation ){
        UsersResource usersResource = getInstance();
        usersResource.get(userId).update(userRepresentation);
    }
    @Override
    public void delete(String userId){
        UsersResource usersResource = getInstance();
        usersResource.get(userId)
                .remove();
    }
    @Override
    public void sendVerificationLink(String userId){
        UsersResource usersResource = getInstance();
        usersResource.get(userId).sendVerifyEmail();
    }
    @Override
    public void sendResetPassword(String userId){
        UsersResource usersResource = getInstance();

        usersResource.get(userId)
                .executeActionsEmail(Arrays.asList("UPDATE_PASSWORD"));
    }
    @Override
    public UsersResource getInstance(){

        return keycloakConfig.getKeycloak().realm(keycloakConfig.realm).users();
    }


    public RealmResource  getRealmResource(){
        return keycloakConfig.getKeycloak().realm(keycloakConfig.realm);
    }









    @Override
    public ResponseEntity<AuthenticationResponseDto> login(AuthenticationRequestDto request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("client_id", keycloakConfig.clientId);
            map.add("client_secret", keycloakConfig.clientSecret);
            map.add("grant_type", keycloakConfig.grantType);
            map.add("username", request.getUsername());
            map.add("password", request.getPassword());

            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);
            System.out.println(keycloakConfig.urlLogin);
            ResponseEntity<LoginResponse> loginResponse = restTemplate.postForEntity(keycloakConfig.urlLogin, httpEntity, LoginResponse.class);

            // Convert the LoginResponse to AuthenticationResponseDto
            AuthenticationResponseDto authenticationResponse = AuthenticationResponseDto.childBuilder()
                    .title("Authentication Response")
                    .datestamp(LocalDate.now())
                    .timestamp(LocalTime.now())
                    .status(ReponseStatus.SUCCESSFUL)
                    .message("Authentication Successful")
                    .token(loginResponse.getBody().getAccess_token())
                    .refresh_token(loginResponse.getBody().getRefresh_token())
                    .build();
            return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
        } catch (HttpClientErrorException ex) {
            // Handle the 401 Unauthorized error here and return a custom error response
            if (ex.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                // Create a custom error response
                AuthenticationResponseDto errorResponse = AuthenticationResponseDto.childBuilder()
                        .title("Authentication Failed")
                        .datestamp(LocalDate.now())
                        .timestamp(LocalTime.now())
                        .status(ReponseStatus.UNSUCCESSFUL)
                        .message("Invalid credentials provided")
                        .build();

                return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
            }
            else if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                // Handle 404 Not Found error and return a custom error response
                AuthenticationResponseDto notFoundResponse = AuthenticationResponseDto.childBuilder()
                        .title("Resource Not Found")
                        .datestamp(LocalDate.now())
                        .timestamp(LocalTime.now())
                        .status(ReponseStatus.ERROR)
                        .message("Resource not found")
                        .build();

                return new ResponseEntity<>(notFoundResponse, HttpStatus.NOT_FOUND);
            }
            else {
                // Handle other HTTP errors as needed
                throw ex; // Re-throw the exception for other cases
            }
        }
    }
    @Override
    public ResponseEntity<MsgReponseStatusDto> logout(HttpServletRequest request) {
        String refreshToken = request.getHeader("refresh-token");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", keycloakConfig.clientId);
        map.add("client_secret", keycloakConfig.clientSecret);
        map.add("refresh_token", refreshToken);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);

        ResponseEntity<Response> response = restTemplate.postForEntity(keycloakConfig.urlLogout, httpEntity, Response.class);

        MsgReponseStatusDto msgResponse = new MsgReponseStatusDto();
        if (response.getStatusCode().is2xxSuccessful()) {
            msgResponse.setTitle("Logout Successful");
            msgResponse.setDatestamp(LocalDate.now());
            msgResponse.setTimestamp(LocalTime.now());
            msgResponse.setStatus(ReponseStatus.SUCCESSFUL);
            msgResponse.setMessage("Logged out successfully");
        } else {
            msgResponse.setTitle("Logout Failed");
            msgResponse.setDatestamp(LocalDate.now());
            msgResponse.setTimestamp(LocalTime.now());
            msgResponse.setStatus(ReponseStatus.ERROR);
            msgResponse.setMessage("Logout was not successful");
        }
        return new ResponseEntity<>(msgResponse, HttpStatus.OK);
    }
}
