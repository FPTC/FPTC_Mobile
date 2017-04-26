package org.pfccap.education.presentation.auth.ui.fragments;

/**
 * Created by jggomez on 25-Apr-17.
 */

public interface IResetPasswordView {

    void enableInputs();

    void disableInputs();

    void showProgress();

    void hideProgress();

    void navigateToLoginScreen();

    void handleResetPassword();

    void resetError(String error);

}
