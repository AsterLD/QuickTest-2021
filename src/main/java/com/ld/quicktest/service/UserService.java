package com.ld.quicktest.service;

import com.ld.quicktest.models.User;
import com.ld.quicktest.repos.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    public UserService(PasswordEncoder passwordEncoder, UserRepo userRepo) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }

    public String findAllUsers(Model model) {
        model.addAttribute("users", userRepo.findAll());
        return "user/usersList";
    }

    public String findUsers(Model model, String search, String searchType) {
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

    public String findUserInfo(Model model, Long userId, String page) {
        model.addAttribute("user", userRepo.findUserByUserId(userId));
        return page;
    }

    public String addUser(Model model, User user) {
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

    public String updateUser(Model model, User user, Long userId) {
        if (userRepo.findUserByUsername(user.getUsername()) != null &&
                !userRepo.findUserByUsername(user.getUsername()).getUserId().equals(userId) ) {
            model.addAttribute("message", "Пользователь с такими именем уже существует");
            return "user/editUser";
        }
        user.setPassword(userRepo.findUserByUserId(userId).getPassword());
        userRepo.save(user);
        return "user/userInfo";
    }

    public String updateUserPassword(Long userId, String newUserPassword) {
        User user = userRepo.findUserByUserId(userId);
        user.setPassword(passwordEncoder.encode(newUserPassword));
        userRepo.save(user);
        return "redirect:/users";
    }

    public String deleteUser(Long userId) {
        userRepo.deleteById(userId);
        return "redirect:/users";
    }
}
