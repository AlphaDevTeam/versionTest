package com.alphadevs.test.web.rest;

import com.alphadevs.test.VersionTestApp;

import com.alphadevs.test.domain.PurchaseOrderDetails;
import com.alphadevs.test.repository.PurchaseOrderDetailsRepository;
import com.alphadevs.test.service.PurchaseOrderDetailsService;
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
 * Test class for the PurchaseOrderDetailsResource REST controller.
 *
 * @see PurchaseOrderDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VersionTestApp.class)
public class PurchaseOrderDetailsResourceIntTest {

    private static final Double DEFAULT_QTY = 1D;
    private static final Double UPDATED_QTY = 2D;

    private static final String DEFAULT_REF_1 = "AAAAAAAAAA";
    private static final String UPDATED_REF_1 = "BBBBBBBBBB";

    @Autowired
    private PurchaseOrderDetailsRepository purchaseOrderDetailsRepository;

    @Autowired
    private PurchaseOrderDetailsService purchaseOrderDetailsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPurchaseOrderDetailsMockMvc;

    private PurchaseOrderDetails purchaseOrderDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PurchaseOrderDetailsResource purchaseOrderDetailsResource = new PurchaseOrderDetailsResource(purchaseOrderDetailsService);
        this.restPurchaseOrderDetailsMockMvc = MockMvcBuilders.standaloneSetup(purchaseOrderDetailsResource)
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
    public static PurchaseOrderDetails createEntity(EntityManager em) {
        PurchaseOrderDetails purchaseOrderDetails = new PurchaseOrderDetails()
            .qty(DEFAULT_QTY)
            .ref1(DEFAULT_REF_1);
        return purchaseOrderDetails;
    }

    @Before
    public void initTest() {
        purchaseOrderDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createPurchaseOrderDetails() throws Exception {
        int databaseSizeBeforeCreate = purchaseOrderDetailsRepository.findAll().size();

        // Create the PurchaseOrderDetails
        restPurchaseOrderDetailsMockMvc.perform(post("/api/purchase-order-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrderDetails)))
            .andExpect(status().isCreated());

        // Validate the PurchaseOrderDetails in the database
        List<PurchaseOrderDetails> purchaseOrderDetailsList = purchaseOrderDetailsRepository.findAll();
        assertThat(purchaseOrderDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        PurchaseOrderDetails testPurchaseOrderDetails = purchaseOrderDetailsList.get(purchaseOrderDetailsList.size() - 1);
        assertThat(testPurchaseOrderDetails.getQty()).isEqualTo(DEFAULT_QTY);
        assertThat(testPurchaseOrderDetails.getRef1()).isEqualTo(DEFAULT_REF_1);
    }

    @Test
    @Transactional
    public void createPurchaseOrderDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = purchaseOrderDetailsRepository.findAll().size();

        // Create the PurchaseOrderDetails with an existing ID
        purchaseOrderDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchaseOrderDetailsMockMvc.perform(post("/api/purchase-order-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrderDetails)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PurchaseOrderDetails> purchaseOrderDetailsList = purchaseOrderDetailsRepository.findAll();
        assertThat(purchaseOrderDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderDetails() throws Exception {
        // Initialize the database
        purchaseOrderDetailsRepository.saveAndFlush(purchaseOrderDetails);

        // Get all the purchaseOrderDetailsList
        restPurchaseOrderDetailsMockMvc.perform(get("/api/purchase-order-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseOrderDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY.doubleValue())))
            .andExpect(jsonPath("$.[*].ref1").value(hasItem(DEFAULT_REF_1.toString())));
    }

    @Test
    @Transactional
    public void getPurchaseOrderDetails() throws Exception {
        // Initialize the database
        purchaseOrderDetailsRepository.saveAndFlush(purchaseOrderDetails);

        // Get the purchaseOrderDetails
        restPurchaseOrderDetailsMockMvc.perform(get("/api/purchase-order-details/{id}", purchaseOrderDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(purchaseOrderDetails.getId().intValue()))
            .andExpect(jsonPath("$.qty").value(DEFAULT_QTY.doubleValue()))
            .andExpect(jsonPath("$.ref1").value(DEFAULT_REF_1.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPurchaseOrderDetails() throws Exception {
        // Get the purchaseOrderDetails
        restPurchaseOrderDetailsMockMvc.perform(get("/api/purchase-order-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurchaseOrderDetails() throws Exception {
        // Initialize the database
        purchaseOrderDetailsService.save(purchaseOrderDetails);

        int databaseSizeBeforeUpdate = purchaseOrderDetailsRepository.findAll().size();

        // Update the purchaseOrderDetails
        PurchaseOrderDetails updatedPurchaseOrderDetails = purchaseOrderDetailsRepository.findOne(purchaseOrderDetails.getId());
        updatedPurchaseOrderDetails
            .qty(UPDATED_QTY)
            .ref1(UPDATED_REF_1);

        restPurchaseOrderDetailsMockMvc.perform(put("/api/purchase-order-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPurchaseOrderDetails)))
            .andExpect(status().isOk());

        // Validate the PurchaseOrderDetails in the database
        List<PurchaseOrderDetails> purchaseOrderDetailsList = purchaseOrderDetailsRepository.findAll();
        assertThat(purchaseOrderDetailsList).hasSize(databaseSizeBeforeUpdate);
        PurchaseOrderDetails testPurchaseOrderDetails = purchaseOrderDetailsList.get(purchaseOrderDetailsList.size() - 1);
        assertThat(testPurchaseOrderDetails.getQty()).isEqualTo(UPDATED_QTY);
        assertThat(testPurchaseOrderDetails.getRef1()).isEqualTo(UPDATED_REF_1);
    }

    @Test
    @Transactional
    public void updateNonExistingPurchaseOrderDetails() throws Exception {
        int databaseSizeBeforeUpdate = purchaseOrderDetailsRepository.findAll().size();

        // Create the PurchaseOrderDetails

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPurchaseOrderDetailsMockMvc.perform(put("/api/purchase-order-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrderDetails)))
            .andExpect(status().isCreated());

        // Validate the PurchaseOrderDetails in the database
        List<PurchaseOrderDetails> purchaseOrderDetailsList = purchaseOrderDetailsRepository.findAll();
        assertThat(purchaseOrderDetailsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePurchaseOrderDetails() throws Exception {
        // Initialize the database
        purchaseOrderDetailsService.save(purchaseOrderDetails);

        int databaseSizeBeforeDelete = purchaseOrderDetailsRepository.findAll().size();

        // Get the purchaseOrderDetails
        restPurchaseOrderDetailsMockMvc.perform(delete("/api/purchase-order-details/{id}", purchaseOrderDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PurchaseOrderDetails> purchaseOrderDetailsList = purchaseOrderDetailsRepository.findAll();
        assertThat(purchaseOrderDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseOrderDetails.class);
        PurchaseOrderDetails purchaseOrderDetails1 = new PurchaseOrderDetails();
        purchaseOrderDetails1.setId(1L);
        PurchaseOrderDetails purchaseOrderDetails2 = new PurchaseOrderDetails();
        purchaseOrderDetails2.setId(purchaseOrderDetails1.getId());
        assertThat(purchaseOrderDetails1).isEqualTo(purchaseOrderDetails2);
        purchaseOrderDetails2.setId(2L);
        assertThat(purchaseOrderDetails1).isNotEqualTo(purchaseOrderDetails2);
        purchaseOrderDetails1.setId(null);
        assertThat(purchaseOrderDetails1).isNotEqualTo(purchaseOrderDetails2);
    }
}
