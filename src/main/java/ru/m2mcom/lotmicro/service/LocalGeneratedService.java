package ru.m2mcom.lotmicro.service;

import ru.m2mcom.lotmicro.service.dto.LocalGeneratedDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing LocalGenerated.
 */
public interface LocalGeneratedService {

    /**
     * Save a localGenerated.
     *
     * @param localGeneratedDTO the entity to save
     * @return the persisted entity
     */
    LocalGeneratedDTO save(LocalGeneratedDTO localGeneratedDTO);

    /**
     *  Get all the localGenerateds.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<LocalGeneratedDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" localGenerated.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    LocalGeneratedDTO findOne(Long id);

    /**
     *  Delete the "id" localGenerated.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the localGenerated corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<LocalGeneratedDTO> search(String query, Pageable pageable);
}
