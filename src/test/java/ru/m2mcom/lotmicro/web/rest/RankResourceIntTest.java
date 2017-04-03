package ru.m2mcom.lotmicro.web.rest;

import ru.m2mcom.lotmicro.LotmicroApp;

import ru.m2mcom.lotmicro.domain.Rank;
import ru.m2mcom.lotmicro.repository.RankRepository;
import ru.m2mcom.lotmicro.service.RankService;
import ru.m2mcom.lotmicro.repository.search.RankSearchRepository;
import ru.m2mcom.lotmicro.service.dto.RankDTO;
import ru.m2mcom.lotmicro.service.mapper.RankMapper;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RankResource REST controller.
 *
 * @see RankResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LotmicroApp.class)
public class RankResourceIntTest {

    private static final Double DEFAULT_ALL_NUMBERS_RANK = 1D;
    private static final Double UPDATED_ALL_NUMBERS_RANK = 2D;

    private static final Double DEFAULT_LONG_ODD_EVEN_RANK = 1D;
    private static final Double UPDATED_LONG_ODD_EVEN_RANK = 2D;

    private static final Double DEFAULT_SHORT_ODD_EVEN_RANK = 1D;
    private static final Double UPDATED_SHORT_ODD_EVEN_RANK = 2D;

    private static final Double DEFAULT_SHORT_HISTORY_SUM_RANK = 1D;
    private static final Double UPDATED_SHORT_HISTORY_SUM_RANK = 2D;

    private static final Double DEFAULT_LONG_HIST_SUM_RANK = 1D;
    private static final Double UPDATED_LONG_HIST_SUM_RANK = 2D;

    private static final Double DEFAULT_SUMM_IN_HIST_RANK = 1D;
    private static final Double UPDATED_SUMM_IN_HIST_RANK = 2D;

    private static final Double DEFAULT_BEEN_DRAWN_IN_PAST = 1D;
    private static final Double UPDATED_BEEN_DRAWN_IN_PAST = 2D;

    private static final Double DEFAULT_SUMM_IN_RECENT_HIST_RANK = 1D;
    private static final Double UPDATED_SUMM_IN_RECENT_HIST_RANK = 2D;

    private static final Double DEFAULT_TOTAL_RANK = 1D;
    private static final Double UPDATED_TOTAL_RANK = 2D;

    @Autowired
    private RankRepository rankRepository;

    @Autowired
    private RankMapper rankMapper;

    @Autowired
    private RankService rankService;

    @Autowired
    private RankSearchRepository rankSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRankMockMvc;

    private Rank rank;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RankResource rankResource = new RankResource(rankService);
        this.restRankMockMvc = MockMvcBuilders.standaloneSetup(rankResource)
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
    public static Rank createEntity(EntityManager em) {
        Rank rank = new Rank()
            .allNumbersRank(DEFAULT_ALL_NUMBERS_RANK)
            .longOddEvenRank(DEFAULT_LONG_ODD_EVEN_RANK)
            .shortOddEvenRank(DEFAULT_SHORT_ODD_EVEN_RANK)
            .shortHistorySumRank(DEFAULT_SHORT_HISTORY_SUM_RANK)
            .longHistSumRank(DEFAULT_LONG_HIST_SUM_RANK)
            .summInHistRank(DEFAULT_SUMM_IN_HIST_RANK)
            .beenDrawnInPast(DEFAULT_BEEN_DRAWN_IN_PAST)
            .summInRecentHistRank(DEFAULT_SUMM_IN_RECENT_HIST_RANK)
            .totalRank(DEFAULT_TOTAL_RANK);
        return rank;
    }

    @Before
    public void initTest() {
        rankSearchRepository.deleteAll();
        rank = createEntity(em);
    }

