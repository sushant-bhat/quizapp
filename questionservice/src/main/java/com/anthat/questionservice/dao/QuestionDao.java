package com.anthat.questionservice.dao;

import com.anthat.questionservice.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {
    List<Question> findAllByCategory(String category);

    @Query(value = "select id from question q where q.category=:category order by random() limit :noOfQ", nativeQuery = true)
    List<Integer> findRandomQuestionsByCategory(String category, int noOfQ);
}
