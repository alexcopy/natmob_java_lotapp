package ru.m2mcom.natmob.web.rest;

import ru.m2mcom.natmob.LotappApp;

import ru.m2mcom.natmob.domain.Nat_history;
import ru.m2mcom.natmob.repository.Nat_historyRepository;
import ru.m2mcom.natmob.service.Nat_historyService;
import ru.m2mcom.natmob.repository.search.Nat_historySearchRepository;

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
 * Test class for the Nat_historyResource REST controller.
 *
 * @see Nat_historyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LotappApp.class)
public class Nat_historyResourceIntTest {

    private static final String DEFAULT_DRAW_DATE = "56-S-43";
    private static final String UPDATED_DRAW_DATE = "02-N0-08";

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

    private static final Integer DEFAULT_BALL_6 = 1;
    private static final Integer UPDATED_BALL_6 = 2;

    private static final Integer DEFAULT_BONUS_BALL = 1;
    private static final Integer UPDATED_BONUS_BALL = 2;

    private static final String DEFAULT_MACHINE = "AAAAAAAAAA";
    private static final String UPDATED_MACHINE = "BBBBBBBBBB";

    private static final String DEFAULT_RAFFLES = "AAAAAAAAAA";
    private static final String UPDATED_RAFFLES = "BBBBBBBBBB";

    private static final String DEFAULT_DRAW_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_DRAW_NUMBER = "BBBBBBBBBB";

    private static final Integer DEFAULT_SUM_B = 1;
    private static final Integer UPDATED_SUM_B = 2;

    private static final String DEFAULT_HASH = "AAAAAAAAAA";
    private static final String UPDATED_HASH = "BBBBBBBBBB";

    private static final Integer DEFAULT_TIMESTAMP = 1;
    private static final Integer UPDATED_TIMESTAMP = 2;

    @Autowired
    private Nat_historyRepository nat_historyRepository;

    @Autowired
    private Nat_historyService nat_historyService;

    @Autowired
    private Nat_historySearchRepository nat_historySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restNat_historyMockMvc;

