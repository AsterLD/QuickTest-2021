package com.ld.quicktest.repos;

import com.ld.quicktest.models.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TestRepo extends PagingAndSortingRepository<Test, Long> {
    List<Test> findAll();
    Page<Test> findAll(Pageable pageable);
    List<Test> findTestByIsActive(Boolean isActive);
    Test findTestByTestId(Long testId);
    List<Test> findTestsByTestNameContains(String testName);
    Page<Test> findTestsByTestNameContains(String testName, Pageable pageable);
}
