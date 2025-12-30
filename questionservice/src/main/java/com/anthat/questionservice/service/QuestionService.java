package com.anthat.questionservice.service;

import com.anthat.questionservice.dao.QuestionDao;
import com.anthat.questionservice.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionDao questionDao;

    public List<Question> getAllQuestions() {
        return questionDao.findAll();
    }

    public List<Question> getQuestionsByCategory(String cat) {
        return questionDao.findAllByCategory(cat);
    }

    public void addQuestion(Question question) {
        questionDao.save(question);
    }

    public List<Integer> generateRandomQuestions(String category, Integer noOfQuestions) {
        return questionDao.findRandomQuestionsByCategory(category, noOfQuestions);
    }

    public List<Question> getQuestions(List<Integer> questionIds) {
        return questionDao.findAllById(questionIds);
    }
}
