package com.example.docvisitorregister.controller;

import com.example.docvisitorregister.domain.dao.Visit;
import com.example.docvisitorregister.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.servlet.function.ServerResponse.ok;

@RestController
@RequestMapping("/visit")
public class VisitController {
    @Autowired
    VisitService visitService;

    @PostMapping()
    public ResponseEntity<Void> addVisit(@RequestBody Visit visit) {
        return null;
    }

}
