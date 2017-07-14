package org.pfccap.education.presentation.main.ui.fragments;

/**
 * Created by USUARIO on 13/07/2017.
 */

public interface IGiftsFragmentView {


    void showProgress();

    void hideProgress();

    void afterUpdateUserInfo();

    void showErrorSnack(String message);

    void showErrorDialog(String title, String message);
}
