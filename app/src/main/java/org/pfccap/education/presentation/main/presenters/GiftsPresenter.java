package org.pfccap.education.presentation.main.presenters;

import android.content.Context;

import com.google.firebase.crash.FirebaseCrash;

import org.json.JSONArray;
import org.json.JSONException;
import org.pfccap.education.R;
import org.pfccap.education.dao.Gift;
import org.pfccap.education.domain.questions.ILQuestionDB;
import org.pfccap.education.domain.questions.LQuestionDB;
import org.pfccap.education.domain.services.ValidationService;
import org.pfccap.education.domain.user.IUserBP;
import org.pfccap.education.domain.user.UserBP;
import org.pfccap.education.entities.UserAuth;
import org.pfccap.education.entities.Validation;
import org.pfccap.education.presentation.main.ui.fragments.IGiftsFragmentView;
import org.pfccap.education.utilities.APIService;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;
import org.pfccap.education.utilities.Utilities;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by USUARIO on 22/06/2017.
 */

public class GiftsPresenter implements IGiftsPresenter {

    private ILQuestionDB ilQuestionDB;
    private Context context;
    private IGiftsFragmentView view;

    public GiftsPresenter(IGiftsFragmentView view, Context context) {
        this.view = view;
        this.context = context;
        ilQuestionDB = new LQuestionDB();
    }


    @Override
    public List<Gift> getListGiftsTable() {
        try {
            List<Gift> listItems = ilQuestionDB.getAllGift();
            return listItems;
        } catch (Exception e) {
            FirebaseCrash.report(e);
            return null;
        }
    }

    @Override
    public void getValidaionAppointment(final String Uid) {

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

                                       if (Integer.valueOf(Cache.getByKey(Constants.BREAST_TURN)) < Integer.valueOf(Cache.getByKey(Constants.NUM_OPPORTUNITIES))) {
                                           view.hideProgress();
                                           view.showErrorSnack(context.getString(R.string.have_opportunities_breast_days, Cache.getByKey(Constants.LAPSE_BREAST)));
                                       } else if (Integer.valueOf(Cache.getByKey(Constants.CERVIX_TURN)) < Integer.valueOf(Cache.getByKey(Constants.NUM_OPPORTUNITIES))) {
                                           view.hideProgress();
                                           view.showErrorSnack(context.getString(R.string.have_opportunities_cervix_days, Cache.getByKey(Constants.LAPSE_CERVIX)));
                                       } else if (Integer.valueOf(Cache.getByKey(Constants.STATE)) >= 2) {
                                           view.hideProgress();
                                           view.showErrorDialog(context.getString(R.string.title_info_dialog), context.getString(R.string.done_get_gift));
                                       } else {

                                           ValidationService service = APIService.getInstanceRetrofit(ValidationService.class);

                                           service.getValidation(Uid)
                                                   .subscribeOn(Schedulers.io())
                                                   .observeOn(AndroidSchedulers.mainThread())
                                                   .subscribe(new DisposableObserver<Validation>() {
                                                       @Override
                                                       public void onNext(Validation value) {

                                                           List<Object> valueObject = value.getResponse();
                                                           if (valueObject != null && valueObject.size() == 0) {
                                                               Cache.save(Constants.APPOINTMENT_TYPE, "0");
                                                           } else if (valueObject.size() == 1) {
                                                               JSONArray valueArray = new JSONArray(valueObject);
                                                               try {
                                                                   String answerApp = valueArray.getString(0);
                                                                   if (answerApp.equals(context.getResources().getString(R.string.cervix))) {
                                                                       Cache.save(Constants.APPOINTMENT_TYPE, "2");
                                                                   } else if (answerApp.equals(context.getResources().getString(R.string.breast))) {
                                                                       Cache.save(Constants.APPOINTMENT_TYPE, "1");
                                                                   }else{
                                                                       Cache.save(Constants.APPOINTMENT_TYPE, "0");
                                                                       FirebaseCrash.log(answerApp);
                                                                   }

                                                               } catch (JSONException e) {
                                                                   e.printStackTrace();
                                                                   FirebaseCrash.report(e);
                                                               }
                                                           } else if (valueObject.size() == 2) {
                                                               Cache.save(Constants.APPOINTMENT_TYPE, "3");
                                                           }
                                                           view.hideProgress();
                                                           view.afterUpdateUserInfo();
                                                       }

                                                       @Override
                                                       public void onError(Throwable e) {
                                                           FirebaseCrash.report(e);
                                                           view.hideProgress();

                                                       }

                                                       @Override
                                                       public void onComplete() {

                                                       }
                                                   });
                                       }

                                   }

                                   @Override
                                   public void onError(Throwable e) {
                                       FirebaseCrash.report(e);
                                       Utilities.traslateErrorCode(e.getMessage(), context);
                                   }

                                   @Override
                                   public void onComplete() {

                                   }
                               }
                );
    }
}
