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

    public List<Long> findAcceptInterval() {
        List<Question> answeredQuestion = getAnsweredQuestion();
        return answeredQuestion.stream()
                .map(question -> question.getLast_activity_date() - question.getCreation_date()).toList();
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

    public double findAcceptedAnswerPercentage() {
        Integer acceptedAnswerCount = questionRepository.findAcceptedAnswerCount();
        Integer totalQuestionCount = questionRepository.findTotalQuestionCount();
        if (totalQuestionCount == 0) {
            return 0.0;
        }
        return (acceptedAnswerCount * 100.0) / totalQuestionCount;
    }

    public double findAcceptedLessVotePercentage() {
        List<Question> answeredQuestion = getAnsweredQuestion();
        answeredQuestion =
        answeredQuestion.stream().filter(x -> x.getAccepted_answer_id() > 0).toList();
        int val = 0, all = answeredQuestion.size();
        for (Question q : answeredQuestion) {
            List<Answer> answers = answerRepository.findAnswersByQuestionId(q.getQuestion_id());
            Optional<Answer> acceptedAnswer = answerRepository.findById((long) q.getQuestion_id());
            if (acceptedAnswer.isEmpty()) continue;
            Answer answer = acceptedAnswer.get();
            Answer answer1 = answers.stream()
                    .max(Comparator.comparing(Answer::getScore)).get();
            if (answer1.getScore() > answer.getScore()) val += 1;
        }
        return 100.0 * val / all;
    }
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
        return result;
    }
    public Map<String, Integer> getTagCombinationVote() {
        List<Question> questions = questionRepository.findAll();

        return questions.stream()
                .collect(Collectors.groupingBy(
                        question -> {
                            List<String> sortedTags = question.getTags().stream()
                                    .sorted()
                                    .collect(Collectors.toList());
                            return String.join(",", sortedTags);
                        },
                        Collectors.summingInt(Question::getScore)
                ));
    }



    public Map<String, Integer> getTagView() {
        List<Question> questions = questionRepository.findAll();

        return questions.stream()
                .collect(Collectors.groupingBy(
                        question -> {
                            List<String> sortedTags = question.getTags().stream()
                                    .sorted()
                                    .collect(Collectors.toList());
                            return String.join(",", sortedTags);
                        },
                        Collectors.summingInt(Question::getView_count)
                ));
    }
    public List<Integer> getQuestionAnswerCount() {
        List<Question> questions = questionRepository.findAll();
        return questions.stream().map(question -> answerRepository
                .findAnswersByQuestionId(question.getQuestion_id()).size()).toList();
    }
    public List<Integer> getQuestionCommentCount() {
        List<Question> questions = questionRepository.findAll();
        return questions.stream().map(question -> commentRepository
                .findAnswersByQuestionId(question.getQuestion_id()).size()).toList();

    }
    public Map<Integer, Integer> getUserAnswerCount() {
        List<Answer> answers = answerRepository.findAll();

        Map<Integer, Integer> userAnswerCount = new HashMap<>();
        for (Answer answer : answers) {
            int userId = answer.getOwner().getUserId();
            userAnswerCount.put(userId, userAnswerCount.getOrDefault(userId, 0) + 1);
        }

        return userAnswerCount;
    }

    public Map<Integer, Integer> getUserCommentCount() {
        List<Comment> answers = commentRepository.findAll();

        Map<Integer, Integer> userCommentCount = new HashMap<>();
        for (Comment answer : answers) {
            int userId = answer.getOwner().getUserId();
            userCommentCount.put(userId, userCommentCount.getOrDefault(userId, 0) + 1);
        }

        return userCommentCount;
    }

    public Map<Integer, Integer> mergeUserCounts(Map<Integer, Integer> answerCountMap, Map<Integer, Integer> commentCountMap) {
        return Stream.of(answerCountMap, commentCountMap)
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        Integer::sum
                ));
    }





}
