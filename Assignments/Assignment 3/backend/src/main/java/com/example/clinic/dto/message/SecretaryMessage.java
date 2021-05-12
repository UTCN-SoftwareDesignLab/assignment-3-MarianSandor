package com.example.clinic.dto.message;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SecretaryMessage {

    private String doctorName;
    private String patientName;
    private String date;
}
