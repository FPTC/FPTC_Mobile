package org.pfccap.education.domain.auth;

import android.support.annotation.NonNull;

import com.facebook.AccessToken;
import com.facebook.Profile;
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
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.pfccap.education.domain.firebase.FirebaseHelper;
import org.pfccap.education.domain.user.IUserBP;
import org.pfccap.education.domain.user.UserBP;
import org.pfccap.education.entities.UserAuth;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jggomez on 20-Apr-17.
 */

public class AuthProcess implements IAuthProcess {

    private IUserBP userBP;

    public AuthProcess() {

    }

    //se obtiene la respuesta después de iniciar sesión
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
                                        user.setName(name);

                                        //se actualiza el usaurio  la fecha en que se crea el usuario
                                        Calendar calendar = Calendar.getInstance();
                                        String dateCreated = String.format(Locale.US, "%d/%d/%d",
                                                calendar.get(Calendar.DAY_OF_MONTH),
                                                calendar.get(Calendar.MONTH) + 1,
                                                calendar.get(Calendar.YEAR)
                                        );
                                        user.setDateCreated(dateCreated);
                                        //se inicializa las variables de cache con los valores por defecto y se guarda los datos de autenticación
                                        saveAuthData(authResult.getUser().getEmail(), name, authResult.getUser().getUid());

                                        UserProfileChangeRequest.Builder userProfileReq = new UserProfileChangeRequest.Builder();
                                        userProfileReq.setDisplayName(name);

                                        authResult.getUser().updateProfile(userProfileReq.build()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                //se crea el usuario en la base de datos firebase
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

    //se obtiene la respeusta después de crear usuario
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
                                        user.setName(authResult.getUser().getDisplayName());

                                        //se inicializa las variables de cache con los valores por defecto y se guarda los datos de autenticación
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

    //se obtiene la respeusta después de iniciar sesión con facebook
    @Override
    public Observable<UserAuth> signInWithCredential(AccessToken token) {

        final AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());

        return Observable.create(
                new ObservableOnSubscribe<UserAuth>() {
                    @Override
                    public void subscribe(final ObservableEmitter<UserAuth> e) throws Exception {

                        FirebaseAuth.getInstance().signInWithCredential(credential)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(final AuthResult authResult) {
                                        final Profile profile = Profile.getCurrentProfile();
                                        final UserAuth user = new UserAuth();
                                        user.setEmail(authResult.getUser().getEmail());
                                        if (profile != null) {
                                            user.setName(profile.getName());
                                        }
                                        //se actualiza el usaurio  la fecha en que se crea el usuario
                                        Calendar calendar = Calendar.getInstance();
                                        String dateCreated = String.format(Locale.US, "%d/%d/%d",
                                                calendar.get(Calendar.DAY_OF_MONTH),
                                                calendar.get(Calendar.MONTH) + 1,
                                                calendar.get(Calendar.YEAR)
                                        );
                                        user.setDateCreated(dateCreated);
                                        //se inicializa las variables de cache con los valores por defecto y se guarda los datos de autenticación
                                        saveAuthData(authResult.getUser().getEmail(),
                                                authResult.getUser().getDisplayName(),
                                                authResult.getUser().getUid());

                                        // se verifica la existencia del usuario para ver si se crea en la base de datos firebase por primera vez
                                        //esto se debe a que el mismo metodo de facebook hace el registro y el login
                                        FirebaseHelper firebaseHelper = FirebaseHelper.getInstance();
                                        firebaseHelper.getMyUserReference().addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                UserAuth userAuth = dataSnapshot.getValue(UserAuth.class);
                                                if (userAuth == null) {
                                                    UserProfileChangeRequest.Builder userProfileReq = new UserProfileChangeRequest.Builder();
                                                    if (profile != null) {
                                                        userProfileReq.setDisplayName(profile.getName());
                                                    }
                                                    authResult.getUser().updateProfile(userProfileReq.build()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            //se crea el usuario en la base de datos firebase
                                                            userBP = new UserBP();
                                                            userBP.save(user);

                                                        }
                                                    });
                                                }
                                                e.onNext(user);
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                                Exception error = new Exception(databaseError.getMessage());
                                                FirebaseCrash.report(error);
                                                e.onError(error);
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

    //se obtiene respuesta después de iniciar proceso de reset password
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

    //guarda datos iniciales en cache
    private void saveAuthData(String email, String name, String uid) {

        Cache.save(Constants.IS_LOGGGIN, "true");
        Cache.save(Constants.EMAIL, email);
        Cache.save(Constants.USER_NAME, name);
        Cache.save(Constants.USER_UID, uid);
        Cache.save(Constants.BREAST_TURN, "0");
        Cache.save(Constants.CERVIX_TURN, "0");
        Cache.save(Constants.PROFILE_COMPLETED, "0");
    }

    //metodo para cerrar sesión
    public void logOut() {
        FirebaseHelper.getInstance().signOut();
        LoginManager.getInstance().logOut();
    }

}
