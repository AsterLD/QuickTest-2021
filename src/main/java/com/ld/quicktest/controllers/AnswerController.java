package com.ld.quicktest.controllers;

import com.ld.quicktest.models.Answer;
import com.ld.quicktest.service.AnswerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tests/{testId}/{questionId}")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping("/{answerId}")
    public String getAnswerInfo(Model model, @PathVariable Long answerId) {
        return answerService.showAnswerInfo(model, answerId);
    }

    @PostMapping
    public String saveAnswer(Model model, Answer answer, @PathVariable Long questionId, @PathVariable Long testId) {
        return answerService.saveAnswer(model, answer, questionId, testId);
    }

    @DeleteMapping("/{answerId}")
    public String deleteAnswer(Answer answer) {
        return answerService.deleteAnswer(answer);
    }
}
