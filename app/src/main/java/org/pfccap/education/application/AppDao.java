package org.pfccap.education.application;

import org.pfccap.education.dao.AnswersQuestionDao;
import org.pfccap.education.dao.CiudadesDao;
import org.pfccap.education.dao.ComunasDao;
import org.pfccap.education.dao.EseDao;
import org.pfccap.education.dao.GiftDao;
import org.pfccap.education.dao.IPSDao;
import org.pfccap.education.dao.PaisesDao;
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
    private static PaisesDao countriesDao;
    private static CiudadesDao citiesDao;
    private static ComunasDao comunasDao;
    private static EseDao eseDao;
    private static IPSDao ipsDao;

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

    public static PaisesDao getCountriesDao() {
        return countriesDao;
    }

    public static void setCountriesDao(PaisesDao countriesDao) {
        AppDao.countriesDao = countriesDao;
    }

    public static CiudadesDao getCitiesDao() {
        return citiesDao;
    }

    public static void setCitiesDao(CiudadesDao citiesDao) {
        AppDao.citiesDao = citiesDao;
    }

    public static ComunasDao getComunasDao() {
        return comunasDao;
    }

    public static void setComunasDao(ComunasDao comunasDao) {
        AppDao.comunasDao = comunasDao;
    }

    public static EseDao getEseDao() {
        return eseDao;
    }

    public static void setEseDao(EseDao eseDao) {
        AppDao.eseDao = eseDao;
    }

    public static IPSDao getIpsDao() {
        return ipsDao;
    }

    public static void setIpsDao(IPSDao ipsDao) {
        AppDao.ipsDao = ipsDao;
    }
}
