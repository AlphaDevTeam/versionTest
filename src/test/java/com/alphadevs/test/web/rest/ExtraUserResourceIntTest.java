package com.alphadevs.test.web.rest;

import com.alphadevs.test.VersionTestApp;

import com.alphadevs.test.domain.ExtraUser;
import com.alphadevs.test.domain.User;
import com.alphadevs.test.repository.ExtraUserRepository;
import com.alphadevs.test.service.ExtraUserService;
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
 * Test class for the ExtraUserResource REST controller.
 *
 * @see ExtraUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VersionTestApp.class)
public class ExtraUserResourceIntTest {

    private static final String DEFAULT_EXTRA_INFO = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA_INFO = "BBBBBBBBBB";

    @Autowired
    private ExtraUserRepository extraUserRepository;

    @Autowired
    private ExtraUserService extraUserService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restExtraUserMockMvc;

    private ExtraUser extraUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ExtraUserResource extraUserResource = new ExtraUserResource(extraUserService);
        this.restExtraUserMockMvc = MockMvcBuilders.standaloneSetup(extraUserResource)
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
    public static ExtraUser createEntity(EntityManager em) {
        ExtraUser extraUser = new ExtraUser()
            .extraInfo(DEFAULT_EXTRA_INFO);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        extraUser.setUser(user);
        return extraUser;
    }

    @Before
    public void initTest() {
        extraUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createExtraUser() throws Exception {
        int databaseSizeBeforeCreate = extraUserRepository.findAll().size();

        // Create the ExtraUser
        restExtraUserMockMvc.perform(post("/api/extra-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(extraUser)))
            .andExpect(status().isCreated());

        // Validate the ExtraUser in the database
        List<ExtraUser> extraUserList = extraUserRepository.findAll();
        assertThat(extraUserList).hasSize(databaseSizeBeforeCreate + 1);
        ExtraUser testExtraUser = extraUserList.get(extraUserList.size() - 1);
        assertThat(testExtraUser.getExtraInfo()).isEqualTo(DEFAULT_EXTRA_INFO);
    }

    @Test
    @Transactional
    public void createExtraUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = extraUserRepository.findAll().size();

        // Create the ExtraUser with an existing ID
        extraUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExtraUserMockMvc.perform(post("/api/extra-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(extraUser)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ExtraUser> extraUserList = extraUserRepository.findAll();
        assertThat(extraUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllExtraUsers() throws Exception {
        // Initialize the database
        extraUserRepository.saveAndFlush(extraUser);

        // Get all the extraUserList
        restExtraUserMockMvc.perform(get("/api/extra-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(extraUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].extraInfo").value(hasItem(DEFAULT_EXTRA_INFO.toString())));
    }

    @Test
    @Transactional
    public void getExtraUser() throws Exception {
        // Initialize the database
        extraUserRepository.saveAndFlush(extraUser);

        // Get the extraUser
        restExtraUserMockMvc.perform(get("/api/extra-users/{id}", extraUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(extraUser.getId().intValue()))
            .andExpect(jsonPath("$.extraInfo").value(DEFAULT_EXTRA_INFO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExtraUser() throws Exception {
        // Get the extraUser
        restExtraUserMockMvc.perform(get("/api/extra-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExtraUser() throws Exception {
        // Initialize the database
        extraUserService.save(extraUser);

        int databaseSizeBeforeUpdate = extraUserRepository.findAll().size();

        // Update the extraUser
        ExtraUser updatedExtraUser = extraUserRepository.findOne(extraUser.getId());
        updatedExtraUser
            .extraInfo(UPDATED_EXTRA_INFO);

        restExtraUserMockMvc.perform(put("/api/extra-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedExtraUser)))
            .andExpect(status().isOk());

        // Validate the ExtraUser in the database
        List<ExtraUser> extraUserList = extraUserRepository.findAll();
        assertThat(extraUserList).hasSize(databaseSizeBeforeUpdate);
        ExtraUser testExtraUser = extraUserList.get(extraUserList.size() - 1);
        assertThat(testExtraUser.getExtraInfo()).isEqualTo(UPDATED_EXTRA_INFO);
    }

    @Test
    @Transactional
    public void updateNonExistingExtraUser() throws Exception {
        int databaseSizeBeforeUpdate = extraUserRepository.findAll().size();

        // Create the ExtraUser

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restExtraUserMockMvc.perform(put("/api/extra-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(extraUser)))
            .andExpect(status().isCreated());

        // Validate the ExtraUser in the database
        List<ExtraUser> extraUserList = extraUserRepository.findAll();
        assertThat(extraUserList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteExtraUser() throws Exception {
        // Initialize the database
        extraUserService.save(extraUser);

        int databaseSizeBeforeDelete = extraUserRepository.findAll().size();

        // Get the extraUser
        restExtraUserMockMvc.perform(delete("/api/extra-users/{id}", extraUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ExtraUser> extraUserList = extraUserRepository.findAll();
        assertThat(extraUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExtraUser.class);
        ExtraUser extraUser1 = new ExtraUser();
        extraUser1.setId(1L);
        ExtraUser extraUser2 = new ExtraUser();
        extraUser2.setId(extraUser1.getId());
        assertThat(extraUser1).isEqualTo(extraUser2);
        extraUser2.setId(2L);
        assertThat(extraUser1).isNotEqualTo(extraUser2);
        extraUser1.setId(null);
        assertThat(extraUser1).isNotEqualTo(extraUser2);
    }
}
