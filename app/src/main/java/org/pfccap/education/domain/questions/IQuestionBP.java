package org.pfccap.education.domain.questions;

import org.pfccap.education.entities.QuestionsListAll;

import io.reactivex.Observable;

/**
 * Created by jggomez on 02-May-17.
 */

public interface IQuestionBP {

    Observable<QuestionsListAll> getQuestions();

    void save(QuestionsListAll questionsListAll);
}
