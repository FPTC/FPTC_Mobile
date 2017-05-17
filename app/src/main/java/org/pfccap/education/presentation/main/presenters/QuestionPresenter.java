package org.pfccap.education.presentation.main.presenters;

import org.json.JSONObject;
import org.pfccap.education.domain.user.IUserBP;
import org.pfccap.education.domain.user.UserBP;
import org.pfccap.education.entities.UserAuth;
import org.pfccap.education.presentation.main.ui.activities.IQuestionView;

/**
 * Created by USUARIO on 08/05/2017.
 */

public class QuestionPresenter implements IQuestionPresenter {

    private IQuestionView questionView;
    private IUserBP userBP;

    public QuestionPresenter(IQuestionView questionView) {
        this.questionView = questionView;
        userBP = new UserBP();
    }

    @Override
    public void getQuestions() {

    }

    @Override
    public void saveAnswerQuestionDB(UserAuth user) {

    }

    @Override
    public void loadLablesAnswer() {
        //TODO traer el objeto de answer de la base de datos para sacar los textos de las opciones de respeusta de las preguntas tipo evaluativas
        //asi como las de las de tipo riesgo que esta en la pregunta anidada.

    }
}
