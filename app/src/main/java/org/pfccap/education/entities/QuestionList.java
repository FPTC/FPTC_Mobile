package org.pfccap.education.entities;

import java.util.HashMap;

/**
 * Created by USUARIO on 08/05/2017.
 */

public class QuestionList {


    private HashMap<String, Questions> cancerCervix = new HashMap<>();
    private HashMap<String, Questions> cencerSeno = new HashMap<>();

    public QuestionList() {

    }

    public HashMap<String, Questions> getCancerCervix() {
        return cancerCervix;
    }

    public void setCancerCervix(HashMap<String, Questions> cancerCervix) {
        this.cancerCervix = cancerCervix;
    }

    public HashMap<String, Questions> getCancerSeno() {
        return cencerSeno;
    }

    public void setCancerSeno(HashMap<String, Questions> cancerSeno) {
        this.cencerSeno = cancerSeno;
    }
}
