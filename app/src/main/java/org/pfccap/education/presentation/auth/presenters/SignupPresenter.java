package org.pfccap.education.presentation.auth.presenters;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.crash.FirebaseCrash;

import org.pfccap.education.domain.auth.AuthProcess;
import org.pfccap.education.domain.auth.IAuthProcess;
import org.pfccap.education.domain.configuration.ConfigurationBP;
import org.pfccap.education.domain.configuration.IConfigurationBP;
import org.pfccap.education.domain.questions.IQuestionBP;
import org.pfccap.education.domain.questions.QuestionBP;
import org.pfccap.education.domain.user.IUserBP;
import org.pfccap.education.domain.user.UserBP;
import org.pfccap.education.entities.Configuration;
import org.pfccap.education.entities.ConfigurationGifts;
import org.pfccap.education.entities.QuestionList;
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
    private IQuestionBP questionBP;
    private IConfigurationBP configurationBP;

    public SignupPresenter(ISignupView signupView) {
        this.signupView = signupView;
        questionBP = new QuestionBP();
        configurationBP = new ConfigurationBP();
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
                                            Cache.save(Constants.NUM_OPPORTUNITIES,
                                                    String.valueOf(value.getNumOpportunities()));

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

                                                                           getQuestion();

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

    private void getQuestion() {
        try {

            questionBP.getQuestions()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<QuestionList>() {
                        @Override
                        public void onNext(QuestionList value) {
                            getConfGifts();
                        }

                        @Override
                        public void onError(Throwable e) {
                            signupView.enableInputs();
                            signupView.hideProgress();
                            FirebaseCrash.report(e);
                            signupView.signUpError(e.getMessage());

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        } catch (Exception e) {
            showErrorView(e);
        }
    }


    private void getConfGifts() {
        try {

            configurationBP.getConfigurationGifts()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<ConfigurationGifts>() {
                        @Override
                        public void onNext(ConfigurationGifts value) {
                            Cache.save(Constants.APPOINTMENT, value.getAppointment());
                            signupView.hideProgress();
                            signupView.enableInputs();
                            signupView.signUpSuccessful();
                            signupView.navigateToLoginScreen();
                        }

                        @Override
                        public void onError(Throwable e) {
                            signupView.enableInputs();
                            signupView.hideProgress();
                            FirebaseCrash.report(e);
                            signupView.signUpError(e.getMessage());

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        } catch (Exception e) {
            showErrorView(e);
        }
    }

    private void showErrorView(Throwable e) {
        signupView.enableInputs();
        signupView.hideProgress();
        FirebaseCrash.report(e);
        if (e instanceof FirebaseAuthException) {
            String errorCode = ((FirebaseAuthException) e).getErrorCode();
            signupView.signUpError(Utilities.traslateErrorCode(errorCode));
        } else {
            signupView.signUpError(e.getMessage());
        }
    }

}
