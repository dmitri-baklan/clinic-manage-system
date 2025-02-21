package com.example.docvisitorregister.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VisitDTO {
    private LocalDateTime start;
    private LocalDateTime end;
    private DoctorDTO doctor;
}
