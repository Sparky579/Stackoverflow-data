package com.example.datafetcher.controller;

import com.example.datafetcher.service.AcceptAnswerService;
import com.example.datafetcher.service.QuestionAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/java/question")
public class AcceptAnswerController {


    private final AcceptAnswerService acceptAnswerService;



    public AcceptAnswerController(AcceptAnswerService acceptAnswerService) {
        this.acceptAnswerService = acceptAnswerService;
    }

    @GetMapping("/accept-rate")
    public double findAcceptedAnswerPercentage() {
        return acceptAnswerService.findAcceptedAnswerPercentage();
    }

    @GetMapping("/accept-interval")
    public String findAcceptInterval() {
        return acceptAnswerService.findAcceptInterval().toString();
    }

    @GetMapping("/acceptedLessVotePercentage")
    public double findAcceptedLessVotePercentage(){
        return acceptAnswerService.findAcceptedLessVotePercentage();
    }
}
