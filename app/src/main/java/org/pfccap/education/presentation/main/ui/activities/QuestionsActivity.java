package org.pfccap.education.presentation.main.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.crash.FirebaseCrash;

import org.pfccap.education.R;
import org.pfccap.education.dao.AnswersQuestion;
import org.pfccap.education.dao.Question;
import org.pfccap.education.presentation.main.adapters.AnswerSecondaryAdapter;
import org.pfccap.education.presentation.main.presenters.IQuestionPresenter;
import org.pfccap.education.presentation.main.presenters.QuestionPresenter;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;
import org.pfccap.education.utilities.Utilities;

import java.util.ArrayList;
import java.util.Arrays;
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

    private AnswerSecondaryAdapter adapter;
    private IQuestionPresenter questionPresenter;

    @BindView(R.id.progressBarQ)
    ProgressBar progressBar;

    private List<Question> lstQuestion;
    private int[] ramdomNumberSecuence;
    private int progressq = 0;
    private int currentQ = 0;

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
        try {
            lstQuestion = questionPresenter.getQuestionsDB();
            ramdomNumberSecuence = questionPresenter.ramdomNumberSecuence(lstQuestion.size());
            if (lstQuestion != null && ramdomNumberSecuence != null && lstQuestion.size() != 0 && ramdomNumberSecuence.length != 0) {
                questionPresenter.loadQuestionCurrent(lstQuestion, ramdomNumberSecuence[currentQ]);
            } else {
                finish();
            }
        }catch (Exception e){
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
        switch (Cache.getByKey(Constants.TYPE_Q)) {
            case "Evaluativa":
                txtPrimaryQuestion.setVisibility(View.VISIBLE);
                layoutButtons.setVisibility(View.GONE);
                recyclerViewAnswers.setVisibility(View.VISIBLE);
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
    public void setLabelButtonTrueFalse(String lableTrue, String valT, String lableFalse, String valF) {
        switch (Cache.getByKey(Constants.TYPE_Q)) {
            case "Riesgo":
                if (Boolean.valueOf(valT)){
                    btnTrue.setText(lableTrue);
                    btnFalse.setText(lableFalse);
                }else{
                    btnTrue.setText(lableFalse);
                    btnFalse.setText(lableTrue);
                }
                break;
            case "Evaluativa":
                btnTrue.setText(lableTrue);
                valueTrue.setText(valT);
                btnFalse.setText(lableFalse);
                valueFalse.setText(valF);
                break;
        }
    }

    @Override
    public void setInfoSnackbar(String text) {
        Utilities.snackbarNextAnswer(findViewById(android.R.id.content), text, context);
    }

    @Override
    public void loadAdapterRecycler(List<AnswersQuestion> answers) {
        adapter.clear();
        for (AnswersQuestion item : answers) {
            adapter.addItemSite(item);
        }
    }

    @Override
    public void saveAnswerQuestion() {

    }

    @Override
    public void loadNextQuestion() {
        currentQ = currentQ + 1; //se aumenta en uno la posición del array que contiene la secuencia de preguntas ramdom
        //TODO trasladar esta desición al presenter
        if (currentQ == lstQuestion.size()) {
            questionPresenter.finishAcivity();
        } else {
            questionPresenter.loadQuestionCurrent(lstQuestion, ramdomNumberSecuence[currentQ]);
        }
    }


    @Override
    public void finishActivity() {
        //TODO buscar la manera de concatenar resource string + la variable de chache
        txtPointsThk.setText("Los puntos obtenidos son " + Cache.getByKey(Constants.TOTAL_POINTS));
        lytThanks.setVisibility(View.VISIBLE);

        Cache.save(Constants.TOTAL_POINTS, "0");
    }

    @Override
    public void disableItemsAdapter() {
        adapter.disableItems();
    }


    @OnClick(R.id.mainQuestionThanks)
    public void clickFinish() {
        Utilities.initActivity(QuestionsActivity.this, MainActivity.class);
        finish();
    }

    @OnClick(R.id.mainQuestionBtnTrue)
    public void clickYes() {
        btnTrue.setEnabled(false);
        btnFalse.setEnabled(false);
        switch (Cache.getByKey(Constants.TYPE_Q)) {
            case "Riesgo":
                txtPrimaryQuestion.setText(Cache.getByKey(Constants.SECOND_Q));
                layoutButtons.setVisibility(View.GONE);
                recyclerViewAnswers.setVisibility(View.VISIBLE);
                txtInfo.setVisibility(View.GONE);
                break;
            case "Educativa":
                if(Boolean.valueOf(valueTrue.getText().toString())) {
                    questionPresenter.loadInfoSnackbar("¡Correcto!");
                }else{
                    questionPresenter.loadInfoSnackbar("¡Incorrecto!");
                }
                txtInfo.setVisibility(View.VISIBLE);
                txtInfo.setText(Cache.getByKey(Constants.INFO_SNACKBAR));
                break;
        }
    }

    @OnClick(R.id.mainQuestionBtnFalse)
    public void clickNO() {
        btnTrue.setEnabled(false);
        btnFalse.setEnabled(false);
        switch (Cache.getByKey(Constants.TYPE_Q)) {
            case "Riesgo":
                questionPresenter.loadInfoSnackbar("");
                break;
            case "Educativa":
                if(Boolean.valueOf(valueTrue.getText().toString())) {
                    questionPresenter.loadInfoSnackbar("¡Correcto!");
                }else{
                    questionPresenter.loadInfoSnackbar("¡Incorrecto!");
                }
                txtInfo.setVisibility(View.VISIBLE);
                txtInfo.setText(Cache.getByKey(Constants.INFO_SNACKBAR));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Cache.save(Constants.TOTAL_POINTS, "0");
        super.onBackPressed();
    }
}
