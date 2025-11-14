package com.tecsup.petclinic.repositories;

import com.tecsup.petclinic.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * @author jgomezm
 *
 */
@Repository
public interface OwnerRepository 
	extends JpaRepository<Owner, Long> {

	// Fetch owners by firstName
	List<Owner> findByFirstName(String firstName);

	// Fetch owners by lastName
	List<Owner> findByLastName(String lastName);

	// Fetch all owners
	@Override
	List<Owner> findAll();

}

