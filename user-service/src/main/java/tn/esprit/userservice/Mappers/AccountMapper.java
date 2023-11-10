package tn.esprit.userservice.Mappers;

import tn.esprit.userservice.Dtos.AccountDto;
import tn.esprit.userservice.Dtos.AttachementDto;
import tn.esprit.userservice.Entitys.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AccountMapper {
    public static Account mapToEntity(AccountDto accountDto){
        return Account.builder()
                .id(accountDto.getId())
                .username(accountDto.getUsername())
                .code(accountDto.getCode())
                .firstname(accountDto.getFirstname())
                .lastname(accountDto.getLastname())
                .cin(accountDto.getCin())
                .phone(accountDto.getPhone())
                .dateOfBirth(accountDto.getDateOfBirth())
                .email(accountDto.getEmail())
                .linkedIn(accountDto.getLinkedIn())
                .github(accountDto.getGithub())
                .gender(accountDto.getGender())
                .state(accountDto.getState())
                .city(accountDto.getCity())
                .zipCode(accountDto.getZipCode())
                .address(accountDto.getAddress())
                .build();
    }
    public static AccountDto mapToDto(Account account){
//        UserDto  userDto = (account.getUser()==null ? null :  UserMapper.mapToDto( account.getUser() ) );
        List<AttachementDto> attachementDtoList = new ArrayList<AttachementDto>();
        attachementDtoList = (
                (account.getAttachments() != null && ! account.getAttachments().isEmpty())
                ?
                account.getAttachments()
                        .stream()
                        .map(attachment -> AttachmentMapper.mapToDto(attachment))
                        .collect(Collectors.toList())
               :
                attachementDtoList
                );

        return AccountDto.builder()
                .id(account.getId())
                .createdAt(account.getCreatedAt())
                .username(account.getUsername())
                .role(account.getRole())
                .enabled(account.isEnabled())
                .code(account.getCode())
                .firstname(account.getFirstname())
                .lastname(account.getLastname())
                .cin(account.getCin())
                .phone(account.getPhone())
                .dateOfBirth(account.getDateOfBirth())
                .email(account.getEmail())
                .linkedIn(account.getLinkedIn())
                .github(account.getGithub())
                .gender(account.getGender())
                .state(account.getState())
                .city(account.getCity())
                .zipCode(account.getZipCode())
                .address(account.getAddress())
                .attachementsDto(attachementDtoList)
                .build();
    }
}