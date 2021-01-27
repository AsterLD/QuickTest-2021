package com.ld.quicktest.repos;

import com.ld.quicktest.models.Question;
import org.springframework.data.repository.CrudRepository;

/*
 * Репозиторий вопросов, реализован поиск вопросов по id.
 */

public interface QuestionRepo extends CrudRepository<Question, Long> {
    Question findQuestionByQuestionId(Long questionId);
}
