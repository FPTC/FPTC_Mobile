package org.pfccap.education.domain.questions;

import org.pfccap.education.dao.AnswersQuestion;
import org.pfccap.education.dao.Gift;
import org.pfccap.education.dao.Question;
import org.pfccap.education.dao.SecondAnswer;

import java.util.List;

/**
 * Created by USUARIO on 18/05/2017.
 */

public interface ILQuestionDB {

    List<Question> getAll(String typeCancer);

    List<AnswersQuestion> getAnswersByQuestion(String idQuestion);

    AnswersQuestion getAnswersByAnswers(String idQuestion, String idAnswer);

    List<SecondAnswer> getSecondAnswers(String idAnswer);

    void deleteDB();

    void questionAnswer(String idQuestion);

    void resetQuestion();

    List<Gift> getAllGift();
}
