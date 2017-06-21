package org.pfccap.education.presentation.main.presenters;

import android.content.Context;

import com.google.firebase.crash.FirebaseCrash;

import org.pfccap.education.R;
import org.pfccap.education.dao.AnswersQuestion;
import org.pfccap.education.dao.Question;
import org.pfccap.education.dao.SecondAnswer;
import org.pfccap.education.domain.questions.ILQuestionDB;
import org.pfccap.education.domain.questions.IQuestionBP;
import org.pfccap.education.domain.questions.LQuestionDB;
import org.pfccap.education.domain.questions.QuestionBP;
import org.pfccap.education.domain.user.IUserBP;
import org.pfccap.education.domain.user.UserBP;
import org.pfccap.education.presentation.main.ui.activities.IQuestionView;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by USUARIO on 08/05/2017.
 */

public class QuestionPresenter implements IQuestionPresenter {

    private IQuestionView questionView;
    private ILQuestionDB ilQuestionDB;
    private IQuestionBP questionBP;
    private String lableTrue = "";
    private String lableFalse = "";
    private String valueTrue = "";
    private String valueFalse = "";
    private List<Question> lstQuestion;
    private int[] randomNumberSecuence;
    private Context context;

    public QuestionPresenter(IQuestionView questionView, Context context) {
        this.questionView = questionView;
        this.context = context;
        ilQuestionDB = new LQuestionDB();
        questionBP = new QuestionBP();
    }

    @Override
    public void getQuestionsDB(int current) {
        lstQuestion = ilQuestionDB.getAll(Cache.getByKey(Constants.TYPE_CANCER));
        randomNumberSecuence = randomNumberSecuence(lstQuestion.size());

        if (lstQuestion != null && randomNumberSecuence != null && lstQuestion.size() != 0
                && randomNumberSecuence.length != 0) {
            loadQuestionCurrent(current);
        } else {
            questionView.finishActivity(context.getResources().getString(R.string.no_db_questions));
        }
    }

    @Override
    public void loadQuestionCurrent(int current) {
        String message = "";

        if (current == lstQuestion.size()) {
            ilQuestionDB.resetQuestion();

            Calendar calendar = Calendar.getInstance();

            String dateCompleted = String.format(Locale.US, "%d/%d/%d",
                    calendar.get(Calendar.DAY_OF_MONTH),
                    calendar.get(Calendar.MONTH) + 1,
                    calendar.get(Calendar.YEAR)
            );

            HashMap<String, Object> data = new HashMap<>();

            switch (Cache.getByKey(Constants.TYPE_CANCER)) {
                case Constants.CERVIX:
                    //TODO ultima pregunta, aqui hay que hacer la verificación con la configuración de firebase para sumar los turnos
                    int turn = Integer.valueOf(Cache.getByKey(Constants.CERVIX_TURN)) + 1;
                    Cache.save(Constants.CERVIX_TURN, String.valueOf(turn));
                    // se coloca la fecha de completado de preguntas
                    Cache.save(Constants.DATE_COMPLETED_CERVIX, dateCompleted);

                    data.put(Constants.DATE_COMPLETED_CERVIX, dateCompleted);

                    if (Integer.valueOf(Cache.getByKey(Constants.CURRENT_POINTS_C)) > Integer.valueOf(Cache.getByKey(Constants.TOTAL_POINTS_C))) {
                        message = context.getResources().getString(R.string.total_points_up,
                                Cache.getByKey(Constants.TOTAL_POINTS_C), Cache.getByKey(Constants.CURRENT_POINTS_C));
                        Cache.save(Constants.TOTAL_POINTS_C, Cache.getByKey(Constants.CURRENT_POINTS_C));
                    } else {
                        message = context.getResources().getString(R.string.total_points_equal,
                                Cache.getByKey(Constants.TOTAL_POINTS_C), Cache.getByKey(Constants.CURRENT_POINTS_C));
                    }

                    break;
                case Constants.BREAST:
                    turn = Integer.valueOf(Cache.getByKey(Constants.BREAST_TURN)) + 1;
                    Cache.save(Constants.BREAST_TURN, String.valueOf(turn));
                    // se coloca la fecha de completado de preguntas
                    Cache.save(Constants.DATE_COMPLETED_BREAST, dateCompleted);

                    data.put(Constants.DATE_COMPLETED_CERVIX, dateCompleted);

                    if (Integer.valueOf(Cache.getByKey(Constants.CURRENT_POINTS_B)) > Integer.valueOf(Cache.getByKey(Constants.TOTAL_POINTS_B))) {
                        message = context.getResources().getString(R.string.total_points_up,
                                Cache.getByKey(Constants.TOTAL_POINTS_B), Cache.getByKey(Constants.CURRENT_POINTS_B));
                        Cache.save(Constants.TOTAL_POINTS_B, Cache.getByKey(Constants.CURRENT_POINTS_B));
                    } else {
                        message = context.getResources().getString(R.string.total_points_equal,
                                Cache.getByKey(Constants.TOTAL_POINTS_B), Cache.getByKey(Constants.CURRENT_POINTS_B));
                    }

                    break;
            }

            IUserBP userBP = new UserBP();
            userBP.update(data, Cache.getByKey(Constants.USER_UID));

            setPoints(message);
        } else {
            setNextQuestion(current);
        }
    }

