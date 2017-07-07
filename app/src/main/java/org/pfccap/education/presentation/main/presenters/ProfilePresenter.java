package org.pfccap.education.presentation.main.presenters;

import android.content.Context;

import com.google.firebase.crash.FirebaseCrash;

import org.pfccap.education.domain.user.IUserBP;
import org.pfccap.education.domain.user.UserBP;
import org.pfccap.education.entities.UserAuth;
import org.pfccap.education.presentation.main.ui.activities.IProfileView;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;

import java.util.Calendar;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by USUARIO on 02/05/2017.
 */

public class ProfilePresenter implements IProfilePresenter {

    private IProfileView profileView;
    private IUserBP userBP;
    private Context context;

    public ProfilePresenter(IProfileView profileView, Context context) {
        this.profileView = profileView;
        this.context = context;
        userBP = new UserBP();
    }

    @Override
    public void calculateAge(int yearBirth) {
        Calendar calendar = Calendar.getInstance();
        int age = calendar.get(Calendar.YEAR) - yearBirth;
        profileView.setAge(age);
    }

    @Override
    public void getEmailUser() {
        profileView.setEmailUser(Cache.getByKey(Constants.USER_EMAIL));
    }

    @Override
    public void updateUserData(HashMap<String, Object> dataUser) {

        try {

            profileView.showProgress();
            profileView.disableInputs();

            userBP.update(dataUser);

            profileView.hideProgress();
            profileView.enableInputs();
            profileView.finishActivity();

        } catch (Exception e) {
            profileView.hideProgress();
            profileView.enableInputs();
            FirebaseCrash.report(e);
            profileView.showError(e.getMessage());
        }

    }

    public void getUserData() {
        try {
            profileView.showProgress();
            profileView.disableInputs();

            userBP.getUser()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<UserAuth>() {
                        @Override
                        public void onNext(UserAuth value) {
                            profileView.hideProgress();
                            profileView.enableInputs();
                            profileView.loadData(value);
                        }

                        @Override
                        public void onError(Throwable e) {
                            profileView.hideProgress();
                            profileView.enableInputs();
                            FirebaseCrash.report(e);
                            profileView.showError(e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                        }
                    });

        } catch (Exception e) {
            profileView.hideProgress();
            profileView.enableInputs();
            FirebaseCrash.report(e);
            profileView.showError(e.getMessage());
        }

    }


}
