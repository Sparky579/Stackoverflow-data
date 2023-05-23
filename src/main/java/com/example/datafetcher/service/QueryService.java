package com.example.datafetcher.service;

import com.example.datafetcher.model.Answer;
import com.example.datafetcher.model.Question;
import com.example.datafetcher.repository.AnswerRepository;
import com.example.datafetcher.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class QueryService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    public List<Question> getQuestionsByTagsAndKey(List<String> tags, String key) {
        List<Question> questionList = (key == null) ?
                questionRepository.findAll() : questionRepository.findByTitleIgnoreCaseContaining(key);
        if (tags != null && ! tags.isEmpty())
        questionList.removeIf(question -> !question.getTags().containsAll(tags));
        return questionList;
    }
    public Answer findAcceptAnswer(int qid) {
        Question answeredQuestion = questionRepository.findByQuestion_id(qid);
        if (answeredQuestion == null) return null;
        List<Answer> acceptedAnswer = answerRepository.findByAnswer_id(answeredQuestion.getAccepted_answer_id());
        if (acceptedAnswer.isEmpty()) return null;
        return acceptedAnswer.get(0);
    }
    public List<Answer> findAnswers(int qid) {
        return answerRepository.findAnswersByQuestionId(qid);
    }
}
