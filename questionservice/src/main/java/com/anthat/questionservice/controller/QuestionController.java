package com.anthat.questionservice.controller;

import com.anthat.questionservice.model.Question;
import com.anthat.questionservice.service.QuestionService;
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

import java.util.List;

@RestController
@RequestMapping("question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<List<Question>> getQuestions(@RequestBody List<Integer> questionIds) {
        try {
            return new ResponseEntity<>(questionService.getQuestions(questionIds), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("all")
    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionService.getAllQuestions(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/random")
    public ResponseEntity<List<Integer>> getRandomQuestions(@RequestParam String category, @RequestParam Integer noOfQuestions) {
        try {
            return new ResponseEntity<>(questionService.generateRandomQuestions(category, noOfQuestions), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("category/{cat}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String cat) {
        try {
            return new ResponseEntity<>(questionService.getQuestionsByCategory(cat), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("new")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        try {
            questionService.addQuestion(question);
            return new ResponseEntity<>("Question added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
