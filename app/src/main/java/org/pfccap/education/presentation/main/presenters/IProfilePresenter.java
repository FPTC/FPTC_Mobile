package org.pfccap.education.presentation.main.presenters;

import java.util.HashMap;

/**
 * Created by USUARIO on 02/05/2017.
 */

public interface IProfilePresenter {

    void calculateAge(int yearBirth);

    void getEmailUser();

    void updateUserData(HashMap<String, Object> dataUser);

    void getUserData();

}
