package tn.esprit.hrservice.Entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Leave_Authorization implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id_LA;
    @Temporal(TemporalType.DATE)
    Date start_date;
    @Temporal(TemporalType.DATE)
    Date end_date;
    LocalTime authStartTime;
    LocalTime authEndTime;
    Long remaining_days;
    @NonNull
    String cause;
    @Enumerated(EnumType.STRING)
    Type_LA type_la;
    @Enumerated(EnumType.STRING)
    State_LA state_la;

    Long idA;


}

