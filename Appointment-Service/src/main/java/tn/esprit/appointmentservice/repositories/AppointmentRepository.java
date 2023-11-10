package tn.esprit.appointmentservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.appointmentservice.entities.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    //@Query("SELECT CASE WHEN COUNT(appt) > 0 THEN true ELSE false END FROM Appointment appt WHERE (((appt.appointmentStartTime >= :startime) AND (appt.appointmentStartTime < :endtime)) OR ((appt.appointmentEndTime > :startime) AND  (appt.appointmentEndTime <= :endtime) ) OR ((appt.appointmentStartTime <= :startime) AND (appt.appointmentEndTime >= :endtime) )  OR ((appt.appointmentStartTime >= :startime) AND (appt.appointmentEndTime <= :endtime)))")
    //boolean isInBetweenTwoTimeAndDate(  @Param("startime") LocalTime startime, @Param("endtime") LocalTime endtime  );


    @Query("SELECT appt  FROM Appointment appt WHERE  ( (appt.appointmentStatus is not null ) AND (appt.appointmentDate = :tommorow )  AND (appt.appointmentStatus = tn.esprit.appointmentservice.entities.AppointmentStatus.Available ) )")
    List<Appointment> findAppointmentsByAppointmentDateAndAppointment_StatusAvaible(@Param("tommorow") LocalDate tommorow  ) ;


    @Query("SELECT CASE WHEN COUNT(appt) > 0 THEN true ELSE false END FROM Appointment appt WHERE (((appt.appointmentStartTime >= :startime) AND (appt.appointmentStartTime < :endtime)) OR ((appt.appointmentEndTime > :startime) AND  (appt.appointmentEndTime <= :endtime) ) OR ((appt.appointmentStartTime <= :startime) AND (appt.appointmentEndTime >= :endtime) )  OR ((appt.appointmentStartTime >= :startime) AND (appt.appointmentEndTime <= :endtime)))")
    boolean isInBetweenTwoTime(@Param("startime") LocalTime startime, @Param("endtime") LocalTime endtime  );

    @Query("SELECT CASE WHEN COUNT(appt) > 0 THEN true ELSE false END FROM Appointment appt WHERE    ((appt.appointmentDate >= :date)   and (((appt.appointmentStartTime >= :startime) AND (appt.appointmentStartTime < :endtime)) OR ((appt.appointmentEndTime > :startime) AND  (appt.appointmentEndTime <= :endtime) ) OR ((appt.appointmentStartTime <= :startime) AND (appt.appointmentEndTime >= :endtime) )  OR ((appt.appointmentStartTime >= :startime) AND (appt.appointmentEndTime <= :endtime))))")
    boolean isInBetweenTwoTimeAndDate(@Param("date") LocalDate date, @Param("startime") LocalTime startime, @Param("endtime") LocalTime endtime  );

    // List<Appointment> findAppointmentsByAppointmentDate(LocalDate date);
    //  @Query("select appt  from  Appointment appt WHERE (appt.appointmentStatus=tn.esprit.Entitys.AppointmentStatus.Available)and (appt.appointmentDate=:date)")
    // List<Appointment> findAppointmentsByavailbleAndDate(@Param("date")LocalDate date);


}
