package com.alphadevs.test.repository;

import com.alphadevs.test.domain.ExtraUser;
import com.alphadevs.test.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the ExtraUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExtraUserRepository extends JpaRepository<ExtraUser,Long> {

    Page<ExtraUser> findAllByUser(Pageable pageable, User logedUser);

}
