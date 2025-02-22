package com.example.docvisitorregister.service.impl;

import com.example.docvisitorregister.domain.dao.Doctor;
import com.example.docvisitorregister.domain.dao.Patient;
import com.example.docvisitorregister.domain.dao.Visit;
import com.example.docvisitorregister.domain.dto.request.VisitRequestDTO;
import com.example.docvisitorregister.domain.dto.response.VisitResponseDTO;
import com.example.docvisitorregister.exception.DoctorNotAvailableException;
import com.example.docvisitorregister.exception.TimeslotWithinWorkingTimeException;
import com.example.docvisitorregister.repository.VisitRepository;
import com.example.docvisitorregister.service.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VisitServiceImplTest {

    @Mock
    private VisitRepository visitRepository;

    @Mock
    private DoctorService doctorService;

    @InjectMocks
    private VisitServiceImpl visitService;

    private VisitRequestDTO visitRequestDTO;

    private static final LocalTime WORK_START = LocalTime.of(8, 0);
    private static final LocalTime WORK_END = LocalTime.of(21, 0);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        visitRequestDTO = VisitRequestDTO.builder()
                .start(LocalDateTime.of(2025, 2, 22, 10, 0))
                .end(LocalDateTime.of(2025, 2, 22, 11, 0))
                .doctorId(1L)
                .patientId(1L)
                .build();
    }

    @Test
    void testCreateVisit_Success() {
        Doctor doctor = new Doctor();
        doctor.setId(1L);

        Patient patient = new Patient();
        patient.setId(1L);

        Visit mockVisit = new Visit();
        mockVisit.setStart(visitRequestDTO.getStart());
        mockVisit.setEnd(visitRequestDTO.getEnd());
        mockVisit.setDoctor(doctor);
        mockVisit.setPatient(patient);

        when(visitRepository.countOverlappingVisits(anyLong(), any(), any())).thenReturn(0L);
        when(visitRepository.save(any(Visit.class))).thenReturn(mockVisit);
        when(visitRepository.hasPatientAttendedDoctor(anyLong(), anyLong())).thenReturn(false);

        VisitResponseDTO response = visitService.createVisit(visitRequestDTO);

        assertNotNull(response);
        assertEquals(visitRequestDTO.getStart(), response.getStart());
        assertEquals(visitRequestDTO.getEnd(), response.getEnd());
        assertEquals(doctor.getId(), response.getDoctorId());
        assertEquals(patient.getId(), response.getPatientId());

        verify(doctorService, times(1)).incrementTotalPatients(any(Doctor.class));
    }

    @Test
    void testCreateVisit_TimeslotNotInWorkingHours() {
        
        visitRequestDTO.setStart(LocalDateTime.of(2025, 2, 22, 7, 30)); // Before working hours
        assertThrows(TimeslotWithinWorkingTimeException.class, () -> visitService.createVisit(visitRequestDTO));
    }

    @Test
    void testCreateVisit_DoctorNotAvailable() {
        
        when(visitRepository.countOverlappingVisits(anyLong(), any(), any())).thenReturn(1L); // Doctor is already booked

         
        DoctorNotAvailableException exception = assertThrows(
                DoctorNotAvailableException.class,
                () -> visitService.createVisit(visitRequestDTO)
        );
        assertEquals("Doctor is not available for that time slot", exception.getMessage());
    }

    @Test
    void testIsWorkingHours_ValidTime() throws Exception {
        
        LocalDateTime start = LocalDateTime.of(2025, 2, 22, 9, 0); // Within working hours
        LocalDateTime end = LocalDateTime.of(2025, 2, 22, 10, 0);

        Method method = VisitServiceImpl.class.getDeclaredMethod("isWorkingHours", LocalDateTime.class, LocalDateTime.class);
        method.setAccessible(true);

        boolean result = (boolean) method.invoke(visitService, start, end);

        
        assertTrue(result);
    }

    @Test
    void testIsWorkingHours_InvalidTime() throws Exception {
        
        LocalDateTime start = LocalDateTime.of(2025, 2, 22, 7, 0); // Before working hours
        LocalDateTime end = LocalDateTime.of(2025, 2, 22, 9, 0);


        Method method = VisitServiceImpl.class.getDeclaredMethod("isWorkingHours", LocalDateTime.class, LocalDateTime.class);
        method.setAccessible(true);

        boolean result = (boolean) method.invoke(visitService, start, end);

        
        assertFalse(result);
    }
}
