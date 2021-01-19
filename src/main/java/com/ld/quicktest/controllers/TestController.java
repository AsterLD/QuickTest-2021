package com.ld.quicktest.controllers;

import com.ld.quicktest.models.Test;
import com.ld.quicktest.repos.TestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tests")
@PreAuthorize("hasAuthority('ADMIN')")
public class TestController {

    private TestRepo testRepo;

    @Autowired
    public TestController(TestRepo testRepo) {
        this.testRepo = testRepo;
    }

    @GetMapping()
    public String showAllTests(Model model) {
        model.addAttribute("tests", testRepo.findAll());
        return "test/testsList";
    }

    @GetMapping("/new")
    public String createNewTest(@ModelAttribute("test") Test test) {
        return "test/newTest";
    }

    @PostMapping
    public String saveNewTest(Test test) {
        testRepo.save(test);
        return "redirect:/tests";
    }

    @PatchMapping("/{testId}")
    public String updateTest(@PathVariable("testId") Long testId,
                             Test test) {
        testRepo.save(test);
        return "redirect:/tests";
    }

    @DeleteMapping("/{testId}")
    public String deleteTest(@PathVariable("testId") Long testId,
                             Test test) {
        testRepo.deleteById(testId);
        return "redirect:/tests";
    }

    @GetMapping("/{testId}/edit")
    public String editTest( @PathVariable("testId") Long testId,
                            Model model) {
        model.addAttribute("test", testRepo.findByTestId(testId));
        return "test/editTest";
    }

}
