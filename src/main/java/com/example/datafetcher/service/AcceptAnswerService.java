package com.example.datafetcher.service;

import com.example.datafetcher.model.Answer;
import com.example.datafetcher.model.Question;
import com.example.datafetcher.repository.AnswerRepository;
import com.example.datafetcher.repository.CommentRepository;
import com.example.datafetcher.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class AcceptAnswerService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    public List<Question> getAnsweredQuestion() {
        List<Question> allQuestions = questionRepository.findAll();

        return allQuestions.stream()
                .filter(Question::isIs_answered) // 根据实际情况，假设Question类有一个名为getAnswer()的方法来获取答案
                .collect(Collectors.toList());
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
            List<Answer> acceptedAnswer = answerRepository.findById(q.getQuestion_id());
            if (acceptedAnswer.isEmpty()) continue;
            Answer answer = acceptedAnswer.get(0);
            Answer answer1 = answers.stream()
                    .max(Comparator.comparing(Answer::getScore)).get();
            if (answer1.getScore() > answer.getScore()) val += 1;
        }
        return 100.0 * val / all;
    }

    public List<Long> findAcceptInterval() {
        List<Question> answeredQuestion = getAnsweredQuestion();
        return answeredQuestion.stream()
                .map(question -> question.getLast_activity_date() - question.getCreation_date()).toList();
    }
}
