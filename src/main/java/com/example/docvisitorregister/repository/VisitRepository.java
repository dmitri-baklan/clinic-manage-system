package com.example.docvisitorregister.repository;

import com.example.docvisitorregister.domain.dao.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface VisitRepository  extends JpaRepository<Visit, Long> {
    @Query("SELECT COUNT(v) FROM Visit v " +
            "WHERE v.doctor.id = :doctorId " +
            "AND (:start < v.end AND :end > v.start)")
    Long countOverlappingVisits(@Param("doctorId") Long doctorId,
                                @Param("start") LocalDateTime start,
                                @Param("end") LocalDateTime end);

    @Query("SELECT COUNT(v) > 0 FROM Visit v WHERE v.patient.id = :patientId AND v.doctor.id = :doctorId")
    boolean hasPatientAttendedDoctor(@Param("patientId") Long patientId, @Param("doctorId") Long doctorId);
}
