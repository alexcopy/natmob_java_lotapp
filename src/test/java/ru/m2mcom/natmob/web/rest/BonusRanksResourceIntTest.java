package ru.m2mcom.natmob.web.rest;

import ru.m2mcom.natmob.LotappApp;

import ru.m2mcom.natmob.domain.BonusRanks;
import ru.m2mcom.natmob.repository.BonusRanksRepository;
import ru.m2mcom.natmob.service.BonusRanksService;
import ru.m2mcom.natmob.repository.search.BonusRanksSearchRepository;

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
 * Test class for the BonusRanksResource REST controller.
 *
 * @see BonusRanksResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LotappApp.class)
public class BonusRanksResourceIntTest {

    private static final String DEFAULT_GAME = "AAAAAAAAAA";
    private static final String UPDATED_GAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_SUMM_IN_RECENT_HIST_RANK = 1;
    private static final Integer UPDATED_SUMM_IN_RECENT_HIST_RANK = 2;

    private static final Integer DEFAULT_PROBABILITY_AND_MO = 1;
    private static final Integer UPDATED_PROBABILITY_AND_MO = 2;

    private static final Integer DEFAULT_LONG_ODD_EVEN_RANK = 1;
    private static final Integer UPDATED_LONG_ODD_EVEN_RANK = 2;

    private static final Integer DEFAULT_SUMM_ANALISIS_WITH_WEIGHTS = 1;
    private static final Integer UPDATED_SUMM_ANALISIS_WITH_WEIGHTS = 2;

    private static final Integer DEFAULT_IN_HIST_DUE_NOT_DUE_ANALISIS = 1;
    private static final Integer UPDATED_IN_HIST_DUE_NOT_DUE_ANALISIS = 2;

    private static final Integer DEFAULT_ALL_NUMBERS_RANK = 1;
    private static final Integer UPDATED_ALL_NUMBERS_RANK = 2;

    private static final Integer DEFAULT_TOTAL_RANK = 1;
    private static final Integer UPDATED_TOTAL_RANK = 2;

    private static final Integer DEFAULT_TIMESTAMP = 1;
    private static final Integer UPDATED_TIMESTAMP = 2;

    @Autowired
    private BonusRanksRepository bonusRanksRepository;

    @Autowired
    private BonusRanksService bonusRanksService;

    @Autowired
    private BonusRanksSearchRepository bonusRanksSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restBonusRanksMockMvc;

