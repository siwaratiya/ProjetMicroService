package tn.esprit.appointmentservice.Mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import tn.esprit.appointmentservice.Dto.AppointmentDto;
import tn.esprit.appointmentservice.entities.Appointment;
import tn.esprit.appointmentservice.interfaces.AccountClient;

@Component
@AllArgsConstructor

public class AppointmentMapper {

    AccountClient accountClient;
    public static  Appointment mapToEntity(AppointmentDto appointmentDto){

        return Appointment.builder()
                .id(appointmentDto.getId())
                .reason(appointmentDto.getReason())
                .createdAt (appointmentDto.getCreatedAt())
                .comments(appointmentDto.getComments())
                .firstVisit (appointmentDto.isFirstVisit())
                .appointmentDate(
                        (appointmentDto.getAppointmentStart() == null ? null :appointmentDto.getAppointmentStart().toLocalDate())
                )
                .appointmentStartTime(
                        (appointmentDto.getAppointmentStart() == null ? null : appointmentDto.getAppointmentStart().toLocalTime())
                )
                .appointmentEndTime(

                        ( appointmentDto.getAppointmentEnd() == null ? null : appointmentDto.getAppointmentEnd().toLocalTime())
                )
                .appointmentStatus(appointmentDto.getAppointmentStatus())
                .idAccount(appointmentDto.getIdAccount())
                .build();

    }
    public static AppointmentDto mapToDto(Appointment appointment){
        return AppointmentDto.builder()
                .id(appointment.getId())
                .reason(appointment.getReason())
                .createdAt (appointment.getCreatedAt())
                .comments(appointment.getComments())
                .FirstVisit (appointment.isFirstVisit())
                .appointmentStart((appointment.getAppointmentStartTime() == null ? null :
                        appointment.getAppointmentDate().atTime(appointment.getAppointmentStartTime())))
                .appointmentEnd((appointment.getAppointmentEndTime() == null ? null :
                        appointment.getAppointmentDate().atTime(appointment.getAppointmentStartTime())
                        ))
                .appointmentStatus(appointment.getAppointmentStatus())
                .idAccount(appointment.getIdAccount())
               // .accountDto(accountClient.SelectById(appointment.getIdAccount()).getBody())
                .build();
    }

}
