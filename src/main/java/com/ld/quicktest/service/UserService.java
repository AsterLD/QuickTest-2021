package com.ld.quicktest.service;

import com.ld.quicktest.models.Role;
import com.ld.quicktest.models.User;
import com.ld.quicktest.repos.UserRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import java.util.*;
import java.util.stream.Collectors;
import static com.ld.quicktest.util.PageListGenerator.generateAvailablePageList;

/*
 * Класс UserService, хранит в себе логику, для работы UserController.
 * loadUserByUsername - Возращает пользователя, если такого пользователя не существует, выбрасывает исключение,
 * findAllUsers - Отображает список всех пользователей, на запрошенной пользователем странице,
 * findUsers - Отображает пользователя, соответствующего критериям поиска, (поиск осуществляется по ФИО или по Отделу),
 * findUserInfo - Отображает конкретного пользователя, поиск осуществляется по id,
 * addUser - Если пользователя с таким username нет, ему присваивается роль USER и он сохраняется в БД,
 * updateUser - Обновляет данные пользователя, с помощью слияния данных с формы, с данными из БД,
 * updateUserPassword - Обновляет пароль пользователя,
 * deleteUser - Удаляет пользователя из БД.
 */

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

    public String findAllUsers(Model model, int pageNumber) {
        Page<User> usersPage = userRepo.findAll(PageRequest.of(pageNumber - 1, 5));
        generateAvailablePageList(model, pageNumber, usersPage);
        return "user/usersListPage";
    }

    public String findUsers(Model model, String search, String searchType, int pageNumber) {
        Page<User> usersPage;
        switch (searchType) {
            case "name":
                usersPage = userRepo.findUsersByFullNameContains(search, PageRequest.of(pageNumber - 1, 5));
                generateAvailablePageList(model, pageNumber, usersPage);
                break;
            case "department":
                usersPage = userRepo.findUsersByDepartmentContains(search, PageRequest.of(pageNumber - 1, 5));
                generateAvailablePageList(model, pageNumber, usersPage);
                break;
        }
        model.addAttribute("search", search);
        model.addAttribute("type", searchType);
        return "user/usersListPage";
    }

    public String findUserInfo(Model model, Long userId, String page) {
        model.addAttribute("user", userRepo.findUserByUserId(userId));
        return page;
    }

    public String addUser(Model model, User user) {
        User userFromDb = userRepo.findUserByUsername(user.getUsername());
        if (userFromDb != null) {
            model.addAttribute("message", "Пользователь с такими именем уже существует");
            return "user/newUserPage";
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

    public String updateUser(Model model, Map<String, String> form, User user) {
            User userFromDb = userRepo.findUserByUserId(user.getUserId());
            User userFromUsername = userRepo.findUserByUsername(user.getUsername());
            if (userFromUsername != null && !userFromDb.getUserId().equals(userFromUsername.getUserId())) {
                model.addAttribute("message", "Пользователь с таким именем уже существует!");
                model.addAttribute("user", userFromDb);
                return "user/editUserPage";
            }
            userFromDb.setEnabled(form.containsKey("isEnabled"));
            userFromDb.userMerge(user);
            userFromDb.getRoles().clear();
            Set<String> userRoles = form.keySet();
            userRoles.retainAll(Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet()));
            userRoles.forEach(s -> userFromDb.getRoles().add(Role.valueOf(s)));
            userRepo.save(userFromDb);
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