    private BonusRanks bonusRanks;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BonusRanksResource bonusRanksResource = new BonusRanksResource(bonusRanksService);
        this.restBonusRanksMockMvc = MockMvcBuilders.standaloneSetup(bonusRanksResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BonusRanks createEntity(EntityManager em) {
        BonusRanks bonusRanks = new BonusRanks()
                .game(DEFAULT_GAME)
                .summInRecentHistRank(DEFAULT_SUMM_IN_RECENT_HIST_RANK)
                .probabilityAndMO(DEFAULT_PROBABILITY_AND_MO)
                .longOddEvenRank(DEFAULT_LONG_ODD_EVEN_RANK)
                .summAnalisisWithWeights(DEFAULT_SUMM_ANALISIS_WITH_WEIGHTS)
                .inHistDueNotDueAnalisis(DEFAULT_IN_HIST_DUE_NOT_DUE_ANALISIS)
                .allNumbersRank(DEFAULT_ALL_NUMBERS_RANK)
                .totalRank(DEFAULT_TOTAL_RANK)
                .timestamp(DEFAULT_TIMESTAMP);
        return bonusRanks;
    }

    @Before
    public void initTest() {
        bonusRanksSearchRepository.deleteAll();
        bonusRanks = createEntity(em);
    }

    @Test
    @Transactional
    public void createBonusRanks() throws Exception {
        int databaseSizeBeforeCreate = bonusRanksRepository.findAll().size();

        // Create the BonusRanks

        restBonusRanksMockMvc.perform(post("/api/bonus-ranks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bonusRanks)))
            .andExpect(status().isCreated());

        // Validate the BonusRanks in the database
        List<BonusRanks> bonusRanksList = bonusRanksRepository.findAll();
        assertThat(bonusRanksList).hasSize(databaseSizeBeforeCreate + 1);
        BonusRanks testBonusRanks = bonusRanksList.get(bonusRanksList.size() - 1);
        assertThat(testBonusRanks.getGame()).isEqualTo(DEFAULT_GAME);
        assertThat(testBonusRanks.getSummInRecentHistRank()).isEqualTo(DEFAULT_SUMM_IN_RECENT_HIST_RANK);
        assertThat(testBonusRanks.getProbabilityAndMO()).isEqualTo(DEFAULT_PROBABILITY_AND_MO);
        assertThat(testBonusRanks.getLongOddEvenRank()).isEqualTo(DEFAULT_LONG_ODD_EVEN_RANK);
        assertThat(testBonusRanks.getSummAnalisisWithWeights()).isEqualTo(DEFAULT_SUMM_ANALISIS_WITH_WEIGHTS);
        assertThat(testBonusRanks.getInHistDueNotDueAnalisis()).isEqualTo(DEFAULT_IN_HIST_DUE_NOT_DUE_ANALISIS);
        assertThat(testBonusRanks.getAllNumbersRank()).isEqualTo(DEFAULT_ALL_NUMBERS_RANK);
        assertThat(testBonusRanks.getTotalRank()).isEqualTo(DEFAULT_TOTAL_RANK);
        assertThat(testBonusRanks.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);

        // Validate the BonusRanks in Elasticsearch
        BonusRanks bonusRanksEs = bonusRanksSearchRepository.findOne(testBonusRanks.getId());
        assertThat(bonusRanksEs).isEqualToComparingFieldByField(testBonusRanks);
    }

    @Test
    @Transactional
    public void createBonusRanksWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bonusRanksRepository.findAll().size();

        // Create the BonusRanks with an existing ID
        BonusRanks existingBonusRanks = new BonusRanks();
        existingBonusRanks.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBonusRanksMockMvc.perform(post("/api/bonus-ranks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingBonusRanks)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BonusRanks> bonusRanksList = bonusRanksRepository.findAll();
        assertThat(bonusRanksList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkGameIsRequired() throws Exception {
        int databaseSizeBeforeTest = bonusRanksRepository.findAll().size();
        // set the field null
        bonusRanks.setGame(null);

        // Create the BonusRanks, which fails.

        restBonusRanksMockMvc.perform(post("/api/bonus-ranks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bonusRanks)))
            .andExpect(status().isBadRequest());

        List<BonusRanks> bonusRanksList = bonusRanksRepository.findAll();
        assertThat(bonusRanksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBonusRanks() throws Exception {
        // Initialize the database
        bonusRanksRepository.saveAndFlush(bonusRanks);

        // Get all the bonusRanksList
        restBonusRanksMockMvc.perform(get("/api/bonus-ranks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bonusRanks.getId().intValue())))
            .andExpect(jsonPath("$.[*].game").value(hasItem(DEFAULT_GAME.toString())))
            .andExpect(jsonPath("$.[*].summInRecentHistRank").value(hasItem(DEFAULT_SUMM_IN_RECENT_HIST_RANK)))
            .andExpect(jsonPath("$.[*].probabilityAndMO").value(hasItem(DEFAULT_PROBABILITY_AND_MO)))
            .andExpect(jsonPath("$.[*].longOddEvenRank").value(hasItem(DEFAULT_LONG_ODD_EVEN_RANK)))
            .andExpect(jsonPath("$.[*].summAnalisisWithWeights").value(hasItem(DEFAULT_SUMM_ANALISIS_WITH_WEIGHTS)))
            .andExpect(jsonPath("$.[*].inHistDueNotDueAnalisis").value(hasItem(DEFAULT_IN_HIST_DUE_NOT_DUE_ANALISIS)))
            .andExpect(jsonPath("$.[*].allNumbersRank").value(hasItem(DEFAULT_ALL_NUMBERS_RANK)))
            .andExpect(jsonPath("$.[*].totalRank").value(hasItem(DEFAULT_TOTAL_RANK)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP)));
    }

    @Test
    @Transactional
    public void getBonusRanks() throws Exception {
        // Initialize the database
        bonusRanksRepository.saveAndFlush(bonusRanks);

        // Get the bonusRanks
        restBonusRanksMockMvc.perform(get("/api/bonus-ranks/{id}", bonusRanks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bonusRanks.getId().intValue()))
            .andExpect(jsonPath("$.game").value(DEFAULT_GAME.toString()))
            .andExpect(jsonPath("$.summInRecentHistRank").value(DEFAULT_SUMM_IN_RECENT_HIST_RANK))
            .andExpect(jsonPath("$.probabilityAndMO").value(DEFAULT_PROBABILITY_AND_MO))
            .andExpect(jsonPath("$.longOddEvenRank").value(DEFAULT_LONG_ODD_EVEN_RANK))
            .andExpect(jsonPath("$.summAnalisisWithWeights").value(DEFAULT_SUMM_ANALISIS_WITH_WEIGHTS))
            .andExpect(jsonPath("$.inHistDueNotDueAnalisis").value(DEFAULT_IN_HIST_DUE_NOT_DUE_ANALISIS))
            .andExpect(jsonPath("$.allNumbersRank").value(DEFAULT_ALL_NUMBERS_RANK))
            .andExpect(jsonPath("$.totalRank").value(DEFAULT_TOTAL_RANK))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP));
    }

    @Test
    @Transactional
    public void getNonExistingBonusRanks() throws Exception {
        // Get the bonusRanks
        restBonusRanksMockMvc.perform(get("/api/bonus-ranks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBonusRanks() throws Exception {
        // Initialize the database
        bonusRanksService.save(bonusRanks);

        int databaseSizeBeforeUpdate = bonusRanksRepository.findAll().size();

        // Update the bonusRanks
        BonusRanks updatedBonusRanks = bonusRanksRepository.findOne(bonusRanks.getId());
        updatedBonusRanks
                .game(UPDATED_GAME)
                .summInRecentHistRank(UPDATED_SUMM_IN_RECENT_HIST_RANK)
                .probabilityAndMO(UPDATED_PROBABILITY_AND_MO)
                .longOddEvenRank(UPDATED_LONG_ODD_EVEN_RANK)
                .summAnalisisWithWeights(UPDATED_SUMM_ANALISIS_WITH_WEIGHTS)
                .inHistDueNotDueAnalisis(UPDATED_IN_HIST_DUE_NOT_DUE_ANALISIS)
                .allNumbersRank(UPDATED_ALL_NUMBERS_RANK)
                .totalRank(UPDATED_TOTAL_RANK)
                .timestamp(UPDATED_TIMESTAMP);

        restBonusRanksMockMvc.perform(put("/api/bonus-ranks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBonusRanks)))
            .andExpect(status().isOk());

        // Validate the BonusRanks in the database
        List<BonusRanks> bonusRanksList = bonusRanksRepository.findAll();
        assertThat(bonusRanksList).hasSize(databaseSizeBeforeUpdate);
        BonusRanks testBonusRanks = bonusRanksList.get(bonusRanksList.size() - 1);
        assertThat(testBonusRanks.getGame()).isEqualTo(UPDATED_GAME);
        assertThat(testBonusRanks.getSummInRecentHistRank()).isEqualTo(UPDATED_SUMM_IN_RECENT_HIST_RANK);
        assertThat(testBonusRanks.getProbabilityAndMO()).isEqualTo(UPDATED_PROBABILITY_AND_MO);
        assertThat(testBonusRanks.getLongOddEvenRank()).isEqualTo(UPDATED_LONG_ODD_EVEN_RANK);
        assertThat(testBonusRanks.getSummAnalisisWithWeights()).isEqualTo(UPDATED_SUMM_ANALISIS_WITH_WEIGHTS);
        assertThat(testBonusRanks.getInHistDueNotDueAnalisis()).isEqualTo(UPDATED_IN_HIST_DUE_NOT_DUE_ANALISIS);
        assertThat(testBonusRanks.getAllNumbersRank()).isEqualTo(UPDATED_ALL_NUMBERS_RANK);
        assertThat(testBonusRanks.getTotalRank()).isEqualTo(UPDATED_TOTAL_RANK);
        assertThat(testBonusRanks.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);

        // Validate the BonusRanks in Elasticsearch
        BonusRanks bonusRanksEs = bonusRanksSearchRepository.findOne(testBonusRanks.getId());
        assertThat(bonusRanksEs).isEqualToComparingFieldByField(testBonusRanks);
    }

    @Test
    @Transactional
    public void updateNonExistingBonusRanks() throws Exception {
        int databaseSizeBeforeUpdate = bonusRanksRepository.findAll().size();

        // Create the BonusRanks

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBonusRanksMockMvc.perform(put("/api/bonus-ranks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bonusRanks)))
            .andExpect(status().isCreated());

        // Validate the BonusRanks in the database
        List<BonusRanks> bonusRanksList = bonusRanksRepository.findAll();
        assertThat(bonusRanksList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBonusRanks() throws Exception {
        // Initialize the database
        bonusRanksService.save(bonusRanks);

        int databaseSizeBeforeDelete = bonusRanksRepository.findAll().size();

        // Get the bonusRanks
        restBonusRanksMockMvc.perform(delete("/api/bonus-ranks/{id}", bonusRanks.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean bonusRanksExistsInEs = bonusRanksSearchRepository.exists(bonusRanks.getId());
        assertThat(bonusRanksExistsInEs).isFalse();

        // Validate the database is empty
        List<BonusRanks> bonusRanksList = bonusRanksRepository.findAll();
        assertThat(bonusRanksList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBonusRanks() throws Exception {
        // Initialize the database
        bonusRanksService.save(bonusRanks);

        // Search the bonusRanks
        restBonusRanksMockMvc.perform(get("/api/_search/bonus-ranks?query=id:" + bonusRanks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bonusRanks.getId().intValue())))
            .andExpect(jsonPath("$.[*].game").value(hasItem(DEFAULT_GAME.toString())))
            .andExpect(jsonPath("$.[*].summInRecentHistRank").value(hasItem(DEFAULT_SUMM_IN_RECENT_HIST_RANK)))
            .andExpect(jsonPath("$.[*].probabilityAndMO").value(hasItem(DEFAULT_PROBABILITY_AND_MO)))
            .andExpect(jsonPath("$.[*].longOddEvenRank").value(hasItem(DEFAULT_LONG_ODD_EVEN_RANK)))
            .andExpect(jsonPath("$.[*].summAnalisisWithWeights").value(hasItem(DEFAULT_SUMM_ANALISIS_WITH_WEIGHTS)))
            .andExpect(jsonPath("$.[*].inHistDueNotDueAnalisis").value(hasItem(DEFAULT_IN_HIST_DUE_NOT_DUE_ANALISIS)))
            .andExpect(jsonPath("$.[*].allNumbersRank").value(hasItem(DEFAULT_ALL_NUMBERS_RANK)))
            .andExpect(jsonPath("$.[*].totalRank").value(hasItem(DEFAULT_TOTAL_RANK)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BonusRanks.class);
    }
}
