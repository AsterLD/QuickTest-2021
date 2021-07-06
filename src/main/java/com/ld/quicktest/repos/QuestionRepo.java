package com.ld.quicktest.repos;

import com.ld.quicktest.models.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepo extends CrudRepository<Question, Long> {
    Question findQuestionByQuestionId(Long questionId);
}
