package org.pfccap.education.application;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDex;

import org.pfccap.education.dao.DaoMaster;
import org.pfccap.education.dao.DaoSession;
import org.pfccap.education.utilities.Cache;

/**
 * Created by USUARIO on 04/04/2017.
 */

public class FPTCApp extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

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
        AppDao.setQuestionDao(daoSession.getQuestionDao());
        AppDao.setAnswersDao(daoSession.getAnswersDao());

        Cache.init(getApplicationContext());
    }
}
