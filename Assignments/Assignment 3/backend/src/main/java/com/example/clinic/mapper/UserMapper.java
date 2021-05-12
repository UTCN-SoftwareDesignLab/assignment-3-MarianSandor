package com.example.clinic.mapper;

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
public interface UserMapper {

    @Mapping(source = "roles", target = "role", qualifiedByName = "getRole")
    UserDTO toDTO(User user);

    @Named("getRole")
    static ERole getRole(Set<Role> roles) {
        if (!roles.isEmpty()) {
            Iterator<Role> it = roles.iterator();

            if (it.hasNext()) {
                return it.next().getName();
            }
        }

        return null;
    }
}