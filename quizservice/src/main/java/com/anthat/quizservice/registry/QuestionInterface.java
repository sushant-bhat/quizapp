package com.anthat.quizservice.registry;

import com.anthat.quizservice.model.Question;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("question-service")
public interface QuestionInterface {
    @GetMapping("question/random")
    ResponseEntity<List<Integer>> getRandomQuestions(@RequestParam String category, @RequestParam Integer noOfQuestions);

    @PostMapping("question")
    ResponseEntity<List<Question>> getQuestions(@RequestBody List<Integer> questionIds);
}
