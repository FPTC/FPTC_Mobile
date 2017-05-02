package org.pfccap.education.presentation.main.presenters;

import org.pfccap.education.presentation.main.ui.fragments.IProfileView;

import java.util.Calendar;

/**
 * Created by USUARIO on 02/05/2017.
 */

public class ProfilePresenter implements IProfilePresenter {

    private IProfileView profileView;

    public ProfilePresenter(IProfileView profileView) {
        this.profileView = profileView;
    }

    @Override
    public void calculateAge(int yearBirth) {
        Calendar calendar = Calendar.getInstance();
        int age = calendar.get(Calendar.YEAR) - yearBirth;
        profileView.setAge(age);
    }
}
