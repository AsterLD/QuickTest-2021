package com.ld.quicktest.controllers;

import com.ld.quicktest.models.User;
import com.ld.quicktest.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showAllUsers(Model model) {
        return userService.findAllUsers(model);
    }

    @GetMapping("/search")
    public String findUsers(Model model,
                            @RequestParam (name = "search", defaultValue = "") String search,
                            @RequestParam(name = "searchType") String searchType) {
        return userService.findUsers(model, search, searchType);
    }

    @GetMapping("/{userId}")
    public String showUserInfo(Model model, @PathVariable Long userId) {
        return userService.findUserInfo(model, userId, "user/userInfo");
    }

    @GetMapping("/new")
    public String createNewUser() {
        return "user/newUser";
    }

    @PostMapping
    public String addUser(Model model, User user) {
        return userService.addUser(model, user);
    }

    @GetMapping("/{userId}/edit")
    public String editUser(Model model, @PathVariable Long userId) {
        return userService.findUserInfo(model, userId, "user/editUser");
    }

    @PatchMapping("/{userId}")
    public String updateUser(Model model, @PathVariable Long userId, User user) {
        return userService.updateUser(model, user, userId);
    }

    @GetMapping("/{userId}/edit/password")
    public String editUserPassword(Model model, @PathVariable Long userId) {
        return userService.findUserInfo(model, userId, "user/editUserPassword");
    }

    @PatchMapping("/{userId}/password")
    public String updateUserPassword(@PathVariable Long userId, String newUserPassword) {
        return userService.updateUserPassword(userId, newUserPassword);
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }
}
