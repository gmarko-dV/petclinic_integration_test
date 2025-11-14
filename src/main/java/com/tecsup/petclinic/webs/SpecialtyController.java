package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import com.tecsup.petclinic.mapper.SpecialtyMapper;
import com.tecsup.petclinic.services.SpecialtyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 
 * @author jgomezm
 *
 */
@RestController
@Slf4j
public class SpecialtyController {

	private SpecialtyService specialtyService;

	private SpecialtyMapper mapper;

	/**
	 * 
	 * @param specialtyService
	 * @param mapper
	 */
	public SpecialtyController(SpecialtyService specialtyService, SpecialtyMapper mapper) {
		this.specialtyService = specialtyService;
		this.mapper = mapper;
	}

	/**
	 * Get all specialties
	 *
	 * @return
	 */
	@GetMapping(value = "/specialties")
	public ResponseEntity<List<SpecialtyDTO>> findAllSpecialties() {

		List<Specialty> specialties = specialtyService.findAll();

		List<SpecialtyDTO> specialtiesTO = this.mapper.mapToDtoList(specialties);
		log.info("specialtiesTO: " + specialtiesTO);
		specialtiesTO.forEach(item -> log.info("SpecialtyTO >>  {} ", item));

		return ResponseEntity.ok(specialtiesTO);

	}

	/**
	 * Create specialty
	 *
	 * @param specialtyTO
	 * @return
	 */
	@PostMapping(value = "/specialties")
	@ResponseStatus(HttpStatus.CREATED)
	ResponseEntity<SpecialtyDTO> create(@RequestBody SpecialtyDTO specialtyTO) {

		SpecialtyDTO newSpecialtyTO = specialtyService.create(specialtyTO);

		return ResponseEntity.status(HttpStatus.CREATED).body(newSpecialtyTO);

	}

	/**
	 * Find specialty by id
	 *
	 * @param id
	 * @return
	 * @throws SpecialtyNotFoundException
	 */
	@GetMapping(value = "/specialties/{id}")
	ResponseEntity<SpecialtyDTO> findById(@PathVariable Integer id) {

		SpecialtyDTO specialtyDto = null;

		try {
			specialtyDto = specialtyService.findById(id);

		} catch (SpecialtyNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(specialtyDto);
	}

	/**
	 * Update and create specialty
	 *
	 * @param specialtyTO
	 * @param id
	 * @return
	 */
	@PutMapping(value = "/specialties/{id}")
	ResponseEntity<SpecialtyDTO> update(@RequestBody SpecialtyDTO specialtyTO, @PathVariable Integer id) {

		SpecialtyDTO updateSpecialtyDto = null;

		try {

			updateSpecialtyDto = specialtyService.findById(id);

			updateSpecialtyDto.setName(specialtyTO.getName());

			specialtyService.update(updateSpecialtyDto);

		} catch (SpecialtyNotFoundException e) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(updateSpecialtyDto);
	}

	/**
	 * Delete specialty by id
	 *
	 * @param id
	 */
	@DeleteMapping(value = "/specialties/{id}")
	ResponseEntity<String> delete(@PathVariable Integer id) {

		try {
			specialtyService.delete(id);
			return ResponseEntity.ok(" Delete ID :" + id);
		} catch (SpecialtyNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

}

