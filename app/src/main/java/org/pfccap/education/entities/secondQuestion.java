package org.pfccap.education.entities;

import java.util.HashMap;

/**
 * Created by USUARIO on 20/05/2017.
 */

public class SecondQuestion {
    private String text;
    private HashMap<String, Answer> answers = new HashMap<>();

    public SecondQuestion(){

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public HashMap<String, Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(HashMap<String, Answer> answers) {
        this.answers = answers;
    }
}
