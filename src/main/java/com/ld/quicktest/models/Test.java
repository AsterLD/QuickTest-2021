package com.ld.quicktest.models;

import javax.persistence.*;
import java.util.List;

/*
 * Класс Test, используется для хранения информации о тестировании,
 * имеет свзяь один к многим с классом Result (таблица results),
 * а также связь один-ко-многим с классом Question (таблица questions).
 * List<Question> questionList - для хранения вопросов тестирования,
 * List<Result> resultList - для хранения результатов прохождения тестирования
 */

@Entity
@Table(name = "tests")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testId;

    @OneToMany(mappedBy = "test", cascade = {CascadeType.ALL})
    private List<Result> resultList;

    @OneToMany(mappedBy = "test", cascade = {CascadeType.ALL})
    private List<Question> questionList;

    private String testName;

    private boolean isActive;

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public List<Result> getResultList() {
        return resultList;
    }

    public void setResultList(List<Result> resultList) {
        this.resultList = resultList;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