    private Nat_history nat_history;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Nat_historyResource nat_historyResource = new Nat_historyResource(nat_historyService);
        this.restNat_historyMockMvc = MockMvcBuilders.standaloneSetup(nat_historyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nat_history createEntity(EntityManager em) {
        Nat_history nat_history = new Nat_history()
                .drawDate(DEFAULT_DRAW_DATE)
                .ball1(DEFAULT_BALL_1)
                .ball2(DEFAULT_BALL_2)
                .ball3(DEFAULT_BALL_3)
                .ball4(DEFAULT_BALL_4)
                .ball5(DEFAULT_BALL_5)
                .ball6(DEFAULT_BALL_6)
                .bonusBall(DEFAULT_BONUS_BALL)
                .machine(DEFAULT_MACHINE)
                .raffles(DEFAULT_RAFFLES)
                .drawNumber(DEFAULT_DRAW_NUMBER)
                .sumB(DEFAULT_SUM_B)
                .hash(DEFAULT_HASH)
                .timestamp(DEFAULT_TIMESTAMP);
        return nat_history;
    }

    @Before
    public void initTest() {
        nat_historySearchRepository.deleteAll();
        nat_history = createEntity(em);
    }

    @Test
    @Transactional
    public void createNat_history() throws Exception {
        int databaseSizeBeforeCreate = nat_historyRepository.findAll().size();

        // Create the Nat_history

        restNat_historyMockMvc.perform(post("/api/nat-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nat_history)))
            .andExpect(status().isCreated());

        // Validate the Nat_history in the database
        List<Nat_history> nat_historyList = nat_historyRepository.findAll();
        assertThat(nat_historyList).hasSize(databaseSizeBeforeCreate + 1);
        Nat_history testNat_history = nat_historyList.get(nat_historyList.size() - 1);
        assertThat(testNat_history.getDrawDate()).isEqualTo(DEFAULT_DRAW_DATE);
        assertThat(testNat_history.getBall1()).isEqualTo(DEFAULT_BALL_1);
        assertThat(testNat_history.getBall2()).isEqualTo(DEFAULT_BALL_2);
        assertThat(testNat_history.getBall3()).isEqualTo(DEFAULT_BALL_3);
        assertThat(testNat_history.getBall4()).isEqualTo(DEFAULT_BALL_4);
        assertThat(testNat_history.getBall5()).isEqualTo(DEFAULT_BALL_5);
        assertThat(testNat_history.getBall6()).isEqualTo(DEFAULT_BALL_6);
        assertThat(testNat_history.getBonusBall()).isEqualTo(DEFAULT_BONUS_BALL);
        assertThat(testNat_history.getMachine()).isEqualTo(DEFAULT_MACHINE);
        assertThat(testNat_history.getRaffles()).isEqualTo(DEFAULT_RAFFLES);
        assertThat(testNat_history.getDrawNumber()).isEqualTo(DEFAULT_DRAW_NUMBER);
        assertThat(testNat_history.getSumB()).isEqualTo(DEFAULT_SUM_B);
        assertThat(testNat_history.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testNat_history.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);

        // Validate the Nat_history in Elasticsearch
        Nat_history nat_historyEs = nat_historySearchRepository.findOne(testNat_history.getId());
        assertThat(nat_historyEs).isEqualToComparingFieldByField(testNat_history);
    }

    @Test
    @Transactional
    public void createNat_historyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nat_historyRepository.findAll().size();

        // Create the Nat_history with an existing ID
        Nat_history existingNat_history = new Nat_history();
        existingNat_history.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNat_historyMockMvc.perform(post("/api/nat-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingNat_history)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Nat_history> nat_historyList = nat_historyRepository.findAll();
        assertThat(nat_historyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDrawDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = nat_historyRepository.findAll().size();
        // set the field null
        nat_history.setDrawDate(null);

        // Create the Nat_history, which fails.

        restNat_historyMockMvc.perform(post("/api/nat-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nat_history)))
            .andExpect(status().isBadRequest());

        List<Nat_history> nat_historyList = nat_historyRepository.findAll();
        assertThat(nat_historyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBall1IsRequired() throws Exception {
        int databaseSizeBeforeTest = nat_historyRepository.findAll().size();
        // set the field null
        nat_history.setBall1(null);

        // Create the Nat_history, which fails.

        restNat_historyMockMvc.perform(post("/api/nat-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nat_history)))
            .andExpect(status().isBadRequest());

        List<Nat_history> nat_historyList = nat_historyRepository.findAll();
        assertThat(nat_historyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBall2IsRequired() throws Exception {
        int databaseSizeBeforeTest = nat_historyRepository.findAll().size();
        // set the field null
        nat_history.setBall2(null);

        // Create the Nat_history, which fails.

        restNat_historyMockMvc.perform(post("/api/nat-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nat_history)))
            .andExpect(status().isBadRequest());

        List<Nat_history> nat_historyList = nat_historyRepository.findAll();
        assertThat(nat_historyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBall3IsRequired() throws Exception {
        int databaseSizeBeforeTest = nat_historyRepository.findAll().size();
        // set the field null
        nat_history.setBall3(null);

        // Create the Nat_history, which fails.

        restNat_historyMockMvc.perform(post("/api/nat-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nat_history)))
            .andExpect(status().isBadRequest());

        List<Nat_history> nat_historyList = nat_historyRepository.findAll();
        assertThat(nat_historyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBall4IsRequired() throws Exception {
        int databaseSizeBeforeTest = nat_historyRepository.findAll().size();
        // set the field null
        nat_history.setBall4(null);

        // Create the Nat_history, which fails.

        restNat_historyMockMvc.perform(post("/api/nat-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nat_history)))
            .andExpect(status().isBadRequest());

        List<Nat_history> nat_historyList = nat_historyRepository.findAll();
        assertThat(nat_historyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBall5IsRequired() throws Exception {
        int databaseSizeBeforeTest = nat_historyRepository.findAll().size();
        // set the field null
        nat_history.setBall5(null);

        // Create the Nat_history, which fails.

        restNat_historyMockMvc.perform(post("/api/nat-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nat_history)))
            .andExpect(status().isBadRequest());

        List<Nat_history> nat_historyList = nat_historyRepository.findAll();
        assertThat(nat_historyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBall6IsRequired() throws Exception {
        int databaseSizeBeforeTest = nat_historyRepository.findAll().size();
        // set the field null
        nat_history.setBall6(null);

        // Create the Nat_history, which fails.

        restNat_historyMockMvc.perform(post("/api/nat-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nat_history)))
            .andExpect(status().isBadRequest());

        List<Nat_history> nat_historyList = nat_historyRepository.findAll();
        assertThat(nat_historyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBonusBallIsRequired() throws Exception {
        int databaseSizeBeforeTest = nat_historyRepository.findAll().size();
        // set the field null
        nat_history.setBonusBall(null);

        // Create the Nat_history, which fails.

        restNat_historyMockMvc.perform(post("/api/nat-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nat_history)))
            .andExpect(status().isBadRequest());

        List<Nat_history> nat_historyList = nat_historyRepository.findAll();
        assertThat(nat_historyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNat_histories() throws Exception {
        // Initialize the database
        nat_historyRepository.saveAndFlush(nat_history);

        // Get all the nat_historyList
        restNat_historyMockMvc.perform(get("/api/nat-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nat_history.getId().intValue())))
            .andExpect(jsonPath("$.[*].drawDate").value(hasItem(DEFAULT_DRAW_DATE.toString())))
            .andExpect(jsonPath("$.[*].ball1").value(hasItem(DEFAULT_BALL_1)))
            .andExpect(jsonPath("$.[*].ball2").value(hasItem(DEFAULT_BALL_2)))
            .andExpect(jsonPath("$.[*].ball3").value(hasItem(DEFAULT_BALL_3)))
            .andExpect(jsonPath("$.[*].ball4").value(hasItem(DEFAULT_BALL_4)))
            .andExpect(jsonPath("$.[*].ball5").value(hasItem(DEFAULT_BALL_5)))
            .andExpect(jsonPath("$.[*].ball6").value(hasItem(DEFAULT_BALL_6)))
            .andExpect(jsonPath("$.[*].bonusBall").value(hasItem(DEFAULT_BONUS_BALL)))
            .andExpect(jsonPath("$.[*].machine").value(hasItem(DEFAULT_MACHINE.toString())))
            .andExpect(jsonPath("$.[*].raffles").value(hasItem(DEFAULT_RAFFLES.toString())))
            .andExpect(jsonPath("$.[*].drawNumber").value(hasItem(DEFAULT_DRAW_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].sumB").value(hasItem(DEFAULT_SUM_B)))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP)));
    }

    @Test
    @Transactional
    public void getNat_history() throws Exception {
        // Initialize the database
        nat_historyRepository.saveAndFlush(nat_history);

        // Get the nat_history
        restNat_historyMockMvc.perform(get("/api/nat-histories/{id}", nat_history.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nat_history.getId().intValue()))
            .andExpect(jsonPath("$.drawDate").value(DEFAULT_DRAW_DATE.toString()))
            .andExpect(jsonPath("$.ball1").value(DEFAULT_BALL_1))
            .andExpect(jsonPath("$.ball2").value(DEFAULT_BALL_2))
            .andExpect(jsonPath("$.ball3").value(DEFAULT_BALL_3))
            .andExpect(jsonPath("$.ball4").value(DEFAULT_BALL_4))
            .andExpect(jsonPath("$.ball5").value(DEFAULT_BALL_5))
            .andExpect(jsonPath("$.ball6").value(DEFAULT_BALL_6))
            .andExpect(jsonPath("$.bonusBall").value(DEFAULT_BONUS_BALL))
            .andExpect(jsonPath("$.machine").value(DEFAULT_MACHINE.toString()))
            .andExpect(jsonPath("$.raffles").value(DEFAULT_RAFFLES.toString()))
            .andExpect(jsonPath("$.drawNumber").value(DEFAULT_DRAW_NUMBER.toString()))
            .andExpect(jsonPath("$.sumB").value(DEFAULT_SUM_B))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH.toString()))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP));
    }

    @Test
    @Transactional
    public void getNonExistingNat_history() throws Exception {
        // Get the nat_history
        restNat_historyMockMvc.perform(get("/api/nat-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNat_history() throws Exception {
        // Initialize the database
        nat_historyService.save(nat_history);

        int databaseSizeBeforeUpdate = nat_historyRepository.findAll().size();

        // Update the nat_history
        Nat_history updatedNat_history = nat_historyRepository.findOne(nat_history.getId());
        updatedNat_history
                .drawDate(UPDATED_DRAW_DATE)
                .ball1(UPDATED_BALL_1)
                .ball2(UPDATED_BALL_2)
                .ball3(UPDATED_BALL_3)
                .ball4(UPDATED_BALL_4)
                .ball5(UPDATED_BALL_5)
                .ball6(UPDATED_BALL_6)
                .bonusBall(UPDATED_BONUS_BALL)
                .machine(UPDATED_MACHINE)
                .raffles(UPDATED_RAFFLES)
                .drawNumber(UPDATED_DRAW_NUMBER)
                .sumB(UPDATED_SUM_B)
                .hash(UPDATED_HASH)
                .timestamp(UPDATED_TIMESTAMP);

        restNat_historyMockMvc.perform(put("/api/nat-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNat_history)))
            .andExpect(status().isOk());

        // Validate the Nat_history in the database
        List<Nat_history> nat_historyList = nat_historyRepository.findAll();
        assertThat(nat_historyList).hasSize(databaseSizeBeforeUpdate);
        Nat_history testNat_history = nat_historyList.get(nat_historyList.size() - 1);
        assertThat(testNat_history.getDrawDate()).isEqualTo(UPDATED_DRAW_DATE);
        assertThat(testNat_history.getBall1()).isEqualTo(UPDATED_BALL_1);
        assertThat(testNat_history.getBall2()).isEqualTo(UPDATED_BALL_2);
        assertThat(testNat_history.getBall3()).isEqualTo(UPDATED_BALL_3);
        assertThat(testNat_history.getBall4()).isEqualTo(UPDATED_BALL_4);
        assertThat(testNat_history.getBall5()).isEqualTo(UPDATED_BALL_5);
        assertThat(testNat_history.getBall6()).isEqualTo(UPDATED_BALL_6);
        assertThat(testNat_history.getBonusBall()).isEqualTo(UPDATED_BONUS_BALL);
        assertThat(testNat_history.getMachine()).isEqualTo(UPDATED_MACHINE);
        assertThat(testNat_history.getRaffles()).isEqualTo(UPDATED_RAFFLES);
        assertThat(testNat_history.getDrawNumber()).isEqualTo(UPDATED_DRAW_NUMBER);
        assertThat(testNat_history.getSumB()).isEqualTo(UPDATED_SUM_B);
        assertThat(testNat_history.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testNat_history.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);

        // Validate the Nat_history in Elasticsearch
        Nat_history nat_historyEs = nat_historySearchRepository.findOne(testNat_history.getId());
        assertThat(nat_historyEs).isEqualToComparingFieldByField(testNat_history);
    }

    @Test
    @Transactional
    public void updateNonExistingNat_history() throws Exception {
        int databaseSizeBeforeUpdate = nat_historyRepository.findAll().size();

        // Create the Nat_history

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNat_historyMockMvc.perform(put("/api/nat-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nat_history)))
            .andExpect(status().isCreated());

        // Validate the Nat_history in the database
        List<Nat_history> nat_historyList = nat_historyRepository.findAll();
        assertThat(nat_historyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNat_history() throws Exception {
        // Initialize the database
        nat_historyService.save(nat_history);

        int databaseSizeBeforeDelete = nat_historyRepository.findAll().size();

        // Get the nat_history
        restNat_historyMockMvc.perform(delete("/api/nat-histories/{id}", nat_history.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean nat_historyExistsInEs = nat_historySearchRepository.exists(nat_history.getId());
        assertThat(nat_historyExistsInEs).isFalse();

        // Validate the database is empty
        List<Nat_history> nat_historyList = nat_historyRepository.findAll();
        assertThat(nat_historyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchNat_history() throws Exception {
        // Initialize the database
        nat_historyService.save(nat_history);

        // Search the nat_history
        restNat_historyMockMvc.perform(get("/api/_search/nat-histories?query=id:" + nat_history.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nat_history.getId().intValue())))
            .andExpect(jsonPath("$.[*].drawDate").value(hasItem(DEFAULT_DRAW_DATE.toString())))
            .andExpect(jsonPath("$.[*].ball1").value(hasItem(DEFAULT_BALL_1)))
            .andExpect(jsonPath("$.[*].ball2").value(hasItem(DEFAULT_BALL_2)))
            .andExpect(jsonPath("$.[*].ball3").value(hasItem(DEFAULT_BALL_3)))
            .andExpect(jsonPath("$.[*].ball4").value(hasItem(DEFAULT_BALL_4)))
            .andExpect(jsonPath("$.[*].ball5").value(hasItem(DEFAULT_BALL_5)))
            .andExpect(jsonPath("$.[*].ball6").value(hasItem(DEFAULT_BALL_6)))
            .andExpect(jsonPath("$.[*].bonusBall").value(hasItem(DEFAULT_BONUS_BALL)))
            .andExpect(jsonPath("$.[*].machine").value(hasItem(DEFAULT_MACHINE.toString())))
            .andExpect(jsonPath("$.[*].raffles").value(hasItem(DEFAULT_RAFFLES.toString())))
            .andExpect(jsonPath("$.[*].drawNumber").value(hasItem(DEFAULT_DRAW_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].sumB").value(hasItem(DEFAULT_SUM_B)))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nat_history.class);
    }
}
