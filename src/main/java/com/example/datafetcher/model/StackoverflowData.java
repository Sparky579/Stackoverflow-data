package com.example.datafetcher.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class StackoverflowData {
    @ElementCollection
    private List<String> tags;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "stackoverflowData")
    private Owner owner;

    private boolean is_answered;
    private int view_count;
    private int answer_count;
    private int score;
    private long last_activity_date;
    private long creation_date;
    private int question_id;
    private String content_license;
    private String link;
    private String title;
    private long closed_date;
    private String closed_reason;
    private int accept_rate;
    private long last_edit_date;
    private int accepted_answer_id;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    public StackoverflowData(List<String> tags, Owner owner, boolean is_answered, int view_count, int answer_count, int score, long last_activity_date, long creation_date, int question_id, String content_license, String link, String title) {
        this.tags = tags;
        this.owner = owner;
        this.is_answered = is_answered;
        this.view_count = view_count;
        this.answer_count = answer_count;
        this.score = score;
        this.last_activity_date = last_activity_date;
        this.creation_date = creation_date;
        this.question_id = question_id;
        this.content_license = content_license;
        this.link = link;
        this.title = title;
    }

    public StackoverflowData() {

    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

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

    public int getAnswer_count() {
        return answer_count;
    }

    public void setAnswer_count(int answer_count) {
        this.answer_count = answer_count;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public long getClosed_date() {
        return closed_date;
    }

    public void setClosed_date(long closed_date) {
        this.closed_date = closed_date;
    }

    public String getClosed_reason() {
        return closed_reason;
    }

    public void setClosed_reason(String closed_reason) {
        this.closed_reason = closed_reason;
    }

    public int getAccept_rate() {
        return accept_rate;
    }

    public void setAccept_rate(int accept_rate) {
        this.accept_rate = accept_rate;
    }

    public long getLast_edit_date() {
        return last_edit_date;
    }

    public void setLast_edit_date(long last_edit_date) {
        this.last_edit_date = last_edit_date;
    }

    public int getAccepted_answer_id() {
        return accepted_answer_id;
    }

    public void setAccepted_answer_id(int accepted_answer_id) {
        this.accepted_answer_id = accepted_answer_id;
    }


}
