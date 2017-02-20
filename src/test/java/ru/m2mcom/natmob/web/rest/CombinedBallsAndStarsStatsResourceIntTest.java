package ru.m2mcom.natmob.web.rest;

import ru.m2mcom.natmob.LotappApp;

import ru.m2mcom.natmob.domain.CombinedBallsAndStarsStats;
import ru.m2mcom.natmob.repository.CombinedBallsAndStarsStatsRepository;
import ru.m2mcom.natmob.service.CombinedBallsAndStarsStatsService;
import ru.m2mcom.natmob.repository.search.CombinedBallsAndStarsStatsSearchRepository;

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
 * Test class for the CombinedBallsAndStarsStatsResource REST controller.
 *
 * @see CombinedBallsAndStarsStatsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LotappApp.class)
public class CombinedBallsAndStarsStatsResourceIntTest {

    private static final Integer DEFAULT_BALL_1 = 1;
    private static final Integer UPDATED_BALL_1 = 2;

    private static final Integer DEFAULT_BALL_2 = 1;
    private static final Integer UPDATED_BALL_2 = 2;

    private static final Integer DEFAULT_BALL_3 = 1;
    private static final Integer UPDATED_BALL_3 = 2;

    private static final Integer DEFAULT_BALL_4 = 1;
    private static final Integer UPDATED_BALL_4 = 2;

    private static final Integer DEFAULT_TIMES_S = 1;
    private static final Integer UPDATED_TIMES_S = 2;

    private static final Integer DEFAULT_TIMES_L = 1;
    private static final Integer UPDATED_TIMES_L = 2;

    private static final Integer DEFAULT_SUM = 1;
    private static final Integer UPDATED_SUM = 2;

    private static final Integer DEFAULT_EVENS = 1;
    private static final Integer UPDATED_EVENS = 2;

    private static final String DEFAULT_BALLS_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_BALLS_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_GAME_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_GAME_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUM_OF_BALLS = 1;
    private static final Integer UPDATED_NUM_OF_BALLS = 2;

    private static final Integer DEFAULT_TIMESTAMP = 1;
    private static final Integer UPDATED_TIMESTAMP = 2;

    @Autowired
    private CombinedBallsAndStarsStatsRepository combinedBallsAndStarsStatsRepository;

    @Autowired
    private CombinedBallsAndStarsStatsService combinedBallsAndStarsStatsService;

    @Autowired
    private CombinedBallsAndStarsStatsSearchRepository combinedBallsAndStarsStatsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restCombinedBallsAndStarsStatsMockMvc;

