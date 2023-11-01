package com.cg.inventorydoctorderservice.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import com.cg.inventorydoctorderservice.config.FileUtils;
import com.cg.inventorydoctorderservice.dto.DoctorRequest;
import com.cg.inventorydoctorderservice.dto.UpdateDoctorRequestPriciple;
import com.cg.inventorydoctorderservice.dto.UpdatePassWordPriciple;
import com.cg.inventorydoctorderservice.dto.updateDoctorRequest;
import com.cg.inventorydoctorderservice.entity.AppUserRole;
import com.cg.inventorydoctorderservice.entity.Awards;
import com.cg.inventorydoctorderservice.entity.Doctor;
import com.cg.inventorydoctorderservice.entity.Education;
import com.cg.inventorydoctorderservice.entity.Experience;
import com.cg.inventorydoctorderservice.exception.CustomException;
import com.cg.inventorydoctorderservice.repository.AwardsRepository;
import com.cg.inventorydoctorderservice.repository.DepartmentRepository;
import com.cg.inventorydoctorderservice.repository.DoctorRepository;
import com.cg.inventorydoctorderservice.repository.EducationRepository;
import com.cg.inventorydoctorderservice.repository.ExperienceRepository;
import com.cg.inventorydoctorderservice.repository.SpecialitiesRepository;
import com.cg.inventorydoctorderservice.service.utils.ListResult;
import com.cg.inventorydoctorderservice.service.utils.SpecificationFilter;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import static  com.cg.inventorydoctorderservice.service.utils.PageableUtils.pageable;
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DoctorService {
    private final DoctorRepository doctorRepository;
    
    private final SpecialitiesRepository specialitiesRepository;
    
    private final HttpServletRequest request;
    
    private final DepartmentRepository departmentRepository;
    
    private final EducationRepository educationRepository;
    
    private final ExperienceRepository experienceRepository;
    
    private final AwardsRepository awardsRepository;
    
    private final SpecificationFilter specificationFilter;

    private final PasswordEncoder passwordEncoder;
    
    private final JwtTokenProvider jwtTokenProvider;
    
    private final AuthenticationManager authenticationManager;
    
    public List<Doctor> getAllDoctor() {
        return doctorRepository.findAll();
    }

    @Value("${image.location}")
    private String location;

	@Transactional
	public Doctor createDoctor(DoctorRequest doctorRequest) {

		if (!doctorRepository.existsByUsername(doctorRequest.getUsername())) {
			Doctor doctors = new Doctor();
			doctors.setFirstName(doctorRequest.getFirstName());
			doctors.setLastName(doctorRequest.getLastName());
			doctors.setDateOfBirth(doctorRequest.getDateOfBirth());
			doctors.setEmail(doctorRequest.getEmail());
			doctors.setPhoneNumber(doctorRequest.getPhoneNumber());
			doctors.setAddress(doctorRequest.getAddress());
			doctors.setAvatar(doctorRequest.getAvatar());
			doctors.setGender(doctorRequest.getGender());
			doctors.setUsername(doctorRequest.getUsername());
			doctors.setPassword(passwordEncoder.encode(doctorRequest.getPassword()));
			departmentRepository.findById(doctorRequest.getDepartmentId())
					.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
			doctors.setDepartmentId(doctorRequest.getDepartmentId());
			
			specialitiesRepository.findById(doctorRequest.getSpecialityId()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
			doctors.setSpecialityId(doctorRequest.getSpecialityId());
			doctors.setServiceName(doctorRequest.getServiceName());
			doctors.setSpecialization(doctorRequest.getSpecialization());
			doctors.setCreated_at(asDate(LocalDate.now()));
			doctors.setStatus(true);
			List<AppUserRole> a = new ArrayList<AppUserRole>();
			a.add(AppUserRole.ROLE_DOCTOR);
			doctors.setAppUserRoles(a);
			doctorRepository.save(doctors);
			return doctors;
		} else {
			throw new HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY, " UserName is already ");
		}

	}

    public Doctor getDetailDoctor(Integer id) {
        return doctorRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
    }

    @Transactional
    public Doctor update(updateDoctorRequest updatedoctorRequest) {
        Doctor doctorUpdate = doctorRepository.findById(updatedoctorRequest.getId()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
        doctorUpdate.setFirstName(updatedoctorRequest.getFirstName());
        doctorUpdate.setLastName(updatedoctorRequest.getLastName());
        doctorUpdate.setDescription(updatedoctorRequest.getDescription());
        doctorUpdate.setDateOfBirth(updatedoctorRequest.getDateOfBirth());
        doctorUpdate.setEmail(updatedoctorRequest.getEmail());
        doctorUpdate.setPhoneNumber(updatedoctorRequest.getPhoneNumber());
        doctorUpdate.setAddress(updatedoctorRequest.getAddress());
        doctorUpdate.setAvatar(updatedoctorRequest.getAvatar());
        departmentRepository.findById(updatedoctorRequest.getDepartmentId()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
        doctorUpdate.setDepartmentId(updatedoctorRequest.getDepartmentId());
        
		specialitiesRepository.findById(updatedoctorRequest.getSpecialityId()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
		doctorUpdate.setSpecialityId(updatedoctorRequest.getSpecialityId());

        doctorUpdate.setAvatar(updatedoctorRequest.getAvatar());
        doctorUpdate.setServiceName(updatedoctorRequest.getServiceName());
        doctorUpdate.setSpecialization(updatedoctorRequest.getSpecialization());
        doctorUpdate.setUpdate_at(asDate(LocalDate.now()));
        doctorRepository.save(doctorUpdate);
        return doctorUpdate;
    }

//    @Transactional
//    public Doctor updateBookingTime(com.cg.inventorydoctorderservice.dto.updateBookingTime updatebookingTime) {
//        Doctor doctorUpdate = doctorRepository.findById(updatebookingTime.getId()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
//        doctorUpdate.setBooking_time(updatebookingTime.getBooking_time());
//        doctorRepository.save(doctorUpdate);
//        return doctorUpdate;
//    }
    
    
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

	public Doctor getPrinciple(Principal principal) {
		Doctor appUser = doctorRepository.findByUsername(principal.getName());
		return appUser;
	}
	
	
	public String signin(String username, String password) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			return jwtTokenProvider.createToken(username, doctorRepository.findByUsername(username).getAppUserRoles());
		} catch (AuthenticationException e) {
			throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	public Doctor updatePrinciple(Principal principal, UpdateDoctorRequestPriciple updateDoctorRequestPriciple) throws IOException {
		Doctor doctorUpdate = doctorRepository.findByUsername(principal.getName());
        doctorUpdate.setFirstName(updateDoctorRequestPriciple.getFirstName());
        doctorUpdate.setLastName(updateDoctorRequestPriciple.getLastName());
        doctorUpdate.setDescription(updateDoctorRequestPriciple.getDescription());
        doctorUpdate.setDateOfBirth(updateDoctorRequestPriciple.getDateOfBirth());
        doctorUpdate.setEmail(updateDoctorRequestPriciple.getEmail());
        doctorUpdate.setPhoneNumber(updateDoctorRequestPriciple.getPhoneNumber());
        doctorUpdate.setAddress(updateDoctorRequestPriciple.getAddress());
        doctorUpdate.setAvatar(updateDoctorRequestPriciple.getAvatar());
        doctorUpdate.setServiceName(updateDoctorRequestPriciple.getServiceName());
        doctorUpdate.setSpecialization(updateDoctorRequestPriciple.getSpecialization());
        doctorUpdate.setPrice(updateDoctorRequestPriciple.getPrice());
		updateEducation(updateDoctorRequestPriciple, doctorUpdate);
		updateExperience(updateDoctorRequestPriciple, doctorUpdate);
		updateAwards(updateDoctorRequestPriciple, doctorUpdate);
        doctorUpdate.setUpdate_at(asDate(LocalDate.now()));
        
        departmentRepository.findById(updateDoctorRequestPriciple.getDepartmentId()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
        doctorUpdate.setDepartmentId(updateDoctorRequestPriciple.getDepartmentId());
        
		specialitiesRepository.findById(updateDoctorRequestPriciple.getSpecialityId()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
		doctorUpdate.setSpecialityId(updateDoctorRequestPriciple.getSpecialityId());
//        String fileName = file.getOriginalFilename();
//        try {
//            FileCopyUtils.copy(file.getBytes(), new File(this.location + fileName));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        doctorUpdate.setAvatar(fileName);
////        doctorUpdate.setAvatar(FileUtils.saveAvatar(file, this.location, request));
        doctorRepository.save(doctorUpdate);
		return doctorUpdate;
	}
	 
	private void updateAwards(UpdateDoctorRequestPriciple updateDoctorRequestPriciple, Doctor doctorUpdate) {
		List<Awards> awardsOlds = awardsRepository.findAllByIdDoctor(doctorUpdate.getId());
			doctorUpdate.setAwards(null);
			awardsRepository.deleteAll(awardsOlds);
		List<Awards> awardsNews = updateDoctorRequestPriciple.getAwards();

		awardsNews.forEach(awardsNew -> {
			Awards awards = new Awards();
			awards.setAwards(awardsNew.getAwards());
			awards.setYear(awardsNew.getYear());
			awards.setIdDoctor(doctorUpdate.getId());
			awardsRepository.save(awards);
		});

		List<Awards> awardsAdd = awardsRepository.findAllByIdDoctor(doctorUpdate.getId());
		List<Integer> ids = awardsAdd.stream().map(Awards::getId).collect(Collectors.toList());
		doctorUpdate.setAwards(ids);
		
	}

	private void updateExperience(UpdateDoctorRequestPriciple updateDoctorRequestPriciple, Doctor doctorUpdate) {
		List<Experience> experienceOlds = experienceRepository.findAllByIdDoctor(doctorUpdate.getId());
			doctorUpdate.setExperience(null);
			experienceRepository.deleteAll(experienceOlds);
		List<Experience> experienceNews = updateDoctorRequestPriciple.getExperience();

		experienceNews.forEach(experienceNew -> {
			Experience experience = new Experience();
			experience.setHospital_Name(experienceNew.getHospital_Name());
			experience.setYear_start(experienceNew.getYear_start());
			experience.setYear_end(experienceNew.getYear_end());
			experience.setDesignation(experienceNew.getDesignation());
			experience.setIdDoctor(doctorUpdate.getId());
			experienceRepository.save(experience);
		});

		List<Experience> experienceAdd = experienceRepository.findAllByIdDoctor(doctorUpdate.getId());
		List<Integer> ids = experienceAdd.stream().map(Experience::getId).collect(Collectors.toList());
		doctorUpdate.setExperience(ids);
		
	}

	private void updateEducation(UpdateDoctorRequestPriciple updateDoctorRequestPriciple, Doctor doctorUpdate) {
		List<Education> educationOlds = educationRepository.findAllByIdDoctor(doctorUpdate.getId());
			doctorUpdate.setEducation(null);
			educationRepository.deleteAll(educationOlds);
		List<Education> educationNews = updateDoctorRequestPriciple.getEducation();

		educationNews.forEach(educationNew -> {
			Education education = new Education();
			education.setDegree(educationNew.getDegree());
			education.setCollege(educationNew.getCollege());
			education.setYear_Compelete(educationNew.getYear_Compelete());
			education.setIdDoctor(doctorUpdate.getId());
			educationRepository.save(education);
		});

		List<Education> educationAdd = educationRepository.findAllByIdDoctor(doctorUpdate.getId());
		List<Integer> ids = educationAdd.stream().map(Education::getId).collect(Collectors.toList());
		doctorUpdate.setEducation(ids);
	}
	
    public Awards getDetailAwards(Integer id) {
        return awardsRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
    }
    
    public Education getDetailEducation(Integer id) {
        return educationRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
    }
    
    public Experience getDetailExperience(Integer id) {
        return experienceRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
    }
    
    public Doctor viewPicture(Principal principal,MultipartFile file) {
		Doctor doctorUpdate = doctorRepository.findByUsername(principal.getName());
        String fileName = file.getOriginalFilename();
        try {
            FileCopyUtils.copy(file.getBytes(), new File(this.location + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        doctorUpdate.setAvatar(fileName);
		return doctorUpdate;
    }
    
	public String updatePassword(Principal principal, UpdatePassWordPriciple updatePassWordPriciple) {
		Doctor doctorUpdate = doctorRepository.findByUsername(principal.getName());
		if (passwordEncoder.matches(updatePassWordPriciple.getOldPassword(),doctorUpdate.getPassword())) {
			if (updatePassWordPriciple.getNewPassword().equals(updatePassWordPriciple.getRepeatNewPassword()) ) {
				doctorUpdate.setPassword(passwordEncoder.encode(updatePassWordPriciple.getNewPassword()));
				doctorRepository.save(doctorUpdate);
				return " Mật khẩu của bạn đã được thay đổi";
			} else {
				throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Vui lòng nhập lại mật khẩu");
			}
		} else {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Mật khẩu hiện tại của bạn không đúng, Vui lòng nhập lại");
		}
	}
}
