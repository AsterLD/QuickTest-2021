package com.ld.quicktest.service;

import com.ld.quicktest.models.Role;
import com.ld.quicktest.models.User;
import com.ld.quicktest.repos.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    public UserService(PasswordEncoder passwordEncoder, UserRepo userRepo) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
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
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpire(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return "redirect:/users";
    }

    public String updateUser(String username, Map<String, String> form, User user, Model model) {
            User userFromDb = userRepo.findUserByUsername(username);
            if (userFromDb != null && !user.getUserId().equals(userFromDb.getUserId())) {
                model.addAttribute("message", "Пользователь с таким именем уже существует!");
                model.addAttribute("user", user);
                return "user/editUser";
            }
            user.setEnabled(form.containsKey("isEnabled"));
            user.setUsername(username);
            user.getRoles().clear();
            Set<String> userRoles = form.keySet();
            userRoles.retainAll(Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet()));
            userRoles.forEach(s -> user.getRoles().add(Role.valueOf(s)));
            userRepo.save(user);
        return "redirect:/users";
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
