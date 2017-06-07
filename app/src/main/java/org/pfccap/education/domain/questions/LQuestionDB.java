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
                .where(QuestionDao.Properties.TypeCancer.eq(typeCancer),
                        QuestionDao.Properties.Answer.eq(false))
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
    public List<SecondAnswer> getSecondAnswers(String idAnswer) {
        try {
            return secondAnswer.queryBuilder()
                    .where(SecondAnswerDao.Properties.IdAnswer.eq(idAnswer))
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

    @Override
    public void questionAnswer(String idQuestion) {
        try{
            Question question = questionDao.queryBuilder()
                    .where(QuestionDao.Properties.Idquest.eq(idQuestion))
                    .unique();

            if (question != null){
                question.setAnswer(true);
                questionDao.update(question);
            }
        }catch (Exception e){
            FirebaseCrash.report(e);
        }
    }

    @Override
    public void resetQuestion() {
        try{
            List<Question> questionList = questionDao.queryBuilder()
                    .where(QuestionDao.Properties.Answer.eq(true))
                    .list();
            for (Question question: questionList){
                question.setAnswer(false);
                questionDao.update(question);
            }
        }catch (Exception e){
            FirebaseCrash.report(e);
        }
    }


}