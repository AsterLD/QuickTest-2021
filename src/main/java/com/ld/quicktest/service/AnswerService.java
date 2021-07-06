package com.ld.quicktest.service;

import com.ld.quicktest.models.Answer;
import com.ld.quicktest.models.Question;
import com.ld.quicktest.repos.AnswerRepo;
import com.ld.quicktest.repos.QuestionRepo;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/*
 * Класс AnswerService, хранит в себе логику, для работы AnswerController.
 * saveAnswer - сохраняет ответ в БД, и возвращает данные для дальнейшего редактирования вопроса.
 * deleteAnswer - удаляет ответ из БД.
 */

@Service
public class AnswerService {

    private final AnswerRepo answerRepo;
    private final QuestionRepo questionRepo;

    public AnswerService(AnswerRepo answerRepo, QuestionRepo questionRepo) {
        this.answerRepo = answerRepo;
        this.questionRepo = questionRepo;
    }

    public String saveAnswer(Model model, Long questionId, Long testId, Answer answer) {
        Question question = questionRepo.findQuestionByQuestionId(questionId);
        answer.setQuestion(question);
        answerRepo.save(answer);
        model.addAttribute("question", question);
        model.addAttribute("questionId", questionId);
        model.addAttribute("testId", testId);
        return "redirect:/tests/{testId}/{questionId}/edit";
    }

    public String deleteAnswer(Answer answer) {
        answerRepo.delete(answer);
        return "redirect:/tests/{testId}/{questionId}/edit";
    }
}
