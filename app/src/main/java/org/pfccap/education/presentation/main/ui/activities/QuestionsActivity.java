package org.pfccap.education.presentation.main.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.crash.FirebaseCrash;

import org.pfccap.education.R;
import org.pfccap.education.dao.AnswersQuestion;
import org.pfccap.education.presentation.main.adapters.AnswerSecondaryAdapter;
import org.pfccap.education.presentation.main.presenters.IQuestionPresenter;
import org.pfccap.education.presentation.main.presenters.QuestionPresenter;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;
import org.pfccap.education.utilities.Utilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuestionsActivity extends AppCompatActivity implements IQuestionView {

    private static final String TAG = "error";
    @BindView(R.id.mainQuestionTxtQuestion)
    TextView txtPrimaryQuestion;

    @BindView(R.id.mainQuestionBtnTrue)
    Button btnTrue;

    @BindView(R.id.mainQuestionTxtTrue)
    TextView valueTrue;

    @BindView(R.id.mainQuestionBtnFalse)
    Button btnFalse;

    @BindView(R.id.mainQuestionTxtFalse)
    TextView valueFalse;

    @BindView(R.id.mainQuestionRecyclerAnswer)
    RecyclerView recyclerViewAnswers;

    @BindView(R.id.questionLayoutButtons)
    LinearLayout layoutButtons;

    @BindView(R.id.mainQuestionLayoutThanks)
    RelativeLayout lytThanks;

    @BindView(R.id.mainQuestionTxtPointThanks)
    TextView txtPointsThk;

    @BindView(R.id.mainQuestionTxtInfo)
    TextView txtInfo;

    @BindView(R.id.mainQuestionTxtPoints)
    TextView txtPoints;

    private AnswerSecondaryAdapter adapter;
    private IQuestionPresenter questionPresenter;

    @BindView(R.id.progressBarQ)
    ProgressBar progressBar;

    private int progressq = 0;
    private int current = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        ButterKnife.bind(this);
        questionPresenter = new QuestionPresenter(this, QuestionsActivity.this);
        initAdapter();
        initRecyclerView();
        initQuestion();
    }

    private void initQuestion() {
        try {
            if (Cache.getByKey(Constants.CURRENT_POINTS_C).equals("") || Cache.getByKey(Constants.TOTAL_POINTS_C).equals("")) {
                Cache.save(Constants.CURRENT_POINTS_C, "0");
                Cache.save(Constants.TOTAL_POINTS_C, "0"); //se guarda las variables con cero al iniciar la app para evitar errores en validaciones
            }

            if (Cache.getByKey(Constants.CURRENT_POINTS_B).equals("") || Cache.getByKey(Constants.TOTAL_POINTS_B).equals("")) {
                Cache.save(Constants.CURRENT_POINTS_B, "0");
                Cache.save(Constants.TOTAL_POINTS_B, "0");
            }

            questionPresenter.getQuestionsDB(current);
        } catch (Exception e) {
            FirebaseCrash.report(e);
        }

    }

    private void initAdapter() {
        if (adapter == null) {
            adapter = new AnswerSecondaryAdapter(new ArrayList<AnswersQuestion>(), questionPresenter);
        }
    }

    private void initRecyclerView() {
        recyclerViewAnswers.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAnswers.setAdapter(adapter);
    }

    @Override
    public void setPrimaryQuestion(String text) {
        btnFalse.setEnabled(true);
        btnTrue.setEnabled(true);
        txtPrimaryQuestion.setVisibility(View.VISIBLE);
        txtInfo.setVisibility(View.GONE);
        switch (Cache.getByKey(Constants.TYPE_Q)) {
            case "Evaluativa":
                layoutButtons.setVisibility(View.GONE);
                recyclerViewAnswers.setVisibility(View.VISIBLE);
                break;
            case "Riesgo":
                layoutButtons.setVisibility(View.VISIBLE);
                recyclerViewAnswers.setVisibility(View.GONE);
                break;
            case "Educativa":
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
        switch (Cache.getByKey(Constants.TYPE_CANCER)) {
            case Constants.CERVIX:
                txtPoints.setText(Cache.getByKey(Constants.CURRENT_POINTS_C)); //se setea los puntos en el circulo del progressbar
                break;
            case Constants.BREAST:
                txtPoints.setText(Cache.getByKey(Constants.CURRENT_POINTS_B));
                break;
        }

    }

    @Override
    public void setLabelButtonTrueFalse(String lableTrue, String valT, String lableFalse, String valF) {
        btnTrue.setText(lableTrue);
        valueTrue.setText(valT);
        btnFalse.setText(lableFalse);
        valueFalse.setText(valF);
    }

    @Override
    public void setInfoSnackbar(String text) {
        Utilities.snackbarNextAnswer(findViewById(android.R.id.content), text, QuestionsActivity.this);
    }

    @Override
    public void loadAdapterRecycler(List<AnswersQuestion> answers) {
        adapter.clear();
        for (AnswersQuestion item : answers) {
            adapter.addItemSite(item);
        }
    }

    @Override
    public void loadNextQuestion() {
        current = current + 1; //se aumenta en uno la posición del array que contiene la secuencia de preguntas ramdom
        questionPresenter.loadQuestionCurrent(current);
    }


    @Override
    public void finishActivity(String message) {
        txtPointsThk.setText(message);
        lytThanks.setVisibility(View.VISIBLE);
        switch (Cache.getByKey(Constants.TYPE_CANCER)) {
            case Constants.CERVIX:
                Cache.save(Constants.CURRENT_POINTS_C, "0");
                break;
            case Constants.BREAST:
                Cache.save(Constants.CURRENT_POINTS_B, "0"); //se reinicia los puntos al finalizar la encuesta
                break;
        }
    }

    @Override
    public void disableItemsAdapter() {
        adapter.disableItems();
    }

    @Override
    public void showInfoTxtSecondary() {
        txtInfo.setVisibility(View.VISIBLE);
        layoutButtons.setVisibility(View.GONE);
        recyclerViewAnswers.setVisibility(View.GONE);
        txtInfo.setText(Cache.getByKey(Constants.INFO_TEACH)); //esto es los mensajes complementarios que se muestran en algunas preguntas
    }


    @OnClick(R.id.mainQuestionThanks)
    public void clickFinish() {
        Utilities.initActivity(QuestionsActivity.this, MainActivity.class);
        finish();
    }

    @OnClick({R.id.mainQuestionBtnFalse, R.id.mainQuestionBtnTrue})
    public void clickFalseOrTrueBtn(Button button) {
        btnTrue.setEnabled(false);
        btnFalse.setEnabled(false);
        switch (button.getId()) {
            case R.id.mainQuestionBtnFalse:
                processAnswer(Cache.getByKey(Constants.TURN_ANSWER), Cache.getByKey(Constants.ANSWER_FALSE_ID),
                        Cache.getByKey(Constants.SECOND_QFALSE), Boolean.valueOf(valueFalse.getText().toString()));
                break;
            case R.id.mainQuestionBtnTrue:
                processAnswer(Cache.getByKey(Constants.TURN_ANSWER), Cache.getByKey(Constants.ANSWER_TRUE_ID),
                        Cache.getByKey(Constants.SECOND_QTRUE), Boolean.valueOf(valueTrue.getText().toString()));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        questionPresenter.backLastQuestion(); //se verifica si ya ha contestado todas las preguntas
        super.onBackPressed();
    }

    void processAnswer(String turnAnswer, String answerId, String txtSecondAnswer, boolean value) {
        questionPresenter.saveAnswerQuestionDB(turnAnswer,
                answerId);//TODO: almacena respeusta en firebase, falta agregar el UID de usuario

        switch (Cache.getByKey(Constants.TYPE_Q)) {
            case Constants.RIESGO:
                if (!txtSecondAnswer.equals("")) {
                    questionPresenter.getSecondAnswers(answerId);
                    txtPrimaryQuestion.setText(txtSecondAnswer);
                    layoutButtons.setVisibility(View.GONE);
                    recyclerViewAnswers.setVisibility(View.VISIBLE);

                } else {

                    if (!Cache.getByKey(Constants.INFO_TEACH).equals("")) {
                        txtInfo.setVisibility(View.VISIBLE);
                        layoutButtons.setVisibility(View.GONE);
                        recyclerViewAnswers.setVisibility(View.GONE);
                        txtInfo.setText(Cache.getByKey(Constants.INFO_TEACH));
                    }
                    questionPresenter.loadInfoSnackbar(getString(R.string.thanks_for_answers));
                }
                break;
            case Constants.EDUCATIVA:
                if (value) {
                    questionPresenter.loadInfoSnackbar(getString(R.string.right));
                } else {
                    questionPresenter.loadInfoSnackbar(getString(R.string.fail));
                }
                txtInfo.setVisibility(View.VISIBLE);
                layoutButtons.setVisibility(View.GONE);
                txtInfo.setText(Cache.getByKey(Constants.INFO_TEACH));
                break;
        }
    }
}
