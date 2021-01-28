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
    User findByUsername(String username);
    User findByUserId(Long userId);
    List<User> findUserByFullNameContains(String fullName);
    List<User> findUserByDepartmentContains(String department);
    List<User> findAll();
}
