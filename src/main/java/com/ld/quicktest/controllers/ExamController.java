package com.ld.quicktest.controllers;

import com.ld.quicktest.models.Result;
import com.ld.quicktest.service.ExamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

@Controller
@RequestMapping("/exam")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @GetMapping()
    public String showTestList(Model model) {
        return examService.showAllTests(model);
    }

    @GetMapping("/{testId}")
    public String showTest(Model model, @PathVariable("testId") Long testId) {
        return examService.showTestInfo(model, testId);
    }

    @PostMapping()
    public String saveResult(@RequestParam HashMap<String, String> answers, Long testId, Result result) {
        return examService.saveResult(answers, testId, result);
    }
}
