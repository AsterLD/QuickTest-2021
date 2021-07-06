package com.ld.quicktest.repos;

import com.ld.quicktest.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepo extends PagingAndSortingRepository<User, Long> {
    User findUserByUsername(String username);
    User findUserByUserId(Long userId);
    List<User> findUsersByFullNameContains(String fullName);
    List<User> findUsersByDepartmentContains(String department);
    Page<User> findUsersByFullNameContains(String fullName, Pageable pageable);
    Page<User> findUsersByDepartmentContains(String department, Pageable pageable);
    Page<User> findAll(Pageable pageable);
}
