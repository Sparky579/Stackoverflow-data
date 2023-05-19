package com.example.datafetcher.service;

import com.example.datafetcher.DTO.QuestionAnswer;
import com.example.datafetcher.model.Answer;
import com.example.datafetcher.model.Comment;
import com.example.datafetcher.model.Question;
import com.example.datafetcher.repository.AnswerRepository;
import com.example.datafetcher.repository.CommentRepository;
import com.example.datafetcher.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class QuestionAnswerService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private CommentRepository commentRepository;

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

    public List<Question> getAnsweredQuestion() {
        List<Question> allQuestions = questionRepository.findAll();

        return allQuestions.stream()
                .filter(Question::isIs_answered) // 根据实际情况，假设Question类有一个名为getAnswer()的方法来获取答案
                .collect(Collectors.toList());
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


    public Map<Integer,Integer> getDistribution(){
        List<Object[]> temp=questionRepository.findAnswerCountDistribution();
        Map<Integer, Integer> statisticsMap = new HashMap<>();
        for (Object[] result : temp) {
            Integer fieldValue = (Integer) result[0];
            Long count = (Long) result[1];
            statisticsMap.put(fieldValue, count.intValue());
        }
        return statisticsMap;
    }






}
