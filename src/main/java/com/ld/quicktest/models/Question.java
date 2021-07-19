package com.ld.quicktest.models;

import javax.persistence.*;
import java.util.*;

/*
 * Класс Question, используется для хранения информации по одному вопросу из тестирования,
 * имеет свзяь многие-к-одному с классом Test.
 */

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long questionId;

    @ManyToOne()
    @JoinColumn(name="test_id")
    private Test test;

    @OneToMany(mappedBy = "question", cascade = {CascadeType.ALL})
    private List<Answer> answerList;

    private String questionText;

    private boolean isMultipleAnswerQuestion;

    public Long getQuestionId() {
        return questionId;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }

    public boolean isMultipleAnswerQuestion() {
        return isMultipleAnswerQuestion;
    }

    public void setMultipleAnswerQuestion(boolean multipleAnswerQuestion) {
        isMultipleAnswerQuestion = multipleAnswerQuestion;
    }
}
