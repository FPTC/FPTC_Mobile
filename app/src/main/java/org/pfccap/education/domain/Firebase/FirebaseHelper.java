package org.pfccap.education.domain.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by jggomez on 05-Apr-17.
 */

public class FirebaseHelper {

    private DatabaseReference dataReference;

    private final static String USERS_PATH = "usuarios";
    private final static String CONTACTS_PATH = "contactos";
    private final static String QUESTIONS_PATH = "preguntas";
    private final static String ANSWERS_PATH = "respuestas";
    private final static String QUESTIONS_TYPE_PATH = "tipoPreguntas";
    private final static String CONFIGURATION_PATH = "configuracion";
    private final static String GIFTS_PATH = "premiosapp";
    private final static String PAISES_PATH = "paises";

    private static class SingletonHolder {
        private static final FirebaseHelper INSTANCE = new FirebaseHelper();
    }

    public static FirebaseHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private FirebaseHelper() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        dataReference = FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference getDataReference() {
        return dataReference;
    }

    public String getAuthUserUID() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getUid();
        }
        return null;
    }

    public DatabaseReference getUserReference(String uid) {
        DatabaseReference userReference = null;
        if (uid != null) {
            userReference = dataReference.getRoot().child(USERS_PATH).child(uid);
        }
        return userReference;
    }

    public DatabaseReference getMyUserReference() {
        return getUserReference(getAuthUserUID());
    }

    public DatabaseReference getContactsReference() {
        return dataReference.getRoot().child(CONTACTS_PATH);
    }

    public DatabaseReference getQuestionsReference() {
        return dataReference.getRoot().child(QUESTIONS_PATH);
    }

    public DatabaseReference getQuestionsTypeReference() {
        return dataReference.getRoot().child(QUESTIONS_TYPE_PATH);
    }

    public DatabaseReference getAnswersReference() {
        return dataReference.getRoot().child(ANSWERS_PATH);
    }

    public DatabaseReference getConfigurationReference() {
        return dataReference.getRoot().child(CONFIGURATION_PATH);
    }

    public DatabaseReference getGiftsReference() {
        return dataReference.getRoot().child(GIFTS_PATH);
    }

    public DatabaseReference getPaisesReference() {
        return dataReference.getRoot().child(PAISES_PATH);
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }
}
