package tn.esprit.userservice.appRunners;


import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tn.esprit.userservice.Dtos.UserDto;
import tn.esprit.userservice.Entitys.Account;
import tn.esprit.userservice.Entitys.Roles;
import tn.esprit.userservice.Services.Class.AccountService;
import tn.esprit.userservice.Services.Interfaces.IKeyCloakService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Order(value=1)//Register BeanRunnerOne bean
@Slf4j
@Component
public class bean_ResetData implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {
        log.info("Bean One of Reset Data  run method Started !!");
        accountService.insert(ChefService);
        List<UserRepresentation> listUserRepresentations = iKeyCloakService.selectByUsername(ChefService.getUsername());
        if(  listUserRepresentations.size() == 0){
            iKeyCloakService.insert(UserDto.builder()
                    .firstname(ChefService.getFirstname())
                    .lastName(ChefService.getFirstname())
                    .username(ChefService.getUsername())
                    .password(ChefService.getPassword())
                    .email(ChefService.getEmail())
                    .enabled(ChefService.isEnabled())
                    .build());
            iKeyCloakService.assignRealmRoleToUser(ChefService.getUsername(), ChefService.getRole().toString());
        }


        accountService.insert(intership);
        listUserRepresentations = iKeyCloakService.selectByUsername(intership.getUsername());
        if(  listUserRepresentations.size() == 0){
            iKeyCloakService.insert(UserDto.builder()
                    .firstname(intership.getFirstname())
                    .lastName(intership.getFirstname())
                    .username(intership.getUsername())
                    .password(intership.getPassword())
                    .email(intership.getEmail())
                    .enabled(intership.isEnabled())
                    .build());
            iKeyCloakService.assignRealmRoleToUser(intership.getUsername(), intership.getRole().toString());
        }
    }

    @Autowired
    AccountService accountService;
    @Autowired
    IKeyCloakService iKeyCloakService;
    Account ChefService = Account.builder().id(0).username("chef-service").role(Roles.Chief_Service).password("97747369").enabled(true).firstname("chef-service").lastname("chef-service").build();
    Account intership = Account.builder().id(1).username("intership").role(Roles.Chief_Service).password("97747369").enabled(true).firstname("intership").lastname("intership").build();
}