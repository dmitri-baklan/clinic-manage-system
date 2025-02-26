package com.example.docvisitorregister.service.impl;

import com.example.docvisitorregister.domain.dao.Doctor;
import com.example.docvisitorregister.repository.DoctorRepository;
import com.example.docvisitorregister.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    DoctorRepository doctorRepository;

    @Override
    public void incrementTotalPatients(Doctor doctor) {
        doctorRepository.updateTotalPatients(doctor.getId(), doctor.getTotalPatients() + 1);
    }
}
