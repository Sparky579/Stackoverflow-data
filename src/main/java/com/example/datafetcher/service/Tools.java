package com.example.datafetcher.service;

import com.example.datafetcher.model.*;
import com.example.datafetcher.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class Tools {
    public static<T>
    T parseJson(String json, Class<T> responseType) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // 解析JSON数据
            T response = objectMapper.readValue(json, responseType);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveAnswerResponse(String json,
                            AnswerRepository answerRepository,
                            AnswerResponseRepository answerResponseRepository,
                            JpaRepository<Owner, Long> ownerRepository) {
        AnswerResponse answerResponse = parseJson(json, AnswerResponse.class);
        List<Answer> items = answerResponse.getItems();
        for (Answer item : items) {
            Owner owner = item.getOwner();
            if (ownerRepository.existsById((long) owner.getUserId())){
                ownerRepository.save(owner);
            }
            answerRepository.save(item);
        }
    }

    static void saveQuestionResponse(String json,
                              QuestionRepository questionRepository,
                              QuestionResponseRepository questionResponseRepository,
                              JpaRepository<Owner, Long> ownerRepository) {
        QuestionResponse questionResponse = parseJson(json, QuestionResponse.class);
        List<Question> items = questionResponse.getItems();
        for (Question item : items) {
            Owner owner = item.getOwner();
            if (owner != null)  ownerRepository.save(owner);
            questionRepository.save(item);
        }
        questionResponseRepository.save(questionResponse);
    }

    static void setCommentResponse(String json,
                                   CommentRepository commentRepository,
                                   JpaRepository<Owner, Long> ownerRepository,
                                   int questionId) {
        CommentResponse commentResponse = parseJson(json, CommentResponse.class);
        List<Comment> items = commentResponse.getItems();
        for (Comment item : items) {
            Owner owner = item.getOwner();
            item.setQuestion_id(questionId);
            if (ownerRepository.existsById((long) owner.getUserId())){
                ownerRepository.save(owner);
            }
            commentRepository.save(item);
        }
    }



}

