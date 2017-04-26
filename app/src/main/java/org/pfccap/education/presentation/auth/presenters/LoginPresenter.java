package org.pfccap.education.presentation.auth.presenters;

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

}
