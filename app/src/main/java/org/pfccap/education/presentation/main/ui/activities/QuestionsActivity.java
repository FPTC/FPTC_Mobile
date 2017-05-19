package org.pfccap.education.presentation.main.ui.activities;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.pfccap.education.R;
import org.pfccap.education.entities.Question;
import org.pfccap.education.presentation.main.adapters.AnswerSecondaryAdapter;
import org.pfccap.education.presentation.main.presenters.IQuestionPresenter;
import org.pfccap.education.presentation.main.presenters.QuestionPresenter;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;
import org.pfccap.education.utilities.Utilities;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuestionsActivity extends AppCompatActivity implements IQuestionView{

    @BindView(R.id.mainQuestionTxtQuestion)
    TextView txtPrimaryQuestion;

    @BindView(R.id.mainQuestionBtnYes)
    Button btnYes;

    @BindView(R.id.mainQuestionBtnNo)
    Button btnNo;

    @BindView(R.id.mainQuestionRecyclerAnswer)
    RecyclerView recyclerViewAnswers;

    @BindView(R.id.questionLayoutButtons)
    LinearLayout layoutButtons;

    @BindView(R.id.mainQuestionLayoutThanks)
    RelativeLayout lytThanks;

    @BindView(R.id.mainQuestionTxtPoints)
    TextView txtPoints;

    @BindView(R.id.mainQuestionTxtPointThanks)
    TextView txtPointsThk;

    private AnswerSecondaryAdapter adapter;
    private IQuestionPresenter questionPresenter;

    @BindView(R.id.progressBarQ)
    ProgressBar progressBar;

    String numQst = "0";

    private int progressq = 0;

    QuestionsActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        ButterKnife.bind(this);
        context = QuestionsActivity.this;
        questionPresenter = new QuestionPresenter(QuestionsActivity.this);
        initAdapter();
        initRecyclerView();
        initQuestion();
    }

    private void initQuestion() {
      questionPresenter.getQuestionsDB(numQst);
    }

    private void initAdapter() {
        if (adapter == null){
            adapter = new AnswerSecondaryAdapter(new ArrayList<String>(), questionPresenter);
        }
    }

    private void initRecyclerView() {
        recyclerViewAnswers.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAnswers.setAdapter(adapter);
    }

    @Override
    public void setPrimaryQuestion(String text) {
        switch (Cache.getByKey(Constants.TYPE_Q)) {
            case "Evaluativa":
                txtPrimaryQuestion.setVisibility(View.VISIBLE);
                layoutButtons.setVisibility(View.GONE);
                recyclerViewAnswers.setVisibility(View.VISIBLE);
                txtPoints.setText("8");
                break;
            case "Riesgo":
                txtPrimaryQuestion.setVisibility(View.VISIBLE);
                layoutButtons.setVisibility(View.VISIBLE);
                recyclerViewAnswers.setVisibility(View.GONE);
                break;
            case "Educativa":
                txtPrimaryQuestion.setVisibility(View.VISIBLE);
                layoutButtons.setVisibility(View.VISIBLE);
                recyclerViewAnswers.setVisibility(View.GONE);
                break;
        }

        txtPrimaryQuestion.setText(text);
    }

    @Override
    public void setProgressBar(int progress) {
        progressq = progressq + progress;
        progressBar.setProgress(progressq);
    }

    @Override
    public void setLabelButtonYesNo(String Yes, String No) {
        btnNo.setText(No);
        btnYes.setText(Yes);
    }

    @Override
    public void setInfoSnackbar(String text, String idQ) {
        Utilities.snackbarNextAnswer(findViewById(android.R.id.content), text, idQ, context);
    }

    @Override
    public void loadAdapterRecycler(List<String> lable) {
        adapter.clear();
        for(String item: lable){
            adapter.addItemSite(item);
        }
    }

    @Override
    public void saveAnswerQuestion() {

    }


    @Override
    public void finishActivity() {
        txtPointsThk.setText("Has obtenido 8 puntos");
        lytThanks.setVisibility(View.VISIBLE);

    }

    @OnClick(R.id.mainQUestionThanks)
    public void clickFinish(){
        Utilities.initActivity(QuestionsActivity.this, MainActivity.class);
        finish();
    }

    @OnClick(R.id.mainQuestionBtnYes)
    public void clickYes(){
        numQst = Cache.getByKey(Constants.NEXT_Q);
        switch (Cache.getByKey(Constants.TYPE_Q)) {
            case "Riesgo":
                txtPrimaryQuestion.setText(Cache.getByKey(Constants.SECOND_Q));
                layoutButtons.setVisibility(View.GONE);
                recyclerViewAnswers.setVisibility(View.VISIBLE);
                break;
            case "Educativa":
                questionPresenter.loadLablesAnswer(numQst);
                break;
        }
    }

    @OnClick(R.id.mainQuestionBtnNo)
    public void clickNO(){
        numQst = Cache.getByKey(Constants.NEXT_Q);
        switch (Cache.getByKey(Constants.TYPE_Q)) {
            case "Riesgo":
                questionPresenter.saveAnswerQuestionDB();
                break;
            case "Educativa":
                questionPresenter.loadLablesAnswer(numQst);
                break;
        }
    }
}
