package org.pfccap.education.domain.questions;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.pfccap.education.entities.QuestionsListAll;
import org.pfccap.education.domain.firebase.FirebaseHelper;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import static com.facebook.GraphRequest.TAG;

/**
 * Created by USUARIO on 08/05/2017.
 */

public class QuestionBP implements IQuestionBP {

    private FirebaseHelper firebaseHelper;

    public QuestionBP() {
        firebaseHelper = FirebaseHelper.getInstance();
    }

    @Override
    public Observable<QuestionsListAll> getQuestions() {
        try {
            return Observable.create(new ObservableOnSubscribe<QuestionsListAll>() {
                @Override
                public void subscribe(final ObservableEmitter<QuestionsListAll> e) throws Exception {

                    firebaseHelper.getQuestionsReference().addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.e(TAG, dataSnapshot.toString());
                            QuestionsListAll questionsListAll = dataSnapshot.getValue(QuestionsListAll.class);
                            e.onNext(questionsListAll);
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
    public void save(QuestionsListAll questionsListAll) {
        try {


        } catch (Exception e) {
            throw e;
        }
    }
}
