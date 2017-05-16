package org.pfccap.education.presentation.auth.presenters;

import android.widget.Switch;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseAuthException;

import org.pfccap.education.dao.AppDao;
import org.pfccap.education.dao.Question;
import org.pfccap.education.dao.QuestionDao;
import org.pfccap.education.domain.auth.AuthProcess;
import org.pfccap.education.domain.auth.IAuthProcess;
import org.pfccap.education.domain.questions.IQuestionBP;
import org.pfccap.education.domain.questions.QuestionBP;
import org.pfccap.education.entities.QuestionsListAll;
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
    private QuestionDao questionDao;

    public LoginPresenter(ILoginView loginView) {
        this.loginView = loginView;
        questionBP = new QuestionBP();
        questionDao = AppDao.getQuestionDao();
    }

    @Override
    public void login(String email, String password) {

        try {

            loginView.disableInputs();
            loginView.showProgress();

            objAuthProcess = new AuthProcess();

            objAuthProcess.signIn(email, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<UserAuth>() {
                        @Override
                        public void onNext(UserAuth value) {
                            getQuestion();
                            loginView.enableInputs();
                            loginView.hideProgress();
                            loginView.navigateToMainScreen();
                        }

                        @Override
                        public void onError(Throwable e) {
                            loginView.enableInputs();
                            loginView.hideProgress();
                            if (e instanceof FirebaseAuthException) {
                               String errorCode = ((FirebaseAuthException) e).getErrorCode();
                                loginView.loginError(Utilities.traslateErrorCode(errorCode));
                            }
                        }

                        @Override
                        public void onComplete() {
                        }
                    });

        } catch (Exception e) {
            loginView.hideProgress();
            loginView.enableInputs();
            loginView.loginError(e.getMessage());
        }

    }

    private void getQuestion() {
      try {
          questionBP.getQuestions()
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribeWith(new DisposableObserver<QuestionsListAll>() {
                      @Override
                      public void onNext(QuestionsListAll value) {
                          Question questionDB;
/*
                          for (){
                              questionDB = new Question();
                              questionDB.setCode();
                              questionDB.setQuestion();
                              questionDB.setTypeCancer();
                              questionDB.setTypeQuestion();
                              questionDao.insert(questionDB);
                          }*/
                      }

                      @Override
                      public void onError(Throwable e) {
                          loginView.enableInputs();
                          loginView.hideProgress();
                          loginView.loginError(e.getMessage());
                      }

                      @Override
                      public void onComplete() {

                      }
                  });

      }catch (Exception e){
          loginView.enableInputs();
          loginView.hideProgress();
          loginView.loginError(e.getMessage());
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
                loginView.loginError(error.getMessage());
            }
        });

        return objCallbackManager;
    }

    @Override
    public void isLogging() {
        String val = Cache.getByKey(Constants.IS_LOGGGIN);
        if(!val.isEmpty()){
            loginView.navigateToMainScreen();
        }
    }


}
