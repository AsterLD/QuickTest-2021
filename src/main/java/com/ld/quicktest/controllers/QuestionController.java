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
    public String createNewQuestion(@PathVariable("testId") Long testId, Model model) {
        return questionService.createNewQuestion(model, testId);
    }

    @PostMapping
    public String saveNewQuestion(@PathVariable("testId") Long testId, Question question) {
        return questionService.updateQuestion(testId, question, "question/editQuestionPage");
    }

    @PatchMapping("/{questionId}")
    public String updateQuestion(@PathVariable("testId") Long testId,
                                 Question question) {
        return questionService.updateQuestion(testId, question, "redirect:/tests/{testId}/{questionId}/edit");
    }

    @DeleteMapping("/{questionId}")
    public String deleteQuestion(Question question) {
        return questionService.deleteQuestion(question);
    }

    @GetMapping("/{questionId}/edit")
    public String editQuestion(@PathVariable("testId") Long testId,
                               @PathVariable("questionId") Long questionId, Model model) {
        return questionService.editQuestion(model, questionId);
    }
}
