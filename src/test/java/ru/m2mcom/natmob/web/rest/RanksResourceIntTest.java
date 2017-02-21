package ru.m2mcom.natmob.web.rest;

import ru.m2mcom.natmob.LotappApp;

import ru.m2mcom.natmob.domain.Ranks;
import ru.m2mcom.natmob.repository.RanksRepository;
import ru.m2mcom.natmob.service.RanksService;
import ru.m2mcom.natmob.repository.search.RanksSearchRepository;

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
 * Test class for the RanksResource REST controller.
 *
 * @see RanksResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LotappApp.class)
public class RanksResourceIntTest {

    private static final String DEFAULT_GAME = "AAAAAAAAAA";
    private static final String UPDATED_GAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_ALL_NUMBERS_RANK = 1;
    private static final Integer UPDATED_ALL_NUMBERS_RANK = 2;

    private static final Integer DEFAULT_LONG_ODD_EVEN_RANK = 1;
    private static final Integer UPDATED_LONG_ODD_EVEN_RANK = 2;

    private static final Integer DEFAULT_SHORT_ODD_EVEN_RANK = 1;
    private static final Integer UPDATED_SHORT_ODD_EVEN_RANK = 2;

    private static final Integer DEFAULT_SHORT_HISTORY_SUM_RANK = 1;
    private static final Integer UPDATED_SHORT_HISTORY_SUM_RANK = 2;

    private static final Integer DEFAULT_LONG_HIST_SUM_RANK = 1;
    private static final Integer UPDATED_LONG_HIST_SUM_RANK = 2;

    private static final Integer DEFAULT_SUMM_IN_HIST_RANK = 1;
    private static final Integer UPDATED_SUMM_IN_HIST_RANK = 2;

    private static final Integer DEFAULT_BEEN_DRAWN_IN_PAST = 1;
    private static final Integer UPDATED_BEEN_DRAWN_IN_PAST = 2;

    private static final Integer DEFAULT_SUMM_IN_RECENT_HIST_RANK = 1;
    private static final Integer UPDATED_SUMM_IN_RECENT_HIST_RANK = 2;

    private static final Integer DEFAULT_TOTAL_RANK = 1;
    private static final Integer UPDATED_TOTAL_RANK = 2;

    @Autowired
    private RanksRepository ranksRepository;

    @Autowired
    private RanksService ranksService;

    @Autowired
    private RanksSearchRepository ranksSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restRanksMockMvc;

