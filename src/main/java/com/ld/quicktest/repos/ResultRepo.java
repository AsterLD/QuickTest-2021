package com.ld.quicktest.repos;

import com.ld.quicktest.models.Result;
import com.ld.quicktest.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ResultRepo extends CrudRepository <Result, Long> {
    List<Result> findResultsByUser(User user);
}
