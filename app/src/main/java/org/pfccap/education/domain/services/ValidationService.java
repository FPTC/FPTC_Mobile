package org.pfccap.education.domain.services;

import org.pfccap.education.entities.Validation;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by USUARIO on 06/07/2017.
 */

public interface ValidationService {

    @GET("{Uid}")
    Observable<Validation> getValidation(@Path("Uid") String Uid);

}
