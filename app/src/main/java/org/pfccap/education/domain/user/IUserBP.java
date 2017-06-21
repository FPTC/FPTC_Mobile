package org.pfccap.education.domain.user;

import org.pfccap.education.entities.UserAuth;

import java.util.HashMap;

import io.reactivex.Observable;

/**
 * Created by jggomez on 02-May-17.
 */

public interface IUserBP {

    Observable<UserAuth> getUser();

    void save(UserAuth user);

    void update(HashMap<String, Object> dataUser, String uid);
}
