package org.pfccap.education.presentation.main.presenters;

import android.content.Context;
import android.view.View;

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

            switch (Cache.getByKey(Constants.TYPE_CANCER)) {
                case Constants.CERVIX:
                    int turn = Integer.valueOf(Cache.getByKey(Constants.CERVIX_TURN)) + 1;
                    Cache.save(Constants.CERVIX_TURN, String.valueOf(turn));
                    // se coloca la fecha de completado de preguntas
                    Cache.save(Constants.DATE_COMPLETED_CERVIX, dateCompleted);

                    updateDataFirebase(Constants.DATE_COMPLETED_CERVIX, dateCompleted);
                    updateDataFirebase(Constants.CERVIX_TURN, turn);

                    if (Integer.valueOf(Cache.getByKey(Constants.CURRENT_POINTS_C)) > Integer.valueOf(Cache.getByKey(Constants.TOTAL_POINTS_C))) {
                        message = context.getResources().getString(R.string.total_points_up,
                                Cache.getByKey(Constants.TOTAL_POINTS_C), Cache.getByKey(Constants.CURRENT_POINTS_C));
                        Cache.save(Constants.TOTAL_POINTS_C, Cache.getByKey(Constants.CURRENT_POINTS_C));
                    } else {
                        message = context.getResources().getString(R.string.total_points_equal,
                                Cache.getByKey(Constants.TOTAL_POINTS_C), Cache.getByKey(Constants.CURRENT_POINTS_C));
                    }
                    updateDataFirebase(Constants.TOTAL_POINTS_C, Integer.valueOf(Cache.getByKey(Constants.TOTAL_POINTS_C)));
                    break;
                case Constants.BREAST:
                    turn = Integer.valueOf(Cache.getByKey(Constants.BREAST_TURN)) + 1;
                    Cache.save(Constants.BREAST_TURN, String.valueOf(turn));
                    // se coloca la fecha de completado de preguntas
                    Cache.save(Constants.DATE_COMPLETED_BREAST, dateCompleted);

                    updateDataFirebase(Constants.DATE_COMPLETED_BREAST, dateCompleted);
                    updateDataFirebase(Constants.BREAST_TURN, turn);

                    if (Integer.valueOf(Cache.getByKey(Constants.CURRENT_POINTS_B)) > Integer.valueOf(Cache.getByKey(Constants.TOTAL_POINTS_B))) {
                        message = context.getResources().getString(R.string.total_points_up,
                                Cache.getByKey(Constants.TOTAL_POINTS_B), Cache.getByKey(Constants.CURRENT_POINTS_B));
                        Cache.save(Constants.TOTAL_POINTS_B, Cache.getByKey(Constants.CURRENT_POINTS_B));
                    } else {
                        message = context.getResources().getString(R.string.total_points_equal,
                                Cache.getByKey(Constants.TOTAL_POINTS_B), Cache.getByKey(Constants.CURRENT_POINTS_B));
                    }
                    updateDataFirebase(Constants.TOTAL_POINTS_B, Integer.valueOf(Cache.getByKey(Constants.TOTAL_POINTS_B)));
                    break;
            }
            updateDataFirebase(Constants.TOTAL_POINTS, Integer.valueOf(Cache.getByKey(Constants.TOTAL_POINTS_C)) +
                    Integer.valueOf(Cache.getByKey(Constants.TOTAL_POINTS_B)));

            setPoints(message);
        } else {
            setNextQuestion(current);
            updateDataFirebase(Constants.STATE, 1);
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
    public void calculatePointsCheck(int points, boolean value, String answerId) {
        switch (Cache.getByKey(Constants.TYPE_Q)) {
            case Constants.EVALUATIVA:
                //se gestiona las respuestas tivo evaluativas
                try {

                    switch (Cache.getByKey(Constants.TYPE_CANCER)) {
                        case Constants.CERVIX:
                            sumPoints(Constants.CURRENT_POINTS_C, points);
                            updateDataFirebase(Constants.CURRENT_POINTS_C, Integer.valueOf(Cache.getByKey(Constants.CURRENT_POINTS_C)));
                            break;
                        case Constants.BREAST:
                            sumPoints(Constants.CURRENT_POINTS_B, points);
                            updateDataFirebase(Constants.CURRENT_POINTS_B, Integer.valueOf(Cache.getByKey(Constants.CURRENT_POINTS_B)));
                            break;
                    }

                    if (!Cache.getByKey(Constants.INFO_TEACH).equals("")) {
                        questionView.showInfoTxtSecondary();
                    }
                    questionView.disableItemsAdapter();

                    textSnackBar(value);
                } catch (Exception e) {
                    FirebaseCrash.report(e);
                    System.out.println("error " + e.getMessage());
                }

                break;
            case Constants.RIESGO:
                //verifico si hay pregunta aninada para la respuesta dada, trayendo los datos de la pregunta que corresponden a esta respuesta
                AnswersQuestion answers = ilQuestionDB.getAnswersByAnswers(Cache.getByKey(Constants.QUESTION_ID),answerId);
                if (answers != null && answers.getTxtSecondQuestion() != null &&
                        !answers.getTxtSecondQuestion().equals("")) {
                    //se guarda en cache la preguna aninada para mostrala cuando se necesite
                    getSecondAnswers(answerId);
                    Cache.save(Constants.SECOND_Q, answers.getTxtSecondQuestion());
                } else {
                    Cache.save(Constants.SECOND_Q, "");
                    loadInfoSnackbar(context.getResources().getString(R.string.thanks_for_answers));
                }
                questionView.processAnswer();
                break;
            case Constants.EDUCATIVA:
                questionView.processAnswer();
                textSnackBar(value);
                break;
            default:
                textSnackBar(value);
                break;
        }

    }

    private void textSnackBar(boolean value) {

        String check;
        if (value) {
            check = context.getResources().getString(R.string.right);
        } else {
            check = context.getResources().getString(R.string.fail);
        }
        loadInfoSnackbar(check);
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

    private void updateDataFirebase(String path, Object data) {
        HashMap<String, Object> obj = new HashMap<>();
        obj.put(path, data);
        IUserBP userBP = new UserBP();
        userBP.update(obj);
    }

    @Override
    public void getSecondAnswers(String idAnswer) {
        //se carga el valor del tipo de respuesta para almacenar en firebase con el valor aninada + el turno de la respeusta de cuestionario
        setTypeAnswer(context.getString(R.string.nested_label));
        //cargo las respuestas de la pregunta aninada, se debe cambiar de tipo de lista por la clase que recibe el adaptador
        List<SecondAnswer> secondAnswerList = ilQuestionDB.getSecondAnswers(idAnswer);
        List<AnswersQuestion> answersQuestionList = new ArrayList<>();
        AnswersQuestion answersQuestion;
        if (secondAnswerList != null && secondAnswerList.size() > 0) {
            for (SecondAnswer secondAnswer : secondAnswerList) {
                answersQuestion = new AnswersQuestion();
                answersQuestion.setDescription(secondAnswer.getDescription());
                answersQuestion.setPoints(0);
                answersQuestion.setValue(false);
                answersQuestion.setIdAnswer(secondAnswer.getIdSecondAnswer());
                answersQuestionList.add(answersQuestion);
            }
            questionView.loadAdapterRecycler(answersQuestionList);
        } else {
            loadInfoSnackbar(context.getResources().getString(R.string.error_nested));
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
                    questionView.loadAdapterRecycler(ilQuestionDB.getAnswersByQuestion(currentQ.getIdquest()));
                    break;
                case Constants.RIESGO:
                    //se carga los label de los botones true y false
                    questionView.loadAdapterRecycler(ilQuestionDB.getAnswersByQuestion(currentQ.getIdquest()));
                    Cache.save(Constants.INFO_TEACH, currentQ.getInfo());
                    break;
                case Constants.EDUCATIVA:
                    questionView.loadAdapterRecycler(ilQuestionDB.getAnswersByQuestion(currentQ.getIdquest()));
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
