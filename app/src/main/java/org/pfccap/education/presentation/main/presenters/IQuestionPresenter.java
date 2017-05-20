package org.pfccap.education.presentation.main.presenters;

import org.pfccap.education.dao.Questions;

import java.util.List;

/**
 * Created by USUARIO on 02/05/2017.
 */

public interface IQuestionPresenter {

    List<Questions> getQuestionsDB();

    void loadQuestionCurrent(List<Questions> questions, int randomQ);

    void saveAnswerQuestionDB();

    void loadLablesAnswer(String numQst);

    void finishAcivity();

    int[] ramdomNumberSecuence(int num);


}
