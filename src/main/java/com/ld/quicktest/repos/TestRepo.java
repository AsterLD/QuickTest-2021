package com.ld.quicktest.repos;

import com.ld.quicktest.models.Test;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/*
 * Репозиторий Тестирований, реализован поиск всех тестирований, добавленных в базу, поиск тестирований по id,
 * поиск одного или нескольких тестирований, по названию тестирования
 */

public interface TestRepo extends CrudRepository<Test, Long> {
    List<Test> findAll();
    List<Test> findTestByIsActive(Boolean isActive);
    Test findTestByTestId(Long testId);
    List<Test> findTestsByTestNameContains(String testName);
}
