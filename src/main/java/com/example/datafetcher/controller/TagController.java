package com.example.datafetcher.controller;

import com.example.datafetcher.service.TagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/java/question/tags")
public class TagController {

    private final TagService tagService;


    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/count")
    public Map<String, Integer> getTagCount() {
        return tagService.getTagCount();
    }

    @GetMapping("/frequent_with_java")
    public Map<String, Integer> getTag_frequent_with_java() {
        Map<String, Integer> result=tagService.getTagCount();
        result.remove("java");
        return result;
    }

    @GetMapping("/mostvote")
    public Map<String, Integer> getTagVote() {
        return tagService.getTagVote();
    }

    @GetMapping("/combine/mostvote")
    public Map<String, Integer> getCombineTagVote() {
        return tagService.getTagCombinationVote();
    }

    @GetMapping("/mostview")
    public Map<String, Integer> getTagView() {
        return tagService.getTagView();
    }
}
