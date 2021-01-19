package com.ld.quicktest.repos;

import com.ld.quicktest.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepo extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User findByUserId(Long userId);
    List<User> findAll();

}
