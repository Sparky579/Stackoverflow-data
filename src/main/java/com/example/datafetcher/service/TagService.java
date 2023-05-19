package com.example.datafetcher.service;

import com.example.datafetcher.model.Question;
import com.example.datafetcher.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
@Service
public class TagService {
    @Autowired
    private QuestionRepository questionRepository;


    public Map<String, Integer> getTagCount() {
        List<Question> questions = questionRepository.findAll();
        Map<String ,Integer> result = new HashMap<>();
        for (Question q : questions) {
            for (String tag : q.getTags()) {
                if (!result.containsKey(tag)) {
                    result.put(tag, 1);
                }
                else result.put(tag, result.get(tag) + 1);
            }
        }
        Map<String, Integer> sortedResult = result.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        return sortedResult;
    }
    public Map<String, Integer> getTagVote() {
        List<Question> questions = questionRepository.findAll();

        Map<String, Integer> result= questions.stream()
                .flatMap(question -> question.getTags().stream())
                .collect(Collectors.groupingBy(
                        tag -> tag,
                        Collectors.summingInt(tag -> questions.stream()
                                .filter(question -> question.getTags().contains(tag))
                                .mapToInt(Question::getScore)
                                .sum()
                        )
                ));
        Map<String, Integer> sortedResult = result.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        return sortedResult;
    }
    public Map<String, Integer> getTagView() {
        List<Question> questions = questionRepository.findAll();

        Map<String, Integer> result=  questions.stream()
                .collect(Collectors.groupingBy(
                        question -> {
                            List<String> sortedTags = question.getTags().stream()
                                    .sorted()
                                    .collect(Collectors.toList());
                            return String.join(",", sortedTags);
                        },
                        Collectors.summingInt(Question::getView_count)
                ));
        Map<String, Integer> sortedResult = result.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        return sortedResult;
    }

    public Map<String, Integer> getTagCombinationVote() {
        List<Question> questions = questionRepository.findAll();

        Map<String, Integer> result=questions.stream()
                .collect(Collectors.groupingBy(
                        question -> {
                            List<String> sortedTags = question.getTags().stream()
                                    .sorted()
                                    .collect(Collectors.toList());
                            return String.join(",", sortedTags);
                        },
                        Collectors.summingInt(Question::getScore)
                ));
        Map<String, Integer> sortedResult = result.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        return sortedResult;
    }

}
