package ru.m2mcom.natmob.web.rest;

import ru.m2mcom.natmob.LotappApp;

import ru.m2mcom.natmob.domain.Eml_history;
import ru.m2mcom.natmob.repository.Eml_historyRepository;
import ru.m2mcom.natmob.service.Eml_historyService;
import ru.m2mcom.natmob.repository.search.Eml_historySearchRepository;

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
 * Test class for the Eml_historyResource REST controller.
 *
 * @see Eml_historyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LotappApp.class)
public class Eml_historyResourceIntTest {

    private static final String DEFAULT_DRAW_DATE = "78-X9-83";
    private static final String UPDATED_DRAW_DATE = "29-J-89";

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

    private static final Integer DEFAULT_LUCKY_STAR_1 = 1;
    private static final Integer UPDATED_LUCKY_STAR_1 = 2;

    private static final Integer DEFAULT_LUCKY_STAR_2 = 1;
    private static final Integer UPDATED_LUCKY_STAR_2 = 2;

    private static final String DEFAULT_RAFFLE = "AAAAAAAAAA";
    private static final String UPDATED_RAFFLE = "BBBBBBBBBB";

    private static final String DEFAULT_DRAW_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_DRAW_NUMBER = "BBBBBBBBBB";

    private static final Integer DEFAULT_SUM_B = 1;
    private static final Integer UPDATED_SUM_B = 2;

    private static final Integer DEFAULT_SUM_S = 1;
    private static final Integer UPDATED_SUM_S = 2;

    private static final String DEFAULT_HASH = "AAAAAAAAAA";
    private static final String UPDATED_HASH = "BBBBBBBBBB";

    @Autowired
    private Eml_historyRepository eml_historyRepository;

    @Autowired
    private Eml_historyService eml_historyService;

    @Autowired
    private Eml_historySearchRepository eml_historySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restEml_historyMockMvc;

