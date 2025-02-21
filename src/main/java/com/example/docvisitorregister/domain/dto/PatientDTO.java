package com.example.docvisitorregister.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class PatientDTO {
    public String firstName;
    public String lastName;
    public List<VisitDTO> lastVisits;
}
