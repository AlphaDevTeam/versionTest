package com.alphadevs.test.repository;

import com.alphadevs.test.domain.Company;
import com.alphadevs.test.domain.User;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Company entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {
    Company findByRelatedUser(User logedUser);
}
