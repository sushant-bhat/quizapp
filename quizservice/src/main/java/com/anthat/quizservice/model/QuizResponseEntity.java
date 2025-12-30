package com.anthat.quizservice.model;

import lombok.Data;

import java.util.List;

@Data
public class QuizResponseEntity {
    private Integer id;

    private String title;

    private List<QuestionResponseEntity> questions;
}
