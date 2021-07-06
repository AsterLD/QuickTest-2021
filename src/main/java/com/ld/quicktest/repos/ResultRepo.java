package com.ld.quicktest.repos;

import com.ld.quicktest.models.Result;
import com.ld.quicktest.models.Test;
import com.ld.quicktest.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ResultRepo extends PagingAndSortingRepository<Result, Long> {
    Page<Result> findResultsByUser(User user, Pageable pageable);
    Page<Result> findResultsByUserIn(List<User> users, Pageable pageable);
    Page<Result> findResultsByTestIn(List<Test> test, Pageable pageable);
}
