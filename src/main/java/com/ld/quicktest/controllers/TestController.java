package com.ld.quicktest.controllers;

import com.ld.quicktest.models.Test;
import com.ld.quicktest.service.TestService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tests")
@PreAuthorize("hasAuthority('ADMIN')")
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping()
    public String showAllTests(Model model) {
        return testService.findAllTests(model);
    }

    @GetMapping("/search")
    public String findTests(Model model, @RequestParam (name = "search", defaultValue = "") String search) {
        return testService.findTests(model, search);
    }

    @GetMapping("/new")
    public String createNewTest(@ModelAttribute("test") Test test) {
        return "test/newTest";
    }

    @PostMapping
    public String saveNewTest(Test test) {
        return testService.saveTest(test);
    }

    @GetMapping("/{testId}/edit")
    public String editTest( @PathVariable("testId") Long testId, Model model) {
        return testService.showTestInfo(model, testId, "test/editTest");
    }

    @PatchMapping("/{testId}")
    public String updateTest(Test test) {
        return testService.saveTest(test);
    }

    @DeleteMapping("/{testId}")
    public String deleteTest(@PathVariable("testId") Long testId) {
        return testService.deleteTest(testId);
    }
}
