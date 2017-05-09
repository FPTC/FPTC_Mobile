package org.pfccap.education.presentation.main.ui.activities;

import org.pfccap.education.entities.UserAuth;

/**
 * Created by USUARIO on 02/05/2017.
 */

public interface IQuestionView {

    void loadPrimaryQuestion(String equestion);

    void loadSecondQuestion(String question);

    void clickBtnPrimatyQuestion();

    void showProgress();

    void hideProgress();

    void clickBtnSecondQuestion();

    void finishActivity();

}
