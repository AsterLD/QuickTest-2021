package com.ld.quicktest.controllers;

import com.ld.quicktest.service.ResultService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/results")
public class ResultController {

    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping()
    public String showResults(Model model) {
        return resultService.showResult(model);
    }

    @GetMapping("/search")
    public String findResult(Model model,
                             @RequestParam (name = "search", defaultValue = "") String search,
                             @RequestParam(name = "searchType") String searchType) {
        return resultService.findResults(model, search, searchType);
    }
}
