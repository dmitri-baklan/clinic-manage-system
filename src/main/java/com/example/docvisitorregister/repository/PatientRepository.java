package com.example.docvisitorregister.repository;

import com.example.docvisitorregister.domain.dao.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long>, PatientQueryRepository {
    Patient getPatientById(Long id);
}
