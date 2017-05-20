package org.pfccap.education.domain.questions;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.pfccap.education.application.AppDao;
import org.pfccap.education.dao.Questions;
import org.pfccap.education.dao.QuestionsDao;
import org.pfccap.education.domain.firebase.FirebaseHelper;
import org.pfccap.education.entities.Answer;
import org.pfccap.education.entities.Question;
import org.pfccap.education.entities.QuestionList;
import org.pfccap.education.entities.SecondQuestion;

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
                                saveQuestionData(questionsListAll);
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

    private void saveQuestionData(QuestionList questionsListAll) {
        try {
            HashMap<String, Question> cancerCervix = questionsListAll.getCancerCervix();

            QuestionsDao questionsDao = AppDao.getQuestionsDao();
            Questions questionsDB;
            for(Map.Entry<String, Question> entry: cancerCervix.entrySet()){
                questionsDB = new Questions();
                questionsDB.setIdquest(entry.getKey());
                questionsDB.setTypeQuestion(entry.getValue().getTypeQuestion());
                questionsDB.setEnable(entry.getValue().isEnable());
                questionsDB.setInfo(entry.getValue().getInfo());
                questionsDB.setOrder(entry.getValue().getOrder());
                questionsDB.setTxtQuestion(entry.getValue().getText());
                questionsDB.setTypeCancer("cervix");

               // HashMap<String, Object> answers = entry.getValue().getAnswers();
                JSONArray answerArray = new JSONArray();
              /*  for (Map.Entry<String, Object> entry1: answers.entrySet()){
                    answerArray.put(entry1.getValue());
                }*/
                questionsDB.setAnswers(answerArray.toString());
                if (entry.getValue().getQuestion()!=null) {
                    HashMap<String, SecondQuestion> secondquestion = entry.getValue().getQuestion();
                    questionsDB.setSecondQuestion(secondquestion.get("text").getText());
                }
                questionsDao.insert(questionsDB);
            }

            HashMap<String, Question> cancerSeno = questionsListAll.getCancerSeno();

            for(Map.Entry<String, Question> entry: cancerSeno.entrySet()){
                questionsDB = new Questions();
                questionsDB.setIdquest(entry.getKey());
                questionsDB.setTypeQuestion(entry.getValue().getTypeQuestion());
                questionsDB.setEnable(entry.getValue().isEnable());
                questionsDB.setInfo(entry.getValue().getInfo());
                questionsDB.setOrder(entry.getValue().getOrder());
                questionsDB.setTxtQuestion(entry.getValue().getText());
                questionsDB.setTypeCancer("breast");
                questionsDB.setAnswers(entry.getValue().getAnswers().toString());
                if (entry.getValue().getQuestion()!=null) {
                    questionsDB.setSecondQuestion(entry.getValue().getQuestion().toString());
                }
                questionsDao.insert(questionsDB);
            }

        } catch (Exception e) {
            throw e;
        }
    }

}
