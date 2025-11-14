package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import com.tecsup.petclinic.mapper.SpecialtyMapper;
import com.tecsup.petclinic.repositories.SpecialtyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author jgomezm
 *
 */
@Service
@Slf4j
public class SpecialtyServiceImpl implements SpecialtyService {

    SpecialtyRepository specialtyRepository;
    SpecialtyMapper specialtyMapper;

    public SpecialtyServiceImpl(SpecialtyRepository specialtyRepository, SpecialtyMapper specialtyMapper) {
        this.specialtyRepository = specialtyRepository;
        this.specialtyMapper = specialtyMapper;
    }

    /**
     *
     * @param specialtyDTO
     * @return
     */
    @Override
    public SpecialtyDTO create(SpecialtyDTO specialtyDTO) {

        Specialty newSpecialty = specialtyRepository.save(specialtyMapper.mapToEntity(specialtyDTO));

        return specialtyMapper.mapToDto(newSpecialty);
    }

    /**
     *
     * @param specialtyDTO
     * @return
     */
    @Override
    public SpecialtyDTO update(SpecialtyDTO specialtyDTO) {

        Specialty newSpecialty = specialtyRepository.save(specialtyMapper.mapToEntity(specialtyDTO));

        return specialtyMapper.mapToDto(newSpecialty);

    }

    /**
     *
     * @param id
     * @throws SpecialtyNotFoundException
     */
    @Override
    public void delete(Integer id) throws SpecialtyNotFoundException {

        SpecialtyDTO specialty = findById(id);

        specialtyRepository.delete(this.specialtyMapper.mapToEntity(specialty));

    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public SpecialtyDTO findById(Integer id) throws SpecialtyNotFoundException {

        Optional<Specialty> specialty = specialtyRepository.findById(id);

        if (!specialty.isPresent())
            throw new SpecialtyNotFoundException("Record not found...!");

        return this.specialtyMapper.mapToDto(specialty.get());
    }

    /**
     *
     * @param name
     * @return
     */
    @Override
    public List<SpecialtyDTO> findByName(String name) {

        List<Specialty> specialties = specialtyRepository.findByName(name);

        specialties.forEach(specialty -> log.info("" + specialty));

        return specialties
                .stream()
                .map(this.specialtyMapper::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     *
     * @return
     */
    @Override
    public List<Specialty> findAll() {
        //
        return specialtyRepository.findAll();

    }
}

