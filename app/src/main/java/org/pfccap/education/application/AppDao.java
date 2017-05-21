package org.pfccap.education.application;

import org.pfccap.education.dao.AnswersDao;
import org.pfccap.education.dao.AnswersQuestionDao;
import org.pfccap.education.dao.QuestionDao;
import org.pfccap.education.dao.SecondAnswerDao;
import org.pfccap.education.dao.UserDao;

/**
 * Created by USUARIO on 04/04/2017.
 */

public class AppDao {

    private static UserDao userDao;
    private static AnswersDao answersDao;
    private static QuestionDao questionDao;
    private static AnswersQuestionDao answersQuestionDao;
    private static SecondAnswerDao secondAnswerDao;

    public static UserDao getUserDao() {
        return userDao;
    }

    public static void setUserDao(UserDao userDao) {
        AppDao.userDao = userDao;
    }

    public static AnswersDao getAnswersDao() {
        return answersDao;
    }

    public static void setAnswersDao(AnswersDao answersDao) {
        AppDao.answersDao = answersDao;
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
}
