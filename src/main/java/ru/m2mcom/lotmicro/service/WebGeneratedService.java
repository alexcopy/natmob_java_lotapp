package ru.m2mcom.lotmicro.service;

import ru.m2mcom.lotmicro.service.dto.WebGeneratedDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing WebGenerated.
 */
public interface WebGeneratedService {

    /**
     * Save a webGenerated.
     *
     * @param webGeneratedDTO the entity to save
     * @return the persisted entity
     */
    WebGeneratedDTO save(WebGeneratedDTO webGeneratedDTO);

    /**
     *  Get all the webGenerateds.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<WebGeneratedDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" webGenerated.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    WebGeneratedDTO findOne(Long id);

    /**
     *  Delete the "id" webGenerated.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the webGenerated corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<WebGeneratedDTO> search(String query, Pageable pageable);
}
