package com.example.datafetcher.controller;

import com.example.datafetcher.model.Question;
import com.example.datafetcher.service.APIService;
import com.example.datafetcher.service.QueryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/query")
public class QueryController {
    private final QueryService queryService;
    private final APIService apiService;
    private final ObjectMapper mapper = new ObjectMapper();
    public QueryController(QueryService queryService, APIService apiService) {
        this.queryService = queryService;
        this.apiService = apiService;
    }
    @GetMapping("/questions/search")
    public ResponseEntity<?> searchQuestions
            (@RequestParam(value="tags", required = false) List<String> tags
            ,@RequestParam(value = "keyword", required = false) String keyword2)
            throws JsonProcessingException {
        List<Question> list = queryService.getQuestionsByTagsAndKey(tags, keyword2);
        String json = mapper.writeValueAsString(list);
        return ResponseEntity.ok(json);
    }
    @GetMapping("/api-distribution")
    public ResponseEntity<?> getApiDistribution() throws JsonProcessingException {
        String json = mapper.writeValueAsString(apiService.getAPIsCountMap());
        return ResponseEntity.ok(json);
    }
    @GetMapping("/accepted-answer")
    public ResponseEntity<?> getAcceptedAnswer(
            @RequestParam("question") String questionId
    ) throws JsonProcessingException {
        //if it is not a number, it will throw an exception
        if (!questionId.matches("\\d+")) {
            return ResponseEntity.badRequest().body("Question id must be a number");
        }
        int id = Integer.parseInt(questionId);
        String json = mapper.writeValueAsString(queryService.findAcceptAnswer(id));
        return ResponseEntity.ok(json);
    }

    @GetMapping("/answers")
    public ResponseEntity<?> getAnswers(
            @RequestParam("question") String questionId
    ) throws JsonProcessingException {
        int id = Integer.parseInt(questionId);
        String json = mapper.writeValueAsString(queryService.findAnswers(id));
        return ResponseEntity.ok(json);
    }


}
