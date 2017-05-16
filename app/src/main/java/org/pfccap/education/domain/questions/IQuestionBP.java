package org.pfccap.education.domain.questions;

import org.pfccap.education.entities.QuestionList;

import io.reactivex.Observable;

/**
 * Created by jggomez on 02-May-17.
 */

public interface IQuestionBP {

    Observable<QuestionList> getQuestions();

    void save(QuestionList questionsListAll);
}
