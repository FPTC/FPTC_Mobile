package org.pfccap.education.application;

import org.pfccap.education.dao.AnswersDao;
import org.pfccap.education.dao.QuestionsDao;
import org.pfccap.education.dao.UserDao;

/**
 * Created by USUARIO on 04/04/2017.
 */

public class AppDao {

    private static UserDao userDao;
    private static AnswersDao answersDao;
    private static QuestionsDao questionsDao;

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

    public static QuestionsDao getQuestionsDao() {
        return questionsDao;
    }

    public static void setQuestionsDao(QuestionsDao questionsDao) {
        AppDao.questionsDao = questionsDao;
    }
}
