package org.pfccap.education.application;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import org.pfccap.education.dao.AppDao;
import org.pfccap.education.dao.DaoMaster;
import org.pfccap.education.dao.DaoSession;
import org.pfccap.education.utilities.Cache;

/**
 * Created by USUARIO on 04/04/2017.
 */

public class FPTCApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //se crea la BD y la sesión  para acceder a ella
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "pfccap-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();

        //se crean los DAOs
        AppDao.setUserDao(daoSession.getUserDao());
        AppDao.setEducationDao(daoSession.getEducationDao());
        AppDao.setRiskDao(daoSession.getRiskDao());
        AppDao.setEvaluationDao(daoSession.getEvaluationDao());
        AppDao.setAnswersDao(daoSession.getAnswersDao());

        Cache.init(getApplicationContext());
    }
}
