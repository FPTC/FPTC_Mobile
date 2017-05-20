package org.pfccap.education.domain.questions;

import org.pfccap.education.application.AppDao;
import org.pfccap.education.dao.Question;
import org.pfccap.education.dao.QuestionDao;

import java.util.List;

/**
 * Created by USUARIO on 18/05/2017.
 */

public class LQuestionDB implements ILQuestionDB {

    private QuestionDao questionDao;

    public LQuestionDB() {
        questionDao = AppDao.getQuestionsDao();
    }

    @Override
    public List<Question> getAll(String typeCancer) {
        List<Question> lstQuestions = questionDao.queryBuilder()
                .where(QuestionDao.Properties.TypeCancer.eq(typeCancer))
                .list();

        return lstQuestions;
    }
}