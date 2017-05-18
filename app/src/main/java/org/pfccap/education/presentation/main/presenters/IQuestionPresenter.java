package org.pfccap.education.presentation.main.presenters;

import org.pfccap.education.entities.UserAuth;

/**
 * Created by USUARIO on 02/05/2017.
 */

public interface IQuestionPresenter {

    void getQuestionsDB(String numQst);

    void saveAnswerQuestionDB();

    void loadLablesAnswer(String numQst);

    void finishAcivity();


}
