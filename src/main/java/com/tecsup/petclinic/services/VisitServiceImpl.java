package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;
import com.tecsup.petclinic.mapper.VisitMapper;
import com.tecsup.petclinic.repositories.PetRepository;
import com.tecsup.petclinic.repositories.VisitRepository;
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
public class VisitServiceImpl implements VisitService {

    VisitRepository visitRepository;
    VisitMapper visitMapper;
    PetRepository petRepository;

    public VisitServiceImpl(VisitRepository visitRepository, VisitMapper visitMapper, PetRepository petRepository) {
        this.visitRepository = visitRepository;
        this.visitMapper = visitMapper;
        this.petRepository = petRepository;
    }

    /**
     *
     * @param visitDTO
     * @return
     */
    @Override
    public VisitDTO create(VisitDTO visitDTO) {

        Visit visit = visitMapper.mapToEntity(visitDTO);
        
        if (visitDTO.getPetId() != null) {
            Optional<Pet> pet = petRepository.findById(visitDTO.getPetId());
            if (pet.isPresent()) {
                visit.setPet(pet.get());
            }
        }

        Visit newVisit = visitRepository.save(visit);

        return visitMapper.mapToDto(newVisit);
    }

    /**
     *
     * @param visitDTO
     * @return
     */
    @Override
    public VisitDTO update(VisitDTO visitDTO) {

        Visit visit = visitMapper.mapToEntity(visitDTO);
        
        if (visitDTO.getPetId() != null) {
            Optional<Pet> pet = petRepository.findById(visitDTO.getPetId());
            if (pet.isPresent()) {
                visit.setPet(pet.get());
            }
        }

        Visit newVisit = visitRepository.save(visit);

        return visitMapper.mapToDto(newVisit);

    }

    /**
     *
     * @param id
     * @throws VisitNotFoundException
     */
    @Override
    public void delete(Long id) throws VisitNotFoundException {

        VisitDTO visit = findById(id);

        visitRepository.delete(this.visitMapper.mapToEntity(visit));

    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public VisitDTO findById(Long id) throws VisitNotFoundException {

        Optional<Visit> visit = visitRepository.findById(id);

        if (!visit.isPresent())
            throw new VisitNotFoundException("Record not found...!");

        return this.visitMapper.mapToDto(visit.get());
    }

    /**
     *
     * @param petId
     * @return
     */
    @Override
    public List<VisitDTO> findByPetId(Integer petId) {

        List<Visit> visits = visitRepository.findByPet_Id(petId);

        visits.forEach(visit -> log.info("" + visit));

        return visits
                .stream()
                .map(this.visitMapper::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     *
     * @return
     */
    @Override
    public List<Visit> findAll() {
        //
        return visitRepository.findAll();

    }
}

