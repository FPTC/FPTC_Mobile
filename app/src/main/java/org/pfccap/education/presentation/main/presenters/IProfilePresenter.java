package org.pfccap.education.presentation.main.presenters;

import org.pfccap.education.entities.UserAuth;

/**
 * Created by USUARIO on 02/05/2017.
 */

public interface IProfilePresenter {

    void calculateAge(int yearBirth);

    void getEmailUser();

    void saveUserData(UserAuth user);

    void getUserData();

}
