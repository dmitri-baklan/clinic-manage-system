package com.example.docvisitorregister.service.impl;

import com.example.docvisitorregister.domain.dao.Doctor;
import com.example.docvisitorregister.domain.dao.Patient;
import com.example.docvisitorregister.domain.dao.Visit;
import com.example.docvisitorregister.domain.dto.request.VisitRequestDTO;
import com.example.docvisitorregister.domain.dto.response.VisitResponseDTO;
import com.example.docvisitorregister.exception.DoctorNotAvailableException;
import com.example.docvisitorregister.exception.TimeslotIsInvalidException;
import com.example.docvisitorregister.exception.TimeslotWithinWorkingTimeException;
import com.example.docvisitorregister.repository.VisitRepository;
import com.example.docvisitorregister.service.DoctorService;
import com.example.docvisitorregister.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class VisitServiceImpl implements VisitService {

    private static final LocalTime WORK_START = LocalTime.of(8, 0);
    private static final LocalTime WORK_END = LocalTime.of(21, 0);

    @Autowired
    VisitRepository visitRepository;

    @Autowired
    DoctorService doctorService;


    @Transactional
    public VisitResponseDTO createVisit(VisitRequestDTO visit) {
        if(isValidTimeSlot(visit.getStart(), visit.getEnd())) {
            if(visit.getTimeZoneId() != null && !visit.getTimeZoneId().isBlank()) {
                visit = convertToDoctorTimezone(visit);
            }
            if(isWorkingHours(visit.getStart(), visit.getEnd())) {
                Visit newVisit = addVisit(visit);

                return VisitResponseDTO.builder()
                        .start(newVisit.getStart())
                        .end(newVisit.getEnd())
                        .doctorId(newVisit.getDoctor().getId())
                        .patientId(newVisit.getPatient().getId())
                        .build();
            } else {
                throw new TimeslotWithinWorkingTimeException("Timeslot is not corresponding to working hours (from %s to %s).",
                        WORK_START, WORK_END);
            }
        } else {
            throw new TimeslotIsInvalidException("Timeslot is not valid. End must be after start time.");
        }
    }

    protected Visit addVisit(VisitRequestDTO visit) {
        if(isTimeSlotFree(visit.getDoctorId(), visit.getStart(), visit.getEnd())) {
            Visit savedVisit = visitRepository.save(Visit.builder()
                    .start(visit.getStart())
                    .end(visit.getEnd())
                    .patient(Patient.builder().id(visit.getPatientId()).build())
                    .doctor(Doctor.builder().id(visit.getDoctorId()).build())
                    .build());

            if(!visitRepository.hasPatientAttendedDoctor(visit.getPatientId(), visit.getDoctorId())) {
                doctorService.incrementTotalPatients(savedVisit.getDoctor());
            }

            return savedVisit;
        } else {
            throw new DoctorNotAvailableException("Doctor is not available for that time slot");
        }
    }

    private boolean isValidTimeSlot(LocalDateTime start, LocalDateTime end) {
        return start.isBefore(end);
    }

    private VisitRequestDTO convertToDoctorTimezone(VisitRequestDTO visit) {
        Doctor doctor = doctorService.findDoctorById(visit.getDoctorId());
        String patientTimezone = visit.getTimeZoneId();

        ZonedDateTime patientStart = convertToZone(visit.getStart(), patientTimezone);
        ZonedDateTime patientEnd = convertToZone(visit.getEnd(), patientTimezone);

        LocalDateTime doctorStart = patientStart.withZoneSameInstant(ZoneId.of(doctor.getTimeZone())).toLocalDateTime();
        LocalDateTime doctorEnd = patientEnd.withZoneSameInstant(ZoneId.of(doctor.getTimeZone())).toLocalDateTime();

        return VisitRequestDTO.builder()
                .start(doctorStart)
                .end(doctorEnd)
                .patientId(visit.getPatientId())
                .doctorId(visit.getDoctorId())
                .build();
    }

    private boolean isWorkingHours(LocalDateTime start, LocalDateTime end) {
        return start.toLocalTime().isAfter(WORK_START) && end.toLocalTime().isBefore(WORK_END);
    }

    @Override
    public boolean isTimeSlotFree(Long doctorId, LocalDateTime timeSlotStart, LocalDateTime timeSlotEnd) {
        return visitRepository.countOverlappingVisits(doctorId, timeSlotStart, timeSlotEnd) == 0;
    }

    private ZonedDateTime convertToZone(LocalDateTime localDateTime, String timezoneId) {
        ZoneId zoneId = ZoneId.of(timezoneId);
        return localDateTime.atZone(zoneId);
    }
}
