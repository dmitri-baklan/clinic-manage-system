package com.example.docvisitorregister.domain.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class PatientVisitsRequestDTO {
    Integer pageNo;
    Integer pageSize;
    String firstName;
    String lastName;
    List<Integer> doctorIds;
}
