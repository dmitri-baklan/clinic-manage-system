package com.example.docvisitorregister.domain.dao;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZoneOffset;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(
        name = "doctors"
)
public class Doctor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 50)
    private String timeZone;

    @Column(nullable = false)
    private Long totalPatients;
}
