package org.pfccap.education.presentation.main.ui.fragments;

/**
 * Created by USUARIO on 15/05/2017.
 */

public interface IMainFragmentView {

    void navigateToBreast();

    void navigateToCervical();

    void navigateToGifts();

    void showError(String error);

    void showProgress();

    void hideProgress();

    void showIntroQuestion(String turn, String dateComplite, String lapse);

    void resetTextBtnsQuestions(String typeCancer);

    void showMessage(String message);

}
