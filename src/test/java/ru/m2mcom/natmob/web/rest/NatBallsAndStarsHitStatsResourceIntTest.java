package ru.m2mcom.natmob.web.rest;

import ru.m2mcom.natmob.LotappApp;

import ru.m2mcom.natmob.domain.NatBallsAndStarsHitStats;
import ru.m2mcom.natmob.repository.NatBallsAndStarsHitStatsRepository;
import ru.m2mcom.natmob.service.NatBallsAndStarsHitStatsService;
import ru.m2mcom.natmob.repository.search.NatBallsAndStarsHitStatsSearchRepository;

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
 * Test class for the NatBallsAndStarsHitStatsResource REST controller.
 *
 * @see NatBallsAndStarsHitStatsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LotappApp.class)
public class NatBallsAndStarsHitStatsResourceIntTest {

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
    private NatBallsAndStarsHitStatsRepository natBallsAndStarsHitStatsRepository;

    @Autowired
    private NatBallsAndStarsHitStatsService natBallsAndStarsHitStatsService;

    @Autowired
    private NatBallsAndStarsHitStatsSearchRepository natBallsAndStarsHitStatsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restNatBallsAndStarsHitStatsMockMvc;

    private NatBallsAndStarsHitStats natBallsAndStarsHitStats;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NatBallsAndStarsHitStatsResource natBallsAndStarsHitStatsResource = new NatBallsAndStarsHitStatsResource(natBallsAndStarsHitStatsService);
        this.restNatBallsAndStarsHitStatsMockMvc = MockMvcBuilders.standaloneSetup(natBallsAndStarsHitStatsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NatBallsAndStarsHitStats createEntity(EntityManager em) {
        NatBallsAndStarsHitStats natBallsAndStarsHitStats = new NatBallsAndStarsHitStats()
                .number(DEFAULT_NUMBER)
                .statS(DEFAULT_STAT_S)
                .statL(DEFAULT_STAT_L)
                .ballType(DEFAULT_BALL_TYPE)
                .timestamp(DEFAULT_TIMESTAMP);
        return natBallsAndStarsHitStats;
    }

    @Before
    public void initTest() {
        natBallsAndStarsHitStatsSearchRepository.deleteAll();
        natBallsAndStarsHitStats = createEntity(em);
    }

    @Test
    @Transactional
    public void createNatBallsAndStarsHitStats() throws Exception {
        int databaseSizeBeforeCreate = natBallsAndStarsHitStatsRepository.findAll().size();

        // Create the NatBallsAndStarsHitStats

        restNatBallsAndStarsHitStatsMockMvc.perform(post("/api/nat-balls-and-stars-hit-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(natBallsAndStarsHitStats)))
            .andExpect(status().isCreated());

        // Validate the NatBallsAndStarsHitStats in the database
        List<NatBallsAndStarsHitStats> natBallsAndStarsHitStatsList = natBallsAndStarsHitStatsRepository.findAll();
        assertThat(natBallsAndStarsHitStatsList).hasSize(databaseSizeBeforeCreate + 1);
        NatBallsAndStarsHitStats testNatBallsAndStarsHitStats = natBallsAndStarsHitStatsList.get(natBallsAndStarsHitStatsList.size() - 1);
        assertThat(testNatBallsAndStarsHitStats.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testNatBallsAndStarsHitStats.getStatS()).isEqualTo(DEFAULT_STAT_S);
        assertThat(testNatBallsAndStarsHitStats.getStatL()).isEqualTo(DEFAULT_STAT_L);
        assertThat(testNatBallsAndStarsHitStats.getBallType()).isEqualTo(DEFAULT_BALL_TYPE);
        assertThat(testNatBallsAndStarsHitStats.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);

        // Validate the NatBallsAndStarsHitStats in Elasticsearch
        NatBallsAndStarsHitStats natBallsAndStarsHitStatsEs = natBallsAndStarsHitStatsSearchRepository.findOne(testNatBallsAndStarsHitStats.getId());
        assertThat(natBallsAndStarsHitStatsEs).isEqualToComparingFieldByField(testNatBallsAndStarsHitStats);
    }

    @Test
    @Transactional
    public void createNatBallsAndStarsHitStatsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = natBallsAndStarsHitStatsRepository.findAll().size();

        // Create the NatBallsAndStarsHitStats with an existing ID
        NatBallsAndStarsHitStats existingNatBallsAndStarsHitStats = new NatBallsAndStarsHitStats();
        existingNatBallsAndStarsHitStats.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNatBallsAndStarsHitStatsMockMvc.perform(post("/api/nat-balls-and-stars-hit-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingNatBallsAndStarsHitStats)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<NatBallsAndStarsHitStats> natBallsAndStarsHitStatsList = natBallsAndStarsHitStatsRepository.findAll();
        assertThat(natBallsAndStarsHitStatsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = natBallsAndStarsHitStatsRepository.findAll().size();
        // set the field null
        natBallsAndStarsHitStats.setNumber(null);

        // Create the NatBallsAndStarsHitStats, which fails.

        restNatBallsAndStarsHitStatsMockMvc.perform(post("/api/nat-balls-and-stars-hit-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(natBallsAndStarsHitStats)))
            .andExpect(status().isBadRequest());

        List<NatBallsAndStarsHitStats> natBallsAndStarsHitStatsList = natBallsAndStarsHitStatsRepository.findAll();
        assertThat(natBallsAndStarsHitStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatSIsRequired() throws Exception {
        int databaseSizeBeforeTest = natBallsAndStarsHitStatsRepository.findAll().size();
        // set the field null
        natBallsAndStarsHitStats.setStatS(null);

        // Create the NatBallsAndStarsHitStats, which fails.

        restNatBallsAndStarsHitStatsMockMvc.perform(post("/api/nat-balls-and-stars-hit-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(natBallsAndStarsHitStats)))
            .andExpect(status().isBadRequest());

        List<NatBallsAndStarsHitStats> natBallsAndStarsHitStatsList = natBallsAndStarsHitStatsRepository.findAll();
        assertThat(natBallsAndStarsHitStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBallTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = natBallsAndStarsHitStatsRepository.findAll().size();
        // set the field null
        natBallsAndStarsHitStats.setBallType(null);

        // Create the NatBallsAndStarsHitStats, which fails.

        restNatBallsAndStarsHitStatsMockMvc.perform(post("/api/nat-balls-and-stars-hit-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(natBallsAndStarsHitStats)))
            .andExpect(status().isBadRequest());

        List<NatBallsAndStarsHitStats> natBallsAndStarsHitStatsList = natBallsAndStarsHitStatsRepository.findAll();
        assertThat(natBallsAndStarsHitStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNatBallsAndStarsHitStats() throws Exception {
        // Initialize the database
        natBallsAndStarsHitStatsRepository.saveAndFlush(natBallsAndStarsHitStats);

        // Get all the natBallsAndStarsHitStatsList
        restNatBallsAndStarsHitStatsMockMvc.perform(get("/api/nat-balls-and-stars-hit-stats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(natBallsAndStarsHitStats.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].statS").value(hasItem(DEFAULT_STAT_S)))
            .andExpect(jsonPath("$.[*].statL").value(hasItem(DEFAULT_STAT_L)))
            .andExpect(jsonPath("$.[*].ballType").value(hasItem(DEFAULT_BALL_TYPE.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP)));
    }

    @Test
    @Transactional
    public void getNatBallsAndStarsHitStats() throws Exception {
        // Initialize the database
        natBallsAndStarsHitStatsRepository.saveAndFlush(natBallsAndStarsHitStats);

        // Get the natBallsAndStarsHitStats
        restNatBallsAndStarsHitStatsMockMvc.perform(get("/api/nat-balls-and-stars-hit-stats/{id}", natBallsAndStarsHitStats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(natBallsAndStarsHitStats.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.statS").value(DEFAULT_STAT_S))
            .andExpect(jsonPath("$.statL").value(DEFAULT_STAT_L))
            .andExpect(jsonPath("$.ballType").value(DEFAULT_BALL_TYPE.toString()))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP));
    }

    @Test
    @Transactional
    public void getNonExistingNatBallsAndStarsHitStats() throws Exception {
        // Get the natBallsAndStarsHitStats
        restNatBallsAndStarsHitStatsMockMvc.perform(get("/api/nat-balls-and-stars-hit-stats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNatBallsAndStarsHitStats() throws Exception {
        // Initialize the database
        natBallsAndStarsHitStatsService.save(natBallsAndStarsHitStats);

        int databaseSizeBeforeUpdate = natBallsAndStarsHitStatsRepository.findAll().size();

        // Update the natBallsAndStarsHitStats
        NatBallsAndStarsHitStats updatedNatBallsAndStarsHitStats = natBallsAndStarsHitStatsRepository.findOne(natBallsAndStarsHitStats.getId());
        updatedNatBallsAndStarsHitStats
                .number(UPDATED_NUMBER)
                .statS(UPDATED_STAT_S)
                .statL(UPDATED_STAT_L)
                .ballType(UPDATED_BALL_TYPE)
                .timestamp(UPDATED_TIMESTAMP);

        restNatBallsAndStarsHitStatsMockMvc.perform(put("/api/nat-balls-and-stars-hit-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNatBallsAndStarsHitStats)))
            .andExpect(status().isOk());

        // Validate the NatBallsAndStarsHitStats in the database
        List<NatBallsAndStarsHitStats> natBallsAndStarsHitStatsList = natBallsAndStarsHitStatsRepository.findAll();
        assertThat(natBallsAndStarsHitStatsList).hasSize(databaseSizeBeforeUpdate);
        NatBallsAndStarsHitStats testNatBallsAndStarsHitStats = natBallsAndStarsHitStatsList.get(natBallsAndStarsHitStatsList.size() - 1);
        assertThat(testNatBallsAndStarsHitStats.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testNatBallsAndStarsHitStats.getStatS()).isEqualTo(UPDATED_STAT_S);
        assertThat(testNatBallsAndStarsHitStats.getStatL()).isEqualTo(UPDATED_STAT_L);
        assertThat(testNatBallsAndStarsHitStats.getBallType()).isEqualTo(UPDATED_BALL_TYPE);
        assertThat(testNatBallsAndStarsHitStats.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);

        // Validate the NatBallsAndStarsHitStats in Elasticsearch
        NatBallsAndStarsHitStats natBallsAndStarsHitStatsEs = natBallsAndStarsHitStatsSearchRepository.findOne(testNatBallsAndStarsHitStats.getId());
        assertThat(natBallsAndStarsHitStatsEs).isEqualToComparingFieldByField(testNatBallsAndStarsHitStats);
    }

    @Test
    @Transactional
    public void updateNonExistingNatBallsAndStarsHitStats() throws Exception {
        int databaseSizeBeforeUpdate = natBallsAndStarsHitStatsRepository.findAll().size();

        // Create the NatBallsAndStarsHitStats

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNatBallsAndStarsHitStatsMockMvc.perform(put("/api/nat-balls-and-stars-hit-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(natBallsAndStarsHitStats)))
            .andExpect(status().isCreated());

        // Validate the NatBallsAndStarsHitStats in the database
        List<NatBallsAndStarsHitStats> natBallsAndStarsHitStatsList = natBallsAndStarsHitStatsRepository.findAll();
        assertThat(natBallsAndStarsHitStatsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNatBallsAndStarsHitStats() throws Exception {
        // Initialize the database
        natBallsAndStarsHitStatsService.save(natBallsAndStarsHitStats);

        int databaseSizeBeforeDelete = natBallsAndStarsHitStatsRepository.findAll().size();

        // Get the natBallsAndStarsHitStats
        restNatBallsAndStarsHitStatsMockMvc.perform(delete("/api/nat-balls-and-stars-hit-stats/{id}", natBallsAndStarsHitStats.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean natBallsAndStarsHitStatsExistsInEs = natBallsAndStarsHitStatsSearchRepository.exists(natBallsAndStarsHitStats.getId());
        assertThat(natBallsAndStarsHitStatsExistsInEs).isFalse();

        // Validate the database is empty
        List<NatBallsAndStarsHitStats> natBallsAndStarsHitStatsList = natBallsAndStarsHitStatsRepository.findAll();
        assertThat(natBallsAndStarsHitStatsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchNatBallsAndStarsHitStats() throws Exception {
        // Initialize the database
        natBallsAndStarsHitStatsService.save(natBallsAndStarsHitStats);

        // Search the natBallsAndStarsHitStats
        restNatBallsAndStarsHitStatsMockMvc.perform(get("/api/_search/nat-balls-and-stars-hit-stats?query=id:" + natBallsAndStarsHitStats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(natBallsAndStarsHitStats.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].statS").value(hasItem(DEFAULT_STAT_S)))
            .andExpect(jsonPath("$.[*].statL").value(hasItem(DEFAULT_STAT_L)))
            .andExpect(jsonPath("$.[*].ballType").value(hasItem(DEFAULT_BALL_TYPE.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NatBallsAndStarsHitStats.class);
    }
}
