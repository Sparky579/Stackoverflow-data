package com.example.datafetcher.controller;

import com.example.datafetcher.service.EngagedUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/question")
public class EngagedUserController {

    private final EngagedUserService engagedUserService;

    public EngagedUserController(EngagedUserService engagedUserService) {
        this.engagedUserService = engagedUserService;
    }

    @GetMapping("/user/answer/count")
    public Map<Integer, Integer> getUserAnswerCount() {
    return engagedUserService.getUserAnswerCount();
    }

    @GetMapping("/comment/count")
    public List<Integer> getQuestionCommentCount() {
        return engagedUserService.getQuestionCommentCount();
    }

    @GetMapping("/answer/count")
    public List<Integer> getQuestionAnswerCount() {
        return engagedUserService.getQuestionAnswerCount();
    }

    @GetMapping("/user/comment/count")
    public Map<Integer, Integer> getUserCommentCount() {
        return engagedUserService.getUserCommentCount();
    }

    @GetMapping("/user/engage")
    public Map<Integer, Integer> mergeUserCounts() {
        return engagedUserService.mergeUserCounts(getUserAnswerCount(),getUserCommentCount());
    }






}
