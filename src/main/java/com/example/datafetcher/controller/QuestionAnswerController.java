package com.example.datafetcher.controller;

import com.example.datafetcher.DTO.QuestionAnswer;
import com.example.datafetcher.model.Question;
import com.example.datafetcher.service.AcceptAnswerService;
import com.example.datafetcher.service.QuestionAnswerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/java/question/answer_count")
public class QuestionAnswerController {
    private final QuestionAnswerService questionAnswerService;
    private final AcceptAnswerService acceptAnswerService;

    public QuestionAnswerController(QuestionAnswerService questionAnswerService,AcceptAnswerService acceptAnswerService) {
        this.questionAnswerService = questionAnswerService;
        this.acceptAnswerService=acceptAnswerService;
    }

    @GetMapping("/all")
    public List<QuestionAnswer> getAll() {
        List<QuestionAnswer> result =questionAnswerService.getQuestionAnswer();
        return result;
    }

    @GetMapping("/average")
    public float getAverage() {
        return questionAnswerService.getAverage();
    }

    @GetMapping("/max")
    public int getMax() {
        return questionAnswerService.getMax();
    }


    @GetMapping("/zero")
    public int getZero() {
        return questionAnswerService.getZero();
    }

    @GetMapping("/distribution")
    public Map<Integer,Integer> getDistribution() {
        return questionAnswerService.getDistribution();
    }



}
