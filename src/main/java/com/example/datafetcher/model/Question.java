package com.example.datafetcher.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Question extends EntityWithOwner{
    @ElementCollection
    private List<String> tags;

    @OneToOne
    private Owner owner;

    private boolean is_answered;
    private int view_count;
    private int answer_count;
    private int score;
    private long last_activity_date;
    private long creation_date;
    @Id
    private int question_id;
    private String content_license;
    @javax.persistence.Column(length=500)
    private String link;
    @javax.persistence.Column(length=500)
    private String title;
    private long closed_date;

    public Question() {
    }

    @Override
    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public boolean isIs_answered() {
        return is_answered;
    }

    public void setIs_answered(boolean is_answered) {
        this.is_answered = is_answered;
    }

    public int getView_count() {
        return view_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getAnswer_count() {
        return answer_count;
    }

    public void setAnswer_count(int answer_count) {
        this.answer_count = answer_count;
    }

    public long getLast_activity_date() {
        return last_activity_date;
    }

    public void setLast_activity_date(long last_activity_date) {
        this.last_activity_date = last_activity_date;
    }

    public long getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(long creation_date) {
        this.creation_date = creation_date;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getContent_license() {
        return content_license;
    }

    public void setContent_license(String content_license) {
        this.content_license = content_license;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getClosed_date() {
        return closed_date;
    }

    public void setClosed_date(long closed_date) {
        this.closed_date = closed_date;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}