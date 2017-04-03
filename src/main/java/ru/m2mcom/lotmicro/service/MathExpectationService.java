package ru.m2mcom.lotmicro.service;

import ru.m2mcom.lotmicro.service.dto.MathExpectationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing MathExpectation.
 */
public interface MathExpectationService {

    /**
     * Save a mathExpectation.
     *
     * @param mathExpectationDTO the entity to save
     * @return the persisted entity
     */
    MathExpectationDTO save(MathExpectationDTO mathExpectationDTO);

    /**
     *  Get all the mathExpectations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MathExpectationDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" mathExpectation.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MathExpectationDTO findOne(Long id);

    /**
     *  Delete the "id" mathExpectation.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the mathExpectation corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MathExpectationDTO> search(String query, Pageable pageable);
}
