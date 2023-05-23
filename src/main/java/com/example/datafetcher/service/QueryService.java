package com.example.datafetcher.service;

import com.example.datafetcher.model.Question;
import com.example.datafetcher.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryService {
    @Autowired
    private QuestionRepository questionRepository;
    public List<Question> getQuestionsByTagsAndKey(List<String> tags, String key) {
        List<Question> questionList = (key == null) ?
                questionRepository.findAll() : questionRepository.findByTitleIgnoreCaseContaining(key);
        if (tags != null && ! tags.isEmpty())
        questionList.removeIf(question -> !question.getTags().containsAll(tags));
        return questionList;
    }
}
