package org.pfccap.education.presentation.main.presenters;

import android.content.Context;

import org.pfccap.education.R;
import org.pfccap.education.dao.Question;
import org.pfccap.education.domain.questions.ILQuestionDB;
import org.pfccap.education.domain.questions.LQuestionDB;
import org.pfccap.education.presentation.main.ui.fragments.IIntroView;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;
import org.pfccap.education.utilities.Utilities;

import java.util.List;

/**
 * Created by USUARIO on 22/05/2017.
 */

public class IntroFragPresenter implements IIntroFragPresenter {

    private ILQuestionDB ilQuestionDB;
    private IIntroView iIntroView;
    private Context context;

    public IntroFragPresenter(IIntroView iIntroView, Context context){
        this.iIntroView = iIntroView;
        this.context = context;
        ilQuestionDB = new LQuestionDB();
    }

    @Override
    public boolean IIntroFragPresenter() {
        List<Question> lstQuestion = ilQuestionDB.getAll(Cache.getByKey(Constants.TYPE_CANCER));
        if (lstQuestion!=null){
            return true;
        }else {
            iIntroView.errorDBQuestion(context.getString(R.string.no_db_questions));
            return false;
        }
    }
}
