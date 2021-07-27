package com.ld.quicktest.controllers;

import com.ld.quicktest.models.User;
import com.ld.quicktest.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showAllUsers(@RequestParam(defaultValue = "1") int page, Model model) {
        return userService.findAllUsers(model, page);
    }

    @GetMapping("/search")
    public String findUsers(@RequestParam (name = "search", defaultValue = "") String search,
                            @RequestParam(name = "type") String searchType,
                            @RequestParam(defaultValue = "1") int page, Model model) {
        return userService.findUsers(model, search, searchType, page);
    }

    @GetMapping("/{userId}")
    public String showUserInfo(@PathVariable Long userId, Model model) {
        return userService.findUserInfo(model, userId, "user/userInfoPage");
    }

    @GetMapping("/new")
    public String createNewUser() {
        return "user/newUserPage";
    }

    @PostMapping
    public String addUser(Model model, User user) {
        return userService.addUser(model, user);
    }

    @GetMapping("/{userId}/edit")
    public String editUser(@PathVariable Long userId, Model model) {
        return userService.findUserInfo(model, userId, "user/editUserPage");
    }

    @PatchMapping("/{userId}")
    public String updateUser(@RequestParam Map<String, String> form, User user, Model model) {
        return userService.updateUser(model, form, user);
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }

    @GetMapping("/{userId}/edit/password")
    public String editUserPassword(@PathVariable Long userId, Model model) {
        return userService.findUserInfo(model, userId, "user/editUserPasswordPage");
    }

    @PatchMapping("/{userId}/password")
    public String updateUserPassword(@PathVariable Long userId, String newUserPassword) {
        return userService.updateUserPassword(userId, newUserPassword);
    }
}
