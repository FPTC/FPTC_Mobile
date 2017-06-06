package org.pfccap.education.presentation.main.presenters;

import android.content.Context;
import android.util.Log;

import com.google.firebase.crash.FirebaseCrash;

import org.pfccap.education.R;
import org.pfccap.education.dao.AnswersQuestion;
import org.pfccap.education.dao.Question;
import org.pfccap.education.dao.SecondAnswer;
import org.pfccap.education.domain.questions.ILQuestionDB;
import org.pfccap.education.domain.questions.LQuestionDB;
import org.pfccap.education.presentation.main.ui.activities.IQuestionView;
import org.pfccap.education.presentation.main.ui.activities.QuestionsActivity;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by USUARIO on 08/05/2017.
 */

public class QuestionPresenter implements IQuestionPresenter {

    private IQuestionView questionView;
    private ILQuestionDB ilQuestionDB;
    private String lableTrue = "";
    private String lableFalse = "";
    private String valueTrue = "";
    private String valueFalse = "";
    private List<Question> lstQuestion;
    private int[] ramdomNumberSecuence;
    private Context context;

    public QuestionPresenter(IQuestionView questionView, Context context) {
        this.questionView = questionView;
        this.context = context;
        ilQuestionDB = new LQuestionDB();
    }

    @Override
    public void getQuestionsDB(int current) {
        lstQuestion = ilQuestionDB.getAll(Cache.getByKey(Constants.TYPE_CANCER));
        ramdomNumberSecuence = ramdomNumberSecuence(lstQuestion.size());
        if (lstQuestion != null && ramdomNumberSecuence != null && lstQuestion.size() != 0
                && ramdomNumberSecuence.length != 0) {
            loadQuestionCurrent(current);
        } else {
            questionView.finishActivity(context.getResources().getString(R.string.no_db_questions));
        }
    }

    @Override
    public void loadQuestionCurrent(int current) {
        if (current == lstQuestion.size()) {
            switch (Cache.getByKey(Constants.TYPE_CANCER)) {
                case Constants.CERVIX:
                    int turn = Integer.valueOf(Cache.getByKey(Constants.CERVIX_TURN)) + 1;
                    Cache.save(Constants.CERVIX_TURN, String.valueOf(turn));
                    break;
                case Constants.BREAST:
                    turn = Integer.valueOf(Cache.getByKey(Constants.BREAST_TURN)) + 1;
                    Cache.save(Constants.BREAST_TURN, String.valueOf(turn));
                    break;
            }
            ilQuestionDB.resetQuestion();
            questionView.finishActivity(context.getResources().getString(R.string.total_points,
                    Cache.getByKey(Constants.TOTAL_POINTS)));
        } else {
            setNextQuestion(current);
        }
    }

    @Override
    public void saveAnswerQuestionDB() {

    }

    @Override
    public void loadNextQuestion() {
        questionView.loadNextQuestion();
    }


    public int[] ramdomNumberSecuence(int num) {

        try {
            //num es el total de preguntas que hay en la lista obtenida en la base de datos
            int[] numerosAleatorios = new int[num];
            for (int i = 0; i < num; i++) {
                numerosAleatorios[i] = i;
            }
            //desordenando los elementos
            Random r = new Random();
            for (int i = numerosAleatorios.length; i > 0; i--) {
                int posicion = r.nextInt(i);
                int tmp = numerosAleatorios[i - 1];
                numerosAleatorios[i - 1] = numerosAleatorios[posicion];
                numerosAleatorios[posicion] = tmp;
            }
            return numerosAleatorios;
        } catch (Exception e) {
            FirebaseCrash.report(e);
            return null;
        }
    }

    @Override
    public void loadInfoSnackbar(String check) {
        //se coloca el valor answer de la respeusta en la base de datos en true para indicar que fue ya respondida
        ilQuestionDB.questionAnswer(Cache.getByKey(Constants.QUESTION_ID));
        questionView.setInfoSnackbar(check);
    }

    @Override
    public void calculatePointsCheck(int points, boolean value) {

        try {
            int totalPoinst;
            if (Cache.getByKey(Constants.TOTAL_POINTS).equals("")) {
                totalPoinst = 0;
            } else {
                totalPoinst = Integer.parseInt(Cache.getByKey(Constants.TOTAL_POINTS));
            }
            totalPoinst = totalPoinst + points;
            Cache.save(Constants.TOTAL_POINTS, String.valueOf(totalPoinst));

            String check;

            if (value && Cache.getByKey(Constants.TYPE_Q).equals(Constants.EVALUATIVA)) {
                check = context.getResources().getString(R.string.right);
            } else if (!value && Cache.getByKey(Constants.TYPE_Q).equals(Constants.EVALUATIVA)) {
                check = context.getResources().getString(R.string.fail);
            } else {
                check = context.getResources().getString(R.string.thanks_for_answers);
                if (!Cache.getByKey(Constants.INFO_TEACH).equals("")) {
                    questionView.showInfoTxtSecondary();
                }
            }
            questionView.disableItemsAdapter();
            loadInfoSnackbar(check);
        } catch (Exception e) {
            FirebaseCrash.report(e);
            System.out.println("error " + e.getMessage());
        }
    }

