package com.example.clinic.dto;

import com.example.clinic.dto.user.UserDTO;
import com.example.clinic.model.Patient;
import com.example.clinic.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsultationDTO {
    private Long id;
    private Long user_id;
    private String user_name;
    private Long patient_id;
    private String patient_name;
    private String description;
    private String time;
}
