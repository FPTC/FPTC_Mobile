package org.pfccap.education.presentation.main.presenters;

import com.google.firebase.crash.FirebaseCrash;

import org.pfccap.education.dao.Gift;
import org.pfccap.education.domain.questions.ILQuestionDB;
import org.pfccap.education.domain.questions.LQuestionDB;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USUARIO on 22/06/2017.
 */

public class GiftsPresenter implements IGiftsPresenter {

    private ILQuestionDB ilQuestionDB;

    public GiftsPresenter(){
        ilQuestionDB = new LQuestionDB();
    }


    @Override
    public List<Gift> getListGiftsTable() {
        try {
            List<Gift> listItems = ilQuestionDB.getAllGift();
            return listItems;
        }catch (Exception e){
            FirebaseCrash.report(e);
            return null;
        }
    }
}
