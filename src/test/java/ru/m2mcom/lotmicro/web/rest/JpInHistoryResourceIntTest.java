package ru.m2mcom.lotmicro.web.rest;

import ru.m2mcom.lotmicro.LotmicroApp;

import ru.m2mcom.lotmicro.domain.JpInHistory;
import ru.m2mcom.lotmicro.repository.JpInHistoryRepository;
import ru.m2mcom.lotmicro.service.JpInHistoryService;
import ru.m2mcom.lotmicro.repository.search.JpInHistorySearchRepository;
import ru.m2mcom.lotmicro.service.dto.JpInHistoryDTO;
import ru.m2mcom.lotmicro.service.mapper.JpInHistoryMapper;
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

import ru.m2mcom.lotmicro.domain.enumeration.DrawsTable;
import ru.m2mcom.lotmicro.domain.enumeration.GamesPlay;
/**
 * Test class for the JpInHistoryResource REST controller.
 *
 * @see JpInHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LotmicroApp.class)
public class JpInHistoryResourceIntTest {

    private static final LocalDate DEFAULT_DRAW_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DRAW_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final DrawsTable DEFAULT_TABLE_NAME = DrawsTable.LOCAL;
    private static final DrawsTable UPDATED_TABLE_NAME = DrawsTable.WEB;

    private static final Integer DEFAULT_TABLEID = 1;
    private static final Integer UPDATED_TABLEID = 2;

    private static final GamesPlay DEFAULT_GAME = GamesPlay.EML;
    private static final GamesPlay UPDATED_GAME = GamesPlay.NAT;

    @Autowired
    private JpInHistoryRepository jpInHistoryRepository;

    @Autowired
    private JpInHistoryMapper jpInHistoryMapper;

    @Autowired
    private JpInHistoryService jpInHistoryService;

    @Autowired
    private JpInHistorySearchRepository jpInHistorySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restJpInHistoryMockMvc;

    private JpInHistory jpInHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JpInHistoryResource jpInHistoryResource = new JpInHistoryResource(jpInHistoryService);
        this.restJpInHistoryMockMvc = MockMvcBuilders.standaloneSetup(jpInHistoryResource)
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
    public static JpInHistory createEntity(EntityManager em) {
        JpInHistory jpInHistory = new JpInHistory()
            .drawDate(DEFAULT_DRAW_DATE)
            .tableName(DEFAULT_TABLE_NAME)
            .tableid(DEFAULT_TABLEID)
            .game(DEFAULT_GAME);
        return jpInHistory;
    }

    @Before
    public void initTest() {
        jpInHistorySearchRepository.deleteAll();
        jpInHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createJpInHistory() throws Exception {
        int databaseSizeBeforeCreate = jpInHistoryRepository.findAll().size();

        // Create the JpInHistory
        JpInHistoryDTO jpInHistoryDTO = jpInHistoryMapper.jpInHistoryToJpInHistoryDTO(jpInHistory);
        restJpInHistoryMockMvc.perform(post("/api/jp-in-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jpInHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the JpInHistory in the database
        List<JpInHistory> jpInHistoryList = jpInHistoryRepository.findAll();
        assertThat(jpInHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        JpInHistory testJpInHistory = jpInHistoryList.get(jpInHistoryList.size() - 1);
        assertThat(testJpInHistory.getDrawDate()).isEqualTo(DEFAULT_DRAW_DATE);
        assertThat(testJpInHistory.getTableName()).isEqualTo(DEFAULT_TABLE_NAME);
        assertThat(testJpInHistory.getTableid()).isEqualTo(DEFAULT_TABLEID);
        assertThat(testJpInHistory.getGame()).isEqualTo(DEFAULT_GAME);

        // Validate the JpInHistory in Elasticsearch
        JpInHistory jpInHistoryEs = jpInHistorySearchRepository.findOne(testJpInHistory.getId());
        assertThat(jpInHistoryEs).isEqualToComparingFieldByField(testJpInHistory);
    }

    @Test
    @Transactional
    public void createJpInHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jpInHistoryRepository.findAll().size();

        // Create the JpInHistory with an existing ID
        jpInHistory.setId(1L);
        JpInHistoryDTO jpInHistoryDTO = jpInHistoryMapper.jpInHistoryToJpInHistoryDTO(jpInHistory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJpInHistoryMockMvc.perform(post("/api/jp-in-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jpInHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<JpInHistory> jpInHistoryList = jpInHistoryRepository.findAll();
        assertThat(jpInHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDrawDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = jpInHistoryRepository.findAll().size();
        // set the field null
        jpInHistory.setDrawDate(null);

        // Create the JpInHistory, which fails.
        JpInHistoryDTO jpInHistoryDTO = jpInHistoryMapper.jpInHistoryToJpInHistoryDTO(jpInHistory);

        restJpInHistoryMockMvc.perform(post("/api/jp-in-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jpInHistoryDTO)))
            .andExpect(status().isBadRequest());

        List<JpInHistory> jpInHistoryList = jpInHistoryRepository.findAll();
        assertThat(jpInHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJpInHistories() throws Exception {
        // Initialize the database
        jpInHistoryRepository.saveAndFlush(jpInHistory);

        // Get all the jpInHistoryList
        restJpInHistoryMockMvc.perform(get("/api/jp-in-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jpInHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].drawDate").value(hasItem(DEFAULT_DRAW_DATE.toString())))
            .andExpect(jsonPath("$.[*].tableName").value(hasItem(DEFAULT_TABLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].tableid").value(hasItem(DEFAULT_TABLEID)))
            .andExpect(jsonPath("$.[*].game").value(hasItem(DEFAULT_GAME.toString())));
    }

    @Test
    @Transactional
    public void getJpInHistory() throws Exception {
        // Initialize the database
        jpInHistoryRepository.saveAndFlush(jpInHistory);

        // Get the jpInHistory
        restJpInHistoryMockMvc.perform(get("/api/jp-in-histories/{id}", jpInHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jpInHistory.getId().intValue()))
            .andExpect(jsonPath("$.drawDate").value(DEFAULT_DRAW_DATE.toString()))
            .andExpect(jsonPath("$.tableName").value(DEFAULT_TABLE_NAME.toString()))
            .andExpect(jsonPath("$.tableid").value(DEFAULT_TABLEID))
            .andExpect(jsonPath("$.game").value(DEFAULT_GAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJpInHistory() throws Exception {
        // Get the jpInHistory
        restJpInHistoryMockMvc.perform(get("/api/jp-in-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJpInHistory() throws Exception {
        // Initialize the database
        jpInHistoryRepository.saveAndFlush(jpInHistory);
        jpInHistorySearchRepository.save(jpInHistory);
        int databaseSizeBeforeUpdate = jpInHistoryRepository.findAll().size();

        // Update the jpInHistory
        JpInHistory updatedJpInHistory = jpInHistoryRepository.findOne(jpInHistory.getId());
        updatedJpInHistory
            .drawDate(UPDATED_DRAW_DATE)
            .tableName(UPDATED_TABLE_NAME)
            .tableid(UPDATED_TABLEID)
            .game(UPDATED_GAME);
        JpInHistoryDTO jpInHistoryDTO = jpInHistoryMapper.jpInHistoryToJpInHistoryDTO(updatedJpInHistory);

        restJpInHistoryMockMvc.perform(put("/api/jp-in-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jpInHistoryDTO)))
            .andExpect(status().isOk());

        // Validate the JpInHistory in the database
        List<JpInHistory> jpInHistoryList = jpInHistoryRepository.findAll();
        assertThat(jpInHistoryList).hasSize(databaseSizeBeforeUpdate);
        JpInHistory testJpInHistory = jpInHistoryList.get(jpInHistoryList.size() - 1);
        assertThat(testJpInHistory.getDrawDate()).isEqualTo(UPDATED_DRAW_DATE);
        assertThat(testJpInHistory.getTableName()).isEqualTo(UPDATED_TABLE_NAME);
        assertThat(testJpInHistory.getTableid()).isEqualTo(UPDATED_TABLEID);
        assertThat(testJpInHistory.getGame()).isEqualTo(UPDATED_GAME);

        // Validate the JpInHistory in Elasticsearch
        JpInHistory jpInHistoryEs = jpInHistorySearchRepository.findOne(testJpInHistory.getId());
        assertThat(jpInHistoryEs).isEqualToComparingFieldByField(testJpInHistory);
    }

    @Test
    @Transactional
    public void updateNonExistingJpInHistory() throws Exception {
        int databaseSizeBeforeUpdate = jpInHistoryRepository.findAll().size();

        // Create the JpInHistory
        JpInHistoryDTO jpInHistoryDTO = jpInHistoryMapper.jpInHistoryToJpInHistoryDTO(jpInHistory);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJpInHistoryMockMvc.perform(put("/api/jp-in-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jpInHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the JpInHistory in the database
        List<JpInHistory> jpInHistoryList = jpInHistoryRepository.findAll();
        assertThat(jpInHistoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteJpInHistory() throws Exception {
        // Initialize the database
        jpInHistoryRepository.saveAndFlush(jpInHistory);
        jpInHistorySearchRepository.save(jpInHistory);
        int databaseSizeBeforeDelete = jpInHistoryRepository.findAll().size();

        // Get the jpInHistory
        restJpInHistoryMockMvc.perform(delete("/api/jp-in-histories/{id}", jpInHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean jpInHistoryExistsInEs = jpInHistorySearchRepository.exists(jpInHistory.getId());
        assertThat(jpInHistoryExistsInEs).isFalse();

        // Validate the database is empty
        List<JpInHistory> jpInHistoryList = jpInHistoryRepository.findAll();
        assertThat(jpInHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchJpInHistory() throws Exception {
        // Initialize the database
        jpInHistoryRepository.saveAndFlush(jpInHistory);
        jpInHistorySearchRepository.save(jpInHistory);

        // Search the jpInHistory
        restJpInHistoryMockMvc.perform(get("/api/_search/jp-in-histories?query=id:" + jpInHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jpInHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].drawDate").value(hasItem(DEFAULT_DRAW_DATE.toString())))
            .andExpect(jsonPath("$.[*].tableName").value(hasItem(DEFAULT_TABLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].tableid").value(hasItem(DEFAULT_TABLEID)))
            .andExpect(jsonPath("$.[*].game").value(hasItem(DEFAULT_GAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JpInHistory.class);
    }
}
