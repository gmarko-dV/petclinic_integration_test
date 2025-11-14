package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;

import java.util.List;

/**
 *
 * @author jgomezm
 *
 */
public interface SpecialtyService {

    /**
     *
     * @param specialtyDTO
     * @return
     */
    public SpecialtyDTO create(SpecialtyDTO specialtyDTO);

    /**
     *
     * @param specialtyDTO
     * @return
     */
    SpecialtyDTO update(SpecialtyDTO specialtyDTO);

    /**
     *
     * @param id
     * @throws SpecialtyNotFoundException
     */
    void delete(Integer id) throws SpecialtyNotFoundException;

    /**
     *
     * @param id
     * @return
     */
    SpecialtyDTO findById(Integer id) throws SpecialtyNotFoundException;

    /**
     *
     * @param name
     * @return
     */
    List<SpecialtyDTO> findByName(String name);

    /**
     *
     * @return
     */
    List<Specialty> findAll();
}

