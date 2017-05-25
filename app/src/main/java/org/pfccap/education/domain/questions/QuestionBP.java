package org.pfccap.education.domain.questions;

import android.util.Log;

import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.pfccap.education.application.AppDao;
import org.pfccap.education.dao.AnswersQuestion;
import org.pfccap.education.dao.AnswersQuestionDao;
import org.pfccap.education.dao.Question;
import org.pfccap.education.dao.QuestionDao;
import org.pfccap.education.dao.SecondAnswer;
import org.pfccap.education.dao.SecondAnswerDao;
import org.pfccap.education.domain.firebase.FirebaseHelper;
import org.pfccap.education.entities.Answer;
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
                                saveQuestionData(questionsListAll);
                                e.onNext(questionsListAll);
                            } catch (Exception ex) {
                                FirebaseCrash.report(ex);
                                e.onError(ex);
                            }
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

    private void saveQuestionData(QuestionList questionsListAll) {
        try {
            //se limpia la base de datos para la nueva descarga
            ILQuestionDB ilQuestionDB = new LQuestionDB();
            ilQuestionDB.deleteDB();
            //este Questions es el de entities con lo que se mapio al cargar de firebase
            HashMap<String, Questions> cancerCervix = questionsListAll.getCancerCervix();

            QuestionDao questionsDao = AppDao.getQuestionDao();
            Question questionsDB;  //este Question es el del DAO para mapear a la base de datos
            for (Map.Entry<String, Questions> entry : cancerCervix.entrySet()) {
                questionsDB = new Question();
                questionsDB.setIdquest(entry.getKey());
                questionsDB.setTxtQuestion(entry.getValue().getText());
                questionsDB.setTypeCancer("cervix");
                questionsDB.setTypeQuestion(entry.getValue().getTypeQuestion());
                questionsDB.setOrder(entry.getValue().getOrder());
                questionsDB.setEnable(entry.getValue().isEnable());
                questionsDB.setInfo(entry.getValue().getInfo());
                if (entry.getValue().getQuestion() != null) {
                    questionsDB.setTxtSecondQuestion(entry.getValue().getQuestion().getText());
                }
                questionsDao.insert(questionsDB);

                HashMap<String, Answer> answersCervix = entry.getValue().getAnswers();
                AnswersQuestionDao answersQuestionDao = AppDao.getAnswersQuestionDao();
                AnswersQuestion answersQuestionDB;
                try {
                    for (Map.Entry<String, Answer> entry1 : answersCervix.entrySet()) {
                        answersQuestionDB = new AnswersQuestion();
                        answersQuestionDB.setIdQuestion(entry.getKey());
                        answersQuestionDB.setDescription(entry1.getValue().getDescription());
                        answersQuestionDB.setValue(entry1.getValue().isValue());
                        answersQuestionDB.setPoints(entry1.getValue().getPoints());
                        answersQuestionDao.insert(answersQuestionDB);

                    }
                    if (entry.getValue().getQuestion() != null) {
                        HashMap<String, Answer> answersCervixSecond = entry.getValue().getQuestion().getAnswers();
                        SecondAnswerDao secondAnswerDao = AppDao.getSecondAnswerDao();
                        SecondAnswer secondAnswerDB;
                        for (Map.Entry<String, Answer> entry2 : answersCervixSecond.entrySet()) {
                            secondAnswerDB = new SecondAnswer();
                            secondAnswerDB.setIdQuestion(entry.getKey());
                            secondAnswerDB.setDescription(entry2.getValue().getDescription());
                            secondAnswerDao.insert(secondAnswerDB);
                        }
                    }
                } catch (Exception e) {
                    FirebaseCrash.report(e);
                }
            }

            HashMap<String, Questions> cancerSeno = questionsListAll.getCancerSeno();

            for (Map.Entry<String, Questions> entry : cancerSeno.entrySet()) {
                questionsDB = new Question();
                questionsDB.setIdquest(entry.getKey());
                questionsDB.setTxtQuestion(entry.getValue().getText());
                questionsDB.setTypeCancer("breast");
                questionsDB.setTypeQuestion(entry.getValue().getTypeQuestion());
                questionsDB.setOrder(entry.getValue().getOrder());
                questionsDB.setEnable(entry.getValue().isEnable());
                questionsDB.setInfo(entry.getValue().getInfo());
                if (entry.getValue().getQuestion() != null) {
                    questionsDB.setTxtSecondQuestion(entry.getValue().getQuestion().getText());
                }
                questionsDao.insert(questionsDB);

                HashMap<String, Answer> answersBreast = entry.getValue().getAnswers();
                AnswersQuestionDao answersQuestionDao = AppDao.getAnswersQuestionDao();
                AnswersQuestion answersQuestionDB;
                try {
                    for (Map.Entry<String, Answer> entry1 : answersBreast.entrySet()) {
                        answersQuestionDB = new AnswersQuestion();
                        answersQuestionDB.setIdQuestion(entry.getKey());
                        answersQuestionDB.setDescription(entry1.getValue().getDescription());
                        answersQuestionDB.setValue(entry1.getValue().isValue());
                        answersQuestionDB.setPoints(entry1.getValue().getPoints());
                        answersQuestionDao.insert(answersQuestionDB);

                    }
                    if (entry.getValue().getQuestion() != null) {
                        HashMap<String, Answer> answersBreastSecond = entry.getValue().getQuestion().getAnswers();
                        SecondAnswerDao secondAnswerDao = AppDao.getSecondAnswerDao();
                        SecondAnswer secondAnswerDB;
                        for (Map.Entry<String, Answer> entry2 : answersBreastSecond.entrySet()) {
                            secondAnswerDB = new SecondAnswer();
                            secondAnswerDB.setIdQuestion(entry.getKey());
                            secondAnswerDB.setDescription(entry2.getValue().getDescription());
                            secondAnswerDao.insert(secondAnswerDB);
                        }
                    }
                } catch (Exception e) {
                    FirebaseCrash.report(e);
                }

            }


        } catch (Exception e) {
            FirebaseCrash.report(e);
            throw e;
        }
    }

}
