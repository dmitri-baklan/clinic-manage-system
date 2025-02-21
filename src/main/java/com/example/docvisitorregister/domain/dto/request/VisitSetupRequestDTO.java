package com.example.docvisitorregister.domain.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VisitSetupRequestDTO {
    private LocalDateTime start;
    private LocalDateTime end;
    private Long doctorId;
    private Long patientId;
}