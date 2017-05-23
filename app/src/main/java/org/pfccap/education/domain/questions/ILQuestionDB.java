package org.pfccap.education.domain.questions;

import org.pfccap.education.dao.AnswersQuestion;
import org.pfccap.education.dao.Question;
import org.pfccap.education.dao.SecondAnswer;

import java.util.List;

/**
 * Created by USUARIO on 18/05/2017.
 */

public interface ILQuestionDB {

    List<Question> getAll(String typeCancer);

    List<AnswersQuestion> getAnswers (String idQuestion);

    List<SecondAnswer> getSecondAnswers (String idQuestion);

    void deleteDB();
}