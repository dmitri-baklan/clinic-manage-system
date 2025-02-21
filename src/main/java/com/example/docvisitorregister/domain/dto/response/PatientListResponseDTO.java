package com.example.docvisitorregister.domain.dto.response;

import com.example.docvisitorregister.domain.dto.PatientDTO;
import lombok.Data;

import java.util.List;

@Data
public class PatientListResponseDTO {
    List<PatientDTO> data;
    Integer count;
}
