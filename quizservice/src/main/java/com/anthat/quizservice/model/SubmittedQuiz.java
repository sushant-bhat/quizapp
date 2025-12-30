package com.anthat.quizservice.model;

import lombok.Data;

import java.util.Map;

@Data
public class SubmittedQuiz {
    private String quizId;

    private Map<Integer, String> answers;
}
