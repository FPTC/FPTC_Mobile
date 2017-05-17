package org.pfccap.education.presentation.main.ui.activities;

import org.pfccap.education.entities.UserAuth;

import java.util.List;

/**
 * Created by USUARIO on 02/05/2017.
 */

public interface IQuestionView {

    void loadLableButtonAnswers(List<String> lable);

    void loadPrimaryQuestion(String equestion);

    void loadSecondQuestion(String question);

    void clickBtnPrimatyQuestion();

    void showProgress();

    void hideProgress();

    void clickBtnSecondQuestion();

    void finishActivity();

}
