package com.cg.inventoryauthservice.service;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties.Web.Client.ClientRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import com.cg.inventoryauthservice.dto.request.DoctorRequest;
import com.cg.inventoryauthservice.dto.request.PatientRequest;
import com.cg.inventoryauthservice.dto.request.SpecialitiesRequest;
import com.cg.inventoryauthservice.dto.request.UpdatePassWordPriciple;
import com.cg.inventoryauthservice.dto.request.updateDoctorRequest;
import com.cg.inventoryauthservice.dto.request.updateSpecialitiesRequest;
import com.cg.inventoryauthservice.entity.Admin;
import com.cg.inventoryauthservice.entity.AppUserRole;
import com.cg.inventoryauthservice.entity.Doctor;
import com.cg.inventoryauthservice.entity.Patient;
import com.cg.inventoryauthservice.entity.Review;
import com.cg.inventoryauthservice.entity.Specialities;
import com.cg.inventoryauthservice.exception.CustomException;
import com.cg.inventoryauthservice.repository.DepartmentRepository;
import com.cg.inventoryauthservice.repository.DoctorRepository;
import com.cg.inventoryauthservice.repository.PatientRepository;
import com.cg.inventoryauthservice.repository.ReviewRepository;
import com.cg.inventoryauthservice.repository.SpecialitiesRepository;
import com.cg.inventoryauthservice.repository.UserRepository;
import com.cg.inventoryauthservice.service.utils.ListResult;
import com.cg.inventoryauthservice.service.utils.SpecificationFilter;

import static com.cg.inventoryauthservice.service.utils.PageableUtils.pageable;


