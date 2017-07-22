package org.pfccap.education.domain.auth;


import org.pfccap.education.entities.UserAuth;

import io.reactivex.Observable;

/**
 * Created by jggomez on 20-Apr-17.
 */

public interface IAuthProcess {

    Observable<UserAuth> signUp(final String name,  final String email, final String password);

    Observable<UserAuth> signIn(final String email, final String password);

    Observable<UserAuth> signInWithCredential(String token);

    Observable<String> resetPassword(final String email);

    void logOut();
}
