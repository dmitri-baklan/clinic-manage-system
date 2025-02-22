package com.example.docvisitorregister.domain.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class VisitResponseDTO {
    LocalDateTime start;
    LocalDateTime end;
    Long doctorId;
    Long patientId;
}
