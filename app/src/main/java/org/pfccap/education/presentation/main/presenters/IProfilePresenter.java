package org.pfccap.education.presentation.main.presenters;

import android.widget.ArrayAdapter;

import org.pfccap.education.entities.Cities;
import org.pfccap.education.entities.ComunasEntity;
import org.pfccap.education.entities.Countries;
import org.pfccap.education.entities.EseEntity;
import org.pfccap.education.entities.IpsEntity;
import org.pfccap.education.entities.SpinnerEntidad;

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

    ArrayAdapter<SpinnerEntidad> getCitiesData(long idCountry);

    ArrayAdapter<SpinnerEntidad> getComunasData(long idCity);

    ArrayAdapter<SpinnerEntidad> getEseData(long idCity);

    ArrayAdapter<SpinnerEntidad> getIpsData(long idEse);
}