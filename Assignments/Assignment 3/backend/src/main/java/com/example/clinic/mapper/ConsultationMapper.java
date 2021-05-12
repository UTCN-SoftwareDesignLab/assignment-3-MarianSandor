package com.example.clinic.mapper;

import com.example.clinic.dto.ConsultationDTO;
import com.example.clinic.model.Consultation;
import com.example.clinic.model.Patient;
import com.example.clinic.model.User;
import com.example.clinic.service.PatientService;
import com.example.clinic.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Mapper(componentModel = "spring")
public interface ConsultationMapper {

    @Mapping(source = "user", target = "user_id", qualifiedByName = "getUserId")
    @Mapping(source = "user", target = "user_name", qualifiedByName = "getUserName")
    @Mapping(source = "patient", target = "patient_id", qualifiedByName = "getPatientId")
    @Mapping(source = "patient", target = "patient_name", qualifiedByName = "getPatientName")
    @Mapping(source = "time", target = "time", qualifiedByName = "getTime")
    ConsultationDTO toDTO(Consultation consultation);

    @Named("getUserId")
    static Long getUserId(User user) {
        return user.getId();
    }

    @Named("getUserName")
    static String getUserName(User user) {
        return user.getUsername();
    }

    @Named("getPatientId")
    static Long getPatientId(Patient patient) {
        return patient.getId();
    }

    @Named("getPatientName")
    static String getPatientName(Patient patient) {
        return patient.getName();
    }

    @Named("getTime")
    static String getTime(Calendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return sdf.format(calendar.getTime());
    }
}
