package org.pfccap.education.domain.questions;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.pfccap.education.application.AppDao;
import org.pfccap.education.dao.Question;
import org.pfccap.education.dao.QuestionDao;
import org.pfccap.education.domain.firebase.FirebaseHelper;
import org.pfccap.education.entities.QuestionList;
import org.pfccap.education.entities.Questions;

import java.util.HashMap;
import java.util.Map;

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
    public Observable<QuestionList> getQuestions() {
        try {
            return Observable.create(new ObservableOnSubscribe<QuestionList>() {
                @Override
                public void subscribe(final ObservableEmitter<QuestionList> e) throws Exception {

                    firebaseHelper.getQuestionsReference().addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.e(TAG, dataSnapshot.toString());
                            try {
                                QuestionList questionsListAll = dataSnapshot.getValue(QuestionList.class);
                                HashMap<String, Questions> dataCervix = questionsListAll.getCancerCervix();
                                HashMap<String, Questions> dataSeno = questionsListAll.getCancerSeno();
                                QuestionDao questionDao = AppDao.getQuestionDao();
                                Question questionDB;
                              for (Map.Entry<String, Questions> entry: dataCervix.entrySet()){
                                  questionDB = new Question();
                                  questionDB.setIdquest(entry.getKey());
                                  questionDB.setTxtQuestion(entry.getValue().getText());
                                  questionDB.setTypeCancer("cancerCervix");
                                  questionDB.setTypeQuestion(entry.getValue().getTypeQuestion());
                                  questionDB.setOrder(entry.getValue().getOrder());
                                  questionDB.setEnable(entry.getValue().isEnable());
                                  questionDB.setInfo(entry.getValue().getInfo());
                                  questionDB.setAnswers(entry.getValue().getAnswers().toString());
                                  questionDao.insert(questionDB);
                              }
                                e.onNext(questionsListAll);
                            } catch (Exception ex) {
                                Log.e(TAG, ex.getMessage());
                                e.onError(ex);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Exception error = new Exception(databaseError.getMessage());
                            e.onError(error);
                        }
                    });

                }
            });
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public void save(QuestionList questionsListAll) {
        try {


        } catch (Exception e) {
            throw e;
        }
    }
}
