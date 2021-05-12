package com.example.clinic.mapper;

import com.example.clinic.dto.DoctorDTO;
import com.example.clinic.dto.user.UserDTO;
import com.example.clinic.model.ERole;
import com.example.clinic.model.Role;
import com.example.clinic.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Iterator;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    @Mapping(source = "username", target = "name", qualifiedByName = "getName")
    DoctorDTO toDTO(User user);

    @Named("getName")
    static String getName(String username) {
        return username;
    }
}
