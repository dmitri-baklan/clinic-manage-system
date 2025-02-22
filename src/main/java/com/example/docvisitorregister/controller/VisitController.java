package com.example.docvisitorregister.controller;

import com.example.docvisitorregister.domain.dao.Visit;
import com.example.docvisitorregister.domain.dto.request.VisitRequestDTO;
import com.example.docvisitorregister.domain.dto.response.VisitResponseDTO;
import com.example.docvisitorregister.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.servlet.function.ServerResponse.ok;

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
