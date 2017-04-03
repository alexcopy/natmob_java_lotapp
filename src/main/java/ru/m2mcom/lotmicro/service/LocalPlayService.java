package ru.m2mcom.lotmicro.service;

import ru.m2mcom.lotmicro.service.dto.LocalPlayDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing LocalPlay.
 */
public interface LocalPlayService {

    /**
     * Save a localPlay.
     *
     * @param localPlayDTO the entity to save
     * @return the persisted entity
     */
    LocalPlayDTO save(LocalPlayDTO localPlayDTO);

    /**
     *  Get all the localPlays.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<LocalPlayDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" localPlay.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    LocalPlayDTO findOne(Long id);

    /**
     *  Delete the "id" localPlay.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the localPlay corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<LocalPlayDTO> search(String query, Pageable pageable);
}
