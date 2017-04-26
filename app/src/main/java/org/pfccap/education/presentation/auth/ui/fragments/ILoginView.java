package org.pfccap.education.presentation.auth.ui.fragments;

/**
 * Created by jggomez on 05-Apr-17.
 */

public interface ILoginView {

    void enableInputs();

    void disableInputs();

    void showProgress();

    void hideProgress();

    void navigateToSignUp();

    void handleSignIn();

    void navigateToMainScreen();

    void loginError(String error);

}