@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationManager authenticationManager;
	private final DoctorRepository doctorRepository;
	private final SpecialitiesRepository specialitiesRepository;
	private final DepartmentRepository departmentRepository;
	private final SpecificationFilter specificationFilter;
	private final ReviewRepository reviewRepository;
	private final PatientRepository patientRepository;
	
	
	public String signin(String username, String password) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getAppUserRoles());
		} catch (AuthenticationException e) {
			throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@Transactional
	public String signup(Admin appUser) {
		if (!userRepository.existsByUsername(appUser.getUsername())) {
			appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
			List<AppUserRole> a = new ArrayList<AppUserRole>();
			a.add(AppUserRole.ROLE_ADMIN);
			appUser.setAppUserRoles(a);
			userRepository.save(appUser);
			return jwtTokenProvider.createToken(appUser.getUsername(), appUser.getAppUserRoles());
		} else {
			throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	public void delete(String username) {
		userRepository.deleteByUsername(username);
	}

	public Admin search(String username) {
		Admin appUser = userRepository.findByUsername(username);
		if (appUser == null) {
			throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
		}
		return appUser;
	}

	public Admin whoami(HttpServletRequest req) {
		return userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
	}

	public String refresh(String username) {
		return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getAppUserRoles());
	}

	public Admin getPrinciple(Principal principal) {
		Admin appUser = userRepository.findByUsername(principal.getName());
		return appUser;
	}

// Doctor-Admin

	@Transactional
	public Doctor createDoctor(@Valid DoctorRequest doctorRequest) {
		if (!doctorRepository.existsByUsername(doctorRequest.getUsername())) {
			Doctor doctors = new Doctor();
			doctors.setFirstName(doctorRequest.getFirstName());
			doctors.setLastName(doctorRequest.getLastName());
			doctors.setDateOfBirth(doctorRequest.getDateOfBirth());
			doctors.setEmail(doctorRequest.getEmail());
			doctors.setPhoneNumber(doctorRequest.getPhoneNumber());
			doctors.setAddress(doctorRequest.getAddress());
			doctors.setAvatar("avatar-facebook-1280x720.jpg");
			doctors.setGender(doctorRequest.getGender());
			doctors.setUsername(doctorRequest.getUsername());
			doctors.setPassword(passwordEncoder.encode(doctorRequest.getPassword()));
			departmentRepository.findById(doctorRequest.getDepartmentId())
					.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
			doctors.setDepartmentId(doctorRequest.getDepartmentId());
			specialitiesRepository.findById(doctorRequest.getSpecialityId())
					.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
			doctors.setSpecialityId(doctorRequest.getSpecialityId());
			doctors.setPrice(doctorRequest.getPrice());
			doctors.setCreated_at(new Date());
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

	@Transactional
	public Doctor updateDoctor(@Valid updateDoctorRequest updatedoctorRequest) {
		Doctor doctorUpdate = doctorRepository.findById(updatedoctorRequest.getId())
				.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
		doctorUpdate.setFirstName(updatedoctorRequest.getFirstName());
		doctorUpdate.setLastName(updatedoctorRequest.getLastName());
		doctorUpdate.setDateOfBirth(updatedoctorRequest.getDateOfBirth());
		doctorUpdate.setEmail(updatedoctorRequest.getEmail());
		doctorUpdate.setPhoneNumber(updatedoctorRequest.getPhoneNumber());
		doctorUpdate.setAddress(updatedoctorRequest.getAddress());
		doctorUpdate.setGender(updatedoctorRequest.getGender());
		doctorUpdate.setUpdate_at(new Date());
		doctorRepository.save(doctorUpdate);
		return doctorUpdate;
	}

	@Transactional
	public String deleteDoctor(Integer id) {
		if (!doctorRepository.existsById(id)) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay id");
		}
		doctorRepository.deleteById(id);
		return "Đã xóa thành công";
	}


	public ListResult<Doctor> filterDoctor(String name, String gender, int size,
			@Min(value = 1, message = "k tim thay trang") int page, boolean desc, String orderBy) {
		Specification<Doctor> documentDoctorSpecification = specificationFilter.doctorSpecification(name, gender);
		return ListResult
				.from(doctorRepository.findAll(documentDoctorSpecification, pageable(page, size, orderBy, desc)));
	}

	public Doctor getDetailDoctor(Integer id) {
		return doctorRepository.findById(id)
				.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
	}

	public String updatePassword(@Valid UpdatePassWordPriciple updatePassWordPriciple) {
		Doctor doctorUpdate = doctorRepository.findById(updatePassWordPriciple.getId())
				.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
		;

		if (passwordEncoder.matches(updatePassWordPriciple.getOldPassword(), doctorUpdate.getPassword())) {
			if (updatePassWordPriciple.getNewPassword().equals(updatePassWordPriciple.getRepeatNewPassword())) {
				doctorUpdate.setPassword(passwordEncoder.encode(updatePassWordPriciple.getNewPassword()));
				doctorRepository.save(doctorUpdate);
				return " Mật khẩu của bạn đã được thay đổi";
			} else {
				throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Vui lòng nhập lại mật khẩu");
			}
		} else {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND,
					"Mật khẩu hiện tại của bạn không đúng, Vui lòng nhập lại");
		}
	}

// Doctor-Admin

// Patient-Admin

//Patient-Admin

    @Value("${image.location}")
    private String location;

	public List<Specialities> getAllSpecialities() {
		return specialitiesRepository.findAll();
	}

	@Transactional
	public Specialities createSpecialities(SpecialitiesRequest specialitiesRequest, MultipartFile file) {
		Specialities specialities = new Specialities();
		specialities.setNameSpecialities(specialitiesRequest.getNameSpecialities());

		String fileName = file.getOriginalFilename();
		try {
			FileCopyUtils.copy(file.getBytes(), new File(this.location + fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		specialities.setImage(fileName);
		specialitiesRepository.save(specialities);
		return specialities;
	}

	public Specialities getDetailSpecialities(Integer id) {
		return specialitiesRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
	}

	@Transactional
	public Specialities updateSpecialities(updateSpecialitiesRequest updatespecialitiesRequest, MultipartFile file)
			throws IOException {
		Specialities specialitiesUpdate = specialitiesRepository.findById(updatespecialitiesRequest.getId())
				.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
		specialitiesUpdate.setNameSpecialities(updatespecialitiesRequest.getNameSpecialities());
		String fileName = file.getOriginalFilename();
		try {
			FileCopyUtils.copy(file.getBytes(), new File(this.location + fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		specialitiesUpdate.setImage(fileName);
		specialitiesRepository.save(specialitiesUpdate);
		return specialitiesUpdate;
	}

	public void deleteSpecialities(Integer id) {
		if (!specialitiesRepository.existsById(id)) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay id");
		}
		specialitiesRepository.deleteById(id);
	}

	public ListResult<Specialities> filterSpecialities(int size, int page, boolean desc, String orderBy) {
		Specification<Specialities> documentPartnerSpecification = specificationFilter.create();
		return ListResult.from(
				specialitiesRepository.findAll(documentPartnerSpecification, pageable(page, size, orderBy, desc)));
	}
	
	
	//review admin
	
	@Transactional
	public String deleteReview(Integer id) {
		Review review = reviewRepository.findById(id)
				.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
		reviewRepository.delete(review);
		return "Đã xóa thành công";
	}

	
	public ListResult<Review> filterReview(int size, int page,
			boolean desc, String orderBy) {
		Specification<Review> documentPatientSpecification = specificationFilter.reviewSpecification();
		return ListResult
				.from(reviewRepository.findAll(documentPatientSpecification, pageable(page, size, orderBy, desc)));
	}

	public Review getDetailReview(Integer id) {
		return reviewRepository.findById(id)
				.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
	}

	public Patient getDetailPatient(Integer id) {
		return patientRepository.findById(id)
				.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
	}

	public ListResult<Patient> filterPatient(int size,
			@Min(value = 1, message = "k tim thay trang") int page, boolean desc, String orderBy) {
		Specification<Patient> documentPatientSpecification = specificationFilter.patientSpecification();
		return ListResult
				.from(patientRepository.findAll(documentPatientSpecification, pageable(page, size, orderBy, desc)));
	}

//	public Object createPatient(PatientRequest patientRequest) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public Object updatePatient(@Valid ClientRequest updatePatientRequest) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
