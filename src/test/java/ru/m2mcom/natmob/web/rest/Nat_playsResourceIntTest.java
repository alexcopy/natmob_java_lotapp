package ru.m2mcom.natmob.web.rest;

import ru.m2mcom.natmob.LotappApp;

import ru.m2mcom.natmob.domain.Nat_plays;
import ru.m2mcom.natmob.repository.Nat_playsRepository;
import ru.m2mcom.natmob.service.Nat_playsService;
import ru.m2mcom.natmob.repository.search.Nat_playsSearchRepository;

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

import ru.m2mcom.natmob.domain.enumeration.DrawType;
/**
 * Test class for the Nat_playsResource REST controller.
 *
 * @see Nat_playsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LotappApp.class)
public class Nat_playsResourceIntTest {

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

    private static final DrawType DEFAULT_DRAW_TYPE = DrawType.local;
    private static final DrawType UPDATED_DRAW_TYPE = DrawType.manual;

    private static final String DEFAULT_GAME_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_GAME_TYPE = "BBBBBBBBBB";

    private static final Double DEFAULT_PRIZE = 1D;
    private static final Double UPDATED_PRIZE = 2D;

    private static final Boolean DEFAULT_CHECKED = false;
    private static final Boolean UPDATED_CHECKED = true;

    private static final Long DEFAULT_RANK_ID = 1L;
    private static final Long UPDATED_RANK_ID = 2L;

    private static final Integer DEFAULT_SUM_B = 1;
    private static final Integer UPDATED_SUM_B = 2;

    private static final String DEFAULT_HASH = "AAAAAAAAAA";
    private static final String UPDATED_HASH = "BBBBBBBBBB";

    @Autowired
    private Nat_playsRepository nat_playsRepository;

    @Autowired
    private Nat_playsService nat_playsService;

    @Autowired
    private Nat_playsSearchRepository nat_playsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restNat_playsMockMvc;

    private Nat_plays nat_plays;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Nat_playsResource nat_playsResource = new Nat_playsResource(nat_playsService);
        this.restNat_playsMockMvc = MockMvcBuilders.standaloneSetup(nat_playsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nat_plays createEntity(EntityManager em) {
        Nat_plays nat_plays = new Nat_plays()
                .drawDate(DEFAULT_DRAW_DATE)
                .ball1(DEFAULT_BALL_1)
                .ball2(DEFAULT_BALL_2)
                .ball3(DEFAULT_BALL_3)
                .ball4(DEFAULT_BALL_4)
                .ball5(DEFAULT_BALL_5)
                .ball6(DEFAULT_BALL_6)
                .bonusBall(DEFAULT_BONUS_BALL)
                .drawType(DEFAULT_DRAW_TYPE)
                .gameType(DEFAULT_GAME_TYPE)
                .prize(DEFAULT_PRIZE)
                .checked(DEFAULT_CHECKED)
                .rank_id(DEFAULT_RANK_ID)
                .sumB(DEFAULT_SUM_B)
                .hash(DEFAULT_HASH);
        return nat_plays;
    }

    @Before
    public void initTest() {
        nat_playsSearchRepository.deleteAll();
        nat_plays = createEntity(em);
    }

    @Test
    @Transactional
    public void createNat_plays() throws Exception {
        int databaseSizeBeforeCreate = nat_playsRepository.findAll().size();

        // Create the Nat_plays

        restNat_playsMockMvc.perform(post("/api/nat-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nat_plays)))
            .andExpect(status().isCreated());

        // Validate the Nat_plays in the database
        List<Nat_plays> nat_playsList = nat_playsRepository.findAll();
        assertThat(nat_playsList).hasSize(databaseSizeBeforeCreate + 1);
        Nat_plays testNat_plays = nat_playsList.get(nat_playsList.size() - 1);
        assertThat(testNat_plays.getDrawDate()).isEqualTo(DEFAULT_DRAW_DATE);
        assertThat(testNat_plays.getBall1()).isEqualTo(DEFAULT_BALL_1);
        assertThat(testNat_plays.getBall2()).isEqualTo(DEFAULT_BALL_2);
        assertThat(testNat_plays.getBall3()).isEqualTo(DEFAULT_BALL_3);
        assertThat(testNat_plays.getBall4()).isEqualTo(DEFAULT_BALL_4);
        assertThat(testNat_plays.getBall5()).isEqualTo(DEFAULT_BALL_5);
        assertThat(testNat_plays.getBall6()).isEqualTo(DEFAULT_BALL_6);
        assertThat(testNat_plays.getBonusBall()).isEqualTo(DEFAULT_BONUS_BALL);
        assertThat(testNat_plays.getDrawType()).isEqualTo(DEFAULT_DRAW_TYPE);
        assertThat(testNat_plays.getGameType()).isEqualTo(DEFAULT_GAME_TYPE);
        assertThat(testNat_plays.getPrize()).isEqualTo(DEFAULT_PRIZE);
        assertThat(testNat_plays.isChecked()).isEqualTo(DEFAULT_CHECKED);
        assertThat(testNat_plays.getRank_id()).isEqualTo(DEFAULT_RANK_ID);
        assertThat(testNat_plays.getSumB()).isEqualTo(DEFAULT_SUM_B);
        assertThat(testNat_plays.getHash()).isEqualTo(DEFAULT_HASH);

        // Validate the Nat_plays in Elasticsearch
        Nat_plays nat_playsEs = nat_playsSearchRepository.findOne(testNat_plays.getId());
        assertThat(nat_playsEs).isEqualToComparingFieldByField(testNat_plays);
    }

    @Test
    @Transactional
    public void createNat_playsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nat_playsRepository.findAll().size();

        // Create the Nat_plays with an existing ID
        Nat_plays existingNat_plays = new Nat_plays();
        existingNat_plays.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNat_playsMockMvc.perform(post("/api/nat-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingNat_plays)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Nat_plays> nat_playsList = nat_playsRepository.findAll();
        assertThat(nat_playsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDrawDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = nat_playsRepository.findAll().size();
        // set the field null
        nat_plays.setDrawDate(null);

        // Create the Nat_plays, which fails.

        restNat_playsMockMvc.perform(post("/api/nat-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nat_plays)))
            .andExpect(status().isBadRequest());

        List<Nat_plays> nat_playsList = nat_playsRepository.findAll();
        assertThat(nat_playsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBall1IsRequired() throws Exception {
        int databaseSizeBeforeTest = nat_playsRepository.findAll().size();
        // set the field null
        nat_plays.setBall1(null);

        // Create the Nat_plays, which fails.

        restNat_playsMockMvc.perform(post("/api/nat-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nat_plays)))
            .andExpect(status().isBadRequest());

        List<Nat_plays> nat_playsList = nat_playsRepository.findAll();
        assertThat(nat_playsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBall2IsRequired() throws Exception {
        int databaseSizeBeforeTest = nat_playsRepository.findAll().size();
        // set the field null
        nat_plays.setBall2(null);

        // Create the Nat_plays, which fails.

        restNat_playsMockMvc.perform(post("/api/nat-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nat_plays)))
            .andExpect(status().isBadRequest());

        List<Nat_plays> nat_playsList = nat_playsRepository.findAll();
        assertThat(nat_playsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBall3IsRequired() throws Exception {
        int databaseSizeBeforeTest = nat_playsRepository.findAll().size();
        // set the field null
        nat_plays.setBall3(null);

        // Create the Nat_plays, which fails.

        restNat_playsMockMvc.perform(post("/api/nat-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nat_plays)))
            .andExpect(status().isBadRequest());

        List<Nat_plays> nat_playsList = nat_playsRepository.findAll();
        assertThat(nat_playsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBall4IsRequired() throws Exception {
        int databaseSizeBeforeTest = nat_playsRepository.findAll().size();
        // set the field null
        nat_plays.setBall4(null);

        // Create the Nat_plays, which fails.

        restNat_playsMockMvc.perform(post("/api/nat-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nat_plays)))
            .andExpect(status().isBadRequest());

        List<Nat_plays> nat_playsList = nat_playsRepository.findAll();
        assertThat(nat_playsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBall6IsRequired() throws Exception {
        int databaseSizeBeforeTest = nat_playsRepository.findAll().size();
        // set the field null
        nat_plays.setBall6(null);

        // Create the Nat_plays, which fails.

        restNat_playsMockMvc.perform(post("/api/nat-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nat_plays)))
            .andExpect(status().isBadRequest());

        List<Nat_plays> nat_playsList = nat_playsRepository.findAll();
        assertThat(nat_playsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDrawTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = nat_playsRepository.findAll().size();
        // set the field null
        nat_plays.setDrawType(null);

        // Create the Nat_plays, which fails.

        restNat_playsMockMvc.perform(post("/api/nat-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nat_plays)))
            .andExpect(status().isBadRequest());

        List<Nat_plays> nat_playsList = nat_playsRepository.findAll();
        assertThat(nat_playsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGameTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = nat_playsRepository.findAll().size();
        // set the field null
        nat_plays.setGameType(null);

        // Create the Nat_plays, which fails.

        restNat_playsMockMvc.perform(post("/api/nat-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nat_plays)))
            .andExpect(status().isBadRequest());

        List<Nat_plays> nat_playsList = nat_playsRepository.findAll();
        assertThat(nat_playsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNat_plays() throws Exception {
        // Initialize the database
        nat_playsRepository.saveAndFlush(nat_plays);

        // Get all the nat_playsList
        restNat_playsMockMvc.perform(get("/api/nat-plays?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nat_plays.getId().intValue())))
            .andExpect(jsonPath("$.[*].drawDate").value(hasItem(DEFAULT_DRAW_DATE.toString())))
            .andExpect(jsonPath("$.[*].ball1").value(hasItem(DEFAULT_BALL_1)))
            .andExpect(jsonPath("$.[*].ball2").value(hasItem(DEFAULT_BALL_2)))
            .andExpect(jsonPath("$.[*].ball3").value(hasItem(DEFAULT_BALL_3)))
            .andExpect(jsonPath("$.[*].ball4").value(hasItem(DEFAULT_BALL_4)))
            .andExpect(jsonPath("$.[*].ball5").value(hasItem(DEFAULT_BALL_5)))
            .andExpect(jsonPath("$.[*].ball6").value(hasItem(DEFAULT_BALL_6)))
            .andExpect(jsonPath("$.[*].bonusBall").value(hasItem(DEFAULT_BONUS_BALL)))
            .andExpect(jsonPath("$.[*].drawType").value(hasItem(DEFAULT_DRAW_TYPE.toString())))
            .andExpect(jsonPath("$.[*].gameType").value(hasItem(DEFAULT_GAME_TYPE.toString())))
            .andExpect(jsonPath("$.[*].prize").value(hasItem(DEFAULT_PRIZE.doubleValue())))
            .andExpect(jsonPath("$.[*].checked").value(hasItem(DEFAULT_CHECKED.booleanValue())))
            .andExpect(jsonPath("$.[*].rank_id").value(hasItem(DEFAULT_RANK_ID.intValue())))
            .andExpect(jsonPath("$.[*].sumB").value(hasItem(DEFAULT_SUM_B)))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH.toString())));
    }

    @Test
    @Transactional
    public void getNat_plays() throws Exception {
        // Initialize the database
        nat_playsRepository.saveAndFlush(nat_plays);

        // Get the nat_plays
        restNat_playsMockMvc.perform(get("/api/nat-plays/{id}", nat_plays.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nat_plays.getId().intValue()))
            .andExpect(jsonPath("$.drawDate").value(DEFAULT_DRAW_DATE.toString()))
            .andExpect(jsonPath("$.ball1").value(DEFAULT_BALL_1))
            .andExpect(jsonPath("$.ball2").value(DEFAULT_BALL_2))
            .andExpect(jsonPath("$.ball3").value(DEFAULT_BALL_3))
            .andExpect(jsonPath("$.ball4").value(DEFAULT_BALL_4))
            .andExpect(jsonPath("$.ball5").value(DEFAULT_BALL_5))
            .andExpect(jsonPath("$.ball6").value(DEFAULT_BALL_6))
            .andExpect(jsonPath("$.bonusBall").value(DEFAULT_BONUS_BALL))
            .andExpect(jsonPath("$.drawType").value(DEFAULT_DRAW_TYPE.toString()))
            .andExpect(jsonPath("$.gameType").value(DEFAULT_GAME_TYPE.toString()))
            .andExpect(jsonPath("$.prize").value(DEFAULT_PRIZE.doubleValue()))
            .andExpect(jsonPath("$.checked").value(DEFAULT_CHECKED.booleanValue()))
            .andExpect(jsonPath("$.rank_id").value(DEFAULT_RANK_ID.intValue()))
            .andExpect(jsonPath("$.sumB").value(DEFAULT_SUM_B))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNat_plays() throws Exception {
        // Get the nat_plays
        restNat_playsMockMvc.perform(get("/api/nat-plays/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNat_plays() throws Exception {
        // Initialize the database
        nat_playsService.save(nat_plays);

        int databaseSizeBeforeUpdate = nat_playsRepository.findAll().size();

        // Update the nat_plays
        Nat_plays updatedNat_plays = nat_playsRepository.findOne(nat_plays.getId());
        updatedNat_plays
                .drawDate(UPDATED_DRAW_DATE)
                .ball1(UPDATED_BALL_1)
                .ball2(UPDATED_BALL_2)
                .ball3(UPDATED_BALL_3)
                .ball4(UPDATED_BALL_4)
                .ball5(UPDATED_BALL_5)
                .ball6(UPDATED_BALL_6)
                .bonusBall(UPDATED_BONUS_BALL)
                .drawType(UPDATED_DRAW_TYPE)
                .gameType(UPDATED_GAME_TYPE)
                .prize(UPDATED_PRIZE)
                .checked(UPDATED_CHECKED)
                .rank_id(UPDATED_RANK_ID)
                .sumB(UPDATED_SUM_B)
                .hash(UPDATED_HASH);

        restNat_playsMockMvc.perform(put("/api/nat-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNat_plays)))
            .andExpect(status().isOk());

        // Validate the Nat_plays in the database
        List<Nat_plays> nat_playsList = nat_playsRepository.findAll();
        assertThat(nat_playsList).hasSize(databaseSizeBeforeUpdate);
        Nat_plays testNat_plays = nat_playsList.get(nat_playsList.size() - 1);
        assertThat(testNat_plays.getDrawDate()).isEqualTo(UPDATED_DRAW_DATE);
        assertThat(testNat_plays.getBall1()).isEqualTo(UPDATED_BALL_1);
        assertThat(testNat_plays.getBall2()).isEqualTo(UPDATED_BALL_2);
        assertThat(testNat_plays.getBall3()).isEqualTo(UPDATED_BALL_3);
        assertThat(testNat_plays.getBall4()).isEqualTo(UPDATED_BALL_4);
        assertThat(testNat_plays.getBall5()).isEqualTo(UPDATED_BALL_5);
        assertThat(testNat_plays.getBall6()).isEqualTo(UPDATED_BALL_6);
        assertThat(testNat_plays.getBonusBall()).isEqualTo(UPDATED_BONUS_BALL);
        assertThat(testNat_plays.getDrawType()).isEqualTo(UPDATED_DRAW_TYPE);
        assertThat(testNat_plays.getGameType()).isEqualTo(UPDATED_GAME_TYPE);
        assertThat(testNat_plays.getPrize()).isEqualTo(UPDATED_PRIZE);
        assertThat(testNat_plays.isChecked()).isEqualTo(UPDATED_CHECKED);
        assertThat(testNat_plays.getRank_id()).isEqualTo(UPDATED_RANK_ID);
        assertThat(testNat_plays.getSumB()).isEqualTo(UPDATED_SUM_B);
        assertThat(testNat_plays.getHash()).isEqualTo(UPDATED_HASH);

        // Validate the Nat_plays in Elasticsearch
        Nat_plays nat_playsEs = nat_playsSearchRepository.findOne(testNat_plays.getId());
        assertThat(nat_playsEs).isEqualToComparingFieldByField(testNat_plays);
    }

    @Test
    @Transactional
    public void updateNonExistingNat_plays() throws Exception {
        int databaseSizeBeforeUpdate = nat_playsRepository.findAll().size();

        // Create the Nat_plays

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNat_playsMockMvc.perform(put("/api/nat-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nat_plays)))
            .andExpect(status().isCreated());

        // Validate the Nat_plays in the database
        List<Nat_plays> nat_playsList = nat_playsRepository.findAll();
        assertThat(nat_playsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNat_plays() throws Exception {
        // Initialize the database
        nat_playsService.save(nat_plays);

        int databaseSizeBeforeDelete = nat_playsRepository.findAll().size();

        // Get the nat_plays
        restNat_playsMockMvc.perform(delete("/api/nat-plays/{id}", nat_plays.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean nat_playsExistsInEs = nat_playsSearchRepository.exists(nat_plays.getId());
        assertThat(nat_playsExistsInEs).isFalse();

        // Validate the database is empty
        List<Nat_plays> nat_playsList = nat_playsRepository.findAll();
        assertThat(nat_playsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchNat_plays() throws Exception {
        // Initialize the database
        nat_playsService.save(nat_plays);

        // Search the nat_plays
        restNat_playsMockMvc.perform(get("/api/_search/nat-plays?query=id:" + nat_plays.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nat_plays.getId().intValue())))
            .andExpect(jsonPath("$.[*].drawDate").value(hasItem(DEFAULT_DRAW_DATE.toString())))
            .andExpect(jsonPath("$.[*].ball1").value(hasItem(DEFAULT_BALL_1)))
            .andExpect(jsonPath("$.[*].ball2").value(hasItem(DEFAULT_BALL_2)))
            .andExpect(jsonPath("$.[*].ball3").value(hasItem(DEFAULT_BALL_3)))
            .andExpect(jsonPath("$.[*].ball4").value(hasItem(DEFAULT_BALL_4)))
            .andExpect(jsonPath("$.[*].ball5").value(hasItem(DEFAULT_BALL_5)))
            .andExpect(jsonPath("$.[*].ball6").value(hasItem(DEFAULT_BALL_6)))
            .andExpect(jsonPath("$.[*].bonusBall").value(hasItem(DEFAULT_BONUS_BALL)))
            .andExpect(jsonPath("$.[*].drawType").value(hasItem(DEFAULT_DRAW_TYPE.toString())))
            .andExpect(jsonPath("$.[*].gameType").value(hasItem(DEFAULT_GAME_TYPE.toString())))
            .andExpect(jsonPath("$.[*].prize").value(hasItem(DEFAULT_PRIZE.doubleValue())))
            .andExpect(jsonPath("$.[*].checked").value(hasItem(DEFAULT_CHECKED.booleanValue())))
            .andExpect(jsonPath("$.[*].rank_id").value(hasItem(DEFAULT_RANK_ID.intValue())))
            .andExpect(jsonPath("$.[*].sumB").value(hasItem(DEFAULT_SUM_B)))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nat_plays.class);
    }
}
