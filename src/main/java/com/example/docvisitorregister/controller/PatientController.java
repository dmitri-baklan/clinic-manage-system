package com.example.docvisitorregister.controller;

import com.example.docvisitorregister.domain.dao.Patient;
import com.example.docvisitorregister.domain.dto.request.PatientVisitsRequestDTO;
import com.example.docvisitorregister.domain.dto.response.PatientListResponseDTO;
import com.example.docvisitorregister.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.springframework.web.servlet.function.ServerResponse.ok;

@Controller
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @GetMapping("getPatientsLastVisit")
    public ResponseEntity<PatientListResponseDTO> getPatientsLastVisit(@ModelAttribute PatientVisitsRequestDTO patientVisitsRequest) {
        return ResponseEntity.ok(patientService.getPatientsLastVisit(patientVisitsRequest));
    }
}
