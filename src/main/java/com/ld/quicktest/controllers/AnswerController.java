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

    @PostMapping
    public String saveAnswer(@PathVariable Long questionId, @PathVariable Long testId, Model model, Answer answer) {
        return answerService.saveAnswer(model, questionId, testId, answer);
    }

    @DeleteMapping("/{answerId}")
    public String deleteAnswer(Answer answer) {
        return answerService.deleteAnswer(answer);
    }
}
