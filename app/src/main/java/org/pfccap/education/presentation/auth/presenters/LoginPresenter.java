package org.pfccap.education.presentation.auth.presenters;

import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.pfccap.education.domain.auth.AuthProcess;
import org.pfccap.education.domain.auth.IAuthProcess;
import org.pfccap.education.entities.UserAuth;
import org.pfccap.education.presentation.auth.ui.fragments.ILoginView;

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

    public LoginPresenter(ILoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void login(String email, String password) {
        loginView.disableInputs();

        objAuthProcess = new AuthProcess();

        objAuthProcess.signIn(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<UserAuth>() {
                    @Override
                    public void accept(UserAuth userAuth) throws Exception {
                        loginView.showProgress();
                    }
                })
                .subscribeWith(new DisposableObserver<UserAuth>() {
                    @Override
                    public void onNext(UserAuth value) {
                        loginView.navigateToMainScreen();
                    }

                    @Override
                    public void onError(Throwable e) {
                        loginView.loginError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        loginView.hideProgress();
                        loginView.enableInputs();
                    }
                });

    }

    @Override
    public CallbackManager registerCallbackFacebook(final LoginButton loginButtonFacebook) {

        CallbackManager objCallbackManager = CallbackManager.Factory.create();

        loginButtonFacebook.registerCallback(objCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);

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
                                loginView.navigateToMainScreen();
                            }

                            @Override
                            public void onError(Throwable e) {
                                loginView.enableInputs();
                                loginView.loginError(e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                loginView.hideProgress();
                                loginView.enableInputs();
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

}
