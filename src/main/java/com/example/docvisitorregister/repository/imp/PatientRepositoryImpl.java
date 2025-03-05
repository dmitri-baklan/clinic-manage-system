package com.example.docvisitorregister.repository.imp;

import com.example.docvisitorregister.repository.PatientQueryRepository;
import com.example.docvisitorregister.util.SQLQueryLoader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PatientRepositoryImpl implements PatientQueryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object[]> getPatientsLastVisit(int pageSize, int pageNo, String firstName, List<Long> doctorIds) {
        String queryToProcess = SQLQueryLoader.getPatientVisitQuery();
        queryToProcess = doctorIdsConditionCheck(queryToProcess, doctorIds);

        Query query = entityManager.createNativeQuery(queryToProcess);
        query.setHint("org.hibernate.cacheable", true)
                .setParameter("firstName", !firstName.isBlank() ? firstName : null)
                .setParameter("pageSize", pageSize)
                .setParameter("pageNo", (pageNo - 1) * pageSize);
        if (doctorIds != null && !doctorIds.isEmpty()) {
            query.setParameter("doctorIds", doctorIds);
        }
        return query.getResultList();
    }

    private String doctorIdsConditionCheck(String queryToProcess, List<Long> doctorIds) {
        return doctorIds == null || doctorIds.isEmpty() ?
                queryToProcess.replace("AND doctor_id IN (:doctorIds)", "") : queryToProcess;
    }
}
