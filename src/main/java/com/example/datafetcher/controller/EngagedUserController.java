package com.example.datafetcher.controller;

import com.example.datafetcher.repository.APIsRepository;
import com.example.datafetcher.service.APIService;
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
    private final APIService apiService;

    public EngagedUserController(EngagedUserService engagedUserService, APIService apisRepository) {
        this.engagedUserService = engagedUserService;
        this.apiService = apisRepository;
    }

    @GetMapping("/user/answer/count")
    public Map<Integer, Integer> getUserAnswerCount() {
    return engagedUserService.getUserAnswerCount();
    }

    @GetMapping("/comment/count")
    public List<Integer> getQuestionCommentCount() {
        return engagedUserService.getQuestionCommentCount();
    }
    @GetMapping("/merge-count")
    public List<Integer> mergeCounts() {
        return engagedUserService.getQuestionMergeCount();
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

    @GetMapping("/api-distribution")
    public Map<String, Integer> getApiDistribution() {
        return apiService.getAPIsCountMap();
    }





}
