package org.pfccap.education.domain.services;

import org.pfccap.education.entities.RespSendCode;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface SendCodeService {

    @GET("consultarCodigos?")
    Observable<Response<RespSendCode>> sendCode(@QueryMap Map<String, String> code);
}
