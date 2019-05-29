package org.pfccap.education.domain.questions;

import com.google.firebase.crash.FirebaseCrash;

import org.pfccap.education.application.AppDao;
import org.pfccap.education.dao.AnswersQuestion;
import org.pfccap.education.dao.AnswersQuestionDao;
import org.pfccap.education.dao.Gift;
import org.pfccap.education.dao.GiftDao;
import org.pfccap.education.dao.PaisesDao;
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
    private GiftDao giftDao;
    private PaisesDao paisesDao;

    //clase para obtener información de la base de datos local
    public LQuestionDB() {
        questionDao = AppDao.getQuestionDao();
        answersQuestionDao = AppDao.getAnswersQuestionDao();
        secondAnswer = AppDao.getSecondAnswerDao();
        giftDao = AppDao.getGiftDao();
        paisesDao = AppDao.getCountriesDao();
    }

    //se obtiene todas las preguntas que no han sido contestadas
    @Override
    public List<Question> getAll(String typeCancer) {
        try {
            return questionDao.queryBuilder()
                    .where(QuestionDao.Properties.TypeCancer.eq(typeCancer),
                            QuestionDao.Properties.Answer.eq(false))
                    .orderDesc()
                    .list();
        } catch (Exception e) {
            FirebaseCrash.report(e);
            return null;
        }
    }

    @Override
    public List<Question> getAllWithoutFilter(String typeCancer) {
        try {
            return questionDao.queryBuilder()
                    .where(QuestionDao.Properties.TypeCancer.eq(typeCancer))
                    .orderDesc()
                    .list();
        } catch (Exception e) {
            FirebaseCrash.report(e);
            return null;
        }
    }

    //se obtiene  todas las respestas de la pregunta actual
    @Override
    public List<AnswersQuestion> getAnswersByQuestion(String idQuestion) {
        try {
            return answersQuestionDao.queryBuilder()
                    .where(AnswersQuestionDao.Properties.IdQuestion.eq(idQuestion))
                    .orderAsc(AnswersQuestionDao.Properties.Description)
                    .list();
        } catch (Exception e) {
            FirebaseCrash.report(e);
            return null;
        }
    }

    //se obtiene una la repuesta
    @Override
    public AnswersQuestion getAnswersByAnswers(String idQuestion, String idAnswer) {
        try {
            return answersQuestionDao.queryBuilder()
                    .where(AnswersQuestionDao.Properties.IdQuestion.eq(idQuestion), AnswersQuestionDao.Properties.IdAnswer.eq(idAnswer))
                    .unique();
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
                    .orderDesc(SecondAnswerDao.Properties.Description)
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
                AppDao.getGiftDao().deleteAll();
                //se agregaron tablas nuevas
                if (paisesDao.loadAll() != null) {
                    AppDao.getCountriesDao().deleteAll();
                    AppDao.getCitiesDao().deleteAll();
                    AppDao.getComunasDao().deleteAll();
                    AppDao.getEseDao().deleteAll();
                    AppDao.getIpsDao().deleteAll();
                }
            }
        } catch (Exception e) {
            FirebaseCrash.report(e);
            throw e;
        }
    }

    @Override
    public void questionAnswer(String idQuestion) {
        try {
            Question question = questionDao.queryBuilder()
                    .where(QuestionDao.Properties.Idquest.eq(idQuestion))
                    .unique();

            if (question != null) {
                question.setAnswer(true);
                questionDao.update(question);
            }
        } catch (Exception e) {
            FirebaseCrash.report(e);
        }
    }

    @Override
    public void resetQuestion() {
        try {
            List<Question> questionList = questionDao.queryBuilder()
                    .where(QuestionDao.Properties.Answer.eq(true))
                    .list();
            for (Question question : questionList) {
                question.setAnswer(false);
                questionDao.update(question);
            }
        } catch (Exception e) {
            FirebaseCrash.report(e);
        }
    }

    @Override
    public List<Gift> getAllGift() {
        return giftDao.queryBuilder()
                .orderAsc(GiftDao.Properties.Order)
                .list();
    }

}