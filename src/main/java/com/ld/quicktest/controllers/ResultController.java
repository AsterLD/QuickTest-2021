package com.ld.quicktest.controllers;

import com.ld.quicktest.repos.ResultRepo;
import com.ld.quicktest.repos.TestRepo;
import com.ld.quicktest.repos.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/results")
public class ResultController {

    private final ResultRepo resultRepo;
    private final UserRepo userRepo;
    private final TestRepo testRepo;


    public ResultController(ResultRepo resultRepo, UserRepo userRepo, TestRepo testRepo) {
        this.resultRepo = resultRepo;
        this.userRepo = userRepo;
        this.testRepo = testRepo;
    }

    @GetMapping()
    public String showResults(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("results", resultRepo.findResultsByUser(userRepo.findByUsername(auth.getName())));
        return "result/resultList";
    }
    @GetMapping("/search")
    public String findResult(Model model, @RequestParam (name = "search", defaultValue = "") String search,
                             @RequestParam(name = "searchType") String searchType) {
        switch (searchType) {
            case "fullName":
                model.addAttribute("results", resultRepo.findResultsByUserIn(userRepo.findUserByFullNameContains(search)));
                break;
            case "testName":
                model.addAttribute("results", resultRepo.findResultsByTestIn(testRepo.findTestByTestNameContains(search)));
                break;
        }
        return "result/resultList";
    }
}
