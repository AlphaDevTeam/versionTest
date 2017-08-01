package com.alphadevs.test.service;

import com.alphadevs.test.domain.ExtraUser;
import com.alphadevs.test.domain.User;
import com.alphadevs.test.repository.ExtraUserRepository;
import com.alphadevs.test.repository.UserRepository;
import com.alphadevs.test.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


/**
 * Service Implementation for managing ExtraUser.
 */
@Service
@Transactional
public class ExtraUserService {

    private final Logger log = LoggerFactory.getLogger(ExtraUserService.class);

    private final ExtraUserRepository extraUserRepository;
    private final UserRepository userRepository;

    public ExtraUserService(ExtraUserRepository extraUserRepository, UserRepository userRepository) {
        this.extraUserRepository = extraUserRepository;
        this.userRepository = userRepository;
    }

    /**
     * Save a extraUser.
     *
     * @param extraUser the entity to save
     * @return the persisted entity
     */
    public ExtraUser save(ExtraUser extraUser) {
        log.debug("Request to save ExtraUser : {}", extraUser);
        return extraUserRepository.save(extraUser);
    }

    /**
     *  Get all the extraUsers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ExtraUser> findAll(Pageable pageable) {
        log.debug("Request to get all ExtraUsers");
        return extraUserRepository.findAll(pageable);
    }

    /**
     *  Get one extraUser by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ExtraUser findOne(Long id) {
        log.debug("Request to get ExtraUser : {}", id);
        return extraUserRepository.findOne(id);
    }

    /**
     *  Delete the  extraUser by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ExtraUser : {}", id);
        extraUserRepository.delete(id);
    }

    /**
     *  Get Extra extraUser by User.
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<ExtraUser> getAllByUser(Pageable pageable) {
        log.debug("Request extra info by User: {}", SecurityUtils.getCurrentUserLogin());
        Optional<User> login = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
        return extraUserRepository.findAllByUser(pageable,login.isPresent() ? login.get() : null);
    }
}
