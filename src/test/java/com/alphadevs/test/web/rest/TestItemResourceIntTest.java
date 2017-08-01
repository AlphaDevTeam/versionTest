package com.alphadevs.test.web.rest;

import com.alphadevs.test.VersionTestApp;

import com.alphadevs.test.domain.TestItem;
import com.alphadevs.test.repository.TestItemRepository;
import com.alphadevs.test.service.TestItemService;
import com.alphadevs.test.web.rest.errors.ExceptionTranslator;

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
 * Test class for the TestItemResource REST controller.
 *
 * @see TestItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VersionTestApp.class)
public class TestItemResourceIntTest {

    private static final String DEFAULT_ITEM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_ITEM_UNIT = 1D;
    private static final Double UPDATED_ITEM_UNIT = 2D;

    private static final Double DEFAULT_ITEM_COST = 1D;
    private static final Double UPDATED_ITEM_COST = 2D;

    @Autowired
    private TestItemRepository testItemRepository;

    @Autowired
    private TestItemService testItemService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTestItemMockMvc;

    private TestItem testItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TestItemResource testItemResource = new TestItemResource(testItemService);
        this.restTestItemMockMvc = MockMvcBuilders.standaloneSetup(testItemResource)
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
    public static TestItem createEntity(EntityManager em) {
        TestItem testItem = new TestItem()
            .itemName(DEFAULT_ITEM_NAME)
            .itemUnit(DEFAULT_ITEM_UNIT)
            .itemCost(DEFAULT_ITEM_COST);
        return testItem;
    }

    @Before
    public void initTest() {
        testItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createTestItem() throws Exception {
        int databaseSizeBeforeCreate = testItemRepository.findAll().size();

        // Create the TestItem
        restTestItemMockMvc.perform(post("/api/test-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testItem)))
            .andExpect(status().isCreated());

        // Validate the TestItem in the database
        List<TestItem> testItemList = testItemRepository.findAll();
        assertThat(testItemList).hasSize(databaseSizeBeforeCreate + 1);
        TestItem testTestItem = testItemList.get(testItemList.size() - 1);
        assertThat(testTestItem.getItemName()).isEqualTo(DEFAULT_ITEM_NAME);
        assertThat(testTestItem.getItemUnit()).isEqualTo(DEFAULT_ITEM_UNIT);
        assertThat(testTestItem.getItemCost()).isEqualTo(DEFAULT_ITEM_COST);
    }

    @Test
    @Transactional
    public void createTestItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = testItemRepository.findAll().size();

        // Create the TestItem with an existing ID
        testItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTestItemMockMvc.perform(post("/api/test-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testItem)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TestItem> testItemList = testItemRepository.findAll();
        assertThat(testItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTestItems() throws Exception {
        // Initialize the database
        testItemRepository.saveAndFlush(testItem);

        // Get all the testItemList
        restTestItemMockMvc.perform(get("/api/test-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(testItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].itemName").value(hasItem(DEFAULT_ITEM_NAME.toString())))
            .andExpect(jsonPath("$.[*].itemUnit").value(hasItem(DEFAULT_ITEM_UNIT.doubleValue())))
            .andExpect(jsonPath("$.[*].itemCost").value(hasItem(DEFAULT_ITEM_COST.doubleValue())));
    }

    @Test
    @Transactional
    public void getTestItem() throws Exception {
        // Initialize the database
        testItemRepository.saveAndFlush(testItem);

        // Get the testItem
        restTestItemMockMvc.perform(get("/api/test-items/{id}", testItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(testItem.getId().intValue()))
            .andExpect(jsonPath("$.itemName").value(DEFAULT_ITEM_NAME.toString()))
            .andExpect(jsonPath("$.itemUnit").value(DEFAULT_ITEM_UNIT.doubleValue()))
            .andExpect(jsonPath("$.itemCost").value(DEFAULT_ITEM_COST.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTestItem() throws Exception {
        // Get the testItem
        restTestItemMockMvc.perform(get("/api/test-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTestItem() throws Exception {
        // Initialize the database
        testItemService.save(testItem);

        int databaseSizeBeforeUpdate = testItemRepository.findAll().size();

        // Update the testItem
        TestItem updatedTestItem = testItemRepository.findOne(testItem.getId());
        updatedTestItem
            .itemName(UPDATED_ITEM_NAME)
            .itemUnit(UPDATED_ITEM_UNIT)
            .itemCost(UPDATED_ITEM_COST);

        restTestItemMockMvc.perform(put("/api/test-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTestItem)))
            .andExpect(status().isOk());

        // Validate the TestItem in the database
        List<TestItem> testItemList = testItemRepository.findAll();
        assertThat(testItemList).hasSize(databaseSizeBeforeUpdate);
        TestItem testTestItem = testItemList.get(testItemList.size() - 1);
        assertThat(testTestItem.getItemName()).isEqualTo(UPDATED_ITEM_NAME);
        assertThat(testTestItem.getItemUnit()).isEqualTo(UPDATED_ITEM_UNIT);
        assertThat(testTestItem.getItemCost()).isEqualTo(UPDATED_ITEM_COST);
    }

    @Test
    @Transactional
    public void updateNonExistingTestItem() throws Exception {
        int databaseSizeBeforeUpdate = testItemRepository.findAll().size();

        // Create the TestItem

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTestItemMockMvc.perform(put("/api/test-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testItem)))
            .andExpect(status().isCreated());

        // Validate the TestItem in the database
        List<TestItem> testItemList = testItemRepository.findAll();
        assertThat(testItemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTestItem() throws Exception {
        // Initialize the database
        testItemService.save(testItem);

        int databaseSizeBeforeDelete = testItemRepository.findAll().size();

        // Get the testItem
        restTestItemMockMvc.perform(delete("/api/test-items/{id}", testItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TestItem> testItemList = testItemRepository.findAll();
        assertThat(testItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TestItem.class);
        TestItem testItem1 = new TestItem();
        testItem1.setId(1L);
        TestItem testItem2 = new TestItem();
        testItem2.setId(testItem1.getId());
        assertThat(testItem1).isEqualTo(testItem2);
        testItem2.setId(2L);
        assertThat(testItem1).isNotEqualTo(testItem2);
        testItem1.setId(null);
        assertThat(testItem1).isNotEqualTo(testItem2);
    }
}
