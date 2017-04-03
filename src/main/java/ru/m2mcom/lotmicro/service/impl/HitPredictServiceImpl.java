package ru.m2mcom.lotmicro.service.impl;

import ru.m2mcom.lotmicro.service.HitPredictService;
import ru.m2mcom.lotmicro.domain.HitPredict;
import ru.m2mcom.lotmicro.repository.HitPredictRepository;
import ru.m2mcom.lotmicro.repository.search.HitPredictSearchRepository;
import ru.m2mcom.lotmicro.service.dto.HitPredictDTO;
import ru.m2mcom.lotmicro.service.mapper.HitPredictMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing HitPredict.
 */
@Service
@Transactional
public class HitPredictServiceImpl implements HitPredictService{

    private final Logger log = LoggerFactory.getLogger(HitPredictServiceImpl.class);
    
    private final HitPredictRepository hitPredictRepository;

    private final HitPredictMapper hitPredictMapper;

    private final HitPredictSearchRepository hitPredictSearchRepository;

    public HitPredictServiceImpl(HitPredictRepository hitPredictRepository, HitPredictMapper hitPredictMapper, HitPredictSearchRepository hitPredictSearchRepository) {
        this.hitPredictRepository = hitPredictRepository;
        this.hitPredictMapper = hitPredictMapper;
        this.hitPredictSearchRepository = hitPredictSearchRepository;
    }

    /**
     * Save a hitPredict.
     *
     * @param hitPredictDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public HitPredictDTO save(HitPredictDTO hitPredictDTO) {
        log.debug("Request to save HitPredict : {}", hitPredictDTO);
        HitPredict hitPredict = hitPredictMapper.hitPredictDTOToHitPredict(hitPredictDTO);
        hitPredict = hitPredictRepository.save(hitPredict);
        HitPredictDTO result = hitPredictMapper.hitPredictToHitPredictDTO(hitPredict);
        hitPredictSearchRepository.save(hitPredict);
        return result;
    }

    /**
     *  Get all the hitPredicts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<HitPredictDTO> findAll(Pageable pageable) {
        log.debug("Request to get all HitPredicts");
        Page<HitPredict> result = hitPredictRepository.findAll(pageable);
        return result.map(hitPredict -> hitPredictMapper.hitPredictToHitPredictDTO(hitPredict));
    }

    /**
     *  Get one hitPredict by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public HitPredictDTO findOne(Long id) {
        log.debug("Request to get HitPredict : {}", id);
        HitPredict hitPredict = hitPredictRepository.findOne(id);
        HitPredictDTO hitPredictDTO = hitPredictMapper.hitPredictToHitPredictDTO(hitPredict);
        return hitPredictDTO;
    }

    /**
     *  Delete the  hitPredict by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete HitPredict : {}", id);
        hitPredictRepository.delete(id);
        hitPredictSearchRepository.delete(id);
    }

    /**
     * Search for the hitPredict corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<HitPredictDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of HitPredicts for query {}", query);
        Page<HitPredict> result = hitPredictSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(hitPredict -> hitPredictMapper.hitPredictToHitPredictDTO(hitPredict));
    }
}
