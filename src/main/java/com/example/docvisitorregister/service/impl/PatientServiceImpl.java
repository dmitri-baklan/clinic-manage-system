package com.example.docvisitorregister.service.impl;

import com.example.docvisitorregister.domain.dto.request.PatientVisitsRequestDTO;
import com.example.docvisitorregister.domain.dto.response.PatientListResponseDTO;
import com.example.docvisitorregister.repository.PatientRepository;
import com.example.docvisitorregister.service.PatientService;
import com.example.docvisitorregister.util.mapper.ComplexResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    private static final Integer defaultPageSize = 5;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ComplexResponseMapper complexResponseMapper;


    @Override
    public PatientListResponseDTO getPatientsLastVisit(PatientVisitsRequestDTO visitRequest) {
        visitRequest = checkRequestValues(visitRequest);

        List<Object[]> result = patientRepository.getPatientsLastVisit(visitRequest.getSize(),
                visitRequest.getPage(),
                visitRequest.getSearch(),
                visitRequest.getDoctorId());

        return complexResponseMapper.toPatientListResponseDTO(result);
    }
    
    private PatientVisitsRequestDTO checkRequestValues(PatientVisitsRequestDTO visitRequest) {
        if(visitRequest.getSize() == null) {
            visitRequest.setSize(defaultPageSize);
        }
        if(visitRequest.getPage() == null) {
            visitRequest.setPage(1);
        }
        return visitRequest;
    }
}
