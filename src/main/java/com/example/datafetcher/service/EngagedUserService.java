package com.example.datafetcher.service;

import com.example.datafetcher.model.Answer;
import com.example.datafetcher.model.Comment;
import com.example.datafetcher.model.Question;
import com.example.datafetcher.repository.AnswerRepository;
import com.example.datafetcher.repository.CommentRepository;
import com.example.datafetcher.repository.QuestionRepository;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Service
public class EngagedUserService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private CommentRepository commentRepository;

    public List<Integer> getQuestionAnswerCount() {
        List<Question> questions = questionRepository.findAll();
        return questions.stream().map(question -> answerRepository
                .findAnswersByQuestionId(question.getQuestion_id()).size()).toList();
    }
    public List<Integer> getQuestionCommentCount() {
        List<Question> questions = questionRepository.findAll();
        return questions.stream().map(question -> commentRepository
                .findCommentsByQuestionId(question.getQuestion_id()).size()).toList();
    }
    public List<Integer> getQuestionMergeCount() {
        List<Question> questions = questionRepository.findAll();
        return questions.stream().map(question -> commentRepository
                .findCommentsByQuestionId(question.getQuestion_id()).size()+answerRepository
                .findAnswersByQuestionId(question.getQuestion_id()).size()).toList();
    }
    public Map<Integer, Integer> getUserAnswerCount() {
        List<Answer> answers = answerRepository.findAll();

        Map<Integer, Integer> userAnswerCount = new HashMap<>();
        for (Answer answer : answers) {
            int userId = answer.getOwner().getUserId();
            userAnswerCount.put(userId, userAnswerCount.getOrDefault(userId, 0) + 1);
        }

        return userAnswerCount;
    }

    public Map<Integer, Integer> getUserCommentCount() {
        List<Comment> answers = commentRepository.findAll();

        Map<Integer, Integer> userCommentCount = new HashMap<>();
        for (Comment answer : answers) {
            int userId = answer.getOwner().getUserId();
            userCommentCount.put(userId, userCommentCount.getOrDefault(userId, 0) + 1);
        }

        return userCommentCount;
    }

    public Map<String, Integer> mergeUserCounts(Map<Integer, Integer> answerCountMap, Map<Integer, Integer> commentCountMap) {
        Map<Integer, Integer> mergedMap = Stream.of(answerCountMap, commentCountMap)
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        Integer::sum,
                        LinkedHashMap::new
                ));

        return mergedMap.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        entry -> String.format("User %d", entry.getKey()),
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }




}
