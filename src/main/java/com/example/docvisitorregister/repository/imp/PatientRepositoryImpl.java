package com.example.docvisitorregister.repository.imp;

import com.example.docvisitorregister.repository.PatientQueryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PatientRepositoryImpl implements PatientQueryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object[]> getPatientsLastVisit(int pageSize, int pageNo, String firstName, String lastName, List<Integer> doctorIds) {
        String sql = "SELECT v.id AS visit_id, v.start, v.end, " +
                "p.id AS patient_id, p.first_name AS patient_first_name, p.last_name AS patient_last_name, " +
                "d.id AS doctor_id, d.first_name AS doctor_first_name, d.last_name AS doctor_last_name " +
                "FROM visits v " +
                "JOIN patients p ON v.patient_id = p.id " +
                "JOIN doctors d ON v.doctor_id = d.id " +
                "WHERE v.start = (SELECT MAX(v2.start) FROM visits v2 WHERE v2.doctor_id = v.doctor_id) " +
                "AND (p.first_name LIKE :firstName OR :firstName IS NULL) " +
                "AND (p.last_name LIKE :lastName OR :lastName IS NULL) " +
                "AND (:doctorIds IS NULL OR v.doctor_id IN :doctorIds)" +
                "ORDER BY v.start DESC"; // Adding ORDER BY for performance and logical ordering.

        return entityManager.createNativeQuery(sql)
                .setHint("org.hibernate.cacheable", true)
                .setParameter("firstName", firstName != null ? "%" + firstName + "%" : null)
                .setParameter("lastName", lastName != null ? "%" + lastName + "%" : null)
                .setParameter("doctorIds", doctorIds != null && !doctorIds.isEmpty() ? doctorIds : null)
                .setFirstResult((pageNo - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }
}
