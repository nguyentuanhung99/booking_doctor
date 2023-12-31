package com.cg.inventorypatientservice.service;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;


import com.cg.inventorypatientservice.dto.PatientRequest;
import com.cg.inventorypatientservice.dto.updatePatientRequest;
import com.cg.inventorypatientservice.entity.AppUserRole;
import com.cg.inventorypatientservice.entity.Patient;
import com.cg.inventorypatientservice.entity.UpdatePassWordPriciple;
import com.cg.inventorypatientservice.exception.CustomException;
import com.cg.inventorypatientservice.repository.PatientRepository;
import com.cg.inventorypatientservice.service.utils.ListResult;
import com.cg.inventorypatientservice.service.utils.SpecificationFilter;

import java.security.Principal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static  com.cg.inventorypatientservice.service.utils.PageableUtils.pageable;
@Service
@AllArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    private final SpecificationFilter specificationFilter;
    
    private final JwtTokenProvider jwtTokenProvider;
    
    private final AuthenticationManager authenticationManager;
    

    public List<Patient> getAllPatient() {
        return patientRepository.findAll();
    }

	@Transactional
	public Patient createPatient(PatientRequest patientRequest) {
		if (!patientRepository.existsByUsername(patientRequest.getUsername())) {
			Patient patients = new Patient();
			patients.setUsername(patientRequest.getUsername());
			patients.setFirstName(patientRequest.getFirstName());
			patients.setLastName(patientRequest.getLastName());
			patients.setDateOfBirth(patientRequest.getDateOfBirth());
			patients.setEmail(patientRequest.getEmail());
			patients.setGender(patientRequest.getGender());
			patients.setPhoneNumber(patientRequest.getPhoneNumber());
			patients.setAddress(patientRequest.getAddress());
			patients.setAvatar(patientRequest.getAvatar());
			patients.setPassword(passwordEncoder.encode(patientRequest.getPassword()));
			patients.setCreated_at(asDate(LocalDate.now()));
			List<AppUserRole> a = new ArrayList<AppUserRole>();
			a.add(AppUserRole.ROLE_CLIENT);
			patients.setAppUserRoles(a);
			patients.setStatus(true);
			patientRepository.save(patients);
			return patients;
		} else {
			throw new  HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY," UserName is already ");
		}
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

	public Patient getPrinciple(Principal principal) {
		Patient appUser = patientRepository.findByUsername(principal.getName());
		return appUser;
	}
	
	
	public String signin(String username, String password) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			return jwtTokenProvider.createToken(username, patientRepository.findByUsername(username).getAppUserRoles());
		} catch (AuthenticationException e) {
			throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	public String updatePassword(Principal principal, UpdatePassWordPriciple updatePassWordPriciple) {
		Patient patientUpdate = patientRepository.findByUsername(principal.getName());
		if (passwordEncoder.matches(updatePassWordPriciple.getOldPassword(),patientUpdate.getPassword())) {
			if (updatePassWordPriciple.getNewPassword().equals(updatePassWordPriciple.getRepeatNewPassword()) ) {
				patientUpdate.setPassword(passwordEncoder.encode(updatePassWordPriciple.getNewPassword()));
				patientRepository.save(patientUpdate);
				return " Mat khau cua ban da duoc thay doi";
			} else {
				throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Vui long nhap lai mat khau");
			}
		} else {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Mau Khau hien tai cua ban khong  dung, vui long nhap lai");
		}
	}

}
