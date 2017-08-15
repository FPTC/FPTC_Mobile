package org.pfccap.education.application;

import org.pfccap.education.dao.AnswersQuestionDao;
import org.pfccap.education.dao.GiftDao;
import org.pfccap.education.dao.QuestionDao;
import org.pfccap.education.dao.SecondAnswerDao;
import org.pfccap.education.dao.UserDao;

/**
 * Created by USUARIO on 04/04/2017.
 */

public class AppDao {
    // se utiliza para poder acceder a la base de datos generada con greendao
    private static UserDao userDao;
    private static QuestionDao questionDao;
    private static AnswersQuestionDao answersQuestionDao;
    private static SecondAnswerDao secondAnswerDao;
    private static GiftDao giftDao;

    public static UserDao getUserDao() {
        return userDao;
    }

    public static void setUserDao(UserDao userDao) {
        AppDao.userDao = userDao;
    }

    public static QuestionDao getQuestionDao() {
        return questionDao;
    }

    public static void setQuestionDao(QuestionDao questionDao) {
        AppDao.questionDao = questionDao;
    }

    public static AnswersQuestionDao getAnswersQuestionDao() {
        return answersQuestionDao;
    }

    public static void setAnswersQuestionDao(AnswersQuestionDao answersQuestionDao) {
        AppDao.answersQuestionDao = answersQuestionDao;
    }

    public static SecondAnswerDao getSecondAnswerDao() {
        return secondAnswerDao;
    }

    public static void setSecondAnswerDao(SecondAnswerDao secondAnswerDao) {
        AppDao.secondAnswerDao = secondAnswerDao;
    }

    public static GiftDao getGiftDao() {
        return giftDao;
    }

    public static void setGiftDao(GiftDao giftDao) {
        AppDao.giftDao = giftDao;
    }
}
