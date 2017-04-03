package ru.m2mcom.lotmicro.service;

import ru.m2mcom.lotmicro.service.dto.HitPredictDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing HitPredict.
 */
public interface HitPredictService {

    /**
     * Save a hitPredict.
     *
     * @param hitPredictDTO the entity to save
     * @return the persisted entity
     */
    HitPredictDTO save(HitPredictDTO hitPredictDTO);

    /**
     *  Get all the hitPredicts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<HitPredictDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" hitPredict.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    HitPredictDTO findOne(Long id);

    /**
     *  Delete the "id" hitPredict.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the hitPredict corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<HitPredictDTO> search(String query, Pageable pageable);
}
