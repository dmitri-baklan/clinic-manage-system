package com.example.docvisitorregister.repository;

import java.util.List;

public interface PatientQueryRepository {

    List<Object[]> getPatientsLastVisit(int pageSize, int pageNo, String firstName, String lastName, List<Long> doctorIds);
}
