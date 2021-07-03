package com.ld.quicktest.controllers;

import com.ld.quicktest.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/exam")
public class ExamController {

    private final TestService testService;

    public ExamController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping()
    public String showTestList(Model model) {
        return testService.findAllAvailableTests(model, true);
    }

    @GetMapping("/{testId}")
    public String showTest(Model model, @PathVariable("testId") Long testId) {
        return testService.showTestInfo(model, testId, "exam/test");
    }

}
