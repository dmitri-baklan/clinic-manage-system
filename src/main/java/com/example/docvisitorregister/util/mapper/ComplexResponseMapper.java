package com.example.docvisitorregister.util.mapper;

import com.example.docvisitorregister.domain.dao.Patient;
import com.example.docvisitorregister.domain.dto.DoctorDTO;
import com.example.docvisitorregister.domain.dto.PatientDTO;
import com.example.docvisitorregister.domain.dto.VisitDTO;
import com.example.docvisitorregister.domain.dto.response.PatientListResponseDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class ComplexResponseMapper {

    public PatientListResponseDTO toPatientListResponseDTO(List<Object[]> tuples) {
        PatientListResponseDTO patientListResponseDTO = new PatientListResponseDTO();
        List<PatientDTO> patientDTOList = new ArrayList<>();
        for(int i = 0; i < tuples.size();){
            long currentId = (Long) tuples.get(i)[0];
            PatientDTO patientDTO = new PatientDTO();
            patientDTO.setFirstName((String) tuples.get(i)[1]);
            patientDTO.setLastName((String) tuples.get(i)[2]);
            List<VisitDTO> visitDTOList = new ArrayList<>();
            while(i < tuples.size()  && currentId == (Long)tuples.get(i)[0]){
                DoctorDTO doctorDTO = new DoctorDTO();
                doctorDTO.setFirstName((String) tuples.get(i)[3]);
                doctorDTO.setLastName((String) tuples.get(i)[4]);
                doctorDTO.setTotalPatients((Long) tuples.get(i)[5]);
                VisitDTO visitDTO = new VisitDTO();
                visitDTO.setStart(LocalDateTime.parse(tuples.get(i)[6].toString().split("\\.")[0],
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                visitDTO.setEnd(LocalDateTime.parse(tuples.get(i)[7].toString().split("\\.")[0],
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                visitDTO.setDoctor(doctorDTO);
                visitDTOList.add(visitDTO);
                i++;
            }
            patientDTO.setLastVisits(visitDTOList);
            patientDTOList.add(patientDTO);
        }
        patientListResponseDTO.setData(patientDTOList);
        patientListResponseDTO.setCount(patientDTOList.size());
        return patientListResponseDTO;
    }

}
