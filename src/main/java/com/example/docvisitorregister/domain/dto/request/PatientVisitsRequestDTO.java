package com.example.docvisitorregister.domain.dto.request;

import jakarta.annotation.Nullable;
import lombok.Data;

import java.util.List;

@Data
public class PatientVisitsRequestDTO {
    Integer pageNo;
    Integer pageSize;
    @Nullable
    String firstName;
    @Nullable
    String lastName;
    @Nullable
    List<Long> doctorId;
}
