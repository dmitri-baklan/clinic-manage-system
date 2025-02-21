package com.example.docvisitorregister.util.dataGeneration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataGeneratorDoctorUpdate {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    protected void updateDoctorsTotalPatients() {
        String sql = "UPDATE doctors d " +
                "SET d.total_patients = ( " +
                "  SELECT COUNT(DISTINCT v.patient_id) " +
                "  FROM visits v " +
                "  WHERE v.doctor_id = d.id " +
                ")";

        Query query = entityManager.createNativeQuery(sql);
        query.executeUpdate();
    }
}
