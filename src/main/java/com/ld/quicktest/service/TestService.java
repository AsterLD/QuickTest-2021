package com.ld.quicktest.service;

import com.ld.quicktest.models.Test;
import com.ld.quicktest.repos.TestRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import static com.ld.quicktest.util.PageListGenerator.generateAvailablePageList;

/*
 * Класс TestService, хранит в себе логику, для работы TestController.
 * findAllTests - Отображает список всех тестов, используется для доступа к редактированию,
 * findAllAvailableTests - Отображает список всех доступных тестов, используется при выборе теста для прохождения,
 * findTests - Отображает список всех тестов, соответствующих критерию поиска (части названия),
 * showTestInfo - Используется для поиска конкретного теста с помощью ID,
 * saveTest - Сохраняет тест в БД,
 * deleteTest - Удаляет тест из БД.
 */


@Service
public class TestService {

    private final TestRepo testRepo;

    public TestService(TestRepo testRepo) {
        this.testRepo = testRepo;
    }

    public String findAllTests (Model model, int pageNumber) {
        Page<Test> testPage = testRepo.findAll(PageRequest.of(pageNumber - 1, 5));
        generateAvailablePageList(model, pageNumber, testPage);
        return "test/testsListPage";
    }

    public String findAllAvailableTests(Model model, Boolean isAvailable) {
        model.addAttribute("tests", testRepo.findTestByIsActive(isAvailable));
        return "exam/testsListPage";
    }

    public String findTests(Model model, String search, int pageNumber) {
        Page<Test> testPage = testRepo.findTestsByTestNameContains(search, PageRequest.of(pageNumber - 1, 5));
        generateAvailablePageList(model, pageNumber, testPage);
        model.addAttribute("search", search);
        return "test/testsListPage";
    }

    public String showTestInfo(Model model, Long testId, String page) {
        model.addAttribute("test", testRepo.findTestByTestId(testId));
        return page;
    }

    public String saveTest (Test test) {
        testRepo.save(test);
        return "redirect:/tests";
    }

    public String deleteTest(Long testId) {
        testRepo.deleteById(testId);
        return "redirect:/tests";
    }
}
