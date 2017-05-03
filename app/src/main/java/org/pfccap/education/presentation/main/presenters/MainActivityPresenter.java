package org.pfccap.education.presentation.main.presenters;

import org.pfccap.education.presentation.main.ui.activities.IMainActivityView;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;

/**
 * Created by jggomez on 03-May-17.
 */

public class MainActivityPresenter implements IMainActivityPresenter {

    private IMainActivityView mainActivityView;

    public MainActivityPresenter(IMainActivityView mainActivityView) {
        this.mainActivityView = mainActivityView;
    }

    @Override
    public void getUser() {

        try {


        } catch (Exception e) {

        }

    }

    @Override
    public void setUserName() {
        try {

            mainActivityView.setUserName(Cache.getByKey(Constants.USER_NAME));

        } catch (Exception e) {
            mainActivityView.showError(e.getMessage());
        }
    }
}