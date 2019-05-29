package org.pfccap.education.presentation.main.ui.fragments;

public interface IMessageGiftView {

    void showProgress();

    void hideProgress();

    void showMessage(String titulo, String message, String typeCancer);

    void showErrorSnack(String message);
}
