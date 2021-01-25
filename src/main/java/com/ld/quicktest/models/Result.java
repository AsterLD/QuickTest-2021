package com.ld.quicktest.models;

import javax.persistence.*;

/*
 * Класс Result, используется для хранения одного результата прохождения тестирования пользователем,
 * имеет связь многие-к-одному с классом Test, и связь многие-к-одному с классом User
 */

@Entity
@Table(name = "results")
public class Result {

    @Id
    @Column(name = "result_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long resultId;

    @ManyToOne()
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne()
    @JoinColumn(name="test_id")
    private Test test;

    @Column(name = "number_of_questions")
    private int numberOfQuestion;

    @Column(name = "number_of_correct_answers")
    private int numberOfCorrectAnswers;

    @Column(name = "percentage_of_correct_answers", precision = 2)
    private double percentageOfCorrectAnswers;

    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    public int getNumberOfQuestion() {
        return numberOfQuestion;
    }

    public void setNumberOfQuestion(int numberOfQuestion) {
        this.numberOfQuestion = numberOfQuestion;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public int getNumberOfCorrectAnswers() {
        return numberOfCorrectAnswers;
    }

    public void setNumberOfCorrectAnswers(int numberOfCorrectAnswers) {
        this.numberOfCorrectAnswers = numberOfCorrectAnswers;
    }

    public double getPercentageOfCorrectAnswers() {
        return percentageOfCorrectAnswers;
    }

    public void setPercentageOfCorrectAnswers(double percentageOfCorrectAnswers) {
        this.percentageOfCorrectAnswers = percentageOfCorrectAnswers;
    }
}
