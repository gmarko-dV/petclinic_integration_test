package com.tecsup.petclinic.repositories;

import com.tecsup.petclinic.entities.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * @author jgomezm
 *
 */
@Repository
public interface VisitRepository 
	extends JpaRepository<Visit, Long> {

	// Fetch visits by pet id
	List<Visit> findByPet_Id(Integer petId);

	// Fetch all visits
	@Override
	List<Visit> findAll();

}

