/**
 * @author Gagandeep Singh
 * @email singh.gagandeep3911@gmail.com
 * @create date 2020-10-27 23:38:13
 * @modify date 2020-10-27 23:38:13
 * @desc Product Order Service - Entrypoint
 */
package com.cg.inventorydoctorderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;







































































































import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.cg.inventorydoctorderservice.repository.DoctorRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = DoctorRepository.class)
public class InventoryDoctorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryDoctorServiceApplication.class, args);
	}

}