    private CombinedBallsAndStarsStats combinedBallsAndStarsStats;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CombinedBallsAndStarsStatsResource combinedBallsAndStarsStatsResource = new CombinedBallsAndStarsStatsResource(combinedBallsAndStarsStatsService);
        this.restCombinedBallsAndStarsStatsMockMvc = MockMvcBuilders.standaloneSetup(combinedBallsAndStarsStatsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CombinedBallsAndStarsStats createEntity(EntityManager em) {
        CombinedBallsAndStarsStats combinedBallsAndStarsStats = new CombinedBallsAndStarsStats()
                .ball1(DEFAULT_BALL_1)
                .ball2(DEFAULT_BALL_2)
                .ball3(DEFAULT_BALL_3)
                .ball4(DEFAULT_BALL_4)
                .timesS(DEFAULT_TIMES_S)
                .timesL(DEFAULT_TIMES_L)
                .sum(DEFAULT_SUM)
                .evens(DEFAULT_EVENS)
                .ballsType(DEFAULT_BALLS_TYPE)
                .gameType(DEFAULT_GAME_TYPE)
                .numOfBalls(DEFAULT_NUM_OF_BALLS)
                .timestamp(DEFAULT_TIMESTAMP);
        return combinedBallsAndStarsStats;
    }

    @Before
    public void initTest() {
        combinedBallsAndStarsStatsSearchRepository.deleteAll();
        combinedBallsAndStarsStats = createEntity(em);
    }

    @Test
    @Transactional
    public void createCombinedBallsAndStarsStats() throws Exception {
        int databaseSizeBeforeCreate = combinedBallsAndStarsStatsRepository.findAll().size();

        // Create the CombinedBallsAndStarsStats

        restCombinedBallsAndStarsStatsMockMvc.perform(post("/api/combined-balls-and-stars-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(combinedBallsAndStarsStats)))
            .andExpect(status().isCreated());

        // Validate the CombinedBallsAndStarsStats in the database
        List<CombinedBallsAndStarsStats> combinedBallsAndStarsStatsList = combinedBallsAndStarsStatsRepository.findAll();
        assertThat(combinedBallsAndStarsStatsList).hasSize(databaseSizeBeforeCreate + 1);
        CombinedBallsAndStarsStats testCombinedBallsAndStarsStats = combinedBallsAndStarsStatsList.get(combinedBallsAndStarsStatsList.size() - 1);
        assertThat(testCombinedBallsAndStarsStats.getBall1()).isEqualTo(DEFAULT_BALL_1);
        assertThat(testCombinedBallsAndStarsStats.getBall2()).isEqualTo(DEFAULT_BALL_2);
        assertThat(testCombinedBallsAndStarsStats.getBall3()).isEqualTo(DEFAULT_BALL_3);
        assertThat(testCombinedBallsAndStarsStats.getBall4()).isEqualTo(DEFAULT_BALL_4);
        assertThat(testCombinedBallsAndStarsStats.getTimesS()).isEqualTo(DEFAULT_TIMES_S);
        assertThat(testCombinedBallsAndStarsStats.getTimesL()).isEqualTo(DEFAULT_TIMES_L);
        assertThat(testCombinedBallsAndStarsStats.getSum()).isEqualTo(DEFAULT_SUM);
        assertThat(testCombinedBallsAndStarsStats.getEvens()).isEqualTo(DEFAULT_EVENS);
        assertThat(testCombinedBallsAndStarsStats.getBallsType()).isEqualTo(DEFAULT_BALLS_TYPE);
        assertThat(testCombinedBallsAndStarsStats.getGameType()).isEqualTo(DEFAULT_GAME_TYPE);
        assertThat(testCombinedBallsAndStarsStats.getNumOfBalls()).isEqualTo(DEFAULT_NUM_OF_BALLS);
        assertThat(testCombinedBallsAndStarsStats.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);

        // Validate the CombinedBallsAndStarsStats in Elasticsearch
        CombinedBallsAndStarsStats combinedBallsAndStarsStatsEs = combinedBallsAndStarsStatsSearchRepository.findOne(testCombinedBallsAndStarsStats.getId());
        assertThat(combinedBallsAndStarsStatsEs).isEqualToComparingFieldByField(testCombinedBallsAndStarsStats);
    }

    @Test
    @Transactional
    public void createCombinedBallsAndStarsStatsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = combinedBallsAndStarsStatsRepository.findAll().size();

        // Create the CombinedBallsAndStarsStats with an existing ID
        CombinedBallsAndStarsStats existingCombinedBallsAndStarsStats = new CombinedBallsAndStarsStats();
        existingCombinedBallsAndStarsStats.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCombinedBallsAndStarsStatsMockMvc.perform(post("/api/combined-balls-and-stars-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCombinedBallsAndStarsStats)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CombinedBallsAndStarsStats> combinedBallsAndStarsStatsList = combinedBallsAndStarsStatsRepository.findAll();
        assertThat(combinedBallsAndStarsStatsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkBall1IsRequired() throws Exception {
        int databaseSizeBeforeTest = combinedBallsAndStarsStatsRepository.findAll().size();
        // set the field null
        combinedBallsAndStarsStats.setBall1(null);

        // Create the CombinedBallsAndStarsStats, which fails.

        restCombinedBallsAndStarsStatsMockMvc.perform(post("/api/combined-balls-and-stars-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(combinedBallsAndStarsStats)))
            .andExpect(status().isBadRequest());

        List<CombinedBallsAndStarsStats> combinedBallsAndStarsStatsList = combinedBallsAndStarsStatsRepository.findAll();
        assertThat(combinedBallsAndStarsStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTimesSIsRequired() throws Exception {
        int databaseSizeBeforeTest = combinedBallsAndStarsStatsRepository.findAll().size();
        // set the field null
        combinedBallsAndStarsStats.setTimesS(null);

        // Create the CombinedBallsAndStarsStats, which fails.

        restCombinedBallsAndStarsStatsMockMvc.perform(post("/api/combined-balls-and-stars-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(combinedBallsAndStarsStats)))
            .andExpect(status().isBadRequest());

        List<CombinedBallsAndStarsStats> combinedBallsAndStarsStatsList = combinedBallsAndStarsStatsRepository.findAll();
        assertThat(combinedBallsAndStarsStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTimesLIsRequired() throws Exception {
        int databaseSizeBeforeTest = combinedBallsAndStarsStatsRepository.findAll().size();
        // set the field null
        combinedBallsAndStarsStats.setTimesL(null);

        // Create the CombinedBallsAndStarsStats, which fails.

        restCombinedBallsAndStarsStatsMockMvc.perform(post("/api/combined-balls-and-stars-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(combinedBallsAndStarsStats)))
            .andExpect(status().isBadRequest());

        List<CombinedBallsAndStarsStats> combinedBallsAndStarsStatsList = combinedBallsAndStarsStatsRepository.findAll();
        assertThat(combinedBallsAndStarsStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSumIsRequired() throws Exception {
        int databaseSizeBeforeTest = combinedBallsAndStarsStatsRepository.findAll().size();
        // set the field null
        combinedBallsAndStarsStats.setSum(null);

        // Create the CombinedBallsAndStarsStats, which fails.

        restCombinedBallsAndStarsStatsMockMvc.perform(post("/api/combined-balls-and-stars-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(combinedBallsAndStarsStats)))
            .andExpect(status().isBadRequest());

        List<CombinedBallsAndStarsStats> combinedBallsAndStarsStatsList = combinedBallsAndStarsStatsRepository.findAll();
        assertThat(combinedBallsAndStarsStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBallsTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = combinedBallsAndStarsStatsRepository.findAll().size();
        // set the field null
        combinedBallsAndStarsStats.setBallsType(null);

        // Create the CombinedBallsAndStarsStats, which fails.

        restCombinedBallsAndStarsStatsMockMvc.perform(post("/api/combined-balls-and-stars-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(combinedBallsAndStarsStats)))
            .andExpect(status().isBadRequest());

        List<CombinedBallsAndStarsStats> combinedBallsAndStarsStatsList = combinedBallsAndStarsStatsRepository.findAll();
        assertThat(combinedBallsAndStarsStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumOfBallsIsRequired() throws Exception {
        int databaseSizeBeforeTest = combinedBallsAndStarsStatsRepository.findAll().size();
        // set the field null
        combinedBallsAndStarsStats.setNumOfBalls(null);

        // Create the CombinedBallsAndStarsStats, which fails.

        restCombinedBallsAndStarsStatsMockMvc.perform(post("/api/combined-balls-and-stars-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(combinedBallsAndStarsStats)))
            .andExpect(status().isBadRequest());

        List<CombinedBallsAndStarsStats> combinedBallsAndStarsStatsList = combinedBallsAndStarsStatsRepository.findAll();
        assertThat(combinedBallsAndStarsStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCombinedBallsAndStarsStats() throws Exception {
        // Initialize the database
        combinedBallsAndStarsStatsRepository.saveAndFlush(combinedBallsAndStarsStats);

        // Get all the combinedBallsAndStarsStatsList
        restCombinedBallsAndStarsStatsMockMvc.perform(get("/api/combined-balls-and-stars-stats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(combinedBallsAndStarsStats.getId().intValue())))
            .andExpect(jsonPath("$.[*].ball1").value(hasItem(DEFAULT_BALL_1)))
            .andExpect(jsonPath("$.[*].ball2").value(hasItem(DEFAULT_BALL_2)))
            .andExpect(jsonPath("$.[*].ball3").value(hasItem(DEFAULT_BALL_3)))
            .andExpect(jsonPath("$.[*].ball4").value(hasItem(DEFAULT_BALL_4)))
            .andExpect(jsonPath("$.[*].timesS").value(hasItem(DEFAULT_TIMES_S)))
            .andExpect(jsonPath("$.[*].timesL").value(hasItem(DEFAULT_TIMES_L)))
            .andExpect(jsonPath("$.[*].sum").value(hasItem(DEFAULT_SUM)))
            .andExpect(jsonPath("$.[*].evens").value(hasItem(DEFAULT_EVENS)))
            .andExpect(jsonPath("$.[*].ballsType").value(hasItem(DEFAULT_BALLS_TYPE.toString())))
            .andExpect(jsonPath("$.[*].gameType").value(hasItem(DEFAULT_GAME_TYPE.toString())))
            .andExpect(jsonPath("$.[*].numOfBalls").value(hasItem(DEFAULT_NUM_OF_BALLS)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP)));
    }

    @Test
    @Transactional
    public void getCombinedBallsAndStarsStats() throws Exception {
        // Initialize the database
        combinedBallsAndStarsStatsRepository.saveAndFlush(combinedBallsAndStarsStats);

        // Get the combinedBallsAndStarsStats
        restCombinedBallsAndStarsStatsMockMvc.perform(get("/api/combined-balls-and-stars-stats/{id}", combinedBallsAndStarsStats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(combinedBallsAndStarsStats.getId().intValue()))
            .andExpect(jsonPath("$.ball1").value(DEFAULT_BALL_1))
            .andExpect(jsonPath("$.ball2").value(DEFAULT_BALL_2))
            .andExpect(jsonPath("$.ball3").value(DEFAULT_BALL_3))
            .andExpect(jsonPath("$.ball4").value(DEFAULT_BALL_4))
            .andExpect(jsonPath("$.timesS").value(DEFAULT_TIMES_S))
            .andExpect(jsonPath("$.timesL").value(DEFAULT_TIMES_L))
            .andExpect(jsonPath("$.sum").value(DEFAULT_SUM))
            .andExpect(jsonPath("$.evens").value(DEFAULT_EVENS))
            .andExpect(jsonPath("$.ballsType").value(DEFAULT_BALLS_TYPE.toString()))
            .andExpect(jsonPath("$.gameType").value(DEFAULT_GAME_TYPE.toString()))
            .andExpect(jsonPath("$.numOfBalls").value(DEFAULT_NUM_OF_BALLS))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP));
    }

    @Test
    @Transactional
    public void getNonExistingCombinedBallsAndStarsStats() throws Exception {
        // Get the combinedBallsAndStarsStats
        restCombinedBallsAndStarsStatsMockMvc.perform(get("/api/combined-balls-and-stars-stats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCombinedBallsAndStarsStats() throws Exception {
        // Initialize the database
        combinedBallsAndStarsStatsService.save(combinedBallsAndStarsStats);

        int databaseSizeBeforeUpdate = combinedBallsAndStarsStatsRepository.findAll().size();

        // Update the combinedBallsAndStarsStats
        CombinedBallsAndStarsStats updatedCombinedBallsAndStarsStats = combinedBallsAndStarsStatsRepository.findOne(combinedBallsAndStarsStats.getId());
        updatedCombinedBallsAndStarsStats
                .ball1(UPDATED_BALL_1)
                .ball2(UPDATED_BALL_2)
                .ball3(UPDATED_BALL_3)
                .ball4(UPDATED_BALL_4)
                .timesS(UPDATED_TIMES_S)
                .timesL(UPDATED_TIMES_L)
                .sum(UPDATED_SUM)
                .evens(UPDATED_EVENS)
                .ballsType(UPDATED_BALLS_TYPE)
                .gameType(UPDATED_GAME_TYPE)
                .numOfBalls(UPDATED_NUM_OF_BALLS)
                .timestamp(UPDATED_TIMESTAMP);

        restCombinedBallsAndStarsStatsMockMvc.perform(put("/api/combined-balls-and-stars-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCombinedBallsAndStarsStats)))
            .andExpect(status().isOk());

        // Validate the CombinedBallsAndStarsStats in the database
        List<CombinedBallsAndStarsStats> combinedBallsAndStarsStatsList = combinedBallsAndStarsStatsRepository.findAll();
        assertThat(combinedBallsAndStarsStatsList).hasSize(databaseSizeBeforeUpdate);
        CombinedBallsAndStarsStats testCombinedBallsAndStarsStats = combinedBallsAndStarsStatsList.get(combinedBallsAndStarsStatsList.size() - 1);
        assertThat(testCombinedBallsAndStarsStats.getBall1()).isEqualTo(UPDATED_BALL_1);
        assertThat(testCombinedBallsAndStarsStats.getBall2()).isEqualTo(UPDATED_BALL_2);
        assertThat(testCombinedBallsAndStarsStats.getBall3()).isEqualTo(UPDATED_BALL_3);
        assertThat(testCombinedBallsAndStarsStats.getBall4()).isEqualTo(UPDATED_BALL_4);
        assertThat(testCombinedBallsAndStarsStats.getTimesS()).isEqualTo(UPDATED_TIMES_S);
        assertThat(testCombinedBallsAndStarsStats.getTimesL()).isEqualTo(UPDATED_TIMES_L);
        assertThat(testCombinedBallsAndStarsStats.getSum()).isEqualTo(UPDATED_SUM);
        assertThat(testCombinedBallsAndStarsStats.getEvens()).isEqualTo(UPDATED_EVENS);
        assertThat(testCombinedBallsAndStarsStats.getBallsType()).isEqualTo(UPDATED_BALLS_TYPE);
        assertThat(testCombinedBallsAndStarsStats.getGameType()).isEqualTo(UPDATED_GAME_TYPE);
        assertThat(testCombinedBallsAndStarsStats.getNumOfBalls()).isEqualTo(UPDATED_NUM_OF_BALLS);
        assertThat(testCombinedBallsAndStarsStats.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);

        // Validate the CombinedBallsAndStarsStats in Elasticsearch
        CombinedBallsAndStarsStats combinedBallsAndStarsStatsEs = combinedBallsAndStarsStatsSearchRepository.findOne(testCombinedBallsAndStarsStats.getId());
        assertThat(combinedBallsAndStarsStatsEs).isEqualToComparingFieldByField(testCombinedBallsAndStarsStats);
    }

    @Test
    @Transactional
    public void updateNonExistingCombinedBallsAndStarsStats() throws Exception {
        int databaseSizeBeforeUpdate = combinedBallsAndStarsStatsRepository.findAll().size();

        // Create the CombinedBallsAndStarsStats

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCombinedBallsAndStarsStatsMockMvc.perform(put("/api/combined-balls-and-stars-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(combinedBallsAndStarsStats)))
            .andExpect(status().isCreated());

        // Validate the CombinedBallsAndStarsStats in the database
        List<CombinedBallsAndStarsStats> combinedBallsAndStarsStatsList = combinedBallsAndStarsStatsRepository.findAll();
        assertThat(combinedBallsAndStarsStatsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCombinedBallsAndStarsStats() throws Exception {
        // Initialize the database
        combinedBallsAndStarsStatsService.save(combinedBallsAndStarsStats);

        int databaseSizeBeforeDelete = combinedBallsAndStarsStatsRepository.findAll().size();

        // Get the combinedBallsAndStarsStats
        restCombinedBallsAndStarsStatsMockMvc.perform(delete("/api/combined-balls-and-stars-stats/{id}", combinedBallsAndStarsStats.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean combinedBallsAndStarsStatsExistsInEs = combinedBallsAndStarsStatsSearchRepository.exists(combinedBallsAndStarsStats.getId());
        assertThat(combinedBallsAndStarsStatsExistsInEs).isFalse();

        // Validate the database is empty
        List<CombinedBallsAndStarsStats> combinedBallsAndStarsStatsList = combinedBallsAndStarsStatsRepository.findAll();
        assertThat(combinedBallsAndStarsStatsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCombinedBallsAndStarsStats() throws Exception {
        // Initialize the database
        combinedBallsAndStarsStatsService.save(combinedBallsAndStarsStats);

        // Search the combinedBallsAndStarsStats
        restCombinedBallsAndStarsStatsMockMvc.perform(get("/api/_search/combined-balls-and-stars-stats?query=id:" + combinedBallsAndStarsStats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(combinedBallsAndStarsStats.getId().intValue())))
            .andExpect(jsonPath("$.[*].ball1").value(hasItem(DEFAULT_BALL_1)))
            .andExpect(jsonPath("$.[*].ball2").value(hasItem(DEFAULT_BALL_2)))
            .andExpect(jsonPath("$.[*].ball3").value(hasItem(DEFAULT_BALL_3)))
            .andExpect(jsonPath("$.[*].ball4").value(hasItem(DEFAULT_BALL_4)))
            .andExpect(jsonPath("$.[*].timesS").value(hasItem(DEFAULT_TIMES_S)))
            .andExpect(jsonPath("$.[*].timesL").value(hasItem(DEFAULT_TIMES_L)))
            .andExpect(jsonPath("$.[*].sum").value(hasItem(DEFAULT_SUM)))
            .andExpect(jsonPath("$.[*].evens").value(hasItem(DEFAULT_EVENS)))
            .andExpect(jsonPath("$.[*].ballsType").value(hasItem(DEFAULT_BALLS_TYPE.toString())))
            .andExpect(jsonPath("$.[*].gameType").value(hasItem(DEFAULT_GAME_TYPE.toString())))
            .andExpect(jsonPath("$.[*].numOfBalls").value(hasItem(DEFAULT_NUM_OF_BALLS)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CombinedBallsAndStarsStats.class);
    }
}
