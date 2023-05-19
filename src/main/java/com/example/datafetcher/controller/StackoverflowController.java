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

    @GetMapping("/java-answers")
    public String getJavaAnswers() {
        return stackoverflowService.fetchJavaAnswers();
    }

    @GetMapping("/{tag}-answers")
    public String getAnswersByTag(@PathVariable String tag) {
        return stackoverflowService.fetchJavaAnswersByTag(tag);
    }

    @GetMapping("/auto-fetch-1")
    public String autoFetch1() {
        stackoverflowService.autoFetchAnswer(1, 50);//12 * 50
        return "Auto fetch completed";
    }

    @GetMapping("/auto-fetch-2")
    public String autoFetch2() {
        stackoverflowService.autoFetchQuestion(1, 50);
        return "Auto fetch completed";
    }


}

