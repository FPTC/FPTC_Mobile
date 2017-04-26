package org.pfccap.education.presentation.auth.presenters;

import org.pfccap.education.domain.auth.AuthProcess;
import org.pfccap.education.domain.auth.IAuthProcess;
import org.pfccap.education.presentation.auth.ui.fragments.IResetPasswordView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jggomez on 25-Apr-17.
 */

public class ResetPasswordPresenter implements IResetPasswordPresenter {

    private IResetPasswordView resetPasswordView;
    private IAuthProcess objAuthProcess;

    public ResetPasswordPresenter(IResetPasswordView resetPasswordView) {
        this.resetPasswordView = resetPasswordView;
    }

    @Override
    public void resetPassword(String email) {

        resetPasswordView.disableInputs();

        objAuthProcess = new AuthProcess();

        objAuthProcess.resetPassword(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String msj) throws Exception {
                        resetPasswordView.showProgress();
                    }
                })
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String value) {
                        resetPasswordView.navigateToLoginScreen();
                    }

                    @Override
                    public void onError(Throwable e) {
                        resetPasswordView.enableInputs();
                        resetPasswordView.resetError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        resetPasswordView.hideProgress();
                        resetPasswordView.enableInputs();
                    }
                });
    }
}
