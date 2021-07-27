package com.ld.quicktest.controllers;

import com.ld.quicktest.models.Result;
import com.ld.quicktest.service.ResultService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;

@Controller
@RequestMapping("/results")
public class ResultController {

    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping
    public String showResults(@RequestParam(defaultValue = "1") int page, Model model) {
        return resultService.showResult(model, page);
    }

    @GetMapping("/search")
    public String findResult(@RequestParam (name = "search", defaultValue = "") String search,
                             @RequestParam(name = "type") String searchType,
                             @RequestParam(defaultValue = "1") int page,
                             Model model) {
        return resultService.findResults(model, search, searchType, page);
    }

    @PostMapping
    public String saveResult(@RequestParam Map<String, String> answers, Long testId, Result result) {
        return resultService.saveResult(answers, testId, result);
    }
}
