package com.alphadevs.test.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.alphadevs.test.domain.TestItem;
import com.alphadevs.test.service.TestItemService;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TestItem.
 */
@RestController
@RequestMapping("/api")
public class TestItemResource {

    private final Logger log = LoggerFactory.getLogger(TestItemResource.class);

    private static final String ENTITY_NAME = "testItem";

    private final TestItemService testItemService;

    public TestItemResource(TestItemService testItemService) {
        this.testItemService = testItemService;
    }

    /**
     * POST  /test-items : Create a new testItem.
     *
     * @param testItem the testItem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new testItem, or with status 400 (Bad Request) if the testItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/test-items")
    @Timed
    public ResponseEntity<TestItem> createTestItem(@RequestBody TestItem testItem) throws URISyntaxException {
        log.debug("REST request to save TestItem : {}", testItem);
        if (testItem.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new testItem cannot already have an ID")).body(null);
        }
        TestItem result = testItemService.save(testItem);
        return ResponseEntity.created(new URI("/api/test-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /test-items : Updates an existing testItem.
     *
     * @param testItem the testItem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated testItem,
     * or with status 400 (Bad Request) if the testItem is not valid,
     * or with status 500 (Internal Server Error) if the testItem couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/test-items")
    @Timed
    public ResponseEntity<TestItem> updateTestItem(@RequestBody TestItem testItem) throws URISyntaxException {
        log.debug("REST request to update TestItem : {}", testItem);
        if (testItem.getId() == null) {
            return createTestItem(testItem);
        }
        TestItem result = testItemService.save(testItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, testItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /test-items : get all the testItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of testItems in body
     */
    @GetMapping("/test-items")
    @Timed
    public ResponseEntity<List<TestItem>> getAllTestItems(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of TestItems");
        Page<TestItem> page = testItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/test-items");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /test-items/:id : get the "id" testItem.
     *
     * @param id the id of the testItem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the testItem, or with status 404 (Not Found)
     */
    @GetMapping("/test-items/{id}")
    @Timed
    public ResponseEntity<TestItem> getTestItem(@PathVariable Long id) {
        log.debug("REST request to get TestItem : {}", id);
        TestItem testItem = testItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(testItem));
    }

    /**
     * DELETE  /test-items/:id : delete the "id" testItem.
     *
     * @param id the id of the testItem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/test-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteTestItem(@PathVariable Long id) {
        log.debug("REST request to delete TestItem : {}", id);
        testItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
