package com.alphadevs.test.service;

import com.alphadevs.test.domain.PurchaseOrderDetails;
import com.alphadevs.test.repository.PurchaseOrderDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing PurchaseOrderDetails.
 */
@Service
@Transactional
public class PurchaseOrderDetailsService {

    private final Logger log = LoggerFactory.getLogger(PurchaseOrderDetailsService.class);

    private final PurchaseOrderDetailsRepository purchaseOrderDetailsRepository;

    public PurchaseOrderDetailsService(PurchaseOrderDetailsRepository purchaseOrderDetailsRepository) {
        this.purchaseOrderDetailsRepository = purchaseOrderDetailsRepository;
    }

    /**
     * Save a purchaseOrderDetails.
     *
     * @param purchaseOrderDetails the entity to save
     * @return the persisted entity
     */
    public PurchaseOrderDetails save(PurchaseOrderDetails purchaseOrderDetails) {
        log.debug("Request to save PurchaseOrderDetails : {}", purchaseOrderDetails);
        return purchaseOrderDetailsRepository.save(purchaseOrderDetails);
    }

    /**
     *  Get all the purchaseOrderDetails.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PurchaseOrderDetails> findAll(Pageable pageable) {
        log.debug("Request to get all PurchaseOrderDetails");
        return purchaseOrderDetailsRepository.findAll(pageable);
    }

    /**
     *  Get one purchaseOrderDetails by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PurchaseOrderDetails findOne(Long id) {
        log.debug("Request to get PurchaseOrderDetails : {}", id);
        return purchaseOrderDetailsRepository.findOne(id);
    }

    /**
     *  Delete the  purchaseOrderDetails by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PurchaseOrderDetails : {}", id);
        purchaseOrderDetailsRepository.delete(id);
    }
}
