package org.pfccap.education.presentation.main.presenters;

import org.pfccap.education.dao.Question;

import java.util.List;

/**
 * Created by USUARIO on 02/05/2017.
 */

public interface IQuestionPresenter {

    List<Question> getQuestionsDB();

    void loadQuestionCurrent(List<Question> questions, int randomQ);

    void saveAnswerQuestionDB();

    void loadLablesAnswer(String numQst);

    void finishAcivity();

    int[] ramdomNumberSecuence(int num);


}
