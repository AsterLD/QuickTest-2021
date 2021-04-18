package com.ld.quicktest.service;

import com.ld.quicktest.models.Test;
import com.ld.quicktest.repos.TestRepo;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class TestService {

    private final TestRepo testRepo;

    public TestService(TestRepo testRepo) {
        this.testRepo = testRepo;
    }

    public String findAllTests (Model model) {
        model.addAttribute("tests", testRepo.findAll());
        return "test/testsList";
    }

    public String findTests(Model model, String search) {
        model.addAttribute("tests",
                testRepo.findTestsByTestNameContains(search));
        return "test/testsList";
    }

    public String updateTest(Model model, Long testId) {
        model.addAttribute("test", testRepo.findTestByTestId(testId));
        return "test/editTest";
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
