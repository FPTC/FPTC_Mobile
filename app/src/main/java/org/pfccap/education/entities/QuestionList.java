package org.pfccap.education.entities;

import java.util.HashMap;

/**
 * Created by USUARIO on 08/05/2017.
 */

public class QuestionList {


    private HashMap<String, Questions> breastCancer = new HashMap<>();
    private HashMap<String, Questions> cervixCancer = new HashMap<>();

    public QuestionList() {

    }

    public HashMap<String, Questions> getBreastCancer() {
        return breastCancer;
    }

    public void setBreastCancer(HashMap<String, Questions> breastCancer) {
        this.breastCancer = breastCancer;
    }

    public HashMap<String, Questions> getCervixCancer() {
        return cervixCancer;
    }

    public void setCervixCancer(HashMap<String, Questions> cervixCancer) {
        this.cervixCancer = cervixCancer;
    }
}
