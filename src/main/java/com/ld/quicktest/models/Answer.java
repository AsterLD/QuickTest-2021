package com.ld.quicktest.models;

import javax.persistence.*;

/*
 * Класс Answer, используется для хранения информации по одному ответу на воропрос из тестирования,
 * имеет свзяь многие-к-одному с классом Question
 */

@Entity
@Table(name = "answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long answerId;

    @ManyToOne()
    @JoinColumn(name="question_id")
    private Question question;

    private String answerText;

    private boolean isRightAnswer;

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public boolean isRightAnswer() {
        return isRightAnswer;
    }

    public void setRightAnswer(boolean rightAnswer) {
        isRightAnswer = rightAnswer;
    }
}
