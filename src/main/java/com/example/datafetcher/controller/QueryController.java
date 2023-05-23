package com.example.datafetcher.controller;

import com.example.datafetcher.model.Question;
import com.example.datafetcher.service.QueryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/query")
public class QueryController {
    private final QueryService queryService;
    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }
    @GetMapping("/questions/search")
    public ResponseEntity<?> searchQuestions
            (@RequestParam(value="tags", required = false) List<String> tags
            ,@RequestParam(value = "keyword", required = false) String keyword2)
            throws JsonProcessingException {
        List<Question> list = queryService.getQuestionsByTagsAndKey(tags, keyword2);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(list);
        return ResponseEntity.ok(json);
    }

}
