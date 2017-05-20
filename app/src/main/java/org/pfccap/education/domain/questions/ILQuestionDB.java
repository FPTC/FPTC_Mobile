package org.pfccap.education.domain.questions;

import org.pfccap.education.dao.Question;

import java.util.List;

/**
 * Created by USUARIO on 18/05/2017.
 */

public interface ILQuestionDB {

    List<Question> getAll(String typeCancer);
}
