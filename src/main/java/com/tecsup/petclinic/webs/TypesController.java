package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.PetTypeDTO;
import com.tecsup.petclinic.entities.PetType;
import com.tecsup.petclinic.exceptions.PetTypeNotFoundException;
import com.tecsup.petclinic.mapper.PetTypeMapper;
import com.tecsup.petclinic.services.PetTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class TypesController {

	private PetTypeService petTypeService;
	private PetTypeMapper mapper;

	public TypesController(PetTypeService petTypeService, PetTypeMapper mapper) {
		this.petTypeService = petTypeService;
		this.mapper = mapper;
	}

	@GetMapping(value = "/types")
	public ResponseEntity<List<PetTypeDTO>> findAllTypes() {
		List<PetType> types = petTypeService.findAll();
		List<PetTypeDTO> typesTO = this.mapper.mapToDtoList(types);
		log.info("typesTO: " + typesTO);
		typesTO.forEach(item -> log.info("TypeTO >>  {} ", item));
		return ResponseEntity.ok(typesTO);
	}

	@PostMapping(value = "/types")
	@ResponseStatus(HttpStatus.CREATED)
	ResponseEntity<PetTypeDTO> create(@RequestBody PetTypeDTO typeTO) {
		PetTypeDTO newTypeTO = petTypeService.create(typeTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(newTypeTO);
	}

	@GetMapping(value = "/types/{id}")
	ResponseEntity<PetTypeDTO> findById(@PathVariable Integer id) {
		PetTypeDTO typeDto = null;
		try {
			typeDto = petTypeService.findById(id);
		} catch (PetTypeNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(typeDto);
	}

	@PutMapping(value = "/types/{id}")
	ResponseEntity<PetTypeDTO> update(@RequestBody PetTypeDTO typeTO, @PathVariable Integer id) {
		PetTypeDTO updateTypeDto = null;
		try {
			updateTypeDto = petTypeService.findById(id);
			updateTypeDto.setName(typeTO.getName());
			petTypeService.update(updateTypeDto);
		} catch (PetTypeNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(updateTypeDto);
	}

	@DeleteMapping(value = "/types/{id}")
	ResponseEntity<String> delete(@PathVariable Integer id) {
		try {
			petTypeService.delete(id);
			return ResponseEntity.ok(" Delete ID :" + id);
		} catch (PetTypeNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

}

