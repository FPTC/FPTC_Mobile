package org.pfccap.education.domain.questions;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.pfccap.education.entities.Questions;
import org.pfccap.education.domain.firebase.FirebaseHelper;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by USUARIO on 08/05/2017.
 */

public class QuestionBP implements IQuestionBP {

    private FirebaseHelper firebaseHelper;

    public QuestionBP() {
        firebaseHelper = FirebaseHelper.getInstance();
    }

    @Override
    public Observable<Questions> getQuestions() {
        try {
            return Observable.create(new ObservableOnSubscribe<Questions>() {
                @Override
                public void subscribe(final ObservableEmitter<Questions> e) throws Exception {

                    firebaseHelper.getQuestionsReference().addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Questions questions = dataSnapshot.getValue(Questions.class);
                            e.onNext(questions);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Exception error = new Exception(databaseError.getMessage());
                            e.onError(error);
                        }
                    });

                }
            });
        }catch (Exception e){
            throw e;
        }

    }

    @Override
    public void save(Questions questions) {
        try {

            firebaseHelper.getQuestionsReference().setValue(questions);

        } catch (Exception e) {
            throw e;
        }
    }
}
