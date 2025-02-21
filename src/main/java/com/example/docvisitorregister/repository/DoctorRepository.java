package com.example.docvisitorregister.repository;

import com.example.docvisitorregister.domain.dao.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Doctor getDoctorById(Long id);
}
