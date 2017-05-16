package org.pfccap.education.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USUARIO on 08/05/2017.
 */

public class QuestionsListAll {



    private List<QuestionByCancer> cancerCervix = new ArrayList<QuestionByCancer>();

    public QuestionsListAll(){

    }

    public List<QuestionByCancer> getCancerCervix() {
        return cancerCervix;
    }

    public void setCancerCervix(List<QuestionByCancer> cancerCervix) {
        this.cancerCervix = cancerCervix;
    }
}
