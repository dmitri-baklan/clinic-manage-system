package com.example.docvisitorregister.repository;

import com.example.docvisitorregister.domain.dao.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PatientRepository extends JpaRepository<Patient, Long>, PatientQueryRepository {
    Patient getPatientById(Long id);

    @Query("SELECT COUNT(*) FROM Patient as p WHERE (p.firstName = :firstName OR :firstName IS NULL)")
    Integer countPatientByFirstName(String firstName);

    Integer countPatientByFirstNameOrFirstNameIsNull(String firstName);
}
