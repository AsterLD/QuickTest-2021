package com.ld.quicktest.controllers;

import com.ld.quicktest.models.Question;
import com.ld.quicktest.service.QuestionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tests/{testId}")
@PreAuthorize("hasAuthority('ADMIN')")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/new")
    public String createNewQuestion(Model model, @PathVariable("testId") Long testId) {
        return questionService.createNewQuestion(model, testId);
    }

    @PostMapping
    public String saveNewQuestion(@PathVariable("testId") Long testId, Question question) {
        return questionService.saveNewQuestion(testId, question);
    }

    @PatchMapping("/{questionId}")
    public String updateQuestion(@PathVariable("testId") Long testId,
                                 Question question) {
        return questionService.updateQuestion(testId, question);
    }

    @DeleteMapping("/{questionId}")
    public String deleteQuestion(Question question) {
        return questionService.deleteQuestion(question);
    }

    @GetMapping("/{questionId}/edit")
    public String editQuestion(Model model,
                               @PathVariable("testId") Long testId,
                               @PathVariable("questionId") Long questionId) {
        return questionService.editQuestion(model, questionId);
    }
}
