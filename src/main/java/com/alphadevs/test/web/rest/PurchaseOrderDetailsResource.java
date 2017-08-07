package com.alphadevs.test.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.alphadevs.test.domain.PurchaseOrderDetails;
import com.alphadevs.test.service.PurchaseOrderDetailsService;
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
 * REST controller for managing PurchaseOrderDetails.
 */
@RestController
@RequestMapping("/api")
public class PurchaseOrderDetailsResource {

    private final Logger log = LoggerFactory.getLogger(PurchaseOrderDetailsResource.class);

    private static final String ENTITY_NAME = "purchaseOrderDetails";

    private final PurchaseOrderDetailsService purchaseOrderDetailsService;

    public PurchaseOrderDetailsResource(PurchaseOrderDetailsService purchaseOrderDetailsService) {
        this.purchaseOrderDetailsService = purchaseOrderDetailsService;
    }

    /**
     * POST  /purchase-order-details : Create a new purchaseOrderDetails.
     *
     * @param purchaseOrderDetails the purchaseOrderDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new purchaseOrderDetails, or with status 400 (Bad Request) if the purchaseOrderDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/purchase-order-details")
    @Timed
    public ResponseEntity<PurchaseOrderDetails> createPurchaseOrderDetails(@RequestBody PurchaseOrderDetails purchaseOrderDetails) throws URISyntaxException {
        log.debug("REST request to save PurchaseOrderDetails : {}", purchaseOrderDetails);
        if (purchaseOrderDetails.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new purchaseOrderDetails cannot already have an ID")).body(null);
        }
        PurchaseOrderDetails result = purchaseOrderDetailsService.save(purchaseOrderDetails);
        return ResponseEntity.created(new URI("/api/purchase-order-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /purchase-order-details : Updates an existing purchaseOrderDetails.
     *
     * @param purchaseOrderDetails the purchaseOrderDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated purchaseOrderDetails,
     * or with status 400 (Bad Request) if the purchaseOrderDetails is not valid,
     * or with status 500 (Internal Server Error) if the purchaseOrderDetails couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/purchase-order-details")
    @Timed
    public ResponseEntity<PurchaseOrderDetails> updatePurchaseOrderDetails(@RequestBody PurchaseOrderDetails purchaseOrderDetails) throws URISyntaxException {
        log.debug("REST request to update PurchaseOrderDetails : {}", purchaseOrderDetails);
        if (purchaseOrderDetails.getId() == null) {
            return createPurchaseOrderDetails(purchaseOrderDetails);
        }
        PurchaseOrderDetails result = purchaseOrderDetailsService.save(purchaseOrderDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, purchaseOrderDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /purchase-order-details : get all the purchaseOrderDetails.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of purchaseOrderDetails in body
     */
    @GetMapping("/purchase-order-details")
    @Timed
    public ResponseEntity<List<PurchaseOrderDetails>> getAllPurchaseOrderDetails(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of PurchaseOrderDetails");
        Page<PurchaseOrderDetails> page = purchaseOrderDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/purchase-order-details");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /purchase-order-details/:id : get the "id" purchaseOrderDetails.
     *
     * @param id the id of the purchaseOrderDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the purchaseOrderDetails, or with status 404 (Not Found)
     */
    @GetMapping("/purchase-order-details/{id}")
    @Timed
    public ResponseEntity<PurchaseOrderDetails> getPurchaseOrderDetails(@PathVariable Long id) {
        log.debug("REST request to get PurchaseOrderDetails : {}", id);
        PurchaseOrderDetails purchaseOrderDetails = purchaseOrderDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(purchaseOrderDetails));
    }

    /**
     * DELETE  /purchase-order-details/:id : delete the "id" purchaseOrderDetails.
     *
     * @param id the id of the purchaseOrderDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/purchase-order-details/{id}")
    @Timed
    public ResponseEntity<Void> deletePurchaseOrderDetails(@PathVariable Long id) {
        log.debug("REST request to delete PurchaseOrderDetails : {}", id);
        purchaseOrderDetailsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
