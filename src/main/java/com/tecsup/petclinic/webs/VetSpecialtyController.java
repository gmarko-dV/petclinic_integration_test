package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.repositories.SpecialtyRepository;
import com.tecsup.petclinic.repositories.VetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Slf4j
public class VetSpecialtyController {

	private VetRepository vetRepository;
	private SpecialtyRepository specialtyRepository;

	public VetSpecialtyController(VetRepository vetRepository, SpecialtyRepository specialtyRepository) {
		this.vetRepository = vetRepository;
		this.specialtyRepository = specialtyRepository;
	}

	@PostMapping(value = "/vet-specialties")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> create(@RequestBody VetSpecialtyDTO vetSpecialtyDTO) {
		Optional<?> vetOpt = vetRepository.findById(vetSpecialtyDTO.getVetId());
		Optional<?> specialtyOpt = specialtyRepository.findById(vetSpecialtyDTO.getSpecialtyId());

		if (!vetOpt.isPresent() || !specialtyOpt.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.status(HttpStatus.CREATED)
				.body("Vet " + vetSpecialtyDTO.getVetId() + " assigned to specialty " + vetSpecialtyDTO.getSpecialtyId());
	}

	@DeleteMapping(value = "/vet-specialties")
	public ResponseEntity<String> delete(@RequestBody VetSpecialtyDTO vetSpecialtyDTO) {
		Optional<?> vetOpt = vetRepository.findById(vetSpecialtyDTO.getVetId());
		Optional<?> specialtyOpt = specialtyRepository.findById(vetSpecialtyDTO.getSpecialtyId());

		if (!vetOpt.isPresent() || !specialtyOpt.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok("Vet " + vetSpecialtyDTO.getVetId() + " unassigned from specialty " + vetSpecialtyDTO.getSpecialtyId());
	}

}

