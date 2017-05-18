package org.pfccap.education.entities;

import java.util.HashMap;

/**
 * Created by USUARIO on 08/05/2017.
 */

public class QuestionList {


    private HashMap<String, Question> cancerCervix = new HashMap();
    private HashMap<String, Question> cencerSeno = new HashMap();

    public QuestionList() {

    }

    public HashMap<String, Question> getCancerCervix() {
        return cancerCervix;
    }

    public void setCancerCervix(HashMap<String, Question> cancerCervix) {
        this.cancerCervix = cancerCervix;
    }

    public HashMap<String, Question> getCancerSeno() {
        return cencerSeno;
    }

    public void setCancerSeno(HashMap<String, Question> cancerSeno) {
        this.cencerSeno = cancerSeno;
    }
}
