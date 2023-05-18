package com.example.datafetcher.repository;

import com.example.datafetcher.model.Answer;
import com.example.datafetcher.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "SELECT * FROM comment WHERE question_id = :questionId", nativeQuery = true)
    List<Answer> findAnswersByQuestionId(int questionId);

}
