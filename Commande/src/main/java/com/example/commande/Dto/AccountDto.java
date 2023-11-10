package com.example.commande.Dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountDto {
    long id;
    String username;
    String firstname;
    String lastname;
    int cin;
    int phone;
    LocalDate dateOfBirth;
    String email;
    String city;
    int zipCode;
    String address;
}
