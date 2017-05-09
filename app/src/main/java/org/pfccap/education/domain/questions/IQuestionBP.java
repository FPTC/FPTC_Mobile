package org.pfccap.education.domain.questions;

import org.pfccap.education.entities.Questions;
import org.pfccap.education.entities.UserAuth;

import io.reactivex.Observable;

/**
 * Created by jggomez on 02-May-17.
 */

public interface IQuestionBP {

    Observable<Questions> getQuestions();

    void save(Questions questions);
}
