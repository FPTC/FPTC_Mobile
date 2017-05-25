package org.pfccap.education.presentation.main.presenters;

import android.content.Context;
import android.util.Log;

import com.google.firebase.crash.FirebaseCrash;

import org.pfccap.education.R;
import org.pfccap.education.dao.Question;
import org.pfccap.education.domain.questions.ILQuestionDB;
import org.pfccap.education.domain.questions.IQuestionBP;
import org.pfccap.education.domain.questions.LQuestionDB;
import org.pfccap.education.domain.questions.QuestionBP;
import org.pfccap.education.entities.QuestionList;
import org.pfccap.education.presentation.main.ui.fragments.IIntroView;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;
import org.pfccap.education.utilities.Utilities;

import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.google.android.gms.wearable.DataMap.TAG;

/**
 * Created by USUARIO on 22/05/2017.
 */

public class IntroFragPresenter implements IIntroFragPresenter {

    private ILQuestionDB ilQuestionDB;
    private IIntroView iIntroView;
    private Context context;
    private IQuestionBP questionBP;

    public IntroFragPresenter(IIntroView iIntroView, Context context){
        this.iIntroView = iIntroView;
        this.context = context;
        ilQuestionDB = new LQuestionDB();
        questionBP = new QuestionBP();
    }

    @Override
    public boolean IIntroFragPresenter() {
        try {
            List<Question> lstQuestion = ilQuestionDB.getAll(Cache.getByKey(Constants.TYPE_CANCER));
            if (lstQuestion!=null && lstQuestion.size()!=0){
                return true;
            }else {
                iIntroView.errorDBQuestion(context.getString(R.string.no_db_questions));
                iIntroView.showProgress();
                getQuestions();
                return false;
            }
        }catch (Exception e){
            FirebaseCrash.report(e);
            return false;
        }
    }

    private void getQuestions() {
        try {
            questionBP.getQuestions()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<QuestionList>() {
                        @Override
                        public void onNext(QuestionList value) {
                            iIntroView.hideProgress();
                            iIntroView.goAnswersQuestion();
                        }

                        @Override
                        public void onError(Throwable e) {
                            iIntroView.hideProgress();
                            FirebaseCrash.report(e);
                            iIntroView.errorDBQuestion(e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        } catch (Exception e) {
            iIntroView.hideProgress();
            FirebaseCrash.report(e);
            iIntroView.errorDBQuestion(e.getMessage());
        }
    }


}
