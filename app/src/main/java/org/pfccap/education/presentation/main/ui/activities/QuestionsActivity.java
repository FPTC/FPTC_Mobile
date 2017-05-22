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
import android.widget.TextView;

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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuestionsActivity extends AppCompatActivity implements IQuestionView{

    @BindView(R.id.mainQuestionTxtQuestion)
    TextView txtPrimaryQuestion;

    @BindView(R.id.mainQuestionBtnTrue)
    Button btnTrue;

    @BindView(R.id.mainQuestionBtnFalse)
    Button btnFalse;

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
      lstQuestion = questionPresenter.getQuestionsDB();
      ramdomNumberSecuence = questionPresenter.ramdomNumberSecuence(lstQuestion.size());
      questionPresenter.loadQuestionCurrent(lstQuestion, ramdomNumberSecuence[currentQ]);
    }

    private void initAdapter() {
        if (adapter == null){
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
        recyclerViewAnswers.setEnabled(true);
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
        //TODO se pone el set de puntos actuales provisional
        txtPoints.setText(Cache.getByKey(Constants.TOTAL_POINTS));
    }

    @Override
    public void setLabelButtonTrueFalse(String lableTrue, String lableFalse) {
        btnTrue.setText(lableTrue);
        btnFalse.setText(lableFalse);
    }

    @Override
    public void setInfoSnackbar(String text) {
        Utilities.snackbarNextAnswer(findViewById(android.R.id.content), text, context);
    }

    @Override
    public void loadAdapterRecycler(List<AnswersQuestion> answers) {
        adapter.clear();
        for(AnswersQuestion item: answers){
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
        if (currentQ>=lstQuestion.size()){
            questionPresenter.finishAcivity();
        }else{
            questionPresenter.loadQuestionCurrent(lstQuestion, ramdomNumberSecuence[currentQ]);

        }
    }


    @Override
    public void finishActivity() {
        //TODO buscar la manera de concatenar resource string + la variable de chache
        txtPointsThk.setText("Los puntos obtenidos son " + Cache.getByKey(Constants.TOTAL_POINTS));
        lytThanks.setVisibility(View.VISIBLE);
        Cache.save(Constants.TOTAL_POINTS, "");
    }


    @OnClick(R.id.mainQUestionThanks)
    public void clickFinish(){
        Utilities.initActivity(QuestionsActivity.this, MainActivity.class);
        finish();
    }

    @OnClick(R.id.mainQuestionBtnTrue)
    public void clickYes(){
        btnTrue.setEnabled(false);
        btnFalse.setEnabled(false);
        switch (Cache.getByKey(Constants.TYPE_Q)) {
            case "Riesgo":
                txtPrimaryQuestion.setText(Cache.getByKey(Constants.SECOND_Q));
                layoutButtons.setVisibility(View.GONE);
                recyclerViewAnswers.setVisibility(View.VISIBLE);
                break;
            case "Educativa":
                questionPresenter.loadInfoSnakbar("¡Correcto!");
                break;
        }
    }

    @OnClick(R.id.mainQuestionBtnFalse)
    public void clickNO(){
        btnTrue.setEnabled(false);
        btnFalse.setEnabled(false);
        switch (Cache.getByKey(Constants.TYPE_Q)) {
            case "Riesgo":
                questionPresenter.loadInfoSnakbar("");
                Cache.save(Constants.INFO_SNACKBAR, "");
                break;
            case "Educativa":
                questionPresenter.loadInfoSnakbar("¡Incorrecto!");
                break;
        }
    }
}
