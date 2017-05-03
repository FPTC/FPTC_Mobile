package org.pfccap.education.presentation.main.ui.activities;

import org.pfccap.education.entities.UserAuth;

/**
 * Created by USUARIO on 02/05/2017.
 */

public interface IProfileView {

    void setAge(int age);

    void clickTxtBirth();

    void setEmailUser(String email);

    void enableInputs();

    void disableInputs();

    void showProgress();

    void hideProgress();

    void showError(String error);

    void loadData(UserAuth user);

    void finishActivity();

}
