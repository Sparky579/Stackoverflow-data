package com.example.datafetcher.DTO;

import java.io.Serializable;


public class QuestionAnswer implements Serializable {
    private int question_id;
    private int answer_count;
    // Getters and setters

    public int  getQuestion_id() {
        return question_id;
    }

    public int getAnswer_count() {
        return answer_count;
    }
    public QuestionAnswer(int question_id, int answer_count){
        this.answer_count=answer_count;
        this.question_id=question_id;
    }

}
