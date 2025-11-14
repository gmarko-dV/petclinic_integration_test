package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;
import com.tecsup.petclinic.mapper.VisitMapper;
import com.tecsup.petclinic.services.VisitService;
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
public class VisitController {

	private VisitService visitService;

	private VisitMapper mapper;

	/**
	 * 
	 * @param visitService
	 * @param mapper
	 */
	public VisitController(VisitService visitService, VisitMapper mapper) {
		this.visitService = visitService;
		this.mapper = mapper;
	}

	/**
	 * Get all visits
	 *
	 * @return
	 */
	@GetMapping(value = "/visits")
	public ResponseEntity<List<VisitDTO>> findAllVisits() {

		List<Visit> visits = visitService.findAll();

		List<VisitDTO> visitsTO = this.mapper.mapToDtoList(visits);
		log.info("visitsTO: " + visitsTO);
		visitsTO.forEach(item -> log.info("VisitTO >>  {} ", item));

		return ResponseEntity.ok(visitsTO);

	}

	/**
	 * Create visit
	 *
	 * @param visitTO
	 * @return
	 */
	@PostMapping(value = "/visits")
	@ResponseStatus(HttpStatus.CREATED)
	ResponseEntity<VisitDTO> create(@RequestBody VisitDTO visitTO) {

		VisitDTO newVisitTO = visitService.create(visitTO);

		return ResponseEntity.status(HttpStatus.CREATED).body(newVisitTO);

	}

	/**
	 * Find visit by id
	 *
	 * @param id
	 * @return
	 * @throws VisitNotFoundException
	 */
	@GetMapping(value = "/visits/{id}")
	ResponseEntity<VisitDTO> findById(@PathVariable Long id) {

		VisitDTO visitDto = null;

		try {
			visitDto = visitService.findById(id);

		} catch (VisitNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(visitDto);
	}

	/**
	 * Update and create visit
	 *
	 * @param visitTO
	 * @param id
	 * @return
	 */
	@PutMapping(value = "/visits/{id}")
	ResponseEntity<VisitDTO> update(@RequestBody VisitDTO visitTO, @PathVariable Long id) {

		VisitDTO updateVisitDto = null;

		try {

			updateVisitDto = visitService.findById(id);

			updateVisitDto.setVisitDate(visitTO.getVisitDate());
			updateVisitDto.setDescription(visitTO.getDescription());
			updateVisitDto.setPetId(visitTO.getPetId());

			visitService.update(updateVisitDto);

		} catch (VisitNotFoundException e) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(updateVisitDto);
	}

	/**
	 * Delete visit by id
	 *
	 * @param id
	 */
	@DeleteMapping(value = "/visits/{id}")
	ResponseEntity<String> delete(@PathVariable Long id) {

		try {
			visitService.delete(id);
			return ResponseEntity.ok(" Delete ID :" + id);
		} catch (VisitNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

}

