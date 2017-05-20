package org.pfccap.education.domain.questions;

import org.pfccap.education.dao.Questions;
import java.util.List;

/**
 * Created by USUARIO on 18/05/2017.
 */

public interface ILQuestionDB {

    List<Questions> getAll(String typeCancer);
}
