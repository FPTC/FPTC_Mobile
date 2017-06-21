package org.pfccap.education.presentation.auth.presenters;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
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
import org.pfccap.education.entities.QuestionList;
import org.pfccap.education.entities.UserAuth;
import org.pfccap.education.presentation.auth.ui.fragments.ILoginView;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;
import org.pfccap.education.utilities.Utilities;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jggomez on 05-Apr-17.
 */

public class LoginPresenter implements ILoginPresenter {

    private String TAG = LoginPresenter.class.getName();

    private ILoginView loginView;
    private IAuthProcess objAuthProcess;
    private IQuestionBP questionBP;


    public LoginPresenter(ILoginView loginView) {
        this.loginView = loginView;
        questionBP = new QuestionBP();

    }

    @Override
    public void login(String email, String password) {

        try {

            loginView.disableInputs();
            loginView.showProgress();

            objAuthProcess = new AuthProcess();
            final IConfigurationBP configurationBP = new ConfigurationBP();

            objAuthProcess.signIn(email, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
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
                                                                           showErrorView(e);
                                                                       }

                                                                       @Override
                                                                       public void onComplete() {

                                                                       }
                                                                   }
                                                    );
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            showErrorView(e);
                                        }

                                        @Override
                                        public void onComplete() {

                                        }
                                    });
                        }

                        @Override
                        public void onError(Throwable e) {
                            showErrorView(e);
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
        loginView.enableInputs();
        loginView.hideProgress();
        FirebaseCrash.report(e);
        if (e instanceof FirebaseAuthException) {
            String errorCode = ((FirebaseAuthException) e).getErrorCode();
            loginView.loginError(Utilities.traslateErrorCode(errorCode));
        } else {
            loginView.loginError(e.getMessage());
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
                            loginView.enableInputs();
                            loginView.hideProgress();
                            loginView.navigateToMainScreen();
                        }

                        @Override
                        public void onError(Throwable e) {
                            loginView.enableInputs();
                            loginView.hideProgress();
                            FirebaseCrash.report(e);
                            loginView.loginError(e.getMessage());

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        } catch (Exception e) {
            showErrorView(e);
        }
    }

    @Override
    public CallbackManager registerCallbackFacebook(final LoginButton loginButtonFacebook) {

        CallbackManager objCallbackManager = CallbackManager.Factory.create();

        loginButtonFacebook.registerCallback(objCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                objAuthProcess = new AuthProcess();

                objAuthProcess.signInWithCredential(loginResult.getAccessToken().getToken())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(new Consumer<UserAuth>() {
                            @Override
                            public void accept(UserAuth userAuth) throws Exception {
                                loginView.disableInputs();
                                loginView.showProgress();
                            }
                        })
                        .subscribeWith(new DisposableObserver<UserAuth>() {
                            @Override
                            public void onNext(UserAuth value) {
                                loginView.enableInputs();
                                loginView.hideProgress();
                                loginView.navigateToMainScreen();
                            }

                            @Override
                            public void onError(Throwable e) {
                                loginView.enableInputs();
                                loginView.hideProgress();
                                FirebaseCrash.report(e);
                                loginView.loginError(e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                            }
                        });

            }

            @Override
            public void onCancel() {
                loginView.enableInputs();
                loginView.hideProgress();
            }

            @Override
            public void onError(FacebookException error) {
                loginView.enableInputs();
                loginView.hideProgress();
                FirebaseCrash.report(error);
                loginView.loginError(error.getMessage());
            }
        });

        return objCallbackManager;
    }

    @Override
    public void isLogging() {
        String val = Cache.getByKey(Constants.IS_LOGGGIN);
        if (!val.isEmpty()) {
            loginView.navigateToMainScreen();
        }
    }


}
