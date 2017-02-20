package ru.m2mcom.natmob.web.rest;

import ru.m2mcom.natmob.LotappApp;

import ru.m2mcom.natmob.domain.EmlBallsAndStarsHitStats;
import ru.m2mcom.natmob.repository.EmlBallsAndStarsHitStatsRepository;
import ru.m2mcom.natmob.service.EmlBallsAndStarsHitStatsService;
import ru.m2mcom.natmob.repository.search.EmlBallsAndStarsHitStatsSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EmlBallsAndStarsHitStatsResource REST controller.
 *
 * @see EmlBallsAndStarsHitStatsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LotappApp.class)
public class EmlBallsAndStarsHitStatsResourceIntTest {

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    private static final Integer DEFAULT_STAT_S = 1;
    private static final Integer UPDATED_STAT_S = 2;

    private static final Integer DEFAULT_STAT_L = 1;
    private static final Integer UPDATED_STAT_L = 2;

    private static final String DEFAULT_BALL_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_BALL_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_TIMESTAMP = 1;
    private static final Integer UPDATED_TIMESTAMP = 2;

    @Autowired
    private EmlBallsAndStarsHitStatsRepository emlBallsAndStarsHitStatsRepository;

    @Autowired
    private EmlBallsAndStarsHitStatsService emlBallsAndStarsHitStatsService;

    @Autowired
    private EmlBallsAndStarsHitStatsSearchRepository emlBallsAndStarsHitStatsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restEmlBallsAndStarsHitStatsMockMvc;

