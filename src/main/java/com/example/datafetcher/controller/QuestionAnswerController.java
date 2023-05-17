package com.example.datafetcher.controller;

import com.example.datafetcher.DTO.QuestionAnswer;
import com.example.datafetcher.service.QuestionAnswerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/java/question/answer_count")
public class QuestionAnswerController {
    private final QuestionAnswerService questionAnswerService;

    public QuestionAnswerController(QuestionAnswerService questionAnswerService) {
        this.questionAnswerService = questionAnswerService;
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


}
