package com.example.docvisitorregister.util.dataGeneration;


import com.example.docvisitorregister.domain.dao.Doctor;
import com.example.docvisitorregister.domain.dao.Patient;
import com.example.docvisitorregister.domain.dto.request.VisitRequestDTO;
import com.example.docvisitorregister.exception.DoctorNotAvailableException;
import com.example.docvisitorregister.exception.TimeslotWithinWorkingTimeException;
import com.example.docvisitorregister.repository.DoctorRepository;
import com.example.docvisitorregister.repository.PatientRepository;
import com.example.docvisitorregister.service.impl.VisitServiceImpl;
import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Random;

import static com.example.docvisitorregister.util.dataGeneration.FakerTimeZones.US_TIMEZONES;

@Component
@DependsOn({"doctorRepository", "patientRepository", "visitServiceImpl", "dataGeneratorDoctorUpdate"})
public class DataGenerator {

    @Value("${dataGeneration.enabled}")
    private boolean dataGenerationEnabled;

    @Value("${dataGeneration.doctor.number}")
    private int doctorNumber;

    @Value("${dataGeneration.patient.number}")
    private int patientNumber;

    @Value("${dataGeneration.visit.number}")
    private int visitNumber;

    @Value("${dataGeneration.visit.range.fromDate}")
    private String fromDate;

    @Value("${dataGeneration.visit.range.toDate}")
    private String toDate;

    private LocalDate startDate;
    private LocalDate endDate;



    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    VisitServiceImpl visitServiceImpl;

    @Autowired
    DataGeneratorDoctorUpdate dataGeneratorDoctorUpdate;

    Faker faker = new Faker(new Locale("en-En"));
    Random random = new Random();

    @PostConstruct
    public void main() {
        if(dataGenerationEnabled) {
            startDate = LocalDate.parse(fromDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            endDate = LocalDate.parse(toDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            generateDoctors();
            generatePatients();
            generateVisits();
            dataGeneratorDoctorUpdate.updateDoctorsTotalPatients();
        }
    }

    private void generateDoctors() {
        for (int i = 0; i < doctorNumber; i++) {
            doctorRepository.save(Doctor.builder()
                    .firstName(faker.name().firstName())
                    .lastName(faker.name().lastName())
                    .timeZone(US_TIMEZONES[random.nextInt(US_TIMEZONES.length)])
                    .totalPatients(0L)
                    .build());
        }
    }
    private  void generatePatients() {
        for (int i = 0; i < patientNumber; i++) {
            patientRepository.save(Patient.builder()
                    .firstName(faker.name().firstName())
                    .lastName(faker.name().lastName())
                    .build());
        }
    }
    private  void generateVisits() {
        for (int i = 0; i < visitNumber; i++) {
            Long docId = 1 + random.nextLong(doctorNumber);
            Long patientId = 1 + random.nextLong(patientNumber);
            try {
                LocalDateTime randomDateTime = getRandomDateTime(startDate, endDate);
                visitServiceImpl.createVisit(VisitRequestDTO.builder()
                        .start(randomDateTime)
                        .end(randomDateTime.plusHours(1))
                        .doctorId(docId)
                        .patientId(patientId)
                        .build());
            } catch (DoctorNotAvailableException | TimeslotWithinWorkingTimeException e) {
                i--;
            }
            catch (Exception e) {
                System.out.println(docId + " - " + patientId + " - " + e.getMessage());
                e.printStackTrace();
            }
        }
    }



    private LocalDateTime getRandomDateTime(LocalDate startDate, LocalDate endDate) {
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        long randomDays = random.nextLong(daysBetween);
        int randomHour = 8 + random.nextInt(13);
        int randomMinute = random.nextInt(60);

        LocalDate randomDate = startDate.plusDays(randomDays);
        LocalTime randomTime = LocalTime.of(randomHour, randomMinute);

        while (randomDate.getDayOfWeek() == DayOfWeek.SATURDAY || randomDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            randomDate = startDate.plusDays(random.nextLong(daysBetween));
        }

        return LocalDateTime.of(randomDate, randomTime);
    }

}
