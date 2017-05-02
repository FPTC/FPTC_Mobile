package org.pfccap.education.presentation.auth.ui.fragments;

/**
 * Created by jggomez on 19-Apr-17.
 */

public interface ISignupView {

    void enableInputs();

    void disableInputs();

    void showProgress();

    void hideProgress();

    void handleSignUp();

    void navigateToLoginScreen();

    void signUpError(String error);

    void signUpSuccessful();

}
