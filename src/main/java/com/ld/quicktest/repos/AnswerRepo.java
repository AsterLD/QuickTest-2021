package com.ld.quicktest.repos;

import com.ld.quicktest.models.Answer;
import org.springframework.data.repository.CrudRepository;

public interface AnswerRepo extends CrudRepository<Answer, Long> {
    Answer findAnswerByAnswerId(Long answerId);
}