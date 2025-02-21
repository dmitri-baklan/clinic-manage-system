package com.example.docvisitorregister.service;

import com.example.docvisitorregister.domain.dao.Visit;
import com.example.docvisitorregister.domain.dto.VisitDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VisitService {
    void createVisit(Visit visit);
}
