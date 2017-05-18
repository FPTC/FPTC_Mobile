package org.pfccap.education.presentation.main.presenters;

import com.google.firebase.database.DataSnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.pfccap.education.domain.questions.ILQuestionDB;
import org.pfccap.education.domain.questions.LQuestionDB;
import org.pfccap.education.domain.user.IUserBP;
import org.pfccap.education.domain.user.UserBP;
import org.pfccap.education.entities.Answer;
import org.pfccap.education.entities.Question;
import org.pfccap.education.entities.QuestionList;
import org.pfccap.education.entities.UserAuth;
import org.pfccap.education.presentation.main.ui.activities.IQuestionView;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by USUARIO on 08/05/2017.
 */

public class QuestionPresenter implements IQuestionPresenter {

    private IQuestionView questionView;
    private IUserBP userBP;
    int progress = 0;

    public QuestionPresenter(IQuestionView questionView) {
        this.questionView = questionView;
        userBP = new UserBP();
    }

    @Override
    public void getQuestionsDB(String numQst) {
        ILQuestionDB lquestion = new LQuestionDB();
        Question listQuestions = lquestion.load(numQst);

        progress = (100/Integer.valueOf(Cache.getByKey(Constants.PROGRESS_BAR)));
        questionView.setProgressBar(progress);

        Cache.save(Constants.NEXT_Q, numQst);
        List<String> lables;
        switch (listQuestions.getTypeQuestion()) {
            case "Evaluativa":
                Cache.save(Constants.TYPE_Q, listQuestions.getTypeQuestion());
                questionView.setPrimaryQuestion(listQuestions.getText());
                lables = new ArrayList<>();
                lables.add("Virus del papiloma humano");
                lables.add("Virus del Zika");
                lables.add("Virus de la gripa");
                questionView.loadAdapterRecycler(lables);
                break;
            case "Riesgo":
                Cache.save(Constants.TYPE_Q, listQuestions.getTypeQuestion());
                questionView.setPrimaryQuestion(listQuestions.getText());
             //   if (listQuestions.getAnswers().get("id234581").isValue()) {
                    questionView.setLabelButtonYesNo("si", "no");
                    Cache.save(Constants.SECOND_Q, "¿Cuando fue tu última citología?");
                    lables = new ArrayList<>();
                    lables.add("Hace menos de un año");
                    lables.add("Hace más de un año");
                    lables.add("No recuerdo");
                    questionView.loadAdapterRecycler(lables);
         //       }
                break;
            case "Educativa":
                Cache.save(Constants.TYPE_Q, listQuestions.getTypeQuestion());
                questionView.setPrimaryQuestion(listQuestions.getText());
             //   if (listQuestions.getAnswers().get("id234574").isValue()) {
                questionView.setLabelButtonYesNo("SI",
                            "NO");
           //     }
                break;
        }


    }

    @Override
    public void saveAnswerQuestionDB() {
        questionView.setInfoSnackbar("", Cache.getByKey(Constants.NEXT_Q));
    }

    @Override
    public void loadLablesAnswer(String numQst) {
        ILQuestionDB lquestion = new LQuestionDB();
        Question listQuestions = lquestion.load(numQst);
        questionView.setInfoSnackbar(listQuestions.getInfo(), numQst);
    }

    @Override
    public void finishAcivity() {
        questionView.finishActivity();
    }
}
