package org.pfccap.education.domain.questions;

import org.pfccap.education.application.AppDao;
import org.pfccap.education.dao.Questions;
import org.pfccap.education.dao.QuestionsDao;

import java.util.List;

/**
 * Created by USUARIO on 18/05/2017.
 */

public class LQuestionDB implements ILQuestionDB {

    private QuestionsDao questionDao;

    public LQuestionDB(){
        questionDao = AppDao.getQuestionsDao();
    }

    @Override
    public List<Questions> getAll(String typeCancer) {
        List<Questions> lstQuestions = questionDao.queryBuilder()
                .where(QuestionsDao.Properties.TypeCancer.eq(typeCancer))
                .list();

        return lstQuestions;
    }
}