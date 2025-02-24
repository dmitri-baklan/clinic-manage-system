package com.example.docvisitorregister.service.impl;

import com.example.docvisitorregister.domain.dto.request.PatientVisitsRequestDTO;
import com.example.docvisitorregister.domain.dto.response.PatientListResponseDTO;
import com.example.docvisitorregister.repository.PatientRepository;
import com.example.docvisitorregister.util.mapper.ComplexResponseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private ComplexResponseMapper complexResponseMapper;

    @InjectMocks
    private PatientServiceImpl patientService;

    private PatientVisitsRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new PatientVisitsRequestDTO();
        requestDTO.setPageSize(10);
        requestDTO.setPageNo(1);
        requestDTO.setFirstName("John");
        requestDTO.setLastName("Doe");
        requestDTO.setDoctorId(List.of(123L, 456L));
    }

    @Test
    void testGetPatientsLastVisit() {
        List<Object[]> mockResult = Collections.emptyList();
        when(patientRepository.getPatientsLastVisit(anyInt(), anyInt(), anyString(), anyString(), anyList()))
                .thenReturn(mockResult);
        when(complexResponseMapper.toPatientListResponseDTO(anyList())).thenReturn(new PatientListResponseDTO());

        PatientListResponseDTO response = patientService.getPatientsLastVisit(requestDTO);
        assertNotNull(response);
    }
}
