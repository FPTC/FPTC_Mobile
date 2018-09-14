package org.pfccap.education.presentation.main.presenters;

import org.pfccap.education.entities.Countries;

import java.util.HashMap;
import java.util.List;

/**
 * Created by USUARIO on 02/05/2017.
 */

public interface IProfilePresenter {

    void calculateAge(int yearBirth, int mothBirth, int dayMonthBirth);

    void getEmailUser();

    void updateUserData(HashMap<String, Object> dataUser);

    void getUserData();

    List<Countries> getCountryData();
}
