package org.pfccap.education.entities;

import java.util.HashMap;

/**
 * Created by USUARIO on 19/05/2017.
 */

public class SecondQuestion {
    private String text;
    private HashMap<String, String> answers = new HashMap();

    public SecondQuestion(){

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public HashMap<String, String> getAnswers() {
        return answers;
    }

    public void setAnswers(HashMap<String, String> answers) {
        this.answers = answers;
    }
}
