package org.pfccap.education.presentation.main.presenters;

import com.google.firebase.crash.FirebaseCrash;
import org.pfccap.education.domain.auth.AuthProcess;
import org.pfccap.education.domain.auth.IAuthProcess;
import org.pfccap.education.presentation.main.ui.activities.IMainActivityView;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;

/**
 * Created by jggomez on 03-May-17.
 */

public class MainActivityPresenter implements IMainActivityPresenter {

    private IMainActivityView mainActivityView;
    private IAuthProcess authProcess;

    public MainActivityPresenter(IMainActivityView mainActivityView) {
        this.mainActivityView = mainActivityView;
    }

    @Override
    public void setUserName() {
        try {

            mainActivityView.setUserName(Cache.getByKey(Constants.USER_NAME));

        } catch (Exception e) {
            FirebaseCrash.report(e);
            mainActivityView.showError(e.getMessage());
        }
    }

    @Override
    public void logOut() {
        Cache.clearAll();
        authProcess = new AuthProcess();
        authProcess.logOut();
        mainActivityView.navigateToLogin();
    }
}
