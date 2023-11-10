package tn.esprit.appointmentservice.Dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    String email;
}


