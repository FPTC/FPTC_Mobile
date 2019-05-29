package org.pfccap.education.presentation.main.presenters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.google.firebase.crash.FirebaseCrash;

import org.pfccap.education.dao.Ciudades;
import org.pfccap.education.dao.Comunas;
import org.pfccap.education.dao.Ese;
import org.pfccap.education.dao.IPS;
import org.pfccap.education.dao.Paises;
import org.pfccap.education.domain.placeData.IProfilePlaceDB;
import org.pfccap.education.domain.placeData.ProfilePlaceDB;
import org.pfccap.education.domain.user.IUserBP;
import org.pfccap.education.domain.user.UserBP;
import org.pfccap.education.entities.Cities;
import org.pfccap.education.entities.ComunasEntity;
import org.pfccap.education.entities.Countries;
import org.pfccap.education.entities.EseEntity;
import org.pfccap.education.entities.IpsEntity;
import org.pfccap.education.entities.SpinnerEntidad;
import org.pfccap.education.entities.UserAuth;
import org.pfccap.education.presentation.main.ui.activities.IProfileView;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by USUARIO on 02/05/2017.
 */

public class ProfilePresenter implements IProfilePresenter {

    private IProfileView profileView;
    private IUserBP userBP;
    private Context context;
    private IProfilePlaceDB profilePlaceDB;
    private ArrayList<SpinnerEntidad> spinnerArray;

    public ProfilePresenter(IProfileView profileView, Context context) {
        this.profileView = profileView;
        this.context = context;
        userBP = new UserBP();
        profilePlaceDB = new ProfilePlaceDB();
    }

    @Override
    public void calculateAge(int yearBirth, int monthBirth, int dayMonthBirth) {
        Calendar calendar = Calendar.getInstance();
        int age = calendar.get(Calendar.YEAR) - yearBirth;
        if (calendar.get(Calendar.MONTH)+1 <= monthBirth && calendar.get(Calendar.DAY_OF_MONTH) < dayMonthBirth){
            age = age - 1;
        }
        profileView.setAge(age);
    }

    @Override
    public void getEmailUser() {
        profileView.setEmailUser(Cache.getByKey(Constants.EMAIL));
    }

    @Override
    public void updateUserData(HashMap<String, Object> dataUser) {

        try {

            profileView.showProgress();
            profileView.disableInputs();

            userBP.update(dataUser);

            profileView.hideProgress();
            profileView.enableInputs();
            profileView.finishActivity();

        } catch (Exception e) {
            profileView.hideProgress();
            profileView.enableInputs();
            FirebaseCrash.report(e);
            profileView.showError(e.getMessage());
        }

    }

    public void getUserData() {
        try {
            profileView.showProgress();
            profileView.disableInputs();

            userBP.getUser()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<UserAuth>() {
                        @Override
                        public void onNext(UserAuth value) {
                            profileView.hideProgress();
                            profileView.enableInputs();
                            profileView.loadData(value);
                        }

                        @Override
                        public void onError(Throwable e) {
                            profileView.hideProgress();
                            profileView.enableInputs();
                            FirebaseCrash.report(e);
                            profileView.showError(e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                        }
                    });

        } catch (Exception e) {
            profileView.hideProgress();
            profileView.enableInputs();
            FirebaseCrash.report(e);
            profileView.showError(e.getMessage());
        }

    }

    @Override
    public List<Countries> getCountryData() {
        List<Paises> lstPaises = profilePlaceDB.getCountryData();
        List<Countries> lstCountries = new ArrayList<>();
        Countries countries;
        for (Paises paises: lstPaises){
            countries = new Countries();
            countries.setId(paises.getIdCountry());
            countries.setName(paises.getName());
            lstCountries.add(countries);
        }
        return lstCountries;
    }

    @Override
    public ArrayAdapter<SpinnerEntidad> getCitiesData(long idCountry) {
        List<Ciudades> lstCiudades = profilePlaceDB.getCitiesData(idCountry);
        spinnerArray = new ArrayList<>();
        spinnerArray.add(new SpinnerEntidad(-1, "Ciudad"));
        for (Ciudades ciudades: lstCiudades){
            spinnerArray.add(new SpinnerEntidad(ciudades.getIdCity(), ciudades.getName()));
        }
        //Combo paises
        ArrayAdapter<SpinnerEntidad> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    @Override
    public ArrayAdapter<SpinnerEntidad> getComunasData(long idCity) {
        List<Comunas> lstComunas = profilePlaceDB.getComunasData(idCity);
        spinnerArray = new ArrayList<>();
        spinnerArray.add(new SpinnerEntidad(-1, "Comuna"));
        for (Comunas comunas: lstComunas){
            spinnerArray.add(new SpinnerEntidad(comunas.getIdComuna(), comunas.getName()));
        }
        //Combo paises
        ArrayAdapter<SpinnerEntidad> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    @Override
    public ArrayAdapter<SpinnerEntidad> getEseData(long idCity) {
        List<Ese> lstEse = profilePlaceDB.getEseData(idCity);
        spinnerArray = new ArrayList<>();
        spinnerArray.add(new SpinnerEntidad(-1, "ESE"));
        for (Ese ese: lstEse){
            spinnerArray.add(new SpinnerEntidad(ese.getIdEse(), ese.getName()));
        }
        //Combo paises
        ArrayAdapter<SpinnerEntidad> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    @Override
    public ArrayAdapter<SpinnerEntidad> getIpsData(long idEse) {
        List<IPS> lstIps = profilePlaceDB.getIpsData(idEse);
        spinnerArray = new ArrayList<>();
        spinnerArray.add(new SpinnerEntidad(-1, "IPS"));
        for (IPS ips: lstIps){
            spinnerArray.add(new SpinnerEntidad(ips.getIdIps(), ips.getName()));
        }
        //Combo paises
        ArrayAdapter<SpinnerEntidad> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }


}
