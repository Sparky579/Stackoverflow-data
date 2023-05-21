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
    private String link;
    private String title;
    private long closed_date;
    private long protected_date;
    private long bounty_amount;
    private long bounty_closes_date;
    private String closed_reason;
    private long last_edit_date;
    private int accepted_answer_id;
    private long community_owned_date;

    public Question() {

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


    public long getProtected_date() {
        return protected_date;
    }

    public void setProtected_date(long protected_date) {
        this.protected_date = protected_date;
    }

    public long getBounty_amount() {
        return bounty_amount;
    }

    public void setBounty_amount(long bounty_amount) {
        this.bounty_amount = bounty_amount;
    }

    public long getBounty_closes_date() {
        return bounty_closes_date;
    }

    public void setBounty_closes_date(long bounty_closes_date) {
        this.bounty_closes_date = bounty_closes_date;
    }

    public long getCommunity_owned_date() {
        return community_owned_date;
    }

    public void setCommunity_owned_date(long community_owned_date) {
        this.community_owned_date = community_owned_date;
    }

}
