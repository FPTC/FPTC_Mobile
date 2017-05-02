package org.pfccap.education.domain.user;

import org.pfccap.education.domain.firebase.FirebaseHelper;
import org.pfccap.education.entities.UserAuth;

/**
 * Created by jggomez on 02-May-17.
 */

public class UserBP implements IUserBP {

    private FirebaseHelper firebaseHelper;
    private final String NOMBRE_PATH = "nombre";

    public UserBP() {
        firebaseHelper = FirebaseHelper.getInstance();
    }

    @Override
    public void saveNameData(String name) {

        try {

            firebaseHelper.getMyUserReference().child(NOMBRE_PATH).setValue(name);

        } catch (Exception e) {
            throw e;
        }


    }

}
