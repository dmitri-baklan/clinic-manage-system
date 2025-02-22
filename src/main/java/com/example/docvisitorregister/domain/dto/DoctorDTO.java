package com.example.docvisitorregister.domain.dto;

import lombok.Data;

@Data
public class DoctorDTO {
    String firstName;
    String lastName;
    Long totalPatients;
}
