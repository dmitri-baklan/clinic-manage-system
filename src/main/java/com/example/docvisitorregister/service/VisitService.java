package com.example.docvisitorregister.service;

import com.example.docvisitorregister.domain.dto.request.VisitRequestDTO;
import com.example.docvisitorregister.domain.dto.response.VisitResponseDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public interface VisitService {
    VisitResponseDTO createVisit(VisitRequestDTO visit);
    boolean timeSlotFree(Long doctorId, LocalDateTime timeSlotStart, LocalDateTime timeSlotEnd);

}
