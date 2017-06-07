package org.pfccap.education.presentation.main.presenters;

import org.pfccap.education.dao.Question;
import org.pfccap.education.entities.SendAnswers;

import java.util.List;

/**
 * Created by USUARIO on 02/05/2017.
 */

public interface IQuestionPresenter {

    void getQuestionsDB(int current);

    void loadQuestionCurrent(int randomQ);

    void saveAnswerQuestionDB(String typeAnswer, String idAnswer);

    void loadNextQuestion();

    void loadInfoSnackbar(String check);

    void calculatePointsCheck(int points, boolean value);

    void getSecondAnswers(String idAnswer);

    void backLastQuestion();

}
