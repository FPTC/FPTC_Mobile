package org.pfccap.education.entities;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by USUARIO on 11/05/2017.
 */

public class Question {

    private HashMap<String, Answer> answers = new HashMap();
    private String id;
    private String info;
    private int order;
    private String text;
    private String typeQuestion;
    private boolean enable;

    public Question() {

    }

    public HashMap<String, Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(HashMap<String, Answer> answers) {
        this.answers = answers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTypeQuestion() {
        return typeQuestion;
    }

    public void setTypeQuestion(String typeQuestion) {
        this.typeQuestion = typeQuestion;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
