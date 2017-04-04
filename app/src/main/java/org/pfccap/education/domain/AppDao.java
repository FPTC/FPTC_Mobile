package org.pfccap.education.domain;

import org.pfccap.education.dao.AnswersDao;
import org.pfccap.education.dao.EducationDao;
import org.pfccap.education.dao.EvaluationDao;
import org.pfccap.education.dao.RiskDao;
import org.pfccap.education.dao.UserDao;

/**
 * Created by USUARIO on 04/04/2017.
 */

public class AppDao {

    private static UserDao userDao;
    private static EducationDao educationDao;
    private static RiskDao riskDao;
    private static EvaluationDao evaluationDao;
    private static AnswersDao answersDao;

    public static UserDao getUserDao() {
        return userDao;
    }

    public static void setUserDao(UserDao userDao) {
        AppDao.userDao = userDao;
    }

    public static EducationDao getEducationDao() {
        return educationDao;
    }

    public static void setEducationDao(EducationDao educationDao) {
        AppDao.educationDao = educationDao;
    }

    public static RiskDao getRiskDao() {
        return riskDao;
    }

    public static void setRiskDao(RiskDao riskDao) {
        AppDao.riskDao = riskDao;
    }

    public static EvaluationDao getEvaluationDao() {
        return evaluationDao;
    }

    public static void setEvaluationDao(EvaluationDao evaluationDao) {
        AppDao.evaluationDao = evaluationDao;
    }

    public static AnswersDao getAnswersDao() {
        return answersDao;
    }

    public static void setAnswersDao(AnswersDao answersDao) {
        AppDao.answersDao = answersDao;
    }
}