    @Test
    @Transactional
    public void createRank() throws Exception {
        int databaseSizeBeforeCreate = rankRepository.findAll().size();

        // Create the Rank
        RankDTO rankDTO = rankMapper.rankToRankDTO(rank);
        restRankMockMvc.perform(post("/api/ranks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rankDTO)))
            .andExpect(status().isCreated());

        // Validate the Rank in the database
        List<Rank> rankList = rankRepository.findAll();
        assertThat(rankList).hasSize(databaseSizeBeforeCreate + 1);
        Rank testRank = rankList.get(rankList.size() - 1);
        assertThat(testRank.getAllNumbersRank()).isEqualTo(DEFAULT_ALL_NUMBERS_RANK);
        assertThat(testRank.getLongOddEvenRank()).isEqualTo(DEFAULT_LONG_ODD_EVEN_RANK);
        assertThat(testRank.getShortOddEvenRank()).isEqualTo(DEFAULT_SHORT_ODD_EVEN_RANK);
        assertThat(testRank.getShortHistorySumRank()).isEqualTo(DEFAULT_SHORT_HISTORY_SUM_RANK);
        assertThat(testRank.getLongHistSumRank()).isEqualTo(DEFAULT_LONG_HIST_SUM_RANK);
        assertThat(testRank.getSummInHistRank()).isEqualTo(DEFAULT_SUMM_IN_HIST_RANK);
        assertThat(testRank.getBeenDrawnInPast()).isEqualTo(DEFAULT_BEEN_DRAWN_IN_PAST);
        assertThat(testRank.getSummInRecentHistRank()).isEqualTo(DEFAULT_SUMM_IN_RECENT_HIST_RANK);
        assertThat(testRank.getTotalRank()).isEqualTo(DEFAULT_TOTAL_RANK);

        // Validate the Rank in Elasticsearch
        Rank rankEs = rankSearchRepository.findOne(testRank.getId());
        assertThat(rankEs).isEqualToComparingFieldByField(testRank);
    }

    @Test
    @Transactional
    public void createRankWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rankRepository.findAll().size();

        // Create the Rank with an existing ID
        rank.setId(1L);
        RankDTO rankDTO = rankMapper.rankToRankDTO(rank);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRankMockMvc.perform(post("/api/ranks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rankDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Rank> rankList = rankRepository.findAll();
        assertThat(rankList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRanks() throws Exception {
        // Initialize the database
        rankRepository.saveAndFlush(rank);

        // Get all the rankList
        restRankMockMvc.perform(get("/api/ranks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rank.getId().intValue())))
            .andExpect(jsonPath("$.[*].allNumbersRank").value(hasItem(DEFAULT_ALL_NUMBERS_RANK.doubleValue())))
            .andExpect(jsonPath("$.[*].longOddEvenRank").value(hasItem(DEFAULT_LONG_ODD_EVEN_RANK.doubleValue())))
            .andExpect(jsonPath("$.[*].shortOddEvenRank").value(hasItem(DEFAULT_SHORT_ODD_EVEN_RANK.doubleValue())))
            .andExpect(jsonPath("$.[*].shortHistorySumRank").value(hasItem(DEFAULT_SHORT_HISTORY_SUM_RANK.doubleValue())))
            .andExpect(jsonPath("$.[*].longHistSumRank").value(hasItem(DEFAULT_LONG_HIST_SUM_RANK.doubleValue())))
            .andExpect(jsonPath("$.[*].summInHistRank").value(hasItem(DEFAULT_SUMM_IN_HIST_RANK.doubleValue())))
            .andExpect(jsonPath("$.[*].beenDrawnInPast").value(hasItem(DEFAULT_BEEN_DRAWN_IN_PAST.doubleValue())))
            .andExpect(jsonPath("$.[*].summInRecentHistRank").value(hasItem(DEFAULT_SUMM_IN_RECENT_HIST_RANK.doubleValue())))
            .andExpect(jsonPath("$.[*].totalRank").value(hasItem(DEFAULT_TOTAL_RANK.doubleValue())));
    }

    @Test
    @Transactional
    public void getRank() throws Exception {
        // Initialize the database
        rankRepository.saveAndFlush(rank);

        // Get the rank
        restRankMockMvc.perform(get("/api/ranks/{id}", rank.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rank.getId().intValue()))
            .andExpect(jsonPath("$.allNumbersRank").value(DEFAULT_ALL_NUMBERS_RANK.doubleValue()))
            .andExpect(jsonPath("$.longOddEvenRank").value(DEFAULT_LONG_ODD_EVEN_RANK.doubleValue()))
            .andExpect(jsonPath("$.shortOddEvenRank").value(DEFAULT_SHORT_ODD_EVEN_RANK.doubleValue()))
            .andExpect(jsonPath("$.shortHistorySumRank").value(DEFAULT_SHORT_HISTORY_SUM_RANK.doubleValue()))
            .andExpect(jsonPath("$.longHistSumRank").value(DEFAULT_LONG_HIST_SUM_RANK.doubleValue()))
            .andExpect(jsonPath("$.summInHistRank").value(DEFAULT_SUMM_IN_HIST_RANK.doubleValue()))
            .andExpect(jsonPath("$.beenDrawnInPast").value(DEFAULT_BEEN_DRAWN_IN_PAST.doubleValue()))
            .andExpect(jsonPath("$.summInRecentHistRank").value(DEFAULT_SUMM_IN_RECENT_HIST_RANK.doubleValue()))
            .andExpect(jsonPath("$.totalRank").value(DEFAULT_TOTAL_RANK.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRank() throws Exception {
        // Get the rank
        restRankMockMvc.perform(get("/api/ranks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRank() throws Exception {
        // Initialize the database
        rankRepository.saveAndFlush(rank);
        rankSearchRepository.save(rank);
        int databaseSizeBeforeUpdate = rankRepository.findAll().size();

        // Update the rank
        Rank updatedRank = rankRepository.findOne(rank.getId());
        updatedRank
            .allNumbersRank(UPDATED_ALL_NUMBERS_RANK)
            .longOddEvenRank(UPDATED_LONG_ODD_EVEN_RANK)
            .shortOddEvenRank(UPDATED_SHORT_ODD_EVEN_RANK)
            .shortHistorySumRank(UPDATED_SHORT_HISTORY_SUM_RANK)
            .longHistSumRank(UPDATED_LONG_HIST_SUM_RANK)
            .summInHistRank(UPDATED_SUMM_IN_HIST_RANK)
            .beenDrawnInPast(UPDATED_BEEN_DRAWN_IN_PAST)
            .summInRecentHistRank(UPDATED_SUMM_IN_RECENT_HIST_RANK)
            .totalRank(UPDATED_TOTAL_RANK);
        RankDTO rankDTO = rankMapper.rankToRankDTO(updatedRank);

        restRankMockMvc.perform(put("/api/ranks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rankDTO)))
            .andExpect(status().isOk());

        // Validate the Rank in the database
        List<Rank> rankList = rankRepository.findAll();
        assertThat(rankList).hasSize(databaseSizeBeforeUpdate);
        Rank testRank = rankList.get(rankList.size() - 1);
        assertThat(testRank.getAllNumbersRank()).isEqualTo(UPDATED_ALL_NUMBERS_RANK);
        assertThat(testRank.getLongOddEvenRank()).isEqualTo(UPDATED_LONG_ODD_EVEN_RANK);
        assertThat(testRank.getShortOddEvenRank()).isEqualTo(UPDATED_SHORT_ODD_EVEN_RANK);
        assertThat(testRank.getShortHistorySumRank()).isEqualTo(UPDATED_SHORT_HISTORY_SUM_RANK);
        assertThat(testRank.getLongHistSumRank()).isEqualTo(UPDATED_LONG_HIST_SUM_RANK);
        assertThat(testRank.getSummInHistRank()).isEqualTo(UPDATED_SUMM_IN_HIST_RANK);
        assertThat(testRank.getBeenDrawnInPast()).isEqualTo(UPDATED_BEEN_DRAWN_IN_PAST);
        assertThat(testRank.getSummInRecentHistRank()).isEqualTo(UPDATED_SUMM_IN_RECENT_HIST_RANK);
        assertThat(testRank.getTotalRank()).isEqualTo(UPDATED_TOTAL_RANK);

        // Validate the Rank in Elasticsearch
        Rank rankEs = rankSearchRepository.findOne(testRank.getId());
        assertThat(rankEs).isEqualToComparingFieldByField(testRank);
    }

    @Test
    @Transactional
    public void updateNonExistingRank() throws Exception {
        int databaseSizeBeforeUpdate = rankRepository.findAll().size();

        // Create the Rank
        RankDTO rankDTO = rankMapper.rankToRankDTO(rank);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRankMockMvc.perform(put("/api/ranks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rankDTO)))
            .andExpect(status().isCreated());

        // Validate the Rank in the database
        List<Rank> rankList = rankRepository.findAll();
        assertThat(rankList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRank() throws Exception {
        // Initialize the database
        rankRepository.saveAndFlush(rank);
        rankSearchRepository.save(rank);
        int databaseSizeBeforeDelete = rankRepository.findAll().size();

        // Get the rank
        restRankMockMvc.perform(delete("/api/ranks/{id}", rank.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean rankExistsInEs = rankSearchRepository.exists(rank.getId());
        assertThat(rankExistsInEs).isFalse();

        // Validate the database is empty
        List<Rank> rankList = rankRepository.findAll();
        assertThat(rankList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRank() throws Exception {
        // Initialize the database
        rankRepository.saveAndFlush(rank);
        rankSearchRepository.save(rank);

        // Search the rank
        restRankMockMvc.perform(get("/api/_search/ranks?query=id:" + rank.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rank.getId().intValue())))
            .andExpect(jsonPath("$.[*].allNumbersRank").value(hasItem(DEFAULT_ALL_NUMBERS_RANK.doubleValue())))
            .andExpect(jsonPath("$.[*].longOddEvenRank").value(hasItem(DEFAULT_LONG_ODD_EVEN_RANK.doubleValue())))
            .andExpect(jsonPath("$.[*].shortOddEvenRank").value(hasItem(DEFAULT_SHORT_ODD_EVEN_RANK.doubleValue())))
            .andExpect(jsonPath("$.[*].shortHistorySumRank").value(hasItem(DEFAULT_SHORT_HISTORY_SUM_RANK.doubleValue())))
            .andExpect(jsonPath("$.[*].longHistSumRank").value(hasItem(DEFAULT_LONG_HIST_SUM_RANK.doubleValue())))
            .andExpect(jsonPath("$.[*].summInHistRank").value(hasItem(DEFAULT_SUMM_IN_HIST_RANK.doubleValue())))
            .andExpect(jsonPath("$.[*].beenDrawnInPast").value(hasItem(DEFAULT_BEEN_DRAWN_IN_PAST.doubleValue())))
            .andExpect(jsonPath("$.[*].summInRecentHistRank").value(hasItem(DEFAULT_SUMM_IN_RECENT_HIST_RANK.doubleValue())))
            .andExpect(jsonPath("$.[*].totalRank").value(hasItem(DEFAULT_TOTAL_RANK.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rank.class);
    }
}
