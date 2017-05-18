package org.pfccap.education.domain.questions;

import org.pfccap.education.entities.Question;

/**
 * Created by USUARIO on 18/05/2017.
 */

public interface ILQuestionDB {

    Question load(String id);
}
