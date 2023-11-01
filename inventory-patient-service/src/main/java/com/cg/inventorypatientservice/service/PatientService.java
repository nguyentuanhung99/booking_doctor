package com.cg.inventorypatientservice.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
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

import com.cg.inventorypatientservice.dto.AppointmentRequest;
import com.cg.inventorypatientservice.dto.PatientRequest;
import com.cg.inventorypatientservice.dto.ReviewRequest;
import com.cg.inventorypatientservice.dto.UpdateAppointmentRequest;
import com.cg.inventorypatientservice.dto.updatePatientRequest;
import com.cg.inventorypatientservice.entity.AppUserRole;
import com.cg.inventorypatientservice.entity.Appointment;
import com.cg.inventorypatientservice.entity.Patient;
import com.cg.inventorypatientservice.entity.Review;
import com.cg.inventorypatientservice.entity.Status;
import com.cg.inventorypatientservice.entity.UpdatePassWordPriciple;
import com.cg.inventorypatientservice.exception.CustomException;
import com.cg.inventorypatientservice.repository.AppointmentRepository;
import com.cg.inventorypatientservice.repository.PatientRepository;
import com.cg.inventorypatientservice.repository.ReviewRepository;
import com.cg.inventorypatientservice.service.utils.ListResult;
import com.cg.inventorypatientservice.service.utils.SpecificationFilter;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static com.cg.inventorypatientservice.service.utils.PageableUtils.pageable;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PatientService {
	private final PatientRepository patientRepository;

	private final ReviewRepository reviewRepository;

	private final PasswordEncoder passwordEncoder;

	private final SpecificationFilter specificationFilter;

	private final JwtTokenProvider jwtTokenProvider;

	private final AuthenticationManager authenticationManager;

	private final AppointmentRepository appointmentRepository;

	@Value("${image.location}")
	private String location;

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
			patients.setCreated_at(asDate());
			List<AppUserRole> a = new ArrayList<AppUserRole>();
			a.add(AppUserRole.ROLE_CLIENT);
			patients.setAppUserRoles(a);
			patients.setStatus(true);
			patientRepository.save(patients);
			return patients;
		} else {
			throw new HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY, " UserName is already ");
		}
	}

	public Patient getDetailPatient(Integer id) {
		return patientRepository.findById(id)
				.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
	}

	public Appointment getDetailAppointment(Integer id) {
		return appointmentRepository.findById(id)
				.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
	}

	@Transactional
	public Patient update(updatePatientRequest updatepatientRequest, MultipartFile file) {
		Patient patientUpdate = patientRepository.findById(updatepatientRequest.getId())
				.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
		patientUpdate.setFirstName(updatepatientRequest.getFirstName());
		patientUpdate.setLastName(updatepatientRequest.getLastName());
		patientUpdate.setDateOfBirth(updatepatientRequest.getDateOfBirth());
		patientUpdate.setEmail(updatepatientRequest.getEmail());
		patientUpdate.setPhoneNumber(updatepatientRequest.getPhoneNumber());
		patientUpdate.setAddress(updatepatientRequest.getAddress());
		String fileName = file.getOriginalFilename();
		try {
			FileCopyUtils.copy(file.getBytes(), new File(this.location + fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		patientUpdate.setAvatar(fileName);
		patientUpdate.setUpdate_at(asDate());
		patientUpdate.setStatus(updatepatientRequest.getStatus());
		patientRepository.save(patientUpdate);
		return patientUpdate;
	}

	@Transactional
	public void delete(Integer id) {
		if (!patientRepository.existsById(id)) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay id");
		}
		patientRepository.deleteById(id);
	}

	public ListResult<Patient> filter(String name, String gender, int size, int page, boolean desc, String orderBy) {
		Specification<Patient> documentPatientSpecification = specificationFilter.patientSpecification(name, gender);
		return ListResult
				.from(patientRepository.findAll(documentPatientSpecification, pageable(page, size, orderBy, desc)));
	}

	public static Date asDate() {
		
		TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
		// Tạo đối tượng Calendar và đặt múi giờ
		Calendar calendar = Calendar.getInstance(timeZone);
		calendar.add(Calendar.HOUR, 7);
		// Lấy thời gian hiện tại theo múi giờ Việt Nam
		Date currentDateTime = calendar.getTime();
		return currentDateTime;
	}

	public Patient getPrinciple(Principal principal) {
		Patient appUser = patientRepository.findByUsername(principal.getName());
		return appUser;
	}

	@Transactional
	public Review createReview(Principal principal, ReviewRequest reviewRequest, Integer idDoctor) {
		Patient appUser = patientRepository.findByUsername(principal.getName());
		Review review = new Review();
		review.setStar(reviewRequest.getStar());
		review.setTitle(reviewRequest.getTitle());
		String input = appUser.getFirstName() + "  " + appUser.getLastName();
		review.setNamePatient(input.replaceAll("\\s+", " ").trim());
		review.setDescription(reviewRequest.getDescription());
		review.setCreated_at(new Date());
		review.setIdDoctor(idDoctor);
		review.setIdPatient(appUser.getId());
		if(reviewRequest.getIdReview() != null) {
			review.setIdReview(reviewRequest.getIdReview());
		}
		reviewRepository.save(review);
		return review;
	}
	
	@Transactional
	public Review deleteReview(Integer id) {
		Review review = reviewRepository.findById(id)
				.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
		reviewRepository.delete(review);
		return review;
	}
	
	public ListResult<Review> filterReview(Integer idPatient, Integer idDoctor , Integer idReview , int size, int page, boolean desc, String orderBy) {
		Specification<Review> documentPatientSpecification = specificationFilter.reviewSpecification(idPatient,idDoctor,idReview);
		return ListResult
				.from(reviewRepository.findAll(documentPatientSpecification, pageable(page, size, orderBy, desc)));
	}

	
	@Transactional
	public Appointment createAppointment(Principal principal, AppointmentRequest appointmentRequest, Integer idDoctor) {
		Patient appUser = patientRepository.findByUsername(principal.getName());
		Specification<Appointment> documentAppointmentSpecification = specificationFilter
				.appointmentSpecification(appUser.getId());
		List<Appointment> appointments = appointmentRepository.findAll(documentAppointmentSpecification);
		Appointment appointment = new Appointment();
		appointment.setStart(appointmentRequest.getStart());
		appointment.setEnd(appointmentRequest.getEnd());
		appointment.setText(appointmentRequest.getText());
		appointment.setParticipants(appointmentRequest.getParticipants());
		appointment.setResizeDisabled(true);
		appointment.setMoveDisabled(true);
		appointment.setStatus(Status.WAITING);
		appointment.setTrangthai(true);
		appointment.setIdDoctor(idDoctor);
		appointment.setIdPatient(appUser.getId());
		appointments.forEach(apm -> {
			if (appointment.isValidLesson(apm) == false) {
				throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Lịch hẹn của bạn bị trùng");
			}
			;
		});

		long diff = appointment.getEnd().getTime() - appointment.getStart().getTime();

		long diffMinutes = diff / (60 * 1000);

		if (diffMinutes > 120) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Lịch hẹn tối đa là 2h");
		}

		appointmentRepository.save(appointment);
		return appointment;
	}

	public ListResult<Appointment> viewMyAppointment(Principal principal, int size, int page, boolean desc,
			String orderBy) {
		Patient appUser = patientRepository.findByUsername(principal.getName());
		Specification<Appointment> documentAppointmentSpecification = specificationFilter
				.appointmentSpecification(appUser.getId());
		return ListResult.from(
				appointmentRepository.findAll(documentAppointmentSpecification, pageable(page, size, orderBy, desc)));
	}

	@Transactional
	public Appointment deleteAppointment(Integer id) {
		Appointment appointment = appointmentRepository.findById(id)
				.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
		appointment.setTrangthai(false);
		appointmentRepository.save(appointment);
		return appointment;
	}

	@Transactional
	public Appointment updateAppointment(UpdateAppointmentRequest updateAppointmentRequest) {
		Appointment appointment = appointmentRepository.findById(updateAppointmentRequest.getId())
				.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
		if (appointment.getTrangthai() == false) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Thông tin appoitment đã xóa");
		}
		appointment.setStart(updateAppointmentRequest.getStart());
		appointment.setEnd(updateAppointmentRequest.getEnd());
		appointment.setText(updateAppointmentRequest.getText());
		
		Specification<Appointment> documentAppointmentSpecification = specificationFilter
				.appointmentSpecification(appointment.getId());
		List<Appointment> appointments = appointmentRepository.findAll(documentAppointmentSpecification);
		appointments.remove(appointment);
		
		appointments.forEach(apm -> {
			if (appointment.isValidLesson(apm) == false) {
				throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Lịch hẹn của bạn bị trùng");
			}
			;
		});

		long diff = appointment.getEnd().getTime() - appointment.getStart().getTime();

		long diffMinutes = diff / (60 * 1000);

		if (diffMinutes > 120) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Lịch hẹn tối đa là 2h");
		}
		
		appointmentRepository.save(appointment);
		return appointment;
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
		if (passwordEncoder.matches(updatePassWordPriciple.getOldPassword(), patientUpdate.getPassword())) {
			if (updatePassWordPriciple.getNewPassword().equals(updatePassWordPriciple.getRepeatNewPassword())) {
				patientUpdate.setPassword(passwordEncoder.encode(updatePassWordPriciple.getNewPassword()));
				patientRepository.save(patientUpdate);
				return " Mat khau cua ban da duoc thay doi";
			} else {
				throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Vui long nhap lai mat khau");
			}
		} else {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND,
					"Mau Khau hien tai cua ban khong  dung, vui long nhap lai");
		}
	}

}
