package org.pfccap.education.presentation.main.ui.activities;

import android.content.Intent;

/**
 * Created by jggomez on 03-May-17.
 */

public interface IMainActivityView {

    void setUserName(String userName);

    void showError(String error);

    void navigateToLogin();

    void goInviteActivity(Intent intent);
}
