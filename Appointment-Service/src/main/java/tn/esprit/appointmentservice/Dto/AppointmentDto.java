package tn.esprit.appointmentservice.Dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.appointmentservice.entities.AppointmentStatus;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
//No Creators, like default construct, exist): cannot deserialize from Object value (no delegate- or property-based Creator
@ToString
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentDto {
    long id;
    String reason;
    LocalDateTime createdAt ;
    String comments;
    LocalDateTime appointmentStart;
    LocalDateTime appointmentEnd;
    AppointmentStatus appointmentStatus;
    long idAccount;
    //String firstName;
    //String lastName;

    boolean FirstVisit ;

    AccountDto accountDto;
    public AppointmentDto(String reason) {
        this.reason = reason;
    }
}

