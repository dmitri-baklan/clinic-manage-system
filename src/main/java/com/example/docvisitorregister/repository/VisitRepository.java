package com.example.docvisitorregister.repository;

import com.example.docvisitorregister.domain.dao.Doctor;
import com.example.docvisitorregister.domain.dao.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository  extends JpaRepository<Visit, Long> {
}
