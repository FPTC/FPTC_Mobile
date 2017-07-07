package org.pfccap.education.utilities;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.pfccap.education.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by USUARIO on 11/07/2017.
 */

public class APIService {

    public static <T> T getInstanceRetrofit(Class<T> tClass){
        String URL = Constants.BASE_URL;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(tClass);
    }
}
