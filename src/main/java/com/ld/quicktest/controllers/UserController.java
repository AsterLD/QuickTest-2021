package com.ld.quicktest.controllers;

import com.ld.quicktest.models.User;
import com.ld.quicktest.repos.UserRepo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    public UserController(PasswordEncoder passwordEncoder, UserRepo userRepo) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }

    @GetMapping
    public String showAllUsers(Model model) {
        model.addAttribute("users", userRepo.findAll());
        return "user/usersList";
    }

    @GetMapping("/search")
    public String findUsers(Model model,
                            @RequestParam (name = "search", defaultValue = "") String search,
                            @RequestParam(name = "searchType") String searchType) {
        switch (searchType) {
            case "fullName":
                model.addAttribute("users", userRepo.findUsersByFullNameContains(search));
                break;
            case "departmentName":
                model.addAttribute("users", userRepo.findUsersByDepartmentContains(search));
                break;
        }
        return "user/usersList";
    }

    @GetMapping("/{userId}")
    public String showUserInfo(Model model, @PathVariable Long userId) {
        model.addAttribute("user", userRepo.findUserByUserId(userId));
        return "user/userInfo";
    }

    @GetMapping("/new")
    public String createNewUser() {
        return "user/newUser";
    }

    @PostMapping
    public String addUser(User user, Model model) {
        User userFromDb = userRepo.findUserByUsername(user.getUsername());
        if (userFromDb != null) {
            model.addAttribute("message", "Пользователь с такими именем уже существует");
            return "user/newUser";
        }
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{userId}/edit")
    public String editUser(@PathVariable Long userId, Model model) {
        model.addAttribute("user", userRepo.findUserByUserId(userId));
        return "user/editUser";
    }

    @PatchMapping("/{userId}")
    public String updateUser(@PathVariable Long userId, User user, Model model) {
        if (userRepo.findUserByUsername(user.getUsername()) != null &&
                !userRepo.findUserByUsername(user.getUsername()).getUserId().equals(userId) ) {
            model.addAttribute("message", "Пользователь с такими именем уже существует");
            return "user/editUser";
        }
        user.setPassword(userRepo.findUserByUserId(userId).getPassword());
        userRepo.save(user);
        return "user/userInfo";
    }

    @GetMapping("/{userId}/edit/password")
    public String editUserPassword(@PathVariable Long userId, Model model) {
        model.addAttribute("user", userRepo.findUserByUserId(userId));
        return "user/editUserPassword";
    }

    @PatchMapping("/{userId}/password")
    public String updateUserPassword(@PathVariable Long userId,  String newUserPassword) {
        User user = userRepo.findUserByUserId(userId);
        user.setPassword(passwordEncoder.encode(newUserPassword));
        userRepo.save(user);
        return "redirect:/users";
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        userRepo.deleteById(userId);
        return "redirect:/users";
    }
}
