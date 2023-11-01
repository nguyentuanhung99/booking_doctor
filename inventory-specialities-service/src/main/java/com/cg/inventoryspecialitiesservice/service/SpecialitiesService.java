package com.cg.inventoryspecialitiesservice.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import com.cg.inventoryspecialitiesservice.dto.SpecialitiesRequest;
import com.cg.inventoryspecialitiesservice.dto.updateSpecialitiesRequest;
import com.cg.inventoryspecialitiesservice.entity.Specialities;
import com.cg.inventoryspecialitiesservice.repository.SpecialitiesRepository;
import com.cg.inventoryspecialitiesservice.service.utils.FileUtils;
import com.cg.inventoryspecialitiesservice.service.utils.ListResult;
import com.cg.inventoryspecialitiesservice.service.utils.SpecificationFilter;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.cg.inventoryspecialitiesservice.service.utils.PageableUtils.pageable;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SpecialitiesService {

	private final SpecialitiesRepository specialitiesRepository;
	private final SpecificationFilter specificationFilter;

//    private final HttpServletRequest request;
//    
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
		return specialitiesRepository.findById(id)
				.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
	}

	@Transactional
	public Specialities update(updateSpecialitiesRequest updatespecialitiesRequest, MultipartFile file)
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

	public void delete(Integer id) {
		if (!specialitiesRepository.existsById(id)) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay id");
		}
		specialitiesRepository.deleteById(id);
	}

	public ListResult<Specialities> filter(int size, int page, boolean desc, String orderBy) {
		Specification<Specialities> documentPartnerSpecification = specificationFilter.create();
		return ListResult.from(
				specialitiesRepository.findAll(documentPartnerSpecification, pageable(page, size, orderBy, desc)));
	}

	public static Date asDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}
}
