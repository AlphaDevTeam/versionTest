package com.alphadevs.test.repository;

import com.alphadevs.test.domain.PurchaseOrderDetails;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PurchaseOrderDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PurchaseOrderDetailsRepository extends JpaRepository<PurchaseOrderDetails,Long> {
    
}
