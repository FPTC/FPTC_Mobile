package org.pfccap.education.domain.questions;

import com.google.firebase.crash.FirebaseCrash;

import org.pfccap.education.application.AppDao;
import org.pfccap.education.dao.AnswersQuestion;
import org.pfccap.education.dao.AnswersQuestionDao;
import org.pfccap.education.dao.Question;
import org.pfccap.education.dao.QuestionDao;
import org.pfccap.education.dao.SecondAnswer;
import org.pfccap.education.dao.SecondAnswerDao;

import java.util.List;

/**
 * Created by USUARIO on 18/05/2017.
 */

public class LQuestionDB implements ILQuestionDB {

    private QuestionDao questionDao;
    private AnswersQuestionDao answersQuestionDao;
    private SecondAnswerDao secondAnswer;

    public LQuestionDB() {
        questionDao = AppDao.getQuestionDao();
        answersQuestionDao = AppDao.getAnswersQuestionDao();
        secondAnswer = AppDao.getSecondAnswerDao();
    }

    @Override
    public List<Question> getAll(String typeCancer) {
        try{
        return questionDao.queryBuilder()
                .where(QuestionDao.Properties.TypeCancer.eq(typeCancer))
                .list();
        } catch (Exception e) {
            FirebaseCrash.report(e);
            return null;
        }
    }

    @Override
    public List<AnswersQuestion> getAnswers(String idQuestion) {
        try {
            return answersQuestionDao.queryBuilder()
                    .where(AnswersQuestionDao.Properties.IdQuestion.eq(idQuestion))
                    .list();
        } catch (Exception e) {
            FirebaseCrash.report(e);
            return null;
        }
    }

    @Override
    public List<SecondAnswer> getSecondAnswers(String idQuestion) {
        try {
            return secondAnswer.queryBuilder()
                    .where(SecondAnswerDao.Properties.IdQuestion.eq(idQuestion))
                    .list();
        } catch (Exception e) {
            FirebaseCrash.report(e);
            return null;
        }
    }

    @Override
    public void deleteDB() {
        try {
            if (questionDao.loadAll() != null) {
                AppDao.getAnswersQuestionDao().deleteAll();
                AppDao.getQuestionDao().deleteAll();
                AppDao.getSecondAnswerDao().deleteAll();
            }
        } catch (Exception e) {
            FirebaseCrash.report(e);
            throw e;
        }
    }


}