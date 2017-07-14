package org.pfccap.education.presentation.main.presenters;

import android.content.Context;
import android.content.Intent;

import com.google.firebase.crash.FirebaseCrash;

import org.pfccap.education.R;
import org.pfccap.education.domain.user.IUserBP;
import org.pfccap.education.domain.user.UserBP;
import org.pfccap.education.entities.UserAuth;
import org.pfccap.education.presentation.main.ui.activities.ProfileActivity;
import org.pfccap.education.presentation.main.ui.fragments.IMainFragmentView;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;
import org.pfccap.education.utilities.Utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jggomez on 14-Jun-17.
 */

public class MainFragmentPresenter implements IMainFragmentPresenter {

    private IMainFragmentView view;
    private Context context;

    public MainFragmentPresenter(IMainFragmentView view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public boolean profileCompleted() {

        if (Cache.getByKey(Constants.PROFILE_COMPLETED).equals("0")) {
            Utilities.dialogoInfoAndIntent(context.getResources().getString(R.string.title_info_dialog)
                    , context.getResources().getString(R.string.profile_completed)
                    , context
                    , new Intent(context, ProfileActivity.class)
            );

            return false;
        }

        return true;
    }

    @Override
    public boolean validateTurn(String turnCancer) {
        //TODO validar cuando las contasntes vienes "" ya que genera error con el Integer.valueOf
        if (Integer.valueOf(Cache.getByKey(turnCancer)) ==
                Integer.valueOf(Cache.getByKey(Constants.NUM_OPPORTUNITIES))) {

            Utilities.dialogoInfo(context.getResources().getString(R.string.title_info_dialog)
                    , String.format(context.getResources().getString(R.string.end_turn),
                            Integer.valueOf(Cache.getByKey(Constants.NUM_OPPORTUNITIES)))
                    , context
            );
            return true;
        }
        return false;
    }

    @Override
    public boolean validateDateLastAnswer(String dateCancer, String lapseCancer) throws ParseException {
        if (dateCancer.equals("null") || dateCancer.equals(""))
            return false;

        if (lapseCancer.equals("0"))
            return false;

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        cal.setTime(sdf.parse(dateCancer));
        cal.add(Calendar.DATE, Integer.valueOf(lapseCancer));

        if (cal.after(Calendar.getInstance())) {
            Utilities.dialogoInfo(context.getResources().getString(R.string.title_info_dialog)
                    , String.format(context.getResources().getString(R.string.lapse_answer),
                            Integer.valueOf(lapseCancer))
                    , context
            );

            return true;
        }

        return false;
    }

    @Override
    public void getDataUserUpdated() {

        view.showProgress();

        IUserBP userBP = new UserBP();
        userBP.getUser().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<UserAuth>() {
                                   @Override
                                   public void onNext(UserAuth userAuth) {
                                       Cache.save(Constants.BREAST_TURN,
                                               String.valueOf(userAuth.getRepetitionsAnswersBreast()));
                                       Cache.save(Constants.CERVIX_TURN,
                                               String.valueOf(userAuth.getRepetitionsAnswersCervix()));
                                       Cache.save(Constants.DATE_COMPLETED_BREAST,
                                               String.valueOf(userAuth.getDateCompletedBreast()));
                                       Cache.save(Constants.DATE_COMPLETED_CERVIX,
                                               String.valueOf(userAuth.getDateCompletedCervix()));
                                       Cache.save(Constants.PROFILE_COMPLETED,
                                               String.valueOf(userAuth.getProfileCompleted()));
                                       Cache.save(Constants.TOTAL_POINTS_B, String.valueOf(userAuth.getPointsBreast()));
                                       Cache.save(Constants.TOTAL_POINTS_C, String.valueOf(userAuth.getPointsCervix()));
                                       Cache.save(Constants.STATE, String.valueOf(userAuth.getState()));
                                       view.hideProgress();
                                       if(Cache.getByKey(Constants.TYPE_CANCER).equals(Constants.BREAST)){
                                           view.showIntroQuestion(Constants.BREAST_TURN, Constants.DATE_COMPLETED_BREAST,
                                                   Constants.LAPSE_BREAST);
                                       } else if(Cache.getByKey(Constants.TYPE_CANCER).equals(Constants.CERVIX)) {
                                           view.showIntroQuestion(Constants.CERVIX_TURN, Constants.DATE_COMPLETED_CERVIX,
                                                   Constants.LAPSE_CERVIX);
                                       }
                                   }

                                   @Override
                                   public void onError(Throwable e) {
                                       FirebaseCrash.report(e);
                                       view.hideProgress();
                                       view.showError(e.getMessage());
                                   }

                                   @Override
                                   public void onComplete() {

                                   }
                               }
                );
    }
}
