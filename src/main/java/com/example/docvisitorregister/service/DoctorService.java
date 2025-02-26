package com.example.docvisitorregister.service;

import com.example.docvisitorregister.domain.dao.Doctor;

public interface DoctorService {
    void incrementTotalPatients(Doctor doctor);

    Doctor findDoctorById(Long id);
}
