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
public class ResultService {

    private final TestRepo testRepo;
    private final ResultRepo resultRepo;
    private final UserRepo userRepo;

    public ResultService(TestRepo testRepo, ResultRepo resultRepo, UserRepo userRepo) {
        this.testRepo = testRepo;
        this.resultRepo = resultRepo;
        this.userRepo = userRepo;
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

    public String showResult (Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("results",
                resultRepo.findResultsByUser(userRepo.findUserByUsername(auth.getName())));
        return "result/resultList";
    }

    public String findResults (Model model, String search, String searchType) {
        switch (searchType) {
            case "fullName":
                model.addAttribute("results",
                        resultRepo.findResultsByUserIn(userRepo.findUsersByFullNameContains(search)));
                break;
            case "testName":
                model.addAttribute("results",
                        resultRepo.findResultsByTestIn(testRepo.findTestsByTestNameContains(search)));
                break;
            case "departmentName":
                model.addAttribute("results",
                        resultRepo.findResultsByUserIn(userRepo.findUsersByDepartmentContains(search)));
                break;
        }
        return "result/resultList";
    }
}