    private void setPoints(String message) {
        questionView.finishActivity(message);
    }

    @Override
    public void saveAnswerQuestionDB(String typeAnswer, String idAnswer) {
        questionBP.save(typeAnswer, idAnswer);
    }

    @Override
    public void loadNextQuestion() {
        questionView.loadNextQuestion();
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

            switch (Cache.getByKey(Constants.TYPE_CANCER)) {
                case Constants.CERVIX:
                    sumPoints(Constants.CURRENT_POINTS_C, points);
                    break;
                case Constants.BREAST:
                    sumPoints(Constants.CURRENT_POINTS_B, points);
                    break;
            }

            String check;

            if (value && Cache.getByKey(Constants.TYPE_Q).equals(Constants.EVALUATIVA)) {
                check = context.getResources().getString(R.string.right);
            } else if (!value && Cache.getByKey(Constants.TYPE_Q).equals(Constants.EVALUATIVA)) {
                check = context.getResources().getString(R.string.fail);
            } else {
                check = context.getResources().getString(R.string.thanks_for_answers);
            }

            if (!Cache.getByKey(Constants.INFO_TEACH).equals("")) {
                questionView.showInfoTxtSecondary();
            }

            questionView.disableItemsAdapter();
            loadInfoSnackbar(check);
        } catch (Exception e) {
            FirebaseCrash.report(e);
            System.out.println("error " + e.getMessage());
        }
    }

    private void sumPoints(String constant, int points) {
        int totalPoinst;
        if (Cache.getByKey(constant).equals("")) {
            totalPoinst = 0;
        } else {
            totalPoinst = Integer.parseInt(Cache.getByKey(constant));
        }
        totalPoinst = totalPoinst + points;
        Cache.save(constant, String.valueOf(totalPoinst));
    }

    @Override
    public void getSecondAnswers(String idAnswer) {
        //se carga el valor del tipo de respuesta para almacenar en firebase con el valor aninada + el turno de la respeusta de cuestionario
        setTypeAnswer(context.getString(R.string.nested_label));
        //cargo las respuestas de la pregunta aninada, se debe cambiar de tipo de lista por la clase que recibe el adaptador
        List<SecondAnswer> secondAnswerList = ilQuestionDB.getSecondAnswers(idAnswer);
        List<AnswersQuestion> answersQuestionList = new ArrayList<>();
        AnswersQuestion answersQuestion;
        if (secondAnswerList.size() > 0) {
            for (SecondAnswer secondAnswer : secondAnswerList) {
                answersQuestion = new AnswersQuestion();
                answersQuestion.setDescription(secondAnswer.getDescription());
                answersQuestion.setPoints(0);
                answersQuestion.setValue(false);
                answersQuestion.setIdAnswer(secondAnswer.getIdAnswer());
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
            int turn;
            Calendar calendar = Calendar.getInstance();

            String dateCompleted = String.format(Locale.US, "%d/%d/%d",
                    calendar.get(Calendar.DAY_OF_MONTH),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.YEAR)
            );

            switch (Cache.getByKey(Constants.TYPE_CANCER)) {
                case Constants.CERVIX:
                    turn = Integer.valueOf(Cache.getByKey(Constants.CERVIX_TURN)) + 1;
                    Cache.save(Constants.CERVIX_TURN, String.valueOf(turn));
                    // se coloca la fecha de completado de preguntas
                    Cache.save(Constants.DATE_COMPLETED_CERVIX, dateCompleted);
                    break;
                case Constants.BREAST:
                    turn = Integer.valueOf(Cache.getByKey(Constants.BREAST_TURN)) + 1;
                    Cache.save(Constants.BREAST_TURN, String.valueOf(turn));
                    // se coloca la fecha de completado de preguntas
                    Cache.save(Constants.DATE_COMPLETED_BREAST, dateCompleted);
                    break;
            }

            ilQuestionDB.resetQuestion();
        }
    }


    private void setNextQuestion(int current) {
        try {
            int randomQ = randomNumberSecuence[current];
            Question currentQ = lstQuestion.get(randomQ);
            Cache.save(Constants.TYPE_Q, currentQ.getTypeQuestion());
            Cache.save(Constants.QUESTION_ID, currentQ.getIdquest());
            questionView.setPrimaryQuestion(currentQ.getTxtQuestion());
            questionView.setProgressBar(100 / lstQuestion.size());
            setTypeAnswer(context.getString(R.string.answer_label));// set de key que contiene el id de la respeusta al contestar
            switch (currentQ.getTypeQuestion()) {
                case Constants.EVALUATIVA:
                    //re carga las respuestas de la pregunta en el adaptador
                    Cache.save(Constants.INFO_TEACH, currentQ.getInfo());
                    questionView.loadAdapterRecycler(ilQuestionDB.getAnswers(currentQ.getIdquest()));
                    break;
                case Constants.RIESGO:
                    //se carga los label de los botones true y false
                    List<AnswersQuestion> lstAnswersQuestion = ilQuestionDB.getAnswers(currentQ.getIdquest());
                    for (AnswersQuestion answersQuestion : lstAnswersQuestion) {
                        if (answersQuestion.getValue()) {
                            lableTrue = answersQuestion.getDescription();
                            valueTrue = String.valueOf(answersQuestion.getValue());
                            Cache.save(Constants.ANSWER_TRUE_ID, answersQuestion.getIdAnswer());
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
                            Cache.save(Constants.ANSWER_FALSE_ID, answersQuestion.getIdAnswer());
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


    private void setTypeAnswer(String labelAnswer) {
        //se inicializa el valor entero para agregar al tipo de respuesta que va a guardar en firbase, 0 primera vez, 1 segunda vez
        //se usa el turno de tipo de pregunta ya que este lleva la cuenta de cuantas veces se ha contestado
        switch (Cache.getByKey(Constants.TYPE_CANCER)) {
            case Constants.CERVIX:
                Cache.save(Constants.TURN_ANSWER, labelAnswer + Cache.getByKey(Constants.CERVIX_TURN));
                break;
            case Constants.BREAST:
                Cache.save(Constants.TURN_ANSWER, labelAnswer + Cache.getByKey(Constants.BREAST_TURN));
                break;
        }
    }

    private int[] randomNumberSecuence(int num) {

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
}
