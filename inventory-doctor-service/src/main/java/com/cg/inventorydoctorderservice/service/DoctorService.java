package com.cg.inventorydoctorderservice.service;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import com.cg.inventorydoctorderservice.dto.DoctorRequest;
import com.cg.inventorydoctorderservice.dto.updateDoctorRequest;
import com.cg.inventorydoctorderservice.entity.Doctor;
import com.cg.inventorydoctorderservice.repository.DepartmentRepository;
import com.cg.inventorydoctorderservice.repository.DoctorRepository;
import com.cg.inventorydoctorderservice.service.utils.ListResult;
import com.cg.inventorydoctorderservice.service.utils.SpecificationFilter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static  com.cg.inventorydoctorderservice.service.utils.PageableUtils.pageable;
@Service
@AllArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;
    
    private final DepartmentRepository departmentRepository;
    
    private final SpecificationFilter specificationFilter;

    public List<Doctor> getAllDoctor() {
        return doctorRepository.findAll();
    }

    @Transactional
    public Doctor createDoctor(DoctorRequest doctorRequest) {
        Doctor doctors = new Doctor();
        doctors.setFirstName(doctorRequest.getFirstName());
        doctors.setLastName(doctorRequest.getLastName());
        doctors.setDateOfBirth(doctorRequest.getDateOfBirth());
        doctors.setEmail(doctorRequest.getEmail());
        doctors.setPhoneNumber(doctorRequest.getPhoneNumber());
        doctors.setAddress(doctorRequest.getAddress());
        doctors.setAvatar(doctorRequest.getAvatar());
        doctors.setBiography(doctorRequest.getBiography());
        departmentRepository.findById(doctorRequest.getDepartmentId()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));       
        doctors.setDepartmentId(doctorRequest.getDepartmentId());
        doctors.setAvatar(doctorRequest.getAvatar());
        doctors.setServiceName(doctorRequest.getServiceName());
        doctors.setSpecialization(doctorRequest.getSpecialization());
        doctors.setBooking_time(doctorRequest.getBooking_time());
        doctors.setCreated_at(asDate(LocalDate.now()));
        doctors.setRole(doctorRequest.getRole());
        doctors.setStatus(doctorRequest.getStatus());
        doctorRepository.save(doctors);
        return doctors;
    }

    public Doctor getDetailDoctor(Integer id) {
        return doctorRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
    }

    @Transactional
    public Doctor update(updateDoctorRequest updatedoctorRequest) {
        Doctor doctorUpdate = doctorRepository.findById(updatedoctorRequest.getId()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
        doctorUpdate.setFirstName(updatedoctorRequest.getFirstName());
        doctorUpdate.setLastName(updatedoctorRequest.getLastName());
        doctorUpdate.setDateOfBirth(updatedoctorRequest.getDateOfBirth());
        doctorUpdate.setEmail(updatedoctorRequest.getEmail());
        doctorUpdate.setPhoneNumber(updatedoctorRequest.getPhoneNumber());
        doctorUpdate.setAddress(updatedoctorRequest.getAddress());
        doctorUpdate.setAvatar(updatedoctorRequest.getAvatar());
        doctorUpdate.setBiography(updatedoctorRequest.getBiography());
        doctorUpdate.setDepartmentId(updatedoctorRequest.getDepartmentId());
        doctorUpdate.setAvatar(updatedoctorRequest.getAvatar());
        doctorUpdate.setServiceName(updatedoctorRequest.getServiceName());
        doctorUpdate.setSpecialization(updatedoctorRequest.getSpecialization());
        doctorUpdate.setUpdate_at(asDate(LocalDate.now()));
        doctorUpdate.setRole(updatedoctorRequest.getRole());
        doctorUpdate.setStatus(updatedoctorRequest.getStatus());
        doctorRepository.save(doctorUpdate);
        return doctorUpdate;
    }

    @Transactional
    public Doctor updateBookingTime(com.cg.inventorydoctorderservice.dto.updateBookingTime updatebookingTime) {
        Doctor doctorUpdate = doctorRepository.findById(updatebookingTime.getId()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
        doctorUpdate.setBooking_time(updatebookingTime.getBooking_time());
        doctorRepository.save(doctorUpdate);
        return doctorUpdate;
    }
    
    
    public void delete(Integer id) {
        if (!doctorRepository.existsById(id)) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay id");
        }
        doctorRepository.deleteById(id);
    }

    public ListResult<Doctor> filter(String name, String gender, int size, int page, boolean desc, String orderBy) {
        Specification<Doctor> documentDoctorSpecification = specificationFilter.doctorSpecification(name,gender);
        return ListResult.from(doctorRepository.findAll(documentDoctorSpecification, pageable(page, size, orderBy, desc)));
    }
    
    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
      }
}
