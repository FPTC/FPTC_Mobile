package org.pfccap.education.presentation.main.presenters;

import com.google.firebase.crash.FirebaseCrash;

import org.pfccap.education.dao.Gift;
import org.pfccap.education.domain.questions.ILQuestionDB;
import org.pfccap.education.domain.questions.LQuestionDB;
import org.pfccap.education.domain.services.ValidationService;
import org.pfccap.education.entities.Validation;
import org.pfccap.education.utilities.APIService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by USUARIO on 22/06/2017.
 */

public class GiftsPresenter implements IGiftsPresenter {

    private ILQuestionDB ilQuestionDB;

    public GiftsPresenter(){
        ilQuestionDB = new LQuestionDB();
    }


    @Override
    public List<Gift> getListGiftsTable() {
        try {
            List<Gift> listItems = ilQuestionDB.getAllGift();
            return listItems;
        }catch (Exception e){
            FirebaseCrash.report(e);
            return null;
        }
    }

    @Override
    public void getValidaionAppointment(String Uid) {

        ValidationService service = APIService.getInstanceRetrofit(ValidationService.class);
        service.getValidation(Uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Validation>() {
                    @Override
                    public void onNext(Validation value) {
                        System.out.println(value.toString() + " data1: " + value.getResponse() + " data2: " + value.getResponseCode());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Exception error = new Exception(e.getMessage());
                        FirebaseCrash.report(error);
                        System.out.println("Error: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
