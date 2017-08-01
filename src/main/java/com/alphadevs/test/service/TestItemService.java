package com.alphadevs.test.service;

import com.alphadevs.test.domain.TestItem;
import com.alphadevs.test.repository.TestItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing TestItem.
 */
@Service
@Transactional
public class TestItemService {

    private final Logger log = LoggerFactory.getLogger(TestItemService.class);

    private final TestItemRepository testItemRepository;
    private final CompanyService companyService;

    public TestItemService(TestItemRepository testItemRepository, CompanyService companyService) {
        this.testItemRepository = testItemRepository;
        this.companyService = companyService;
    }

    /**
     * Save a testItem.
     *
     * @param testItem the entity to save
     * @return the persisted entity
     */
    public TestItem save(TestItem testItem) {
        log.debug("Request to save TestItem : {}", testItem);
        return testItemRepository.save(testItem);
    }

    /**
     *  Get all the testItems.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TestItem> findAll(Pageable pageable) {
        log.debug("Request to get all TestItems");
        return testItemRepository.findAllByRelatedCompany(pageable,companyService.getByUser());
    }

    /**
     *  Get one testItem by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public TestItem findOne(Long id) {
        log.debug("Request to get TestItem : {}", id);
        return testItemRepository.findOne(id);
    }

    /**
     *  Delete the  testItem by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TestItem : {}", id);
        testItemRepository.delete(id);
    }
}
