package com.example.docvisitorregister.repository;

import com.example.docvisitorregister.domain.dao.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Doctor getDoctorById(Long id);
    @Modifying
    @Query("UPDATE Doctor d SET d.totalPatients = :totalPatients WHERE d.id = :id")
    void updateTotalPatients(@Param("id") Long id, @Param("totalPatients") Long totalPatients);
}
