package com.ld.quicktest.controllers;

import com.ld.quicktest.models.Question;
import com.ld.quicktest.repos.QuestionRepo;
import com.ld.quicktest.repos.TestRepo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tests/{testId}")
@PreAuthorize("hasAuthority('ADMIN')")
public class QuestionController {

    private final TestRepo testRepo;
    private final QuestionRepo questionRepo;

    public QuestionController(TestRepo testRepo, QuestionRepo questionRepo) {
        this.testRepo = testRepo;
        this.questionRepo = questionRepo;
    }

    @GetMapping("/new")
    public String createNewQuestion(@PathVariable("testId") Long testId, Model model, Question question) {
        model.addAttribute("question", question);
        model.addAttribute("test", testRepo.findTestByTestId(testId));
        return "question/newQuestions";
    }

    @PostMapping
    public String saveNewQuestion(@PathVariable("testId") Long testId, Question question) {
        question.setTest(testRepo.findTestByTestId(testId));
        questionRepo.save(question);
        return "redirect:/tests/{testId}/edit";
    }

    @PatchMapping("/{questionId}")
    public String updateQuestion(@PathVariable("questionId") Long questionId,
                                 @PathVariable("testId") Long testId,
                                 Question question) {
        question.setTest(testRepo.findTestByTestId(testId));
        questionRepo.save(question);
        return "redirect:/tests/{testId}/edit";
    }

    @DeleteMapping("/{questionId}")
    public String deleteQuestion(
                                 @PathVariable("testId") Long testId,
                                 Question question) {
        questionRepo.delete(question);
        return "redirect:/tests/{testId}/edit";
    }

    @GetMapping("/{questionId}/edit")
    public String editQuestion(@PathVariable("testId") Long testId,
                               @PathVariable("questionId") Long questionId,
                               Model model) {
        model.addAttribute("question", questionRepo.findQuestionByQuestionId(questionId));
        return "question/editQuestion";
    }
}
