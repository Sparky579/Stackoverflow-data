package com.example.datafetcher.service;

import com.example.datafetcher.DTO.QuestionAnswer;
import com.example.datafetcher.model.Question;
import com.example.datafetcher.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class QuestionAnswerService {
    @Autowired
    private QuestionRepository questionRepository;

    public List<QuestionAnswer> getQuestionAnswer(){
        List<Question> result=questionRepository.findAll();
        List<QuestionAnswer> answers=new ArrayList<>();
        Iterator<Question> iterator = result.iterator();
        while (iterator.hasNext()) {
            Question item = iterator.next();
            answers.add(new QuestionAnswer(item.getQuestion_id(),item.getAnswer_count()));
        }
        return answers;
    }

    public float getAverage(){
        List<Question> result=questionRepository.findAll();
        float answer=0;
        Iterator<Question> iterator = result.iterator();
        while (iterator.hasNext()) {
            Question item = iterator.next();
            answer=answer+item.getAnswer_count();
        }
        answer=answer/result.size();
        return  answer;
    }

    public int getMax(){
        return questionRepository.findMaxAnswerCount();
    }

    public int getZero(){
        return  questionRepository.findZeroCount();
    }
}
