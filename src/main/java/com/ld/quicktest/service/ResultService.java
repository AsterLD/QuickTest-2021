package com.ld.quicktest.service;

import com.ld.quicktest.models.Question;
import com.ld.quicktest.models.Result;
import com.ld.quicktest.models.Test;
import com.ld.quicktest.repos.ResultRepo;
import com.ld.quicktest.repos.TestRepo;
import com.ld.quicktest.repos.UserRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import static com.ld.quicktest.util.PageListGenerator.generateAvailablePageList;

/*
 * Класс ResultService, хранит в себе логику, для работы ResultController.
 * saveResult - Метод, который обрабатывает ответы, полученные от пользователя:
 * 1. очищает коллекцию map, под названием form, от данных, не являющимися ответами,
 * 2. Последовательно сравнивает список правильных ответов с отфильтрованным списком ответов из коллекции form
 * (список ответов в коллекции form повторно фильтруется, оставляя только ответы пользователя на проверяемый вопрос),
 * Если пользователь ответил верно - переменная answerResult увеличивается на единицу,
 * 3. заполняются поля, хранящие дополнительные сведения о тестировании и результат записывается в БД,
 * showResult - Пользователю, на основе данных авторизации, отображаются его результаты в тестировании,
 * findResults - Доступен только администраторами, поиск результатов для составления отчета о тестировании,
 * getRightAnswerMap - Метод, используемый для получения коллекции Map, с перечнем правильных ответов.
 */

@Service
public class ResultService {

    private final TestRepo testRepo;
    private final ResultRepo resultRepo;
    private final UserRepo userRepo;
    private final int NUMBER_OF_PAGES = 10;

    public ResultService(TestRepo testRepo, ResultRepo resultRepo, UserRepo userRepo) {
        this.testRepo = testRepo;
        this.resultRepo = resultRepo;
        this.userRepo = userRepo;
    }

    public String saveResult(Map<String, String> answers, Long testId, Result result) {
        int answerResult = 0;
        Test test = testRepo.findTestByTestId(testId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Question> questionList = test.getQuestionList();
        answers.keySet().removeIf(key -> !key.contains("answer"));
        for (Question question : questionList) {
            if(getRightAnswerMap(question).keySet().equals(answers.keySet().stream()
                    .filter(answer -> answer.startsWith("answer[" + question.getQuestionId() + "."))
                    .collect(Collectors.toSet()))) {
                answerResult++;
            }
        }
        result.setNumberOfCorrectAnswers(answerResult);
        result.setNumberOfQuestion(questionList.size());
        result.setPercentageOfCorrectAnswers(BigDecimal.valueOf((float) answerResult * 100 / questionList.size()));
        result.setTest(test);
        result.setUser(userRepo.findUserByUsername(auth.getName()));
        resultRepo.save(result);
        return "exam/resultPage";
    }

    public String showResult (Model model, int pageNumber) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Page<Result> resultPage = resultRepo.findResultsByUser(userRepo.findUserByUsername(auth.getName()), PageRequest.of(pageNumber - 1, NUMBER_OF_PAGES));
        generateAvailablePageList(model, pageNumber, resultPage);
        return "result/resultListPage";
    }

    public String findResults (Model model, String search, String searchType, int pageNumber) {
        model.addAttribute("search", search);
        model.addAttribute("type", searchType);
        Page<Result> resultPage;
        switch (searchType) {
            case "name":
                resultPage = resultRepo.findResultsByUserIn(userRepo.findUsersByFullNameContains(search), PageRequest.of(pageNumber - 1, NUMBER_OF_PAGES));
                generateAvailablePageList(model, pageNumber, resultPage);
                break;
            case "test":
                resultPage = resultRepo.findResultsByTestIn(testRepo.findTestsByTestNameContains(search), PageRequest.of(pageNumber - 1, NUMBER_OF_PAGES));
                generateAvailablePageList(model, pageNumber, resultPage);
                break;
            case "department":
                resultPage = resultRepo.findResultsByUserIn(userRepo.findUsersByDepartmentContains(search) , PageRequest.of(pageNumber - 1, NUMBER_OF_PAGES));
                generateAvailablePageList(model, pageNumber, resultPage);
                break;
        }
        return "result/resultListPage";
    }

    public Map<String, String> getRightAnswerMap(Question question) {
        Map<String, String> rightAnswerMap = new HashMap<>();
        question.getAnswerList().forEach((answer) -> {
            if (answer.isRightAnswer()) {
                rightAnswerMap.put("answer["
                        + answer.getQuestion().getQuestionId()
                        + "." + answer.getAnswerId()
                        +"]", answer.getAnswerText());
            }
        });
        return rightAnswerMap;
    }
}
