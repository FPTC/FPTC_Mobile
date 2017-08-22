package org.pfccap.education.utilities;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.pfccap.education.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by USUARIO on 11/07/2017.
 */

public class APIService {

    private final static String TAG = APIService.class.getName();

    public static <T> T getInstanceRetrofit(Class<T> tClass){
        String URL = Cache.getByKey(Constants.BASE_URL_SERVICE_KEY);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(tClass);
    }

    public static void getBASEURL() {
        final FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);

        mFirebaseRemoteConfig.fetch()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mFirebaseRemoteConfig.activateFetched();
                        } else {
                            Log.e(TAG, "Fetch failed");
                        }
                        String fireBaseURL = mFirebaseRemoteConfig.getString(Constants.BASE_URL_SERVICE_KEY);
                        String termBaseURL = mFirebaseRemoteConfig.getString(Constants.BASE_URL_TERMS_CONDITIONS_KEY);
                        String privacyyBaseURL = mFirebaseRemoteConfig.getString(Constants.BASE_URL_PRIVACY_POLICY_KEY);
                        String emailBaseURL = mFirebaseRemoteConfig.getString(Constants.BASE_URL_CREATE_EMAIL_APP);

                        Cache.save(Constants.BASE_URL_SERVICE_KEY, fireBaseURL);
                        Cache.save(Constants.BASE_URL_TERMS_CONDITIONS_KEY, termBaseURL);
                        Cache.save(Constants.BASE_URL_PRIVACY_POLICY_KEY, privacyyBaseURL);
                        Cache.save(Constants.BASE_URL_CREATE_EMAIL_APP, emailBaseURL);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        FirebaseCrash.report(e);
                    }
                });
    }
}
