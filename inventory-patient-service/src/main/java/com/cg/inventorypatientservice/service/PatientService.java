package com.cg.inventorypatientservice.service;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import com.cg.inventorypatientservice.dto.PatientRequest;
import com.cg.inventorypatientservice.dto.updatePatientRequest;
import com.cg.inventorypatientservice.entity.Patient;
import com.cg.inventorypatientservice.repository.PatientRepository;
import com.cg.inventorypatientservice.service.utils.ListResult;
import com.cg.inventorypatientservice.service.utils.SpecificationFilter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static  com.cg.inventorypatientservice.service.utils.PageableUtils.pageable;
@Service
@AllArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    
    private final SpecificationFilter specificationFilter;
    
    private  final String User = "User";

    public List<Patient> getAllPatient() {
        return patientRepository.findAll();
    }

    @Transactional
    public Patient createPatient(PatientRequest patientRequest) {
        Patient patients = new Patient();
        patients.setFirstName(patientRequest.getFirstName());
        patients.setLastName(patientRequest.getLastName());
        patients.setDateOfBirth(patientRequest.getDateOfBirth());
        patients.setEmail(patientRequest.getEmail());
        patients.setGender(patientRequest.getGender());
        patients.setPhoneNumber(patientRequest.getPhoneNumber());
        patients.setAddress(patientRequest.getAddress());
        patients.setAvatar(patientRequest.getAvatar());
        patients.setPassword(patientRequest.getPassword());
        patients.setCreated_at(asDate(LocalDate.now()));
        patients.setRole(User);
        patients.setStatus(patientRequest.getStatus());
        patientRepository.save(patients);
        return patients;
    }

    public Patient getDetailPatient(Integer id) {
        return patientRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
    }

    @Transactional
    public Patient update(updatePatientRequest updatepatientRequest) {
        Patient patientUpdate = patientRepository.findById(updatepatientRequest.getId()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
        patientUpdate.setFirstName(updatepatientRequest.getFirstName());
        patientUpdate.setLastName(updatepatientRequest.getLastName());
        patientUpdate.setDateOfBirth(updatepatientRequest.getDateOfBirth());
        patientUpdate.setEmail(updatepatientRequest.getEmail());
        patientUpdate.setPhoneNumber(updatepatientRequest.getPhoneNumber());
        patientUpdate.setAddress(updatepatientRequest.getAddress());
        patientUpdate.setAvatar(updatepatientRequest.getAvatar());
        patientUpdate.setUpdate_at(asDate(LocalDate.now()));
        patientUpdate.setRole(User);
        patientUpdate.setStatus(updatepatientRequest.getStatus());
        patientRepository.save(patientUpdate);
        return patientUpdate;
    }
   
    
    public void delete(Integer id) {
        if (!patientRepository.existsById(id)) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay id");
        }
        patientRepository.deleteById(id);
    }

    public ListResult<Patient> filter(String name, String gender, int size, int page, boolean desc, String orderBy) {
        Specification<Patient> documentPatientSpecification = specificationFilter.patientSpecification(name,gender);
        return ListResult.from(patientRepository.findAll(documentPatientSpecification, pageable(page, size, orderBy, desc)));
    }
    
    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
      }
}
