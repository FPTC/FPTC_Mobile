package org.pfccap.education.presentation.main.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import org.pfccap.education.R;
import org.pfccap.education.presentation.main.adapters.AnswerSecondaryAdapter;
import org.pfccap.education.presentation.main.presenters.IQuestionPresenter;
import org.pfccap.education.presentation.main.presenters.QuestionPresenter;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class QuestionsActivity extends AppCompatActivity implements IQuestionView{

    @BindView(R.id.mainQuestionTxtQuestion)
    TextView txtPrimaryQuestion;

    @BindView(R.id.mainQuestionBtnYes)
    Button btnYes;

    @BindView(R.id.mainQuestionBtnNo)
    Button btnNo;

    @BindView(R.id.mainQuestionTxtSecondQuestion)
    TextView txtSecondaryQuestion;

    @BindView(R.id.mainQuestionSpinnerAnswer)
    RecyclerView recyclerViewAnswers;

    private AnswerSecondaryAdapter adapter;
    private IQuestionPresenter questionPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        questionPresenter = new QuestionPresenter(QuestionsActivity.this);
        initAdapter();
        initRecyclerView();
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
    public void loadLableButtonAnswers(List<String> lables){
            for (String lable: lables){
                adapter.addItemSite(lable);
            }
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
