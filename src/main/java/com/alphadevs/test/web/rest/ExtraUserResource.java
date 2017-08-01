package com.alphadevs.test.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.alphadevs.test.domain.ExtraUser;
import com.alphadevs.test.service.ExtraUserService;
import com.alphadevs.test.web.rest.util.HeaderUtil;
import com.alphadevs.test.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ExtraUser.
 */
@RestController
@RequestMapping("/api")
public class ExtraUserResource {

    private final Logger log = LoggerFactory.getLogger(ExtraUserResource.class);

    private static final String ENTITY_NAME = "extraUser";

    private final ExtraUserService extraUserService;

    public ExtraUserResource(ExtraUserService extraUserService) {
        this.extraUserService = extraUserService;
    }

    /**
     * POST  /extra-users : Create a new extraUser.
     *
     * @param extraUser the extraUser to create
     * @return the ResponseEntity with status 201 (Created) and with body the new extraUser, or with status 400 (Bad Request) if the extraUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/extra-users")
    @Timed
    public ResponseEntity<ExtraUser> createExtraUser(@Valid @RequestBody ExtraUser extraUser) throws URISyntaxException {
        log.debug("REST request to save ExtraUser : {}", extraUser);
        if (extraUser.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new extraUser cannot already have an ID")).body(null);
        }
        ExtraUser result = extraUserService.save(extraUser);
        return ResponseEntity.created(new URI("/api/extra-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /extra-users : Updates an existing extraUser.
     *
     * @param extraUser the extraUser to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated extraUser,
     * or with status 400 (Bad Request) if the extraUser is not valid,
     * or with status 500 (Internal Server Error) if the extraUser couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/extra-users")
    @Timed
    public ResponseEntity<ExtraUser> updateExtraUser(@Valid @RequestBody ExtraUser extraUser) throws URISyntaxException {
        log.debug("REST request to update ExtraUser : {}", extraUser);
        if (extraUser.getId() == null) {
            return createExtraUser(extraUser);
        }
        ExtraUser result = extraUserService.save(extraUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, extraUser.getId().toString()))
            .body(result);
    }

    /**
     * GET  /extra-users : get all the extraUsers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of extraUsers in body
     */
    @GetMapping("/extra-users")
    @Timed
    public ResponseEntity<List<ExtraUser>> getAllExtraUsers(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ExtraUsers");
        Page<ExtraUser> page = extraUserService.getAllByUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/extra-users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /extra-users/:id : get the "id" extraUser.
     *
     * @param id the id of the extraUser to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the extraUser, or with status 404 (Not Found)
     */
    @GetMapping("/extra-users/{id}")
    @Timed
    public ResponseEntity<ExtraUser> getExtraUser(@PathVariable Long id) {
        log.debug("REST request to get ExtraUser : {}", id);
        ExtraUser extraUser = extraUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(extraUser));
    }

    /**
     * DELETE  /extra-users/:id : delete the "id" extraUser.
     *
     * @param id the id of the extraUser to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/extra-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteExtraUser(@PathVariable Long id) {
        log.debug("REST request to delete ExtraUser : {}", id);
        extraUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }




}
