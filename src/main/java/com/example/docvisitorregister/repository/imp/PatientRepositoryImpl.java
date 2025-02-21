package com.example.docvisitorregister.repository.imp;

import com.example.docvisitorregister.domain.dao.Doctor;
import com.example.docvisitorregister.domain.dao.Patient;
import com.example.docvisitorregister.domain.dao.Visit;
import com.example.docvisitorregister.repository.PatientQueryRepository;
import com.example.docvisitorregister.util.SQLQueryLoader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PatientRepositoryImpl implements PatientQueryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object[]> getPatientsLastVisit(int pageSize, int pageNo, String firstName, String lastName, List<Long> doctorIds) {
        String queryToProcess = SQLQueryLoader.getPatientVisitQuery();
        queryToProcess = setDoctorIds(queryToProcess, doctorIds);
        Query query = entityManager.createNativeQuery(queryToProcess);
        query.setHint("org.hibernate.cacheable", true)
                .setParameter("firstName", firstName != null ? "%" + firstName + "%" : null)
                .setParameter("lastName", lastName != null ? "%" + lastName + "%" : null)
                .setParameter("pageSize", Integer.valueOf(pageSize))
                .setParameter("pageNo", Integer.valueOf((pageNo - 1) * pageSize));
        return query.getResultList();
    }

    private String setDoctorIds(String queryToProcess, List<Long> doctorIds) {
        if (doctorIds != null && !doctorIds.isEmpty()) {
            String doctorIdsString = doctorIds.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(",", "(", ")"));
            queryToProcess = queryToProcess.replace("AND (v.doctor_id IN ())", "AND (v.doctor_id IN " + doctorIdsString + ")");
        } else {
            queryToProcess = queryToProcess.replace("AND (v.doctor_id IN ())", "");
        }
        return queryToProcess;
    }
}
