package com.anthat.quizservice.service;

import com.anthat.quizservice.dao.QuizDao;
import com.anthat.quizservice.model.Question;
import com.anthat.quizservice.model.QuestionResponseEntity;
import com.anthat.quizservice.model.Quiz;
import com.anthat.quizservice.model.QuizResponseEntity;
import com.anthat.quizservice.model.SubmittedQuiz;
import com.anthat.quizservice.registry.QuestionInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizDao quizDao;

    private final QuestionInterface questionInterface;

    public Quiz createQuiz(String title, String category, int noOfQ) {
        List<Integer> quizQuestions = questionInterface.getRandomQuestions(category, noOfQ).getBody(); // Needs to come from question service

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(quizQuestions);
        quizDao.save(quiz);

        return quiz;
    }

    public QuizResponseEntity getQuizById(Integer id) {
        Quiz foundQuiz = quizDao.findById(id).orElseThrow();

        QuizResponseEntity quizResponseEntity = new QuizResponseEntity();
        quizResponseEntity.setId(id);
        quizResponseEntity.setTitle(foundQuiz.getTitle());

        // Make a call to question service to get actual questions
        List<Integer> quizQuestionIds = foundQuiz.getQuestionIds();
        List<Question> actualQuestions = questionInterface.getQuestions(quizQuestionIds).getBody();

        List<QuestionResponseEntity> quizQuestionResponseEntities = new ArrayList<>();
        for (Question question : actualQuestions) {
            quizQuestionResponseEntities.add(new QuestionResponseEntity(
                    question.getId(),
                    question.getQuestionTitle(),
                    question.getOption1(),
                    question.getOption2(),
                    question.getOption3(),
                    question.getOption4()
            ));
        }
        quizResponseEntity.setQuestions(quizQuestionResponseEntities);

        return quizResponseEntity;
    }

    public Integer evaluteQuiz(SubmittedQuiz submittedQuiz) {
        // Get the question IDs and get the right answers for them from question service
        List<Integer> quizQuestionIds = new ArrayList<>(submittedQuiz.getAnswers().keySet());

        List<Question> actualQuestions = questionInterface.getQuestions(quizQuestionIds).getBody(); // Get from question service
        Map<Integer, String> answerKey = new HashMap<>();
        for (Question question : actualQuestions) {
            answerKey.put(question.getId(), question.getRightAnswer());
        }

        // Match the right answers with the submitted answers and calculate score
        int score = 0;
        for (Map.Entry<Integer, String> entry : submittedQuiz.getAnswers().entrySet()) {
            Integer questionId = entry.getKey();
            String answer = entry.getValue();

            if (answer.equalsIgnoreCase(answerKey.get(questionId))) {
                score++;
            }
        }

        return score;
    }
}
