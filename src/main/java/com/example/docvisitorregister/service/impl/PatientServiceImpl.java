package com.example.docvisitorregister.service.impl;

import com.example.docvisitorregister.domain.dao.Patient;
import com.example.docvisitorregister.domain.dto.request.PatientVisitsRequestDTO;
import com.example.docvisitorregister.domain.dto.response.PatientListResponseDTO;
import com.example.docvisitorregister.repository.PatientRepository;
import com.example.docvisitorregister.service.PatientService;
import com.example.docvisitorregister.util.mapper.ComplexResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ComplexResponseMapper complexResponseMapper;

    @Override
    public PatientListResponseDTO getPatientsLastVisit(PatientVisitsRequestDTO patientVisitsRequestDTO) {

        List<Object[]> result = patientRepository.getPatientsLastVisit(patientVisitsRequestDTO.getPageSize(),
                patientVisitsRequestDTO.getPageNo(),
                patientVisitsRequestDTO.getFirstName(),
                patientVisitsRequestDTO.getLastName(),
                patientVisitsRequestDTO.getDoctorIds());
        PatientListResponseDTO patientListResponseDTO = complexResponseMapper.toPatientListResponseDTO(result);
        return patientListResponseDTO;
    }
}
