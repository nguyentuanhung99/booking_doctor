package com.cg.inventoryproductorderservice.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import com.cg.inventoryproductorderservice.dto.MedicineRequest;
import com.cg.inventoryproductorderservice.dto.updateMedicineRequest;
import com.cg.inventoryproductorderservice.entity.Medicine;
import com.cg.inventoryproductorderservice.repository.MedicineRepository;
import com.cg.inventoryproductorderservice.service.utils.ListResult;
import com.cg.inventoryproductorderservice.service.utils.SpecificationFilter;

import lombok.AllArgsConstructor;

import static com.cg.inventoryproductorderservice.service.utils.PageableUtils.pageable;
@Service
@AllArgsConstructor

public class MedicineService {
    private final MedicineRepository medicineRepository;
    private final SpecificationFilter specificationFilter;

    public List<Medicine> getAllMedicine() {
        return medicineRepository.findAll();
    }
    
    @Transactional
    public Medicine createMedicine(MedicineRequest medicineRequest) {
        Medicine medicine = new Medicine();
        medicine.setName(medicineRequest.getName());
        medicine.setImage(medicineRequest.getImage());
        medicine.setDescription(medicineRequest.getDescription());
        medicine.setPrice(medicineRequest.getPrice());
        medicine.setCreate_at(asDate(LocalDate.now()));
        medicineRepository.save(medicine);
        return medicine;
    }

    public Medicine getDetailMedicine(Integer id) {
        return medicineRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
    }

    @Transactional
    public Medicine update(updateMedicineRequest updateMedicineRequest) {
        Medicine medicineUpdate = medicineRepository.findById(updateMedicineRequest.getId()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay"));
        medicineUpdate.setName(updateMedicineRequest.getName());
        medicineUpdate.setImage(updateMedicineRequest.getImage());
        medicineUpdate.setDescription(updateMedicineRequest.getDescription());
        medicineUpdate.setPrice(updateMedicineRequest.getPrice());
        medicineUpdate.setUpdate_at(asDate(LocalDate.now()));
        medicineRepository.save(medicineUpdate);
        return medicineUpdate;
    }

    public void delete(Integer id) {
        if (!medicineRepository.existsById(id)) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Khong tim thay id");
        }
        medicineRepository.deleteById(id);
    }

    public ListResult<Medicine> filter(String nameMedicine, int size, int page, boolean desc, String orderBy) {
        Specification<Medicine> documentPartnerSpecification = specificationFilter.create(nameMedicine);
        return ListResult.from(medicineRepository.findAll(documentPartnerSpecification, pageable(page, size, orderBy, desc)));
    }

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
      }
}

