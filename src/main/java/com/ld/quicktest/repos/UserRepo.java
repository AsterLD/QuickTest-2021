package com.ld.quicktest.repos;

import com.ld.quicktest.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/*
 * Репозиторий пользователей, реализован поиск пользователя по логину, по id,
 * поиск пользователей, по части ФИО, по целому или частичному названию отдела,
 * а также поиск всех пользователей добавленных в базу.
 */

public interface UserRepo extends CrudRepository<User, Long> {
    User findUserByUsername(String username);
    User findUserByUserId(Long userId);
    List<User> findUsersByFullNameContains(String fullName);
    List<User> findUsersByDepartmentContains(String department);
    List<User> findAll();
}
