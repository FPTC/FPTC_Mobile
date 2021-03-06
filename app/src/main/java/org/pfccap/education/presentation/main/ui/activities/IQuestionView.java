package org.pfccap.education.presentation.main.ui.activities;

import android.text.SpannableString;

import org.pfccap.education.dao.AnswersQuestion;

import java.util.List;

/**
 * Created by USUARIO on 02/05/2017.
 */

public interface IQuestionView {

    void setPrimaryQuestion(String text);

    void setProgressBar(int progress);

    void setInfoSnackbar(String text);

    void loadAdapterRecycler(List<AnswersQuestion> lable);

    void loadNextQuestion();

    void finishActivity(String message);

    void disableItemsAdapter();

    void showInfoTxtSecondary(boolean value);

    void processAnswer(boolean value);

    void tamizaje();

    void showProgress();

    void hideProgress();

}
