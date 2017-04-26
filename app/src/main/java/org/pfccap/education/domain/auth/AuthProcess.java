package org.pfccap.education.domain.auth;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import org.pfccap.education.domain.Firebase.FirebaseHelper;
import org.pfccap.education.entities.UserAuth;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by jggomez on 20-Apr-17.
 */

public class AuthProcess implements IAuthProcess {

    private FirebaseHelper firebaseHelper;

    public AuthProcess() {
        firebaseHelper = FirebaseHelper.getInstance();
    }

    @Override
    public Observable<UserAuth> signUp(final String email, final String password) {

        return Observable.create(
                new ObservableOnSubscribe<UserAuth>() {
                    @Override
                    public void subscribe(final ObservableEmitter<UserAuth> e) throws Exception {

                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(final AuthResult authResult) {

                                        final UserAuth userAuth = new UserAuth();
                                        userAuth.setEmail(authResult.getUser().getEmail());
                                        userAuth.setName(authResult.getUser().getDisplayName());
                                        userAuth.setUID(authResult.getUser().getUid());

                                        saveAuthData(userAuth);
                                        e.onNext(userAuth);
                                        e.onComplete();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception ex) {
                                        e.onError(ex);
                                        e.onComplete();
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

                                        final UserAuth userAuth = new UserAuth();
                                        userAuth.setEmail(authResult.getUser().getEmail());
                                        userAuth.setName(authResult.getUser().getDisplayName());
                                        userAuth.setUID(authResult.getUser().getUid());

                                        saveAuthData(userAuth);
                                        e.onNext(userAuth);
                                        e.onComplete();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception ex) {
                                        e.onError(ex);
                                        e.onComplete();
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
                                        userAuth.setName(authResult.getUser().getDisplayName());
                                        userAuth.setUID(authResult.getUser().getUid());

                                        saveAuthData(userAuth);
                                        e.onNext(userAuth);
                                        e.onComplete();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception ex) {
                                        e.onError(ex);
                                        e.onComplete();
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
                                                e.onComplete();
                                            }
                                        }
                                )
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception ex) {
                                        e.onError(ex);
                                        e.onComplete();
                                    }
                                });
                    }
                }
        );
    }

    private void saveAuthData(UserAuth userAuth) {
        Cache.saveObject(Constants.USER_AUTH_KEY, userAuth);
    }

}
