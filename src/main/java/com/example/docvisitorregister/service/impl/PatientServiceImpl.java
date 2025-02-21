package com.example.docvisitorregister.service.impl;

import com.example.docvisitorregister.domain.dao.Patient;
import com.example.docvisitorregister.domain.dto.request.PatientVisitsRequestDTO;
import com.example.docvisitorregister.domain.dto.response.PatientListResponseDTO;
import com.example.docvisitorregister.service.PatientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {
    @Override
    public List<PatientListResponseDTO> getPatientsLastVisit(PatientVisitsRequestDTO patientVisitsRequestDTO) {

        return List.of();
    }
}
