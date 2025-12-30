package com.anthat.quizservice.controller;

import com.anthat.quizservice.model.Quiz;
import com.anthat.quizservice.model.QuizResponseEntity;
import com.anthat.quizservice.model.SubmittedQuiz;
import com.anthat.quizservice.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("quiz")
public class QuizController {

    private final QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<Quiz> createQuiz(@RequestParam String title, @RequestParam String category, @RequestParam int noOfQ) {
        try {
            return new ResponseEntity<>(quizService.createQuiz(title, category, noOfQ), HttpStatus.CREATED);
        } catch (Exception exp) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<QuizResponseEntity> getQuizById(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(quizService.getQuizById(id), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("result")
    public ResponseEntity<Integer> submitQuiz(@RequestBody SubmittedQuiz submittedQuiz) {
        try {
            return new ResponseEntity<>(quizService.evaluteQuiz(submittedQuiz), HttpStatus.OK);
        } catch (Exception exp) {
            return ResponseEntity.badRequest().build();
        }
    }
}
