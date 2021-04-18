package com.ld.quicktest.service;

import com.ld.quicktest.repos.ResultRepo;
import com.ld.quicktest.repos.TestRepo;
import com.ld.quicktest.repos.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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
