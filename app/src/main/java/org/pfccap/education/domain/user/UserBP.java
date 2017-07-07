package org.pfccap.education.domain.user;

import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.pfccap.education.domain.firebase.FirebaseHelper;
import org.pfccap.education.entities.UserAuth;

import java.util.HashMap;

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

            user.setPointsTotal(0);
            user.setRepetitionsAnswersBreast(0);
            user.setRepetitionsAnswersCervix(0);
            user.setProfileCompleted(0);
            user.setState(0);
            user.setCurrentPointsBreast(0);
            user.setCurrentPointsCervix(0);
            user.setDateCompletedBreast("");
            user.setDateCompletedCervix("");

            firebaseHelper.getMyUserReference().setValue(user);

        } catch (Exception e) {
            FirebaseCrash.report(e);
            throw e;
        }
    }

    @Override
    public void update(HashMap<String, Object> dataUser) {
        try {
            firebaseHelper.getMyUserReference().updateChildren(dataUser);
        } catch (Exception e) {
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
                            if (userAuth == null) {
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