    private Eml_history eml_history;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Eml_historyResource eml_historyResource = new Eml_historyResource(eml_historyService);
        this.restEml_historyMockMvc = MockMvcBuilders.standaloneSetup(eml_historyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eml_history createEntity(EntityManager em) {
        Eml_history eml_history = new Eml_history()
                .drawDate(DEFAULT_DRAW_DATE)
                .ball1(DEFAULT_BALL_1)
                .ball2(DEFAULT_BALL_2)
                .ball3(DEFAULT_BALL_3)
                .ball4(DEFAULT_BALL_4)
                .ball5(DEFAULT_BALL_5)
                .luckyStar1(DEFAULT_LUCKY_STAR_1)
                .luckyStar2(DEFAULT_LUCKY_STAR_2)
                .raffle(DEFAULT_RAFFLE)
                .drawNumber(DEFAULT_DRAW_NUMBER)
                .sumB(DEFAULT_SUM_B)
                .sumS(DEFAULT_SUM_S)
                .hash(DEFAULT_HASH);
        return eml_history;
    }

    @Before
    public void initTest() {
        eml_historySearchRepository.deleteAll();
        eml_history = createEntity(em);
    }

    @Test
    @Transactional
    public void createEml_history() throws Exception {
        int databaseSizeBeforeCreate = eml_historyRepository.findAll().size();

        // Create the Eml_history

        restEml_historyMockMvc.perform(post("/api/eml-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eml_history)))
            .andExpect(status().isCreated());

        // Validate the Eml_history in the database
        List<Eml_history> eml_historyList = eml_historyRepository.findAll();
        assertThat(eml_historyList).hasSize(databaseSizeBeforeCreate + 1);
        Eml_history testEml_history = eml_historyList.get(eml_historyList.size() - 1);
        assertThat(testEml_history.getDrawDate()).isEqualTo(DEFAULT_DRAW_DATE);
        assertThat(testEml_history.getBall1()).isEqualTo(DEFAULT_BALL_1);
        assertThat(testEml_history.getBall2()).isEqualTo(DEFAULT_BALL_2);
        assertThat(testEml_history.getBall3()).isEqualTo(DEFAULT_BALL_3);
        assertThat(testEml_history.getBall4()).isEqualTo(DEFAULT_BALL_4);
        assertThat(testEml_history.getBall5()).isEqualTo(DEFAULT_BALL_5);
        assertThat(testEml_history.getLuckyStar1()).isEqualTo(DEFAULT_LUCKY_STAR_1);
        assertThat(testEml_history.getLuckyStar2()).isEqualTo(DEFAULT_LUCKY_STAR_2);
        assertThat(testEml_history.getRaffle()).isEqualTo(DEFAULT_RAFFLE);
        assertThat(testEml_history.getDrawNumber()).isEqualTo(DEFAULT_DRAW_NUMBER);
        assertThat(testEml_history.getSumB()).isEqualTo(DEFAULT_SUM_B);
        assertThat(testEml_history.getSumS()).isEqualTo(DEFAULT_SUM_S);
        assertThat(testEml_history.getHash()).isEqualTo(DEFAULT_HASH);

        // Validate the Eml_history in Elasticsearch
        Eml_history eml_historyEs = eml_historySearchRepository.findOne(testEml_history.getId());
        assertThat(eml_historyEs).isEqualToComparingFieldByField(testEml_history);
    }

    @Test
    @Transactional
    public void createEml_historyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eml_historyRepository.findAll().size();

        // Create the Eml_history with an existing ID
        Eml_history existingEml_history = new Eml_history();
        existingEml_history.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEml_historyMockMvc.perform(post("/api/eml-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingEml_history)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Eml_history> eml_historyList = eml_historyRepository.findAll();
        assertThat(eml_historyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDrawDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = eml_historyRepository.findAll().size();
        // set the field null
        eml_history.setDrawDate(null);

        // Create the Eml_history, which fails.

        restEml_historyMockMvc.perform(post("/api/eml-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eml_history)))
            .andExpect(status().isBadRequest());

        List<Eml_history> eml_historyList = eml_historyRepository.findAll();
        assertThat(eml_historyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBall1IsRequired() throws Exception {
        int databaseSizeBeforeTest = eml_historyRepository.findAll().size();
        // set the field null
        eml_history.setBall1(null);

        // Create the Eml_history, which fails.

        restEml_historyMockMvc.perform(post("/api/eml-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eml_history)))
            .andExpect(status().isBadRequest());

        List<Eml_history> eml_historyList = eml_historyRepository.findAll();
        assertThat(eml_historyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBall2IsRequired() throws Exception {
        int databaseSizeBeforeTest = eml_historyRepository.findAll().size();
        // set the field null
        eml_history.setBall2(null);

        // Create the Eml_history, which fails.

        restEml_historyMockMvc.perform(post("/api/eml-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eml_history)))
            .andExpect(status().isBadRequest());

        List<Eml_history> eml_historyList = eml_historyRepository.findAll();
        assertThat(eml_historyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBall3IsRequired() throws Exception {
        int databaseSizeBeforeTest = eml_historyRepository.findAll().size();
        // set the field null
        eml_history.setBall3(null);

        // Create the Eml_history, which fails.

        restEml_historyMockMvc.perform(post("/api/eml-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eml_history)))
            .andExpect(status().isBadRequest());

        List<Eml_history> eml_historyList = eml_historyRepository.findAll();
        assertThat(eml_historyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBall4IsRequired() throws Exception {
        int databaseSizeBeforeTest = eml_historyRepository.findAll().size();
        // set the field null
        eml_history.setBall4(null);

        // Create the Eml_history, which fails.

        restEml_historyMockMvc.perform(post("/api/eml-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eml_history)))
            .andExpect(status().isBadRequest());

        List<Eml_history> eml_historyList = eml_historyRepository.findAll();
        assertThat(eml_historyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBall5IsRequired() throws Exception {
        int databaseSizeBeforeTest = eml_historyRepository.findAll().size();
        // set the field null
        eml_history.setBall5(null);

        // Create the Eml_history, which fails.

        restEml_historyMockMvc.perform(post("/api/eml-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eml_history)))
            .andExpect(status().isBadRequest());

        List<Eml_history> eml_historyList = eml_historyRepository.findAll();
        assertThat(eml_historyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLuckyStar1IsRequired() throws Exception {
        int databaseSizeBeforeTest = eml_historyRepository.findAll().size();
        // set the field null
        eml_history.setLuckyStar1(null);

        // Create the Eml_history, which fails.

        restEml_historyMockMvc.perform(post("/api/eml-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eml_history)))
            .andExpect(status().isBadRequest());

        List<Eml_history> eml_historyList = eml_historyRepository.findAll();
        assertThat(eml_historyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLuckyStar2IsRequired() throws Exception {
        int databaseSizeBeforeTest = eml_historyRepository.findAll().size();
        // set the field null
        eml_history.setLuckyStar2(null);

        // Create the Eml_history, which fails.

        restEml_historyMockMvc.perform(post("/api/eml-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eml_history)))
            .andExpect(status().isBadRequest());

        List<Eml_history> eml_historyList = eml_historyRepository.findAll();
        assertThat(eml_historyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEml_histories() throws Exception {
        // Initialize the database
        eml_historyRepository.saveAndFlush(eml_history);

        // Get all the eml_historyList
        restEml_historyMockMvc.perform(get("/api/eml-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eml_history.getId().intValue())))
            .andExpect(jsonPath("$.[*].drawDate").value(hasItem(DEFAULT_DRAW_DATE.toString())))
            .andExpect(jsonPath("$.[*].ball1").value(hasItem(DEFAULT_BALL_1)))
            .andExpect(jsonPath("$.[*].ball2").value(hasItem(DEFAULT_BALL_2)))
            .andExpect(jsonPath("$.[*].ball3").value(hasItem(DEFAULT_BALL_3)))
            .andExpect(jsonPath("$.[*].ball4").value(hasItem(DEFAULT_BALL_4)))
            .andExpect(jsonPath("$.[*].ball5").value(hasItem(DEFAULT_BALL_5)))
            .andExpect(jsonPath("$.[*].luckyStar1").value(hasItem(DEFAULT_LUCKY_STAR_1)))
            .andExpect(jsonPath("$.[*].luckyStar2").value(hasItem(DEFAULT_LUCKY_STAR_2)))
            .andExpect(jsonPath("$.[*].raffle").value(hasItem(DEFAULT_RAFFLE.toString())))
            .andExpect(jsonPath("$.[*].drawNumber").value(hasItem(DEFAULT_DRAW_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].sumB").value(hasItem(DEFAULT_SUM_B)))
            .andExpect(jsonPath("$.[*].sumS").value(hasItem(DEFAULT_SUM_S)))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH.toString())));
    }

    @Test
    @Transactional
    public void getEml_history() throws Exception {
        // Initialize the database
        eml_historyRepository.saveAndFlush(eml_history);

        // Get the eml_history
        restEml_historyMockMvc.perform(get("/api/eml-histories/{id}", eml_history.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eml_history.getId().intValue()))
            .andExpect(jsonPath("$.drawDate").value(DEFAULT_DRAW_DATE.toString()))
            .andExpect(jsonPath("$.ball1").value(DEFAULT_BALL_1))
            .andExpect(jsonPath("$.ball2").value(DEFAULT_BALL_2))
            .andExpect(jsonPath("$.ball3").value(DEFAULT_BALL_3))
            .andExpect(jsonPath("$.ball4").value(DEFAULT_BALL_4))
            .andExpect(jsonPath("$.ball5").value(DEFAULT_BALL_5))
            .andExpect(jsonPath("$.luckyStar1").value(DEFAULT_LUCKY_STAR_1))
            .andExpect(jsonPath("$.luckyStar2").value(DEFAULT_LUCKY_STAR_2))
            .andExpect(jsonPath("$.raffle").value(DEFAULT_RAFFLE.toString()))
            .andExpect(jsonPath("$.drawNumber").value(DEFAULT_DRAW_NUMBER.toString()))
            .andExpect(jsonPath("$.sumB").value(DEFAULT_SUM_B))
            .andExpect(jsonPath("$.sumS").value(DEFAULT_SUM_S))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEml_history() throws Exception {
        // Get the eml_history
        restEml_historyMockMvc.perform(get("/api/eml-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEml_history() throws Exception {
        // Initialize the database
        eml_historyService.save(eml_history);

        int databaseSizeBeforeUpdate = eml_historyRepository.findAll().size();

        // Update the eml_history
        Eml_history updatedEml_history = eml_historyRepository.findOne(eml_history.getId());
        updatedEml_history
                .drawDate(UPDATED_DRAW_DATE)
                .ball1(UPDATED_BALL_1)
                .ball2(UPDATED_BALL_2)
                .ball3(UPDATED_BALL_3)
                .ball4(UPDATED_BALL_4)
                .ball5(UPDATED_BALL_5)
                .luckyStar1(UPDATED_LUCKY_STAR_1)
                .luckyStar2(UPDATED_LUCKY_STAR_2)
                .raffle(UPDATED_RAFFLE)
                .drawNumber(UPDATED_DRAW_NUMBER)
                .sumB(UPDATED_SUM_B)
                .sumS(UPDATED_SUM_S)
                .hash(UPDATED_HASH);

        restEml_historyMockMvc.perform(put("/api/eml-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEml_history)))
            .andExpect(status().isOk());

        // Validate the Eml_history in the database
        List<Eml_history> eml_historyList = eml_historyRepository.findAll();
        assertThat(eml_historyList).hasSize(databaseSizeBeforeUpdate);
        Eml_history testEml_history = eml_historyList.get(eml_historyList.size() - 1);
        assertThat(testEml_history.getDrawDate()).isEqualTo(UPDATED_DRAW_DATE);
        assertThat(testEml_history.getBall1()).isEqualTo(UPDATED_BALL_1);
        assertThat(testEml_history.getBall2()).isEqualTo(UPDATED_BALL_2);
        assertThat(testEml_history.getBall3()).isEqualTo(UPDATED_BALL_3);
        assertThat(testEml_history.getBall4()).isEqualTo(UPDATED_BALL_4);
        assertThat(testEml_history.getBall5()).isEqualTo(UPDATED_BALL_5);
        assertThat(testEml_history.getLuckyStar1()).isEqualTo(UPDATED_LUCKY_STAR_1);
        assertThat(testEml_history.getLuckyStar2()).isEqualTo(UPDATED_LUCKY_STAR_2);
        assertThat(testEml_history.getRaffle()).isEqualTo(UPDATED_RAFFLE);
        assertThat(testEml_history.getDrawNumber()).isEqualTo(UPDATED_DRAW_NUMBER);
        assertThat(testEml_history.getSumB()).isEqualTo(UPDATED_SUM_B);
        assertThat(testEml_history.getSumS()).isEqualTo(UPDATED_SUM_S);
        assertThat(testEml_history.getHash()).isEqualTo(UPDATED_HASH);

        // Validate the Eml_history in Elasticsearch
        Eml_history eml_historyEs = eml_historySearchRepository.findOne(testEml_history.getId());
        assertThat(eml_historyEs).isEqualToComparingFieldByField(testEml_history);
    }

    @Test
    @Transactional
    public void updateNonExistingEml_history() throws Exception {
        int databaseSizeBeforeUpdate = eml_historyRepository.findAll().size();

        // Create the Eml_history

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEml_historyMockMvc.perform(put("/api/eml-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eml_history)))
            .andExpect(status().isCreated());

        // Validate the Eml_history in the database
        List<Eml_history> eml_historyList = eml_historyRepository.findAll();
        assertThat(eml_historyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEml_history() throws Exception {
        // Initialize the database
        eml_historyService.save(eml_history);

        int databaseSizeBeforeDelete = eml_historyRepository.findAll().size();

        // Get the eml_history
        restEml_historyMockMvc.perform(delete("/api/eml-histories/{id}", eml_history.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean eml_historyExistsInEs = eml_historySearchRepository.exists(eml_history.getId());
        assertThat(eml_historyExistsInEs).isFalse();

        // Validate the database is empty
        List<Eml_history> eml_historyList = eml_historyRepository.findAll();
        assertThat(eml_historyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEml_history() throws Exception {
        // Initialize the database
        eml_historyService.save(eml_history);

        // Search the eml_history
        restEml_historyMockMvc.perform(get("/api/_search/eml-histories?query=id:" + eml_history.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eml_history.getId().intValue())))
            .andExpect(jsonPath("$.[*].drawDate").value(hasItem(DEFAULT_DRAW_DATE.toString())))
            .andExpect(jsonPath("$.[*].ball1").value(hasItem(DEFAULT_BALL_1)))
            .andExpect(jsonPath("$.[*].ball2").value(hasItem(DEFAULT_BALL_2)))
            .andExpect(jsonPath("$.[*].ball3").value(hasItem(DEFAULT_BALL_3)))
            .andExpect(jsonPath("$.[*].ball4").value(hasItem(DEFAULT_BALL_4)))
            .andExpect(jsonPath("$.[*].ball5").value(hasItem(DEFAULT_BALL_5)))
            .andExpect(jsonPath("$.[*].luckyStar1").value(hasItem(DEFAULT_LUCKY_STAR_1)))
            .andExpect(jsonPath("$.[*].luckyStar2").value(hasItem(DEFAULT_LUCKY_STAR_2)))
            .andExpect(jsonPath("$.[*].raffle").value(hasItem(DEFAULT_RAFFLE.toString())))
            .andExpect(jsonPath("$.[*].drawNumber").value(hasItem(DEFAULT_DRAW_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].sumB").value(hasItem(DEFAULT_SUM_B)))
            .andExpect(jsonPath("$.[*].sumS").value(hasItem(DEFAULT_SUM_S)))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Eml_history.class);
    }
}
