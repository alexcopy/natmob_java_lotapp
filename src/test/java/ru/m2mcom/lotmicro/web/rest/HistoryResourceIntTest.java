package ru.m2mcom.lotmicro.web.rest;

import ru.m2mcom.lotmicro.LotmicroApp;

import ru.m2mcom.lotmicro.domain.History;
import ru.m2mcom.lotmicro.repository.HistoryRepository;
import ru.m2mcom.lotmicro.service.HistoryService;
import ru.m2mcom.lotmicro.repository.search.HistorySearchRepository;
import ru.m2mcom.lotmicro.service.dto.HistoryDTO;
import ru.m2mcom.lotmicro.service.mapper.HistoryMapper;
import ru.m2mcom.lotmicro.web.rest.errors.ExceptionTranslator;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ru.m2mcom.lotmicro.domain.enumeration.GamesPlay;
/**
 * Test class for the HistoryResource REST controller.
 *
 * @see HistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LotmicroApp.class)
public class HistoryResourceIntTest {

    private static final LocalDate DEFAULT_DRAW_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DRAW_DATE = LocalDate.now(ZoneId.systemDefault());

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

    private static final Integer DEFAULT_BONUS_BALL_1 = 1;
    private static final Integer UPDATED_BONUS_BALL_1 = 2;

    private static final Integer DEFAULT_BONUS_BALL_2 = 1;
    private static final Integer UPDATED_BONUS_BALL_2 = 2;

    private static final Integer DEFAULT_DRAW_NUMBER = 1;
    private static final Integer UPDATED_DRAW_NUMBER = 2;

    private static final String DEFAULT_BALL_SET = "AAAAAAAAAA";
    private static final String UPDATED_BALL_SET = "BBBBBBBBBB";

    private static final String DEFAULT_WINS = "AAAAAAAAAA";
    private static final String UPDATED_WINS = "BBBBBBBBBB";

    private static final String DEFAULT_MACHINE = "AAAAAAAAAA";
    private static final String UPDATED_MACHINE = "BBBBBBBBBB";

    private static final Integer DEFAULT_SUM_B = 1;
    private static final Integer UPDATED_SUM_B = 2;

    private static final Integer DEFAULT_SUM_S = 1;
    private static final Integer UPDATED_SUM_S = 2;

    private static final Integer DEFAULT_JACKPOT = 1;
    private static final Integer UPDATED_JACKPOT = 2;

    private static final String DEFAULT_HASH = "AAAAAAAAAA";
    private static final String UPDATED_HASH = "BBBBBBBBBB";

    private static final String DEFAULT_DRAW_HASH = "AAAAAAAAAA";
    private static final String UPDATED_DRAW_HASH = "BBBBBBBBBB";

    private static final GamesPlay DEFAULT_GAME = GamesPlay.EML;
    private static final GamesPlay UPDATED_GAME = GamesPlay.NAT;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private HistoryMapper historyMapper;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private HistorySearchRepository historySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHistoryMockMvc;

    private History history;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HistoryResource historyResource = new HistoryResource(historyService);
        this.restHistoryMockMvc = MockMvcBuilders.standaloneSetup(historyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static History createEntity(EntityManager em) {
        History history = new History()
            .drawDate(DEFAULT_DRAW_DATE)
            .ball1(DEFAULT_BALL_1)
            .ball2(DEFAULT_BALL_2)
            .ball3(DEFAULT_BALL_3)
            .ball4(DEFAULT_BALL_4)
            .ball5(DEFAULT_BALL_5)
            .ball6(DEFAULT_BALL_6)
            .bonusBall1(DEFAULT_BONUS_BALL_1)
            .bonusBall2(DEFAULT_BONUS_BALL_2)
            .drawNumber(DEFAULT_DRAW_NUMBER)
            .ballSet(DEFAULT_BALL_SET)
            .wins(DEFAULT_WINS)
            .machine(DEFAULT_MACHINE)
            .sumB(DEFAULT_SUM_B)
            .sumS(DEFAULT_SUM_S)
            .jackpot(DEFAULT_JACKPOT)
            .hash(DEFAULT_HASH)
            .drawHash(DEFAULT_DRAW_HASH)
            .game(DEFAULT_GAME);
        return history;
    }

    @Before
    public void initTest() {
        historySearchRepository.deleteAll();
        history = createEntity(em);
    }

    @Test
    @Transactional
    public void createHistory() throws Exception {
        int databaseSizeBeforeCreate = historyRepository.findAll().size();

        // Create the History
        HistoryDTO historyDTO = historyMapper.historyToHistoryDTO(history);
        restHistoryMockMvc.perform(post("/api/histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historyDTO)))
            .andExpect(status().isCreated());

        // Validate the History in the database
        List<History> historyList = historyRepository.findAll();
        assertThat(historyList).hasSize(databaseSizeBeforeCreate + 1);
        History testHistory = historyList.get(historyList.size() - 1);
        assertThat(testHistory.getDrawDate()).isEqualTo(DEFAULT_DRAW_DATE);
        assertThat(testHistory.getBall1()).isEqualTo(DEFAULT_BALL_1);
        assertThat(testHistory.getBall2()).isEqualTo(DEFAULT_BALL_2);
        assertThat(testHistory.getBall3()).isEqualTo(DEFAULT_BALL_3);
        assertThat(testHistory.getBall4()).isEqualTo(DEFAULT_BALL_4);
        assertThat(testHistory.getBall5()).isEqualTo(DEFAULT_BALL_5);
        assertThat(testHistory.getBall6()).isEqualTo(DEFAULT_BALL_6);
        assertThat(testHistory.getBonusBall1()).isEqualTo(DEFAULT_BONUS_BALL_1);
        assertThat(testHistory.getBonusBall2()).isEqualTo(DEFAULT_BONUS_BALL_2);
        assertThat(testHistory.getDrawNumber()).isEqualTo(DEFAULT_DRAW_NUMBER);
        assertThat(testHistory.getBallSet()).isEqualTo(DEFAULT_BALL_SET);
        assertThat(testHistory.getWins()).isEqualTo(DEFAULT_WINS);
        assertThat(testHistory.getMachine()).isEqualTo(DEFAULT_MACHINE);
        assertThat(testHistory.getSumB()).isEqualTo(DEFAULT_SUM_B);
        assertThat(testHistory.getSumS()).isEqualTo(DEFAULT_SUM_S);
        assertThat(testHistory.getJackpot()).isEqualTo(DEFAULT_JACKPOT);
        assertThat(testHistory.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testHistory.getDrawHash()).isEqualTo(DEFAULT_DRAW_HASH);
        assertThat(testHistory.getGame()).isEqualTo(DEFAULT_GAME);

        // Validate the History in Elasticsearch
        History historyEs = historySearchRepository.findOne(testHistory.getId());
        assertThat(historyEs).isEqualToComparingFieldByField(testHistory);
    }

    @Test
    @Transactional
    public void createHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = historyRepository.findAll().size();

        // Create the History with an existing ID
        history.setId(1L);
        HistoryDTO historyDTO = historyMapper.historyToHistoryDTO(history);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHistoryMockMvc.perform(post("/api/histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<History> historyList = historyRepository.findAll();
        assertThat(historyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDrawDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = historyRepository.findAll().size();
        // set the field null
        history.setDrawDate(null);

        // Create the History, which fails.
        HistoryDTO historyDTO = historyMapper.historyToHistoryDTO(history);

        restHistoryMockMvc.perform(post("/api/histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historyDTO)))
            .andExpect(status().isBadRequest());

        List<History> historyList = historyRepository.findAll();
        assertThat(historyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHistories() throws Exception {
        // Initialize the database
        historyRepository.saveAndFlush(history);

        // Get all the historyList
        restHistoryMockMvc.perform(get("/api/histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(history.getId().intValue())))
            .andExpect(jsonPath("$.[*].drawDate").value(hasItem(DEFAULT_DRAW_DATE.toString())))
            .andExpect(jsonPath("$.[*].ball1").value(hasItem(DEFAULT_BALL_1)))
            .andExpect(jsonPath("$.[*].ball2").value(hasItem(DEFAULT_BALL_2)))
            .andExpect(jsonPath("$.[*].ball3").value(hasItem(DEFAULT_BALL_3)))
            .andExpect(jsonPath("$.[*].ball4").value(hasItem(DEFAULT_BALL_4)))
            .andExpect(jsonPath("$.[*].ball5").value(hasItem(DEFAULT_BALL_5)))
            .andExpect(jsonPath("$.[*].ball6").value(hasItem(DEFAULT_BALL_6)))
            .andExpect(jsonPath("$.[*].bonusBall1").value(hasItem(DEFAULT_BONUS_BALL_1)))
            .andExpect(jsonPath("$.[*].bonusBall2").value(hasItem(DEFAULT_BONUS_BALL_2)))
            .andExpect(jsonPath("$.[*].drawNumber").value(hasItem(DEFAULT_DRAW_NUMBER)))
            .andExpect(jsonPath("$.[*].ballSet").value(hasItem(DEFAULT_BALL_SET.toString())))
            .andExpect(jsonPath("$.[*].wins").value(hasItem(DEFAULT_WINS.toString())))
            .andExpect(jsonPath("$.[*].machine").value(hasItem(DEFAULT_MACHINE.toString())))
            .andExpect(jsonPath("$.[*].sumB").value(hasItem(DEFAULT_SUM_B)))
            .andExpect(jsonPath("$.[*].sumS").value(hasItem(DEFAULT_SUM_S)))
            .andExpect(jsonPath("$.[*].jackpot").value(hasItem(DEFAULT_JACKPOT)))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH.toString())))
            .andExpect(jsonPath("$.[*].drawHash").value(hasItem(DEFAULT_DRAW_HASH.toString())))
            .andExpect(jsonPath("$.[*].game").value(hasItem(DEFAULT_GAME.toString())));
    }

    @Test
    @Transactional
    public void getHistory() throws Exception {
        // Initialize the database
        historyRepository.saveAndFlush(history);

        // Get the history
        restHistoryMockMvc.perform(get("/api/histories/{id}", history.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(history.getId().intValue()))
            .andExpect(jsonPath("$.drawDate").value(DEFAULT_DRAW_DATE.toString()))
            .andExpect(jsonPath("$.ball1").value(DEFAULT_BALL_1))
            .andExpect(jsonPath("$.ball2").value(DEFAULT_BALL_2))
            .andExpect(jsonPath("$.ball3").value(DEFAULT_BALL_3))
            .andExpect(jsonPath("$.ball4").value(DEFAULT_BALL_4))
            .andExpect(jsonPath("$.ball5").value(DEFAULT_BALL_5))
            .andExpect(jsonPath("$.ball6").value(DEFAULT_BALL_6))
            .andExpect(jsonPath("$.bonusBall1").value(DEFAULT_BONUS_BALL_1))
            .andExpect(jsonPath("$.bonusBall2").value(DEFAULT_BONUS_BALL_2))
            .andExpect(jsonPath("$.drawNumber").value(DEFAULT_DRAW_NUMBER))
            .andExpect(jsonPath("$.ballSet").value(DEFAULT_BALL_SET.toString()))
            .andExpect(jsonPath("$.wins").value(DEFAULT_WINS.toString()))
            .andExpect(jsonPath("$.machine").value(DEFAULT_MACHINE.toString()))
            .andExpect(jsonPath("$.sumB").value(DEFAULT_SUM_B))
            .andExpect(jsonPath("$.sumS").value(DEFAULT_SUM_S))
            .andExpect(jsonPath("$.jackpot").value(DEFAULT_JACKPOT))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH.toString()))
            .andExpect(jsonPath("$.drawHash").value(DEFAULT_DRAW_HASH.toString()))
            .andExpect(jsonPath("$.game").value(DEFAULT_GAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHistory() throws Exception {
        // Get the history
        restHistoryMockMvc.perform(get("/api/histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHistory() throws Exception {
        // Initialize the database
        historyRepository.saveAndFlush(history);
        historySearchRepository.save(history);
        int databaseSizeBeforeUpdate = historyRepository.findAll().size();

        // Update the history
        History updatedHistory = historyRepository.findOne(history.getId());
        updatedHistory
            .drawDate(UPDATED_DRAW_DATE)
            .ball1(UPDATED_BALL_1)
            .ball2(UPDATED_BALL_2)
            .ball3(UPDATED_BALL_3)
            .ball4(UPDATED_BALL_4)
            .ball5(UPDATED_BALL_5)
            .ball6(UPDATED_BALL_6)
            .bonusBall1(UPDATED_BONUS_BALL_1)
            .bonusBall2(UPDATED_BONUS_BALL_2)
            .drawNumber(UPDATED_DRAW_NUMBER)
            .ballSet(UPDATED_BALL_SET)
            .wins(UPDATED_WINS)
            .machine(UPDATED_MACHINE)
            .sumB(UPDATED_SUM_B)
            .sumS(UPDATED_SUM_S)
            .jackpot(UPDATED_JACKPOT)
            .hash(UPDATED_HASH)
            .drawHash(UPDATED_DRAW_HASH)
            .game(UPDATED_GAME);
        HistoryDTO historyDTO = historyMapper.historyToHistoryDTO(updatedHistory);

        restHistoryMockMvc.perform(put("/api/histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historyDTO)))
            .andExpect(status().isOk());

        // Validate the History in the database
        List<History> historyList = historyRepository.findAll();
        assertThat(historyList).hasSize(databaseSizeBeforeUpdate);
        History testHistory = historyList.get(historyList.size() - 1);
        assertThat(testHistory.getDrawDate()).isEqualTo(UPDATED_DRAW_DATE);
        assertThat(testHistory.getBall1()).isEqualTo(UPDATED_BALL_1);
        assertThat(testHistory.getBall2()).isEqualTo(UPDATED_BALL_2);
        assertThat(testHistory.getBall3()).isEqualTo(UPDATED_BALL_3);
        assertThat(testHistory.getBall4()).isEqualTo(UPDATED_BALL_4);
        assertThat(testHistory.getBall5()).isEqualTo(UPDATED_BALL_5);
        assertThat(testHistory.getBall6()).isEqualTo(UPDATED_BALL_6);
        assertThat(testHistory.getBonusBall1()).isEqualTo(UPDATED_BONUS_BALL_1);
        assertThat(testHistory.getBonusBall2()).isEqualTo(UPDATED_BONUS_BALL_2);
        assertThat(testHistory.getDrawNumber()).isEqualTo(UPDATED_DRAW_NUMBER);
        assertThat(testHistory.getBallSet()).isEqualTo(UPDATED_BALL_SET);
        assertThat(testHistory.getWins()).isEqualTo(UPDATED_WINS);
        assertThat(testHistory.getMachine()).isEqualTo(UPDATED_MACHINE);
        assertThat(testHistory.getSumB()).isEqualTo(UPDATED_SUM_B);
        assertThat(testHistory.getSumS()).isEqualTo(UPDATED_SUM_S);
        assertThat(testHistory.getJackpot()).isEqualTo(UPDATED_JACKPOT);
        assertThat(testHistory.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testHistory.getDrawHash()).isEqualTo(UPDATED_DRAW_HASH);
        assertThat(testHistory.getGame()).isEqualTo(UPDATED_GAME);

        // Validate the History in Elasticsearch
        History historyEs = historySearchRepository.findOne(testHistory.getId());
        assertThat(historyEs).isEqualToComparingFieldByField(testHistory);
    }

    @Test
    @Transactional
    public void updateNonExistingHistory() throws Exception {
        int databaseSizeBeforeUpdate = historyRepository.findAll().size();

        // Create the History
        HistoryDTO historyDTO = historyMapper.historyToHistoryDTO(history);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHistoryMockMvc.perform(put("/api/histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historyDTO)))
            .andExpect(status().isCreated());

        // Validate the History in the database
        List<History> historyList = historyRepository.findAll();
        assertThat(historyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHistory() throws Exception {
        // Initialize the database
        historyRepository.saveAndFlush(history);
        historySearchRepository.save(history);
        int databaseSizeBeforeDelete = historyRepository.findAll().size();

        // Get the history
        restHistoryMockMvc.perform(delete("/api/histories/{id}", history.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean historyExistsInEs = historySearchRepository.exists(history.getId());
        assertThat(historyExistsInEs).isFalse();

        // Validate the database is empty
        List<History> historyList = historyRepository.findAll();
        assertThat(historyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchHistory() throws Exception {
        // Initialize the database
        historyRepository.saveAndFlush(history);
        historySearchRepository.save(history);

        // Search the history
        restHistoryMockMvc.perform(get("/api/_search/histories?query=id:" + history.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(history.getId().intValue())))
            .andExpect(jsonPath("$.[*].drawDate").value(hasItem(DEFAULT_DRAW_DATE.toString())))
            .andExpect(jsonPath("$.[*].ball1").value(hasItem(DEFAULT_BALL_1)))
            .andExpect(jsonPath("$.[*].ball2").value(hasItem(DEFAULT_BALL_2)))
            .andExpect(jsonPath("$.[*].ball3").value(hasItem(DEFAULT_BALL_3)))
            .andExpect(jsonPath("$.[*].ball4").value(hasItem(DEFAULT_BALL_4)))
            .andExpect(jsonPath("$.[*].ball5").value(hasItem(DEFAULT_BALL_5)))
            .andExpect(jsonPath("$.[*].ball6").value(hasItem(DEFAULT_BALL_6)))
            .andExpect(jsonPath("$.[*].bonusBall1").value(hasItem(DEFAULT_BONUS_BALL_1)))
            .andExpect(jsonPath("$.[*].bonusBall2").value(hasItem(DEFAULT_BONUS_BALL_2)))
            .andExpect(jsonPath("$.[*].drawNumber").value(hasItem(DEFAULT_DRAW_NUMBER)))
            .andExpect(jsonPath("$.[*].ballSet").value(hasItem(DEFAULT_BALL_SET.toString())))
            .andExpect(jsonPath("$.[*].wins").value(hasItem(DEFAULT_WINS.toString())))
            .andExpect(jsonPath("$.[*].machine").value(hasItem(DEFAULT_MACHINE.toString())))
            .andExpect(jsonPath("$.[*].sumB").value(hasItem(DEFAULT_SUM_B)))
            .andExpect(jsonPath("$.[*].sumS").value(hasItem(DEFAULT_SUM_S)))
            .andExpect(jsonPath("$.[*].jackpot").value(hasItem(DEFAULT_JACKPOT)))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH.toString())))
            .andExpect(jsonPath("$.[*].drawHash").value(hasItem(DEFAULT_DRAW_HASH.toString())))
            .andExpect(jsonPath("$.[*].game").value(hasItem(DEFAULT_GAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(History.class);
    }
}