    private EmlBallsAndStarsHitStats emlBallsAndStarsHitStats;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmlBallsAndStarsHitStatsResource emlBallsAndStarsHitStatsResource = new EmlBallsAndStarsHitStatsResource(emlBallsAndStarsHitStatsService);
        this.restEmlBallsAndStarsHitStatsMockMvc = MockMvcBuilders.standaloneSetup(emlBallsAndStarsHitStatsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmlBallsAndStarsHitStats createEntity(EntityManager em) {
        EmlBallsAndStarsHitStats emlBallsAndStarsHitStats = new EmlBallsAndStarsHitStats()
                .number(DEFAULT_NUMBER)
                .statS(DEFAULT_STAT_S)
                .statL(DEFAULT_STAT_L)
                .ballType(DEFAULT_BALL_TYPE)
                .timestamp(DEFAULT_TIMESTAMP);
        return emlBallsAndStarsHitStats;
    }

    @Before
    public void initTest() {
        emlBallsAndStarsHitStatsSearchRepository.deleteAll();
        emlBallsAndStarsHitStats = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmlBallsAndStarsHitStats() throws Exception {
        int databaseSizeBeforeCreate = emlBallsAndStarsHitStatsRepository.findAll().size();

        // Create the EmlBallsAndStarsHitStats

        restEmlBallsAndStarsHitStatsMockMvc.perform(post("/api/eml-balls-and-stars-hit-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emlBallsAndStarsHitStats)))
            .andExpect(status().isCreated());

        // Validate the EmlBallsAndStarsHitStats in the database
        List<EmlBallsAndStarsHitStats> emlBallsAndStarsHitStatsList = emlBallsAndStarsHitStatsRepository.findAll();
        assertThat(emlBallsAndStarsHitStatsList).hasSize(databaseSizeBeforeCreate + 1);
        EmlBallsAndStarsHitStats testEmlBallsAndStarsHitStats = emlBallsAndStarsHitStatsList.get(emlBallsAndStarsHitStatsList.size() - 1);
        assertThat(testEmlBallsAndStarsHitStats.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testEmlBallsAndStarsHitStats.getStatS()).isEqualTo(DEFAULT_STAT_S);
        assertThat(testEmlBallsAndStarsHitStats.getStatL()).isEqualTo(DEFAULT_STAT_L);
        assertThat(testEmlBallsAndStarsHitStats.getBallType()).isEqualTo(DEFAULT_BALL_TYPE);
        assertThat(testEmlBallsAndStarsHitStats.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);

        // Validate the EmlBallsAndStarsHitStats in Elasticsearch
        EmlBallsAndStarsHitStats emlBallsAndStarsHitStatsEs = emlBallsAndStarsHitStatsSearchRepository.findOne(testEmlBallsAndStarsHitStats.getId());
        assertThat(emlBallsAndStarsHitStatsEs).isEqualToComparingFieldByField(testEmlBallsAndStarsHitStats);
    }

    @Test
    @Transactional
    public void createEmlBallsAndStarsHitStatsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emlBallsAndStarsHitStatsRepository.findAll().size();

        // Create the EmlBallsAndStarsHitStats with an existing ID
        EmlBallsAndStarsHitStats existingEmlBallsAndStarsHitStats = new EmlBallsAndStarsHitStats();
        existingEmlBallsAndStarsHitStats.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmlBallsAndStarsHitStatsMockMvc.perform(post("/api/eml-balls-and-stars-hit-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingEmlBallsAndStarsHitStats)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<EmlBallsAndStarsHitStats> emlBallsAndStarsHitStatsList = emlBallsAndStarsHitStatsRepository.findAll();
        assertThat(emlBallsAndStarsHitStatsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = emlBallsAndStarsHitStatsRepository.findAll().size();
        // set the field null
        emlBallsAndStarsHitStats.setNumber(null);

        // Create the EmlBallsAndStarsHitStats, which fails.

        restEmlBallsAndStarsHitStatsMockMvc.perform(post("/api/eml-balls-and-stars-hit-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emlBallsAndStarsHitStats)))
            .andExpect(status().isBadRequest());

        List<EmlBallsAndStarsHitStats> emlBallsAndStarsHitStatsList = emlBallsAndStarsHitStatsRepository.findAll();
        assertThat(emlBallsAndStarsHitStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatSIsRequired() throws Exception {
        int databaseSizeBeforeTest = emlBallsAndStarsHitStatsRepository.findAll().size();
        // set the field null
        emlBallsAndStarsHitStats.setStatS(null);

        // Create the EmlBallsAndStarsHitStats, which fails.

        restEmlBallsAndStarsHitStatsMockMvc.perform(post("/api/eml-balls-and-stars-hit-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emlBallsAndStarsHitStats)))
            .andExpect(status().isBadRequest());

        List<EmlBallsAndStarsHitStats> emlBallsAndStarsHitStatsList = emlBallsAndStarsHitStatsRepository.findAll();
        assertThat(emlBallsAndStarsHitStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBallTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = emlBallsAndStarsHitStatsRepository.findAll().size();
        // set the field null
        emlBallsAndStarsHitStats.setBallType(null);

        // Create the EmlBallsAndStarsHitStats, which fails.

        restEmlBallsAndStarsHitStatsMockMvc.perform(post("/api/eml-balls-and-stars-hit-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emlBallsAndStarsHitStats)))
            .andExpect(status().isBadRequest());

        List<EmlBallsAndStarsHitStats> emlBallsAndStarsHitStatsList = emlBallsAndStarsHitStatsRepository.findAll();
        assertThat(emlBallsAndStarsHitStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmlBallsAndStarsHitStats() throws Exception {
        // Initialize the database
        emlBallsAndStarsHitStatsRepository.saveAndFlush(emlBallsAndStarsHitStats);

        // Get all the emlBallsAndStarsHitStatsList
        restEmlBallsAndStarsHitStatsMockMvc.perform(get("/api/eml-balls-and-stars-hit-stats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emlBallsAndStarsHitStats.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].statS").value(hasItem(DEFAULT_STAT_S)))
            .andExpect(jsonPath("$.[*].statL").value(hasItem(DEFAULT_STAT_L)))
            .andExpect(jsonPath("$.[*].ballType").value(hasItem(DEFAULT_BALL_TYPE.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP)));
    }

    @Test
    @Transactional
    public void getEmlBallsAndStarsHitStats() throws Exception {
        // Initialize the database
        emlBallsAndStarsHitStatsRepository.saveAndFlush(emlBallsAndStarsHitStats);

        // Get the emlBallsAndStarsHitStats
        restEmlBallsAndStarsHitStatsMockMvc.perform(get("/api/eml-balls-and-stars-hit-stats/{id}", emlBallsAndStarsHitStats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(emlBallsAndStarsHitStats.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.statS").value(DEFAULT_STAT_S))
            .andExpect(jsonPath("$.statL").value(DEFAULT_STAT_L))
            .andExpect(jsonPath("$.ballType").value(DEFAULT_BALL_TYPE.toString()))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP));
    }

    @Test
    @Transactional
    public void getNonExistingEmlBallsAndStarsHitStats() throws Exception {
        // Get the emlBallsAndStarsHitStats
        restEmlBallsAndStarsHitStatsMockMvc.perform(get("/api/eml-balls-and-stars-hit-stats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmlBallsAndStarsHitStats() throws Exception {
        // Initialize the database
        emlBallsAndStarsHitStatsService.save(emlBallsAndStarsHitStats);

        int databaseSizeBeforeUpdate = emlBallsAndStarsHitStatsRepository.findAll().size();

        // Update the emlBallsAndStarsHitStats
        EmlBallsAndStarsHitStats updatedEmlBallsAndStarsHitStats = emlBallsAndStarsHitStatsRepository.findOne(emlBallsAndStarsHitStats.getId());
        updatedEmlBallsAndStarsHitStats
                .number(UPDATED_NUMBER)
                .statS(UPDATED_STAT_S)
                .statL(UPDATED_STAT_L)
                .ballType(UPDATED_BALL_TYPE)
                .timestamp(UPDATED_TIMESTAMP);

        restEmlBallsAndStarsHitStatsMockMvc.perform(put("/api/eml-balls-and-stars-hit-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmlBallsAndStarsHitStats)))
            .andExpect(status().isOk());

        // Validate the EmlBallsAndStarsHitStats in the database
        List<EmlBallsAndStarsHitStats> emlBallsAndStarsHitStatsList = emlBallsAndStarsHitStatsRepository.findAll();
        assertThat(emlBallsAndStarsHitStatsList).hasSize(databaseSizeBeforeUpdate);
        EmlBallsAndStarsHitStats testEmlBallsAndStarsHitStats = emlBallsAndStarsHitStatsList.get(emlBallsAndStarsHitStatsList.size() - 1);
        assertThat(testEmlBallsAndStarsHitStats.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testEmlBallsAndStarsHitStats.getStatS()).isEqualTo(UPDATED_STAT_S);
        assertThat(testEmlBallsAndStarsHitStats.getStatL()).isEqualTo(UPDATED_STAT_L);
        assertThat(testEmlBallsAndStarsHitStats.getBallType()).isEqualTo(UPDATED_BALL_TYPE);
        assertThat(testEmlBallsAndStarsHitStats.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);

        // Validate the EmlBallsAndStarsHitStats in Elasticsearch
        EmlBallsAndStarsHitStats emlBallsAndStarsHitStatsEs = emlBallsAndStarsHitStatsSearchRepository.findOne(testEmlBallsAndStarsHitStats.getId());
        assertThat(emlBallsAndStarsHitStatsEs).isEqualToComparingFieldByField(testEmlBallsAndStarsHitStats);
    }

    @Test
    @Transactional
    public void updateNonExistingEmlBallsAndStarsHitStats() throws Exception {
        int databaseSizeBeforeUpdate = emlBallsAndStarsHitStatsRepository.findAll().size();

        // Create the EmlBallsAndStarsHitStats

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmlBallsAndStarsHitStatsMockMvc.perform(put("/api/eml-balls-and-stars-hit-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emlBallsAndStarsHitStats)))
            .andExpect(status().isCreated());

        // Validate the EmlBallsAndStarsHitStats in the database
        List<EmlBallsAndStarsHitStats> emlBallsAndStarsHitStatsList = emlBallsAndStarsHitStatsRepository.findAll();
        assertThat(emlBallsAndStarsHitStatsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEmlBallsAndStarsHitStats() throws Exception {
        // Initialize the database
        emlBallsAndStarsHitStatsService.save(emlBallsAndStarsHitStats);

        int databaseSizeBeforeDelete = emlBallsAndStarsHitStatsRepository.findAll().size();

        // Get the emlBallsAndStarsHitStats
        restEmlBallsAndStarsHitStatsMockMvc.perform(delete("/api/eml-balls-and-stars-hit-stats/{id}", emlBallsAndStarsHitStats.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean emlBallsAndStarsHitStatsExistsInEs = emlBallsAndStarsHitStatsSearchRepository.exists(emlBallsAndStarsHitStats.getId());
        assertThat(emlBallsAndStarsHitStatsExistsInEs).isFalse();

        // Validate the database is empty
        List<EmlBallsAndStarsHitStats> emlBallsAndStarsHitStatsList = emlBallsAndStarsHitStatsRepository.findAll();
        assertThat(emlBallsAndStarsHitStatsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEmlBallsAndStarsHitStats() throws Exception {
        // Initialize the database
        emlBallsAndStarsHitStatsService.save(emlBallsAndStarsHitStats);

        // Search the emlBallsAndStarsHitStats
        restEmlBallsAndStarsHitStatsMockMvc.perform(get("/api/_search/eml-balls-and-stars-hit-stats?query=id:" + emlBallsAndStarsHitStats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emlBallsAndStarsHitStats.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].statS").value(hasItem(DEFAULT_STAT_S)))
            .andExpect(jsonPath("$.[*].statL").value(hasItem(DEFAULT_STAT_L)))
            .andExpect(jsonPath("$.[*].ballType").value(hasItem(DEFAULT_BALL_TYPE.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmlBallsAndStarsHitStats.class);
    }
}
