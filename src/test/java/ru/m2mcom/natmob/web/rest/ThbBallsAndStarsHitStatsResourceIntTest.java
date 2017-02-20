package ru.m2mcom.natmob.web.rest;

import ru.m2mcom.natmob.LotappApp;

import ru.m2mcom.natmob.domain.ThbBallsAndStarsHitStats;
import ru.m2mcom.natmob.repository.ThbBallsAndStarsHitStatsRepository;
import ru.m2mcom.natmob.service.ThbBallsAndStarsHitStatsService;
import ru.m2mcom.natmob.repository.search.ThbBallsAndStarsHitStatsSearchRepository;

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
 * Test class for the ThbBallsAndStarsHitStatsResource REST controller.
 *
 * @see ThbBallsAndStarsHitStatsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LotappApp.class)
public class ThbBallsAndStarsHitStatsResourceIntTest {

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
    private ThbBallsAndStarsHitStatsRepository thbBallsAndStarsHitStatsRepository;

    @Autowired
    private ThbBallsAndStarsHitStatsService thbBallsAndStarsHitStatsService;

    @Autowired
    private ThbBallsAndStarsHitStatsSearchRepository thbBallsAndStarsHitStatsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restThbBallsAndStarsHitStatsMockMvc;

    private ThbBallsAndStarsHitStats thbBallsAndStarsHitStats;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ThbBallsAndStarsHitStatsResource thbBallsAndStarsHitStatsResource = new ThbBallsAndStarsHitStatsResource(thbBallsAndStarsHitStatsService);
        this.restThbBallsAndStarsHitStatsMockMvc = MockMvcBuilders.standaloneSetup(thbBallsAndStarsHitStatsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ThbBallsAndStarsHitStats createEntity(EntityManager em) {
        ThbBallsAndStarsHitStats thbBallsAndStarsHitStats = new ThbBallsAndStarsHitStats()
                .number(DEFAULT_NUMBER)
                .statS(DEFAULT_STAT_S)
                .statL(DEFAULT_STAT_L)
                .ballType(DEFAULT_BALL_TYPE)
                .timestamp(DEFAULT_TIMESTAMP);
        return thbBallsAndStarsHitStats;
    }

    @Before
    public void initTest() {
        thbBallsAndStarsHitStatsSearchRepository.deleteAll();
        thbBallsAndStarsHitStats = createEntity(em);
    }

    @Test
    @Transactional
    public void createThbBallsAndStarsHitStats() throws Exception {
        int databaseSizeBeforeCreate = thbBallsAndStarsHitStatsRepository.findAll().size();

        // Create the ThbBallsAndStarsHitStats

        restThbBallsAndStarsHitStatsMockMvc.perform(post("/api/thb-balls-and-stars-hit-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thbBallsAndStarsHitStats)))
            .andExpect(status().isCreated());

        // Validate the ThbBallsAndStarsHitStats in the database
        List<ThbBallsAndStarsHitStats> thbBallsAndStarsHitStatsList = thbBallsAndStarsHitStatsRepository.findAll();
        assertThat(thbBallsAndStarsHitStatsList).hasSize(databaseSizeBeforeCreate + 1);
        ThbBallsAndStarsHitStats testThbBallsAndStarsHitStats = thbBallsAndStarsHitStatsList.get(thbBallsAndStarsHitStatsList.size() - 1);
        assertThat(testThbBallsAndStarsHitStats.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testThbBallsAndStarsHitStats.getStatS()).isEqualTo(DEFAULT_STAT_S);
        assertThat(testThbBallsAndStarsHitStats.getStatL()).isEqualTo(DEFAULT_STAT_L);
        assertThat(testThbBallsAndStarsHitStats.getBallType()).isEqualTo(DEFAULT_BALL_TYPE);
        assertThat(testThbBallsAndStarsHitStats.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);

        // Validate the ThbBallsAndStarsHitStats in Elasticsearch
        ThbBallsAndStarsHitStats thbBallsAndStarsHitStatsEs = thbBallsAndStarsHitStatsSearchRepository.findOne(testThbBallsAndStarsHitStats.getId());
        assertThat(thbBallsAndStarsHitStatsEs).isEqualToComparingFieldByField(testThbBallsAndStarsHitStats);
    }

    @Test
    @Transactional
    public void createThbBallsAndStarsHitStatsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = thbBallsAndStarsHitStatsRepository.findAll().size();

        // Create the ThbBallsAndStarsHitStats with an existing ID
        ThbBallsAndStarsHitStats existingThbBallsAndStarsHitStats = new ThbBallsAndStarsHitStats();
        existingThbBallsAndStarsHitStats.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restThbBallsAndStarsHitStatsMockMvc.perform(post("/api/thb-balls-and-stars-hit-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingThbBallsAndStarsHitStats)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ThbBallsAndStarsHitStats> thbBallsAndStarsHitStatsList = thbBallsAndStarsHitStatsRepository.findAll();
        assertThat(thbBallsAndStarsHitStatsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = thbBallsAndStarsHitStatsRepository.findAll().size();
        // set the field null
        thbBallsAndStarsHitStats.setNumber(null);

        // Create the ThbBallsAndStarsHitStats, which fails.

        restThbBallsAndStarsHitStatsMockMvc.perform(post("/api/thb-balls-and-stars-hit-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thbBallsAndStarsHitStats)))
            .andExpect(status().isBadRequest());

        List<ThbBallsAndStarsHitStats> thbBallsAndStarsHitStatsList = thbBallsAndStarsHitStatsRepository.findAll();
        assertThat(thbBallsAndStarsHitStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatSIsRequired() throws Exception {
        int databaseSizeBeforeTest = thbBallsAndStarsHitStatsRepository.findAll().size();
        // set the field null
        thbBallsAndStarsHitStats.setStatS(null);

        // Create the ThbBallsAndStarsHitStats, which fails.

        restThbBallsAndStarsHitStatsMockMvc.perform(post("/api/thb-balls-and-stars-hit-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thbBallsAndStarsHitStats)))
            .andExpect(status().isBadRequest());

        List<ThbBallsAndStarsHitStats> thbBallsAndStarsHitStatsList = thbBallsAndStarsHitStatsRepository.findAll();
        assertThat(thbBallsAndStarsHitStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBallTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = thbBallsAndStarsHitStatsRepository.findAll().size();
        // set the field null
        thbBallsAndStarsHitStats.setBallType(null);

        // Create the ThbBallsAndStarsHitStats, which fails.

        restThbBallsAndStarsHitStatsMockMvc.perform(post("/api/thb-balls-and-stars-hit-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thbBallsAndStarsHitStats)))
            .andExpect(status().isBadRequest());

        List<ThbBallsAndStarsHitStats> thbBallsAndStarsHitStatsList = thbBallsAndStarsHitStatsRepository.findAll();
        assertThat(thbBallsAndStarsHitStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllThbBallsAndStarsHitStats() throws Exception {
        // Initialize the database
        thbBallsAndStarsHitStatsRepository.saveAndFlush(thbBallsAndStarsHitStats);

        // Get all the thbBallsAndStarsHitStatsList
        restThbBallsAndStarsHitStatsMockMvc.perform(get("/api/thb-balls-and-stars-hit-stats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thbBallsAndStarsHitStats.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].statS").value(hasItem(DEFAULT_STAT_S)))
            .andExpect(jsonPath("$.[*].statL").value(hasItem(DEFAULT_STAT_L)))
            .andExpect(jsonPath("$.[*].ballType").value(hasItem(DEFAULT_BALL_TYPE.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP)));
    }

    @Test
    @Transactional
    public void getThbBallsAndStarsHitStats() throws Exception {
        // Initialize the database
        thbBallsAndStarsHitStatsRepository.saveAndFlush(thbBallsAndStarsHitStats);

        // Get the thbBallsAndStarsHitStats
        restThbBallsAndStarsHitStatsMockMvc.perform(get("/api/thb-balls-and-stars-hit-stats/{id}", thbBallsAndStarsHitStats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(thbBallsAndStarsHitStats.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.statS").value(DEFAULT_STAT_S))
            .andExpect(jsonPath("$.statL").value(DEFAULT_STAT_L))
            .andExpect(jsonPath("$.ballType").value(DEFAULT_BALL_TYPE.toString()))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP));
    }

    @Test
    @Transactional
    public void getNonExistingThbBallsAndStarsHitStats() throws Exception {
        // Get the thbBallsAndStarsHitStats
        restThbBallsAndStarsHitStatsMockMvc.perform(get("/api/thb-balls-and-stars-hit-stats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThbBallsAndStarsHitStats() throws Exception {
        // Initialize the database
        thbBallsAndStarsHitStatsService.save(thbBallsAndStarsHitStats);

        int databaseSizeBeforeUpdate = thbBallsAndStarsHitStatsRepository.findAll().size();

        // Update the thbBallsAndStarsHitStats
        ThbBallsAndStarsHitStats updatedThbBallsAndStarsHitStats = thbBallsAndStarsHitStatsRepository.findOne(thbBallsAndStarsHitStats.getId());
        updatedThbBallsAndStarsHitStats
                .number(UPDATED_NUMBER)
                .statS(UPDATED_STAT_S)
                .statL(UPDATED_STAT_L)
                .ballType(UPDATED_BALL_TYPE)
                .timestamp(UPDATED_TIMESTAMP);

        restThbBallsAndStarsHitStatsMockMvc.perform(put("/api/thb-balls-and-stars-hit-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedThbBallsAndStarsHitStats)))
            .andExpect(status().isOk());

        // Validate the ThbBallsAndStarsHitStats in the database
        List<ThbBallsAndStarsHitStats> thbBallsAndStarsHitStatsList = thbBallsAndStarsHitStatsRepository.findAll();
        assertThat(thbBallsAndStarsHitStatsList).hasSize(databaseSizeBeforeUpdate);
        ThbBallsAndStarsHitStats testThbBallsAndStarsHitStats = thbBallsAndStarsHitStatsList.get(thbBallsAndStarsHitStatsList.size() - 1);
        assertThat(testThbBallsAndStarsHitStats.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testThbBallsAndStarsHitStats.getStatS()).isEqualTo(UPDATED_STAT_S);
        assertThat(testThbBallsAndStarsHitStats.getStatL()).isEqualTo(UPDATED_STAT_L);
        assertThat(testThbBallsAndStarsHitStats.getBallType()).isEqualTo(UPDATED_BALL_TYPE);
        assertThat(testThbBallsAndStarsHitStats.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);

        // Validate the ThbBallsAndStarsHitStats in Elasticsearch
        ThbBallsAndStarsHitStats thbBallsAndStarsHitStatsEs = thbBallsAndStarsHitStatsSearchRepository.findOne(testThbBallsAndStarsHitStats.getId());
        assertThat(thbBallsAndStarsHitStatsEs).isEqualToComparingFieldByField(testThbBallsAndStarsHitStats);
    }

    @Test
    @Transactional
    public void updateNonExistingThbBallsAndStarsHitStats() throws Exception {
        int databaseSizeBeforeUpdate = thbBallsAndStarsHitStatsRepository.findAll().size();

        // Create the ThbBallsAndStarsHitStats

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restThbBallsAndStarsHitStatsMockMvc.perform(put("/api/thb-balls-and-stars-hit-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thbBallsAndStarsHitStats)))
            .andExpect(status().isCreated());

        // Validate the ThbBallsAndStarsHitStats in the database
        List<ThbBallsAndStarsHitStats> thbBallsAndStarsHitStatsList = thbBallsAndStarsHitStatsRepository.findAll();
        assertThat(thbBallsAndStarsHitStatsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteThbBallsAndStarsHitStats() throws Exception {
        // Initialize the database
        thbBallsAndStarsHitStatsService.save(thbBallsAndStarsHitStats);

        int databaseSizeBeforeDelete = thbBallsAndStarsHitStatsRepository.findAll().size();

        // Get the thbBallsAndStarsHitStats
        restThbBallsAndStarsHitStatsMockMvc.perform(delete("/api/thb-balls-and-stars-hit-stats/{id}", thbBallsAndStarsHitStats.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean thbBallsAndStarsHitStatsExistsInEs = thbBallsAndStarsHitStatsSearchRepository.exists(thbBallsAndStarsHitStats.getId());
        assertThat(thbBallsAndStarsHitStatsExistsInEs).isFalse();

        // Validate the database is empty
        List<ThbBallsAndStarsHitStats> thbBallsAndStarsHitStatsList = thbBallsAndStarsHitStatsRepository.findAll();
        assertThat(thbBallsAndStarsHitStatsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchThbBallsAndStarsHitStats() throws Exception {
        // Initialize the database
        thbBallsAndStarsHitStatsService.save(thbBallsAndStarsHitStats);

        // Search the thbBallsAndStarsHitStats
        restThbBallsAndStarsHitStatsMockMvc.perform(get("/api/_search/thb-balls-and-stars-hit-stats?query=id:" + thbBallsAndStarsHitStats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thbBallsAndStarsHitStats.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].statS").value(hasItem(DEFAULT_STAT_S)))
            .andExpect(jsonPath("$.[*].statL").value(hasItem(DEFAULT_STAT_L)))
            .andExpect(jsonPath("$.[*].ballType").value(hasItem(DEFAULT_BALL_TYPE.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThbBallsAndStarsHitStats.class);
    }
}
