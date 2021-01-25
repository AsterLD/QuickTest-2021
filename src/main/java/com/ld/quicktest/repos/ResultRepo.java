package com.ld.quicktest.repos;

import com.ld.quicktest.models.Result;
import com.ld.quicktest.models.Test;
import com.ld.quicktest.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/*
 * Репозиторий результатов, реализован поиск результатов тестирования по логину пользователя,
 * поиск результатов по нескольким пользователям, поиск результатов по нескольким тестированиям
 */

public interface ResultRepo extends CrudRepository <Result, Long> {
    List<Result> findResultsByUser(User user);
    List<Result> findResultsByUserIn(List<User> users);
    List<Result> findResultsByTestIn(List<Test> test);

}
