package org.pfccap.education.domain.user;

import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.pfccap.education.domain.firebase.FirebaseHelper;
import org.pfccap.education.entities.UserAuth;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

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
    public void save(UserAuth user) {
        try {

            firebaseHelper.getMyUserReference().setValue(user);

        } catch (Exception e) {
            FirebaseCrash.report(e);
            throw e;
        }
    }


    @Override
    public Observable<UserAuth> getUser() {
        try {

            return Observable.create(new ObservableOnSubscribe<UserAuth>() {
                @Override
                public void subscribe(final ObservableEmitter<UserAuth> e) throws Exception {

                    firebaseHelper.getMyUserReference().addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            UserAuth userAuth = dataSnapshot.getValue(UserAuth.class);
                            if (userAuth == null){
                                userAuth = new UserAuth();
                            }
                            e.onNext(userAuth);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Exception error = new Exception(databaseError.getMessage());
                            FirebaseCrash.report(error);
                            e.onError(error);
                        }
                    });

                }
            });

        } catch (Exception e) {
            FirebaseCrash.report(e);
            throw e;
        }

    }

}
