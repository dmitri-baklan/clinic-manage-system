package com.example.docvisitorregister.controller;

import com.example.docvisitorregister.domain.dto.request.VisitRequestDTO;
import com.example.docvisitorregister.domain.dto.response.VisitResponseDTO;
import com.example.docvisitorregister.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/visit")
public class VisitController {
    @Autowired
    VisitService visitService;

    @PostMapping("/addVisit")
    public ResponseEntity<VisitResponseDTO> addVisit(@ModelAttribute VisitRequestDTO visit) {
        return ResponseEntity.ok( visitService.createVisit(visit));
    }

}
