package com.example.docvisitorregister.domain.dto.request;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class PatientVisitsRequestDTO {
    Integer page;
    Integer size;
    @Nullable
    String search;
    List<Long> doctorId;
}
