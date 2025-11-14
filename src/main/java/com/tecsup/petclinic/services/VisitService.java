package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;

import java.util.List;

/**
 *
 * @author jgomezm
 *
 */
public interface VisitService {

    /**
     *
     * @param visitDTO
     * @return
     */
    public VisitDTO create(VisitDTO visitDTO);

    /**
     *
     * @param visitDTO
     * @return
     */
    VisitDTO update(VisitDTO visitDTO);

    /**
     *
     * @param id
     * @throws VisitNotFoundException
     */
    void delete(Long id) throws VisitNotFoundException;

    /**
     *
     * @param id
     * @return
     */
    VisitDTO findById(Long id) throws VisitNotFoundException;

    /**
     *
     * @param petId
     * @return
     */
    List<VisitDTO> findByPetId(Integer petId);

    /**
     *
     * @return
     */
    List<Visit> findAll();
}

