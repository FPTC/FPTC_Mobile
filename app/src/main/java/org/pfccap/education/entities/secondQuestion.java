package org.pfccap.education.entities;

import java.util.HashMap;

/**
 * Created by USUARIO on 18/05/2017.
 */

public class SecondQuestion {

    private String text;
    private HashMap answers = new HashMap();

    public SecondQuestion(){

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public HashMap getAnswer() {
        return answers;
    }

    public void setAnswer(HashMap answer) {
        this.answers = answer;
    }
}
