package com.example.docvisitorregister.service;

import com.example.docvisitorregister.domain.dao.Patient;
import com.example.docvisitorregister.domain.dto.request.PatientVisitsRequestDTO;
import com.example.docvisitorregister.domain.dto.response.PatientListResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PatientService {
    PatientListResponseDTO getPatientsLastVisit(PatientVisitsRequestDTO patientVisitsRequest);
}
