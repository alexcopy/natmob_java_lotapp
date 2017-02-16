package ru.m2mcom.natmob.web.rest;

import ru.m2mcom.natmob.LotappApp;

import ru.m2mcom.natmob.domain.Thb_history;
import ru.m2mcom.natmob.repository.Thb_historyRepository;
import ru.m2mcom.natmob.service.Thb_historyService;
import ru.m2mcom.natmob.repository.search.Thb_historySearchRepository;

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
 * Test class for the Thb_historyResource REST controller.
 *
 * @see Thb_historyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LotappApp.class)
public class Thb_historyResourceIntTest {

    private static final String DEFAULT_DRAW_DATE = "92-D-44";
    private static final String UPDATED_DRAW_DATE = "32-P-13";

    private static final Integer DEFAULT_BALL_1 = 1;
    private static final Integer UPDATED_BALL_1 = 2;

    private static final Integer DEFAULT_BALL_2 = 1;
    private static final Integer UPDATED_BALL_2 = 2;

    private static final Integer DEFAULT_BALL_3 = 1;
    private static final Integer UPDATED_BALL_3 = 2;

    private static final Integer DEFAULT_BALL_4 = 1;
    private static final Integer UPDATED_BALL_4 = 2;

    private static final Integer DEFAULT_BALL_5 = 1;
    private static final Integer UPDATED_BALL_5 = 2;

    private static final Integer DEFAULT_THUNDER_BALL = 1;
    private static final Integer UPDATED_THUNDER_BALL = 2;

    private static final Integer DEFAULT_SUM_B = 1;
    private static final Integer UPDATED_SUM_B = 2;

    private static final String DEFAULT_MACHINE = "AAAAAAAAAA";
    private static final String UPDATED_MACHINE = "BBBBBBBBBB";

    private static final String DEFAULT_BALL_SET = "AAAAAAAAAA";
    private static final String UPDATED_BALL_SET = "BBBBBBBBBB";

    private static final Integer DEFAULT_DRAW_NUMBER = 1;
    private static final Integer UPDATED_DRAW_NUMBER = 2;

    private static final String DEFAULT_HASH = "AAAAAAAAAA";
    private static final String UPDATED_HASH = "BBBBBBBBBB";

    private static final Integer DEFAULT_TIMESTAMP = 1;
    private static final Integer UPDATED_TIMESTAMP = 2;

    @Autowired
    private Thb_historyRepository thb_historyRepository;

    @Autowired
    private Thb_historyService thb_historyService;

    @Autowired
    private Thb_historySearchRepository thb_historySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restThb_historyMockMvc;