    @Override
    public void getSecondAnswers() {
        //cargo las respuestas de la pregunta aninada, se debe cambiar de tipo de lista por la clase que recibe el adaptador
        List<SecondAnswer> secondAnswerList = ilQuestionDB.getSecondAnswers(Cache.getByKey(Constants.QUESTION_ID));
        List<AnswersQuestion> answersQuestionList = new ArrayList<>();
        AnswersQuestion answersQuestion;
        if (secondAnswerList.size() > 0) {
            for (SecondAnswer secondAnswer : secondAnswerList) {
                answersQuestion = new AnswersQuestion();
                answersQuestion.setDescription(secondAnswer.getDescription());
                answersQuestion.setPoints(0);
                answersQuestion.setValue(false);
                answersQuestionList.add(answersQuestion);
            }
            questionView.loadAdapterRecycler(answersQuestionList);
        }
    }

    @Override
    public void backLastQuestion() {
        lstQuestion = ilQuestionDB.getAll(Cache.getByKey(Constants.TYPE_CANCER));
       if (lstQuestion != null && lstQuestion.size() == 0) {
           //esto se da si dan back en al responder la ultima pregunta sin permitir mostrar el mensaje de finalizar
           int turn = Integer.valueOf(Cache.getByKey(Constants.CERVIX_TURN)) + 1;
           Cache.save(Constants.CERVIX_TURN, String.valueOf(turn));
           ilQuestionDB.resetQuestion();
       }
    }


    private void setNextQuestion(int current) {
        try {
            int randomQ = ramdomNumberSecuence[current];
            Question currentQ = lstQuestion.get(randomQ);
            Cache.save(Constants.TYPE_Q, currentQ.getTypeQuestion());
            Cache.save(Constants.QUESTION_ID, currentQ.getIdquest());
            questionView.setPrimaryQuestion(currentQ.getTxtQuestion());
            questionView.setProgressBar(100 / lstQuestion.size());
            switch (currentQ.getTypeQuestion()) {
                case Constants.EVALUATIVA:
                    //re carga las respuestas de la pregunta en el adaptador
                    questionView.loadAdapterRecycler(ilQuestionDB.getAnswers(currentQ.getIdquest()));
                    break;
                case Constants.RIESGO:
                    //se carga los label de los botones true y false
                    List<AnswersQuestion> lstAnswersQuestion = ilQuestionDB.getAnswers(currentQ.getIdquest());
                    for (AnswersQuestion answersQuestion : lstAnswersQuestion) {
                        if (answersQuestion.getValue()) {
                            lableTrue = answersQuestion.getDescription();
                            valueTrue = String.valueOf(answersQuestion.getValue());
                            if (answersQuestion.getTxtSecondQuestion() != null &&
                                    !answersQuestion.getTxtSecondQuestion().equals("")) {
                                //se guarda en cache la preguna aninada para mostrala cuando se necesite
                                Cache.save(Constants.SECOND_QTRUE, answersQuestion.getTxtSecondQuestion());
                            } else {
                                Cache.save(Constants.SECOND_QTRUE, "");
                            }
                        }
                        if (!answersQuestion.getValue()) {
                            lableFalse = answersQuestion.getDescription();
                            valueFalse = String.valueOf(answersQuestion.getValue());
                            if (answersQuestion.getTxtSecondQuestion() != null &&
                                    !answersQuestion.getTxtSecondQuestion().equals("")) {
                                //se guarda en cache la preguna aninada para mostrala cuando se necesite
                                Cache.save(Constants.SECOND_QFALSE, answersQuestion.getTxtSecondQuestion());
                            } else {
                                Cache.save(Constants.SECOND_QFALSE, "");
                            }
                        }
                    }
                    questionView.setLabelButtonTrueFalse(lableTrue, valueTrue, lableFalse, valueFalse);
                    Cache.save(Constants.INFO_TEACH, currentQ.getInfo());
                    break;
                case Constants.EDUCATIVA:
                    //se carga los label de los botones true y false
                    List<AnswersQuestion> lstAnswersQuestion1 = ilQuestionDB.getAnswers(currentQ.getIdquest());
                    if (lstAnswersQuestion1 != null && lstAnswersQuestion1.size() != 0) {
                        lableTrue = lstAnswersQuestion1.get(0).getDescription();
                        valueTrue = String.valueOf(lstAnswersQuestion1.get(0).getValue());
                        lableFalse = lstAnswersQuestion1.get(1).getDescription();
                        valueFalse = String.valueOf(lstAnswersQuestion1.get(1).getValue());
                    }
                    questionView.setLabelButtonTrueFalse(lableTrue, valueTrue, lableFalse, valueFalse);
                    //se carga la información que se muestra en el snack bar para usarla luego
                    Cache.save(Constants.INFO_TEACH, currentQ.getInfo());
                    break;
            }
        } catch (Exception e) {
            FirebaseCrash.report(e);
        }
    }
}
