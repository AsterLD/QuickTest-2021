package com.ld.quicktest.service;

import com.ld.quicktest.models.Question;
import com.ld.quicktest.repos.QuestionRepo;
import com.ld.quicktest.repos.TestRepo;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class QuestionService {

    private final TestRepo testRepo;
    private final QuestionRepo questionRepo;

    public QuestionService(TestRepo testRepo, QuestionRepo questionRepo) {
        this.testRepo = testRepo;
        this.questionRepo = questionRepo;
    }

    public String createNewQuestion(Model model, Long testId) {
        Question question = new Question();

        questionRepo.save(question);
        model.addAttribute("question", question);
        model.addAttribute("test", testRepo.findTestByTestId(testId));
        return "question/newQuestions";
    }

    public String saveNewQuestion(Long testId, Question question) {
        question.setTest(testRepo.findTestByTestId(testId));
        questionRepo.save(question);
        return "question/editQuestion";
    }

    public String editQuestion(Model model, Long questionId) {
        model.addAttribute("question", questionRepo.findQuestionByQuestionId(questionId));
        return "question/editQuestion";
    }

    public String updateQuestion(Long testId, Question question) {
        question.setTest(testRepo.findTestByTestId(testId));
        questionRepo.save(question);
        return "redirect:/tests/{testId}/{questionId}/edit";
    }

    public String deleteQuestion(Question question) {
        questionRepo.delete(question);
        return "redirect:/tests/{testId}/edit";
    }

}
