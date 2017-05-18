package org.pfccap.education.presentation.main.ui.activities;

import android.graphics.Color;

import org.pfccap.education.entities.Question;
import org.pfccap.education.entities.UserAuth;

import java.util.HashMap;
import java.util.List;

/**
 * Created by USUARIO on 02/05/2017.
 */

public interface IQuestionView {

    void setPrimaryQuestion(String text);

    void setProgressBar(int progress);

    void setLabelButtonYesNo(String No, String Yes);

    void setInfoSnackbar(String text, String idQ);

    void loadAdapterRecycler(List<String> lable);

    void saveAnswerQuestion();

    void finishActivity();

}