    private Thb_history thb_history;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Thb_historyResource thb_historyResource = new Thb_historyResource(thb_historyService);
        this.restThb_historyMockMvc = MockMvcBuilders.standaloneSetup(thb_historyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Thb_history createEntity(EntityManager em) {
        Thb_history thb_history = new Thb_history()
                .drawDate(DEFAULT_DRAW_DATE)
                .ball1(DEFAULT_BALL_1)
                .ball2(DEFAULT_BALL_2)
                .ball3(DEFAULT_BALL_3)
                .ball4(DEFAULT_BALL_4)
                .ball5(DEFAULT_BALL_5)
                .thunderBall(DEFAULT_THUNDER_BALL)
                .sumB(DEFAULT_SUM_B)
                .machine(DEFAULT_MACHINE)
                .ballSet(DEFAULT_BALL_SET)
                .drawNumber(DEFAULT_DRAW_NUMBER)
                .hash(DEFAULT_HASH)
                .timestamp(DEFAULT_TIMESTAMP);
        return thb_history;
    }

    @Before
    public void initTest() {
        thb_historySearchRepository.deleteAll();
        thb_history = createEntity(em);
    }

    @Test
    @Transactional
    public void createThb_history() throws Exception {
        int databaseSizeBeforeCreate = thb_historyRepository.findAll().size();

        // Create the Thb_history

        restThb_historyMockMvc.perform(post("/api/thb-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thb_history)))
            .andExpect(status().isCreated());

        // Validate the Thb_history in the database
        List<Thb_history> thb_historyList = thb_historyRepository.findAll();
        assertThat(thb_historyList).hasSize(databaseSizeBeforeCreate + 1);
        Thb_history testThb_history = thb_historyList.get(thb_historyList.size() - 1);
        assertThat(testThb_history.getDrawDate()).isEqualTo(DEFAULT_DRAW_DATE);
        assertThat(testThb_history.getBall1()).isEqualTo(DEFAULT_BALL_1);
        assertThat(testThb_history.getBall2()).isEqualTo(DEFAULT_BALL_2);
        assertThat(testThb_history.getBall3()).isEqualTo(DEFAULT_BALL_3);
        assertThat(testThb_history.getBall4()).isEqualTo(DEFAULT_BALL_4);
        assertThat(testThb_history.getBall5()).isEqualTo(DEFAULT_BALL_5);
        assertThat(testThb_history.getThunderBall()).isEqualTo(DEFAULT_THUNDER_BALL);
        assertThat(testThb_history.getSumB()).isEqualTo(DEFAULT_SUM_B);
        assertThat(testThb_history.getMachine()).isEqualTo(DEFAULT_MACHINE);
        assertThat(testThb_history.getBallSet()).isEqualTo(DEFAULT_BALL_SET);
        assertThat(testThb_history.getDrawNumber()).isEqualTo(DEFAULT_DRAW_NUMBER);
        assertThat(testThb_history.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testThb_history.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);

        // Validate the Thb_history in Elasticsearch
        Thb_history thb_historyEs = thb_historySearchRepository.findOne(testThb_history.getId());
        assertThat(thb_historyEs).isEqualToComparingFieldByField(testThb_history);
    }

    @Test
    @Transactional
    public void createThb_historyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = thb_historyRepository.findAll().size();

        // Create the Thb_history with an existing ID
        Thb_history existingThb_history = new Thb_history();
        existingThb_history.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restThb_historyMockMvc.perform(post("/api/thb-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingThb_history)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Thb_history> thb_historyList = thb_historyRepository.findAll();
        assertThat(thb_historyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDrawDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = thb_historyRepository.findAll().size();
        // set the field null
        thb_history.setDrawDate(null);

        // Create the Thb_history, which fails.

        restThb_historyMockMvc.perform(post("/api/thb-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thb_history)))
            .andExpect(status().isBadRequest());

        List<Thb_history> thb_historyList = thb_historyRepository.findAll();
        assertThat(thb_historyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBall1IsRequired() throws Exception {
        int databaseSizeBeforeTest = thb_historyRepository.findAll().size();
        // set the field null
        thb_history.setBall1(null);

        // Create the Thb_history, which fails.

        restThb_historyMockMvc.perform(post("/api/thb-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thb_history)))
            .andExpect(status().isBadRequest());

        List<Thb_history> thb_historyList = thb_historyRepository.findAll();
        assertThat(thb_historyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBall2IsRequired() throws Exception {
        int databaseSizeBeforeTest = thb_historyRepository.findAll().size();
        // set the field null
        thb_history.setBall2(null);

        // Create the Thb_history, which fails.

        restThb_historyMockMvc.perform(post("/api/thb-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thb_history)))
            .andExpect(status().isBadRequest());

        List<Thb_history> thb_historyList = thb_historyRepository.findAll();
        assertThat(thb_historyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBall3IsRequired() throws Exception {
        int databaseSizeBeforeTest = thb_historyRepository.findAll().size();
        // set the field null
        thb_history.setBall3(null);

        // Create the Thb_history, which fails.

        restThb_historyMockMvc.perform(post("/api/thb-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thb_history)))
            .andExpect(status().isBadRequest());

        List<Thb_history> thb_historyList = thb_historyRepository.findAll();
        assertThat(thb_historyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBall4IsRequired() throws Exception {
        int databaseSizeBeforeTest = thb_historyRepository.findAll().size();
        // set the field null
        thb_history.setBall4(null);

        // Create the Thb_history, which fails.

        restThb_historyMockMvc.perform(post("/api/thb-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thb_history)))
            .andExpect(status().isBadRequest());

        List<Thb_history> thb_historyList = thb_historyRepository.findAll();
        assertThat(thb_historyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBall5IsRequired() throws Exception {
        int databaseSizeBeforeTest = thb_historyRepository.findAll().size();
        // set the field null
        thb_history.setBall5(null);

        // Create the Thb_history, which fails.

        restThb_historyMockMvc.perform(post("/api/thb-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thb_history)))
            .andExpect(status().isBadRequest());

        List<Thb_history> thb_historyList = thb_historyRepository.findAll();
        assertThat(thb_historyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkThunderBallIsRequired() throws Exception {
        int databaseSizeBeforeTest = thb_historyRepository.findAll().size();
        // set the field null
        thb_history.setThunderBall(null);

        // Create the Thb_history, which fails.

        restThb_historyMockMvc.perform(post("/api/thb-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thb_history)))
            .andExpect(status().isBadRequest());

        List<Thb_history> thb_historyList = thb_historyRepository.findAll();
        assertThat(thb_historyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSumBIsRequired() throws Exception {
        int databaseSizeBeforeTest = thb_historyRepository.findAll().size();
        // set the field null
        thb_history.setSumB(null);

        // Create the Thb_history, which fails.

        restThb_historyMockMvc.perform(post("/api/thb-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thb_history)))
            .andExpect(status().isBadRequest());

        List<Thb_history> thb_historyList = thb_historyRepository.findAll();
        assertThat(thb_historyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllThb_histories() throws Exception {
        // Initialize the database
        thb_historyRepository.saveAndFlush(thb_history);

        // Get all the thb_historyList
        restThb_historyMockMvc.perform(get("/api/thb-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thb_history.getId().intValue())))
            .andExpect(jsonPath("$.[*].drawDate").value(hasItem(DEFAULT_DRAW_DATE.toString())))
            .andExpect(jsonPath("$.[*].ball1").value(hasItem(DEFAULT_BALL_1)))
            .andExpect(jsonPath("$.[*].ball2").value(hasItem(DEFAULT_BALL_2)))
            .andExpect(jsonPath("$.[*].ball3").value(hasItem(DEFAULT_BALL_3)))
            .andExpect(jsonPath("$.[*].ball4").value(hasItem(DEFAULT_BALL_4)))
            .andExpect(jsonPath("$.[*].ball5").value(hasItem(DEFAULT_BALL_5)))
            .andExpect(jsonPath("$.[*].thunderBall").value(hasItem(DEFAULT_THUNDER_BALL)))
            .andExpect(jsonPath("$.[*].sumB").value(hasItem(DEFAULT_SUM_B)))
            .andExpect(jsonPath("$.[*].machine").value(hasItem(DEFAULT_MACHINE.toString())))
            .andExpect(jsonPath("$.[*].ballSet").value(hasItem(DEFAULT_BALL_SET.toString())))
            .andExpect(jsonPath("$.[*].drawNumber").value(hasItem(DEFAULT_DRAW_NUMBER)))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP)));
    }

    @Test
    @Transactional
    public void getThb_history() throws Exception {
        // Initialize the database
        thb_historyRepository.saveAndFlush(thb_history);

        // Get the thb_history
        restThb_historyMockMvc.perform(get("/api/thb-histories/{id}", thb_history.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(thb_history.getId().intValue()))
            .andExpect(jsonPath("$.drawDate").value(DEFAULT_DRAW_DATE.toString()))
            .andExpect(jsonPath("$.ball1").value(DEFAULT_BALL_1))
            .andExpect(jsonPath("$.ball2").value(DEFAULT_BALL_2))
            .andExpect(jsonPath("$.ball3").value(DEFAULT_BALL_3))
            .andExpect(jsonPath("$.ball4").value(DEFAULT_BALL_4))
            .andExpect(jsonPath("$.ball5").value(DEFAULT_BALL_5))
            .andExpect(jsonPath("$.thunderBall").value(DEFAULT_THUNDER_BALL))
            .andExpect(jsonPath("$.sumB").value(DEFAULT_SUM_B))
            .andExpect(jsonPath("$.machine").value(DEFAULT_MACHINE.toString()))
            .andExpect(jsonPath("$.ballSet").value(DEFAULT_BALL_SET.toString()))
            .andExpect(jsonPath("$.drawNumber").value(DEFAULT_DRAW_NUMBER))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH.toString()))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP));
    }

    @Test
    @Transactional
    public void getNonExistingThb_history() throws Exception {
        // Get the thb_history
        restThb_historyMockMvc.perform(get("/api/thb-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThb_history() throws Exception {
        // Initialize the database
        thb_historyService.save(thb_history);

        int databaseSizeBeforeUpdate = thb_historyRepository.findAll().size();

        // Update the thb_history
        Thb_history updatedThb_history = thb_historyRepository.findOne(thb_history.getId());
        updatedThb_history
                .drawDate(UPDATED_DRAW_DATE)
                .ball1(UPDATED_BALL_1)
                .ball2(UPDATED_BALL_2)
                .ball3(UPDATED_BALL_3)
                .ball4(UPDATED_BALL_4)
                .ball5(UPDATED_BALL_5)
                .thunderBall(UPDATED_THUNDER_BALL)
                .sumB(UPDATED_SUM_B)
                .machine(UPDATED_MACHINE)
                .ballSet(UPDATED_BALL_SET)
                .drawNumber(UPDATED_DRAW_NUMBER)
                .hash(UPDATED_HASH)
                .timestamp(UPDATED_TIMESTAMP);

        restThb_historyMockMvc.perform(put("/api/thb-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedThb_history)))
            .andExpect(status().isOk());

        // Validate the Thb_history in the database
        List<Thb_history> thb_historyList = thb_historyRepository.findAll();
        assertThat(thb_historyList).hasSize(databaseSizeBeforeUpdate);
        Thb_history testThb_history = thb_historyList.get(thb_historyList.size() - 1);
        assertThat(testThb_history.getDrawDate()).isEqualTo(UPDATED_DRAW_DATE);
        assertThat(testThb_history.getBall1()).isEqualTo(UPDATED_BALL_1);
        assertThat(testThb_history.getBall2()).isEqualTo(UPDATED_BALL_2);
        assertThat(testThb_history.getBall3()).isEqualTo(UPDATED_BALL_3);
        assertThat(testThb_history.getBall4()).isEqualTo(UPDATED_BALL_4);
        assertThat(testThb_history.getBall5()).isEqualTo(UPDATED_BALL_5);
        assertThat(testThb_history.getThunderBall()).isEqualTo(UPDATED_THUNDER_BALL);
        assertThat(testThb_history.getSumB()).isEqualTo(UPDATED_SUM_B);
        assertThat(testThb_history.getMachine()).isEqualTo(UPDATED_MACHINE);
        assertThat(testThb_history.getBallSet()).isEqualTo(UPDATED_BALL_SET);
        assertThat(testThb_history.getDrawNumber()).isEqualTo(UPDATED_DRAW_NUMBER);
        assertThat(testThb_history.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testThb_history.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);

        // Validate the Thb_history in Elasticsearch
        Thb_history thb_historyEs = thb_historySearchRepository.findOne(testThb_history.getId());
        assertThat(thb_historyEs).isEqualToComparingFieldByField(testThb_history);
    }

    @Test
    @Transactional
    public void updateNonExistingThb_history() throws Exception {
        int databaseSizeBeforeUpdate = thb_historyRepository.findAll().size();

        // Create the Thb_history

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restThb_historyMockMvc.perform(put("/api/thb-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thb_history)))
            .andExpect(status().isCreated());

        // Validate the Thb_history in the database
        List<Thb_history> thb_historyList = thb_historyRepository.findAll();
        assertThat(thb_historyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteThb_history() throws Exception {
        // Initialize the database
        thb_historyService.save(thb_history);

        int databaseSizeBeforeDelete = thb_historyRepository.findAll().size();

        // Get the thb_history
        restThb_historyMockMvc.perform(delete("/api/thb-histories/{id}", thb_history.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean thb_historyExistsInEs = thb_historySearchRepository.exists(thb_history.getId());
        assertThat(thb_historyExistsInEs).isFalse();

        // Validate the database is empty
        List<Thb_history> thb_historyList = thb_historyRepository.findAll();
        assertThat(thb_historyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchThb_history() throws Exception {
        // Initialize the database
        thb_historyService.save(thb_history);

        // Search the thb_history
        restThb_historyMockMvc.perform(get("/api/_search/thb-histories?query=id:" + thb_history.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thb_history.getId().intValue())))
            .andExpect(jsonPath("$.[*].drawDate").value(hasItem(DEFAULT_DRAW_DATE.toString())))
            .andExpect(jsonPath("$.[*].ball1").value(hasItem(DEFAULT_BALL_1)))
            .andExpect(jsonPath("$.[*].ball2").value(hasItem(DEFAULT_BALL_2)))
            .andExpect(jsonPath("$.[*].ball3").value(hasItem(DEFAULT_BALL_3)))
            .andExpect(jsonPath("$.[*].ball4").value(hasItem(DEFAULT_BALL_4)))
            .andExpect(jsonPath("$.[*].ball5").value(hasItem(DEFAULT_BALL_5)))
            .andExpect(jsonPath("$.[*].thunderBall").value(hasItem(DEFAULT_THUNDER_BALL)))
            .andExpect(jsonPath("$.[*].sumB").value(hasItem(DEFAULT_SUM_B)))
            .andExpect(jsonPath("$.[*].machine").value(hasItem(DEFAULT_MACHINE.toString())))
            .andExpect(jsonPath("$.[*].ballSet").value(hasItem(DEFAULT_BALL_SET.toString())))
            .andExpect(jsonPath("$.[*].drawNumber").value(hasItem(DEFAULT_DRAW_NUMBER)))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Thb_history.class);
    }
}
