package com.example.docvisitorregister.util;

import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class SQLQueryLoader {

    private static String patientVisitQuery;

    @PostConstruct
    public static void loadSqlQuery() {
        try {
            patientVisitQuery = StreamUtils.copyToString(
                    new ClassPathResource("/sql/patients_last_visits_request.sql").getInputStream(),
                    StandardCharsets.UTF_8
            );
            System.out.println("SQL query loaded successfully.");
        } catch (IOException e) {
            throw new RuntimeException("Error loading SQL query from file", e);
        }
    }

    public static String getPatientVisitQuery() {
        return patientVisitQuery;
    }
}
