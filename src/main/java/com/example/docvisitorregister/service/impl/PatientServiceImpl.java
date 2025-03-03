package com.example.docvisitorregister.service.impl;

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

        List<Object[]> result = patientRepository.getPatientsLastVisit(patientVisitsRequestDTO.getSize(),
                patientVisitsRequestDTO.getPage(),
                patientVisitsRequestDTO.getSearch(),
                patientVisitsRequestDTO.getDoctorId());
        PatientListResponseDTO patientListResponseDTO = complexResponseMapper.toPatientListResponseDTO(result);

        String searchName = patientVisitsRequestDTO.getSearch().isBlank()? null : patientVisitsRequestDTO.getSearch();
        Integer countOfPatients = patientRepository.countPatientByFirstName(searchName);
        patientListResponseDTO.setCount(countOfPatients);

        return patientListResponseDTO;
    }
}