    private Ranks ranks;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RanksResource ranksResource = new RanksResource(ranksService);
        this.restRanksMockMvc = MockMvcBuilders.standaloneSetup(ranksResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ranks createEntity(EntityManager em) {
        Ranks ranks = new Ranks()
                .game(DEFAULT_GAME)
                .allNumbersRank(DEFAULT_ALL_NUMBERS_RANK)
                .longOddEvenRank(DEFAULT_LONG_ODD_EVEN_RANK)
                .shortOddEvenRank(DEFAULT_SHORT_ODD_EVEN_RANK)
                .shortHistorySumRank(DEFAULT_SHORT_HISTORY_SUM_RANK)
                .longHistSumRank(DEFAULT_LONG_HIST_SUM_RANK)
                .summInHistRank(DEFAULT_SUMM_IN_HIST_RANK)
                .beenDrawnInPast(DEFAULT_BEEN_DRAWN_IN_PAST)
                .summInRecentHistRank(DEFAULT_SUMM_IN_RECENT_HIST_RANK)
                .totalRank(DEFAULT_TOTAL_RANK);
        return ranks;
    }

    @Before
    public void initTest() {
        ranksSearchRepository.deleteAll();
        ranks = createEntity(em);
    }

    @Test
    @Transactional
    public void createRanks() throws Exception {
        int databaseSizeBeforeCreate = ranksRepository.findAll().size();

        // Create the Ranks

        restRanksMockMvc.perform(post("/api/ranks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ranks)))
            .andExpect(status().isCreated());

        // Validate the Ranks in the database
        List<Ranks> ranksList = ranksRepository.findAll();
        assertThat(ranksList).hasSize(databaseSizeBeforeCreate + 1);
        Ranks testRanks = ranksList.get(ranksList.size() - 1);
        assertThat(testRanks.getGame()).isEqualTo(DEFAULT_GAME);
        assertThat(testRanks.getAllNumbersRank()).isEqualTo(DEFAULT_ALL_NUMBERS_RANK);
        assertThat(testRanks.getLongOddEvenRank()).isEqualTo(DEFAULT_LONG_ODD_EVEN_RANK);
        assertThat(testRanks.getShortOddEvenRank()).isEqualTo(DEFAULT_SHORT_ODD_EVEN_RANK);
        assertThat(testRanks.getShortHistorySumRank()).isEqualTo(DEFAULT_SHORT_HISTORY_SUM_RANK);
        assertThat(testRanks.getLongHistSumRank()).isEqualTo(DEFAULT_LONG_HIST_SUM_RANK);
        assertThat(testRanks.getSummInHistRank()).isEqualTo(DEFAULT_SUMM_IN_HIST_RANK);
        assertThat(testRanks.getBeenDrawnInPast()).isEqualTo(DEFAULT_BEEN_DRAWN_IN_PAST);
        assertThat(testRanks.getSummInRecentHistRank()).isEqualTo(DEFAULT_SUMM_IN_RECENT_HIST_RANK);
        assertThat(testRanks.getTotalRank()).isEqualTo(DEFAULT_TOTAL_RANK);

        // Validate the Ranks in Elasticsearch
        Ranks ranksEs = ranksSearchRepository.findOne(testRanks.getId());
        assertThat(ranksEs).isEqualToComparingFieldByField(testRanks);
    }

    @Test
    @Transactional
    public void createRanksWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ranksRepository.findAll().size();

        // Create the Ranks with an existing ID
        Ranks existingRanks = new Ranks();
        existingRanks.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRanksMockMvc.perform(post("/api/ranks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingRanks)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Ranks> ranksList = ranksRepository.findAll();
        assertThat(ranksList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkGameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ranksRepository.findAll().size();
        // set the field null
        ranks.setGame(null);

        // Create the Ranks, which fails.

        restRanksMockMvc.perform(post("/api/ranks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ranks)))
            .andExpect(status().isBadRequest());

        List<Ranks> ranksList = ranksRepository.findAll();
        assertThat(ranksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRanks() throws Exception {
        // Initialize the database
        ranksRepository.saveAndFlush(ranks);

        // Get all the ranksList
        restRanksMockMvc.perform(get("/api/ranks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ranks.getId().intValue())))
            .andExpect(jsonPath("$.[*].game").value(hasItem(DEFAULT_GAME.toString())))
            .andExpect(jsonPath("$.[*].allNumbersRank").value(hasItem(DEFAULT_ALL_NUMBERS_RANK)))
            .andExpect(jsonPath("$.[*].longOddEvenRank").value(hasItem(DEFAULT_LONG_ODD_EVEN_RANK)))
            .andExpect(jsonPath("$.[*].shortOddEvenRank").value(hasItem(DEFAULT_SHORT_ODD_EVEN_RANK)))
            .andExpect(jsonPath("$.[*].shortHistorySumRank").value(hasItem(DEFAULT_SHORT_HISTORY_SUM_RANK)))
            .andExpect(jsonPath("$.[*].longHistSumRank").value(hasItem(DEFAULT_LONG_HIST_SUM_RANK)))
            .andExpect(jsonPath("$.[*].summInHistRank").value(hasItem(DEFAULT_SUMM_IN_HIST_RANK)))
            .andExpect(jsonPath("$.[*].beenDrawnInPast").value(hasItem(DEFAULT_BEEN_DRAWN_IN_PAST)))
            .andExpect(jsonPath("$.[*].summInRecentHistRank").value(hasItem(DEFAULT_SUMM_IN_RECENT_HIST_RANK)))
            .andExpect(jsonPath("$.[*].totalRank").value(hasItem(DEFAULT_TOTAL_RANK)));
    }

    @Test
    @Transactional
    public void getRanks() throws Exception {
        // Initialize the database
        ranksRepository.saveAndFlush(ranks);

        // Get the ranks
        restRanksMockMvc.perform(get("/api/ranks/{id}", ranks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ranks.getId().intValue()))
            .andExpect(jsonPath("$.game").value(DEFAULT_GAME.toString()))
            .andExpect(jsonPath("$.allNumbersRank").value(DEFAULT_ALL_NUMBERS_RANK))
            .andExpect(jsonPath("$.longOddEvenRank").value(DEFAULT_LONG_ODD_EVEN_RANK))
            .andExpect(jsonPath("$.shortOddEvenRank").value(DEFAULT_SHORT_ODD_EVEN_RANK))
            .andExpect(jsonPath("$.shortHistorySumRank").value(DEFAULT_SHORT_HISTORY_SUM_RANK))
            .andExpect(jsonPath("$.longHistSumRank").value(DEFAULT_LONG_HIST_SUM_RANK))
            .andExpect(jsonPath("$.summInHistRank").value(DEFAULT_SUMM_IN_HIST_RANK))
            .andExpect(jsonPath("$.beenDrawnInPast").value(DEFAULT_BEEN_DRAWN_IN_PAST))
            .andExpect(jsonPath("$.summInRecentHistRank").value(DEFAULT_SUMM_IN_RECENT_HIST_RANK))
            .andExpect(jsonPath("$.totalRank").value(DEFAULT_TOTAL_RANK));
    }

    @Test
    @Transactional
    public void getNonExistingRanks() throws Exception {
        // Get the ranks
        restRanksMockMvc.perform(get("/api/ranks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRanks() throws Exception {
        // Initialize the database
        ranksService.save(ranks);

        int databaseSizeBeforeUpdate = ranksRepository.findAll().size();

        // Update the ranks
        Ranks updatedRanks = ranksRepository.findOne(ranks.getId());
        updatedRanks
                .game(UPDATED_GAME)
                .allNumbersRank(UPDATED_ALL_NUMBERS_RANK)
                .longOddEvenRank(UPDATED_LONG_ODD_EVEN_RANK)
                .shortOddEvenRank(UPDATED_SHORT_ODD_EVEN_RANK)
                .shortHistorySumRank(UPDATED_SHORT_HISTORY_SUM_RANK)
                .longHistSumRank(UPDATED_LONG_HIST_SUM_RANK)
                .summInHistRank(UPDATED_SUMM_IN_HIST_RANK)
                .beenDrawnInPast(UPDATED_BEEN_DRAWN_IN_PAST)
                .summInRecentHistRank(UPDATED_SUMM_IN_RECENT_HIST_RANK)
                .totalRank(UPDATED_TOTAL_RANK);

        restRanksMockMvc.perform(put("/api/ranks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRanks)))
            .andExpect(status().isOk());

        // Validate the Ranks in the database
        List<Ranks> ranksList = ranksRepository.findAll();
        assertThat(ranksList).hasSize(databaseSizeBeforeUpdate);
        Ranks testRanks = ranksList.get(ranksList.size() - 1);
        assertThat(testRanks.getGame()).isEqualTo(UPDATED_GAME);
        assertThat(testRanks.getAllNumbersRank()).isEqualTo(UPDATED_ALL_NUMBERS_RANK);
        assertThat(testRanks.getLongOddEvenRank()).isEqualTo(UPDATED_LONG_ODD_EVEN_RANK);
        assertThat(testRanks.getShortOddEvenRank()).isEqualTo(UPDATED_SHORT_ODD_EVEN_RANK);
        assertThat(testRanks.getShortHistorySumRank()).isEqualTo(UPDATED_SHORT_HISTORY_SUM_RANK);
        assertThat(testRanks.getLongHistSumRank()).isEqualTo(UPDATED_LONG_HIST_SUM_RANK);
        assertThat(testRanks.getSummInHistRank()).isEqualTo(UPDATED_SUMM_IN_HIST_RANK);
        assertThat(testRanks.getBeenDrawnInPast()).isEqualTo(UPDATED_BEEN_DRAWN_IN_PAST);
        assertThat(testRanks.getSummInRecentHistRank()).isEqualTo(UPDATED_SUMM_IN_RECENT_HIST_RANK);
        assertThat(testRanks.getTotalRank()).isEqualTo(UPDATED_TOTAL_RANK);

        // Validate the Ranks in Elasticsearch
        Ranks ranksEs = ranksSearchRepository.findOne(testRanks.getId());
        assertThat(ranksEs).isEqualToComparingFieldByField(testRanks);
    }

    @Test
    @Transactional
    public void updateNonExistingRanks() throws Exception {
        int databaseSizeBeforeUpdate = ranksRepository.findAll().size();

        // Create the Ranks

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRanksMockMvc.perform(put("/api/ranks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ranks)))
            .andExpect(status().isCreated());

        // Validate the Ranks in the database
        List<Ranks> ranksList = ranksRepository.findAll();
        assertThat(ranksList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRanks() throws Exception {
        // Initialize the database
        ranksService.save(ranks);

        int databaseSizeBeforeDelete = ranksRepository.findAll().size();

        // Get the ranks
        restRanksMockMvc.perform(delete("/api/ranks/{id}", ranks.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean ranksExistsInEs = ranksSearchRepository.exists(ranks.getId());
        assertThat(ranksExistsInEs).isFalse();

        // Validate the database is empty
        List<Ranks> ranksList = ranksRepository.findAll();
        assertThat(ranksList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRanks() throws Exception {
        // Initialize the database
        ranksService.save(ranks);

        // Search the ranks
        restRanksMockMvc.perform(get("/api/_search/ranks?query=id:" + ranks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ranks.getId().intValue())))
            .andExpect(jsonPath("$.[*].game").value(hasItem(DEFAULT_GAME.toString())))
            .andExpect(jsonPath("$.[*].allNumbersRank").value(hasItem(DEFAULT_ALL_NUMBERS_RANK)))
            .andExpect(jsonPath("$.[*].longOddEvenRank").value(hasItem(DEFAULT_LONG_ODD_EVEN_RANK)))
            .andExpect(jsonPath("$.[*].shortOddEvenRank").value(hasItem(DEFAULT_SHORT_ODD_EVEN_RANK)))
            .andExpect(jsonPath("$.[*].shortHistorySumRank").value(hasItem(DEFAULT_SHORT_HISTORY_SUM_RANK)))
            .andExpect(jsonPath("$.[*].longHistSumRank").value(hasItem(DEFAULT_LONG_HIST_SUM_RANK)))
            .andExpect(jsonPath("$.[*].summInHistRank").value(hasItem(DEFAULT_SUMM_IN_HIST_RANK)))
            .andExpect(jsonPath("$.[*].beenDrawnInPast").value(hasItem(DEFAULT_BEEN_DRAWN_IN_PAST)))
            .andExpect(jsonPath("$.[*].summInRecentHistRank").value(hasItem(DEFAULT_SUMM_IN_RECENT_HIST_RANK)))
            .andExpect(jsonPath("$.[*].totalRank").value(hasItem(DEFAULT_TOTAL_RANK)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ranks.class);
    }
}
