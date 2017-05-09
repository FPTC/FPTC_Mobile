package org.pfccap.education.presentation.main.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.pfccap.education.R;

public class QuestionsActivity extends AppCompatActivity implements IQuestionView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
    }

    @Override
    public void loadPrimaryQuestion(String equestion) {

    }

    @Override
    public void loadSecondQuestion(String question) {

    }

    @Override
    public void clickBtnPrimatyQuestion() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void clickBtnSecondQuestion() {

    }

    @Override
    public void finishActivity() {

    }
}
