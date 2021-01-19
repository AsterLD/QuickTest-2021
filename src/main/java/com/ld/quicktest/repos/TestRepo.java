package com.ld.quicktest.repos;

import com.ld.quicktest.models.Question;
import com.ld.quicktest.models.Test;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TestRepo extends CrudRepository<Test, Long> {
    List<Test> findAll();
    Test findByTestId(Long testId);
}
