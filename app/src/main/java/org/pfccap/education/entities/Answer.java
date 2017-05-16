package org.pfccap.education.entities;

/**
 * Created by USUARIO on 16/05/2017.
 */

public class Answer {

    private String description;
    private int points;
    private boolean value;

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
}
