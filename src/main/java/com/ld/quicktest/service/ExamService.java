package com.ld.quicktest.service;

import com.ld.quicktest.repos.TestRepo;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class ExamService {

    private final TestRepo testRepo;

    public ExamService(TestRepo testRepo) {
        this.testRepo = testRepo;
    }


    public String showAllTests(Model model) {
        model.addAttribute("tests", testRepo.findAll());
        return "exam/testsList";
    }

    public String showTestInfo(Model model, Long testId) {
        model.addAttribute("test", testRepo.findTestByTestId(testId));
        return "exam/test";
    }

}
