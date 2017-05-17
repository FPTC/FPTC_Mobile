package org.pfccap.education.entities;

import java.util.HashMap;
import java.util.List;

/**
 * Created by USUARIO on 16/05/2017.
 */

public class Answer {

    private String description;
    private int points;
    private boolean value;
    private List<SecondaryQst> question;

    public Answer() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public List<SecondaryQst> getQuestion() {
        return question;
    }

    public void setQuestion(List<SecondaryQst> question) {
        this.question = question;
    }
}
