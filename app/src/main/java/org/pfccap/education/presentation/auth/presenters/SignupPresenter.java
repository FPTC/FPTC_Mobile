package org.pfccap.education.presentation.auth.presenters;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.crash.FirebaseCrash;

import org.pfccap.education.domain.auth.AuthProcess;
import org.pfccap.education.domain.auth.IAuthProcess;
import org.pfccap.education.domain.configuration.ConfigurationBP;
import org.pfccap.education.domain.configuration.IConfigurationBP;
import org.pfccap.education.domain.user.IUserBP;
import org.pfccap.education.domain.user.UserBP;
import org.pfccap.education.entities.Configuration;
import org.pfccap.education.entities.UserAuth;
import org.pfccap.education.presentation.auth.ui.fragments.ISignupView;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;
import org.pfccap.education.utilities.Utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jggomez on 19-Apr-17.
 */

public class SignupPresenter implements ISignupPresenter {

    private ISignupView signupView;

    public SignupPresenter(ISignupView signupView) {
        this.signupView = signupView;
    }

    @Override
    public void signUp(final String name, String email, String password) {

        try {

            signupView.disableInputs();
            signupView.showProgress();

            IAuthProcess objAuthProcess = new AuthProcess();
            final IConfigurationBP configurationBP = new ConfigurationBP();

            objAuthProcess.signUp(name, email, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(new Consumer<UserAuth>() {
                        @Override
                        public void accept(UserAuth userAuth) throws Exception {
                            signupView.showProgress();
                        }
                    })
                    .subscribeWith(new DisposableObserver<UserAuth>() {
                        @Override
                        public void onNext(UserAuth value) {


                            configurationBP.getConfiguration()
                                    .observeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeWith(new DisposableObserver<Configuration>() {
                                        @Override
                                        public void onNext(Configuration value) {
                                            Cache.save(Constants.LAPSE_BREAST,
                                                    String.valueOf(value.getLapseBreast()));
                                            Cache.save(Constants.LAPSE_CERVIX,
                                                    String.valueOf(value.getLapseCervix()));

                                            IUserBP userBP = new UserBP();
                                            userBP.getUser().subscribeOn(Schedulers.io())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribeWith(new DisposableObserver<UserAuth>() {

                                                                       @Override
                                                                       public void onNext(UserAuth userAuth) {
                                                                           Cache.save(Constants.DATE_COMPLETED_BREAST,
                                                                                   String.valueOf(userAuth.getDateCompletedBreast()));
                                                                           Cache.save(Constants.DATE_COMPLETED_CERVIX,
                                                                                   String.valueOf(userAuth.getDateCompletedCervix()));

                                                                           signupView.hideProgress();
                                                                           signupView.enableInputs();
                                                                           signupView.signUpSuccessful();
                                                                           signupView.navigateToLoginScreen();
                                                                       }

                                                                       @Override
                                                                       public void onError(Throwable e) {

                                                                       }

                                                                       @Override
                                                                       public void onComplete() {

                                                                       }
                                                                   }
                                                    );
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            signupView.hideProgress();
                                            signupView.enableInputs();
                                            FirebaseCrash.report(e);
                                            signupView.signUpError(Utilities.traslateErrorCode(e.getMessage()));
                                        }

                                        @Override
                                        public void onComplete() {

                                        }
                                    });
                        }

                        @Override
                        public void onError(Throwable e) {
                            signupView.hideProgress();
                            signupView.enableInputs();
                            FirebaseCrash.report(e);
                            if (e instanceof FirebaseAuthException) {
                                String errorCode = ((FirebaseAuthException) e).getErrorCode();
                                signupView.signUpError(Utilities.traslateErrorCode(errorCode));
                            } else {
                                Pattern pattern = Pattern.compile(".*WEAK_PASSWORD.*");
                                Matcher matcher = pattern.matcher(e.getMessage());
                                if (matcher.find()) {
                                    signupView.signUpError(Utilities.traslateErrorCode("WEAK_PASSWORD"));
                                }
                            }
                        }

                        @Override
                        public void onComplete() {
                        }
                    });

        } catch (Exception e) {
            signupView.hideProgress();
            signupView.enableInputs();
            FirebaseCrash.report(e);
            signupView.signUpError(e.getMessage());
        }

    }

}
