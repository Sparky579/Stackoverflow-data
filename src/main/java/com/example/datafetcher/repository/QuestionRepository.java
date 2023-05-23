package com.example.datafetcher.repository;

import com.example.datafetcher.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT MAX(e.answer_count) FROM Question e")
    Integer findMaxAnswerCount();

    @Query("SELECT Count(e.answer_count) FROM Question e where e.answer_count=0")
    Integer findZeroCount();

    @Query("SELECT e.answer_count, COUNT(e.answer_count) FROM Question e GROUP BY e.answer_count")
    List<Object[]> findAnswerCountDistribution();

    @Query("SELECT COUNT(q) FROM Question q WHERE q.accepted_answer_id > 0")
    Integer findAcceptedAnswerCount();

    List<Question> findByTagsContaining(String tag);

    List<Question> findByTitleIgnoreCaseContaining(String key);

    @Query("SELECT q FROM Question q WHERE q.question_id = ?1")
    Question findByQuestion_id(int id);


    @Query("SELECT COUNT(q) FROM Question q")
    Integer findTotalQuestionCount();


}
