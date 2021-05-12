package com.example.clinic.mapper;

import com.example.clinic.dto.PatientDTO;
import com.example.clinic.model.Patient;
import com.example.clinic.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Mapping(source = "birthDate", target = "birthDate", qualifiedByName = "getBirthDay")
    PatientDTO toDTO(Patient patient);

    @Mapping(source = "birthDate", target = "birthDate", qualifiedByName = "getBirthDay")
    Patient fromDTO(PatientDTO patientDTO);

    @Named("getBirthDay")
    static Date getBirthDay(String birthDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.parse(birthDate);
    }

    @Named("getBirthDay")
    static String getBirthDay(Date birthDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(birthDate);
    }
}
