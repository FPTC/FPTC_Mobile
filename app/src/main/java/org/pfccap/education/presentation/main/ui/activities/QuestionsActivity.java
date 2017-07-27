package org.pfccap.education.presentation.main.ui.activities;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.crash.FirebaseCrash;

import org.pfccap.education.R;
import org.pfccap.education.dao.AnswersQuestion;
import org.pfccap.education.presentation.main.adapters.AnswerQuestionsAdapter;
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

    private AnswerQuestionsAdapter adapter;
    private IQuestionPresenter questionPresenter;

    @BindView(R.id.progressBarQ)
    ProgressBar progressBar;

    private int progressq = 0;
    private int current = 0;

    private GradientDrawable shapeSecondary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        ButterKnife.bind(this);
        questionPresenter = new QuestionPresenter(this, QuestionsActivity.this);

        initColorTxtByCancer();
        initAdapter();
        initRecyclerView();
        initQuestion();
    }


    private void initColorTxtByCancer() {
        //esto es para cambiar de color los campos de texto según el cáncer
        LayerDrawable drawableFilePrimary = (LayerDrawable) txtPrimaryQuestion.getBackground();
        final GradientDrawable shapePrimary = (GradientDrawable) drawableFilePrimary.findDrawableByLayerId(R.id.shape_txt_primary);

        LayerDrawable drawableFileSecondary = (LayerDrawable) txtInfo.getBackground();
        shapeSecondary = (GradientDrawable) drawableFileSecondary.findDrawableByLayerId(R.id.shape_txt_secondary);


        switch (Cache.getByKey(Constants.TYPE_CANCER)) {
            case Constants.BREAST:
                shapePrimary.setColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
                setPrimaryProgressColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

                break;
            case Constants.CERVIX:
                shapePrimary.setColor(ContextCompat.getColor(this, R.color.colorBlue));
                setPrimaryProgressColor(ContextCompat.getColor(this, R.color.colorBlue));

                break;
        }
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

    public void setPrimaryProgressColor(int colorInstance) {
        if (progressBar.getProgressDrawable() instanceof LayerDrawable) {
            LayerDrawable layered = (LayerDrawable) progressBar.getProgressDrawable();
            Drawable circleDrawableExample = layered.getDrawable(1);
            circleDrawableExample.setColorFilter(colorInstance, PorterDuff.Mode.SRC_IN);
            progressBar.setProgressDrawable(layered);
        } else {
            progressBar.getProgressDrawable().setColorFilter(colorInstance, PorterDuff.Mode.SRC_IN);
        }
    }

    private void initAdapter() {
        if (adapter == null) {
            adapter = new AnswerQuestionsAdapter(new ArrayList<AnswersQuestion>(), questionPresenter);
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
                layoutButtons.setVisibility(View.GONE);
                recyclerViewAnswers.setVisibility(View.VISIBLE);
                break;
            case "Educativa":
                layoutButtons.setVisibility(View.GONE);
                recyclerViewAnswers.setVisibility(View.VISIBLE);
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
    public void showInfoTxtSecondary(boolean value) {
        SpannableString info;
        if (value) {
            info = formatInfoTextAnswer(getResources().getString(R.string.right));
            shapeSecondary.setColor(ContextCompat.getColor(this, R.color.answerColorRight));
        } else {
            info = formatInfoTextAnswer(getResources().getString(R.string.fail));
            shapeSecondary.setColor(ContextCompat.getColor(this, R.color.answerColorFail));
        }
        txtInfo.setVisibility(View.VISIBLE);
        recyclerViewAnswers.setVisibility(View.GONE);
        txtInfo.setText(info); //esto es los mensajes complementarios que se muestran en algunas preguntas
    }


    @OnClick(R.id.mainQuestionThanks)
    public void clickFinish() {
        Utilities.initActivity(QuestionsActivity.this, MainActivity.class);
        finish();
    }


    @Override
    public void onBackPressed() {
        questionPresenter.backLastQuestion(); //se verifica si ya ha contestado todas las preguntas
        super.onBackPressed();
    }

    @Override
    public void processAnswer(boolean value) {
        SpannableString info;
        switch (Cache.getByKey(Constants.TYPE_Q)) {
            case Constants.RIESGO:
                if (!Cache.getByKey(Constants.SECOND_Q).equals("")) {
                    txtPrimaryQuestion.setText(Cache.getByKey(Constants.SECOND_Q));
                } else {
                    if (!Cache.getByKey(Constants.INFO_TEACH).equals("")) {
                        info = formatInfoTextAnswer(getResources().getString(R.string.warning_answers));
                        shapeSecondary.setColor(ContextCompat.getColor(this, R.color.answerColorWarning));
                        txtInfo.setVisibility(View.VISIBLE);
                        recyclerViewAnswers.setVisibility(View.GONE);
                        txtInfo.setText(info);
                    }
                }
                break;
            case Constants.EDUCATIVA:
                if (value) {
                    info = formatInfoTextAnswer(getResources().getString(R.string.right));
                    shapeSecondary.setColor(ContextCompat.getColor(this, R.color.answerColorRight));
                } else {
                    info = formatInfoTextAnswer(getResources().getString(R.string.fail));
                    shapeSecondary.setColor(ContextCompat.getColor(this, R.color.answerColorFail));
                }
                txtInfo.setVisibility(View.VISIBLE);
                recyclerViewAnswers.setVisibility(View.GONE);
                txtInfo.setText(info);
                break;
        }
    }

    private SpannableString formatInfoTextAnswer(String s) {
        //esto es para agregar el subtitulo en el mismo edittext que dice correcto, incorrecto o cuidado
        s = s + "\n\n" + Cache.getByKey(Constants.INFO_TEACH);
        SpannableString ss1 = new SpannableString(s);
        ss1.setSpan(new RelativeSizeSpan(1.2f), 0, 12, 0);
        return ss1;
    }
}
