package com.tecsup.petclinic.repositories;

import com.tecsup.petclinic.entities.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * @author jgomezm
 *
 */
@Repository
public interface SpecialtyRepository 
	extends JpaRepository<Specialty, Integer> {

	// Fetch specialties by name
	List<Specialty> findByName(String name);

	// Fetch all specialties
	@Override
	List<Specialty> findAll();

}

