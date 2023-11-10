package tn.esprit.hrservice.Dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Leave_AuthorizationDto {

    Long id_LA;
    Date start_date;
    Date end_date;
    LocalTime authStartTime;
    LocalTime authEndTime;
    String remainingLeaveDays;
    String  cause;
    Type_LA type_la;
    State_LA state_la;
    Long account_id;
    AccountDto accountDto;

}
