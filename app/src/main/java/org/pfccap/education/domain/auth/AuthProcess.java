package org.pfccap.education.domain.auth;

import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.pfccap.education.application.AppDao;
import org.pfccap.education.dao.User;
import org.pfccap.education.dao.UserDao;
import org.pfccap.education.domain.firebase.FirebaseHelper;
import org.pfccap.education.domain.user.IUserBP;
import org.pfccap.education.domain.user.UserBP;
import org.pfccap.education.entities.UserAuth;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;

import java.util.Calendar;
import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

/**
 * Created by jggomez on 20-Apr-17.
 */

public class AuthProcess implements IAuthProcess {

    private IUserBP userBP;

    public AuthProcess() {

    }

    @Override
    public Observable<UserAuth> signUp(final String name, final String email, final String password) {

        return Observable.create(
                new ObservableOnSubscribe<UserAuth>() {
                    @Override
                    public void subscribe(final ObservableEmitter<UserAuth> e) throws Exception {

                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(final AuthResult authResult) {

                                        final UserAuth user = new UserAuth();
                                        user.setEmail(authResult.getUser().getEmail());
                                        user.setFirstLastName(name);
                                        user.setPointsTotal(0);
                                        user.setRepetitionsAnswers(0);
                                        user.setState(1);

                                        saveAuthData(authResult.getUser().getEmail(), name, authResult.getUser().getUid());

                                        UserProfileChangeRequest.Builder userProfileReq = new UserProfileChangeRequest.Builder();
                                        userProfileReq.setDisplayName(name);

                                        authResult.getUser().updateProfile(userProfileReq.build()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                userBP = new UserBP();
                                                userBP.save(user);
                                                e.onNext(user);
                                            }
                                        });
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception ex) {
                                        e.onError(ex);
                                    }
                                });

                    }
                }
        );
    }

    @Override
    public Observable<UserAuth> signIn(final String email, final String password) {

        return Observable.create(
                new ObservableOnSubscribe<UserAuth>() {
                    @Override
                    public void subscribe(final ObservableEmitter<UserAuth> e) throws Exception {

                        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(final AuthResult authResult) {

                                        final UserAuth user = new UserAuth();
                                        user.setEmail(authResult.getUser().getEmail());
                                        user.setFirstLastName(authResult.getUser().getDisplayName());

                                        saveAuthData(authResult.getUser().getEmail(),
                                                authResult.getUser().getDisplayName(),
                                                authResult.getUser().getUid());

                                        e.onNext(user);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception ex) {
                                        e.onError(ex);
                                    }
                                });
                    }
                }
        );
    }

    @Override
    public Observable<UserAuth> signInWithCredential(String token) {

        final AuthCredential credential = FacebookAuthProvider.getCredential(token);

        return Observable.create(
                new ObservableOnSubscribe<UserAuth>() {
                    @Override
                    public void subscribe(final ObservableEmitter<UserAuth> e) throws Exception {

                        FirebaseAuth.getInstance().signInWithCredential(credential)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(final AuthResult authResult) {

                                        final UserAuth userAuth = new UserAuth();
                                        userAuth.setEmail(authResult.getUser().getEmail());
                                        userAuth.setFirstLastName(authResult.getUser().getDisplayName());

                                        saveAuthData(authResult.getUser().getEmail(),
                                                authResult.getUser().getDisplayName(),
                                                authResult.getUser().getUid());

                                        e.onNext(userAuth);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception ex) {
                                        e.onError(ex);
                                    }
                                });
                    }
                }
        );
    }

    @Override
    public Observable<String> resetPassword(final String email) {

        return Observable.create(
                new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(final ObservableEmitter<String> e) throws Exception {

                        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                                .addOnSuccessListener(
                                        new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                e.onNext("");
                                            }
                                        }
                                )
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception ex) {
                                        e.onError(ex);
                                    }
                                });
                    }
                }
        );
    }

    private void saveAuthData(String email, String name, String uid) {
        Cache.save(Constants.IS_LOGGGIN, "true");
        Cache.save(Constants.USER_EMAIL, email);
        Cache.save(Constants.USER_NAME, name);
        Cache.save(Constants.USER_UID, uid);

    }

    public void logOut(){
        FirebaseHelper.getInstance().signOut();
        LoginManager.getInstance().logOut();
    }

}
