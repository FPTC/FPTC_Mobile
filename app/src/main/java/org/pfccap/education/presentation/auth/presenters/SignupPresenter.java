package org.pfccap.education.presentation.auth.presenters;

import org.pfccap.education.domain.auth.AuthProcess;
import org.pfccap.education.domain.auth.IAuthProcess;
import org.pfccap.education.entities.UserAuth;
import org.pfccap.education.presentation.auth.ui.fragments.ISignupView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jggomez on 19-Apr-17.
 */

public class SignupPresenter implements ISignupPresenter {

    private ISignupView signupView;
    private IAuthProcess objAuthProcess;

    public SignupPresenter(ISignupView signupView) {
        this.signupView = signupView;
    }

    @Override
    public void signUp(String email, String password) {
        signupView.disableInputs();

        objAuthProcess = new AuthProcess();

        objAuthProcess.signUp(email, password)
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
                        signupView.navigateToLoginScreen();
                    }

                    @Override
                    public void onError(Throwable e) {
                        signupView.enableInputs();
                        signupView.signUpError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        signupView.hideProgress();
                        signupView.enableInputs();
                    }
                });

    }

}
