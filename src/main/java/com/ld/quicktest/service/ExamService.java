package com.ld.quicktest.service;

import com.ld.quicktest.models.Question;
import com.ld.quicktest.models.Result;
import com.ld.quicktest.models.Test;
import com.ld.quicktest.repos.ResultRepo;
import com.ld.quicktest.repos.TestRepo;
import com.ld.quicktest.repos.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExamService {

    private final ResultRepo resultRepo;
    private final TestRepo testRepo;
    private final UserRepo userRepo;

    public ExamService(ResultRepo resultRepo, TestRepo testRepo, UserRepo userRepo) {
        this.resultRepo = resultRepo;
        this.testRepo = testRepo;
        this.userRepo = userRepo;
    }

    public String showAllTests(Model model) {
        model.addAttribute("tests", testRepo.findAll());
        return "exam/testsList";
    }

    public String showTestInfo(Model model, Long testId) {
        model.addAttribute("test", testRepo.findTestByTestId(testId));
        return "exam/test";
    }

    public String saveResult(HashMap<String, String> answers, Long testId, Result result) {
        int answerResult = 0;
        Test test = testRepo.findTestByTestId(testId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Question> questionList = test.getQuestionList();

        answers.keySet().removeIf(key -> !key.contains("answer"));
        for(Map.Entry<String, String> answer : answers.entrySet()) {
            for (Question question: questionList) {
                if(answer.getKey().contains(question.getQuestionId().toString())) {
                    if (answer.getValue().equals(question.getAnswer()))
                        answerResult++;
                    break;
                }
            }
        }
        result.setNumberOfCorrectAnswers(answerResult);
        result.setNumberOfQuestion(questionList.size());
        result.setPercentageOfCorrectAnswers(BigDecimal.valueOf((float) answerResult * 100 / questionList.size()));
        result.setTest(test);
        result.setUser(userRepo.findUserByUsername(auth.getName()));
        resultRepo.save(result);
        return "exam/result";
    }
}
