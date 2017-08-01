package com.alphadevs.test.repository;

import com.alphadevs.test.domain.Company;
import com.alphadevs.test.domain.TestItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;



/**
 * Spring Data JPA repository for the TestItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TestItemRepository extends JpaRepository<TestItem,Long> {
    Page<TestItem> findAllByRelatedCompany(Pageable pageable, Company company);
}
