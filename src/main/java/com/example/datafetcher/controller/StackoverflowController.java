package com.example.datafetcher.controller;

import com.example.datafetcher.service.StackoverflowService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StackoverflowController {

    private final StackoverflowService stackoverflowService;

    public StackoverflowController(StackoverflowService stackoverflowService) {
        this.stackoverflowService = stackoverflowService;
    }

    @GetMapping("/java-questions")
    public String getJavaQuestions() {
        return stackoverflowService.fetchJavaQuestions();
    }

    @GetMapping("/{tag}-questions")
    public String getQuestionsByTag(@PathVariable String tag) {
        return stackoverflowService.fetchQuestionsByTag(tag);
    }

    @GetMapping("/auto-fetch")
    public String autoFetch() {
        stackoverflowService.autoFetch(12);//12 * 50
        return "Auto fetch completed";
    }
}

