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
import org.pfccap.education.domain.services.ValidationService;
import org.pfccap.education.domain.user.IUserBP;
import org.pfccap.education.domain.user.UserBP;
import org.pfccap.education.entities.UserAuth;
import org.pfccap.education.entities.Validation;
import org.pfccap.education.presentation.main.ui.activities.IQuestionView;
import org.pfccap.education.utilities.APIService;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by USUARIO on 08/05/2017.
 */

public class QuestionPresenter implements IQuestionPresenter {

    private IQuestionView questionView;
    private ILQuestionDB ilQuestionDB;
    private IQuestionBP questionBP;
    private List<Question> lstQuestion;
    private List<Question> lstQuestionWithoutFilter;
    private int[] randomNumberSecuence;
    private Context context;
    int progress;
    private String message = "";

    public QuestionPresenter(IQuestionView questionView, Context context) {
        this.questionView = questionView;
        this.context = context;
        ilQuestionDB = new LQuestionDB();
        questionBP = new QuestionBP();
    }

    @Override
    public void getQuestionsDB(int current) {
        lstQuestion = ilQuestionDB.getAll(Cache.getByKey(Constants.TYPE_CANCER));
        lstQuestionWithoutFilter = ilQuestionDB.getAllWithoutFilter(Cache.getByKey(Constants.TYPE_CANCER));
        randomNumberSecuence = randomNumberSecuence(lstQuestion.size());
        progress = 100 / lstQuestion.size();

        if (lstQuestion != null && randomNumberSecuence != null && lstQuestion.size() != 0
                && randomNumberSecuence.length != 0) {
            //esta validación se coloco para el caso especial en el que a un usuario cuando contastaba las preguntas de un modulo
            //se le reiniciaban las preguntas pero el total de puntos seguia acumulando, dando como resultado un tope mayor al límite.
            if (lstQuestion.size() == lstQuestionWithoutFilter.size() && current == 0) {
                switch (Cache.getByKey(Constants.TYPE_CANCER)) {
                    case Constants.CERVIX:
                        Cache.save(Constants.TOTAL_POINTS_C, "0");
                        Cache.save(Constants.CURRENT_POINTS_C, "0");
                        break;
                    case Constants.BREAST:
                        Cache.save(Constants.TOTAL_POINTS_B, "0");
                        Cache.save(Constants.CURRENT_POINTS_B, "0");
                        break;
                }

            }
            loadQuestionCurrent(current);
        } else {
            questionView.finishActivity(context.getResources().getString(R.string.no_db_questions));
        }
    }

    @Override
    public void loadQuestionCurrent(int current) {

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

                    message = context.getResources().getString(R.string.firs_total_points, Cache.getByKey(Constants.CURRENT_POINTS_C));
                    if (Integer.valueOf(Cache.getByKey(Constants.LAPSE_CERVIX)) > 1) {
                        message = context.getResources().getString(R.string.firs_total_points, Cache.getByKey(Constants.CURRENT_POINTS_C)) +
                                context.getResources().getString(R.string.turn_to_next_Oportunity, Cache.getByKey(Constants.LAPSE_CERVIX));
                    }
                    Cache.save(Constants.TOTAL_POINTS_C, Cache.getByKey(Constants.CURRENT_POINTS_C));

                    /*if (Cache.getByKey(Constants.TOTAL_POINTS_C).equals("0")) {

                        message = context.getResources().getString(R.string.firs_total_points, Cache.getByKey(Constants.CURRENT_POINTS_C),
                                Cache.getByKey(Constants.LAPSE_CERVIX));
                        Cache.save(Constants.TOTAL_POINTS_C, Cache.getByKey(Constants.CURRENT_POINTS_C));

                    } else if (Integer.valueOf(Cache.getByKey(Constants.CURRENT_POINTS_C)) >
                            Integer.valueOf(Cache.getByKey(Constants.TOTAL_POINTS_C))) {
                        message = context.getResources().getString(R.string.total_points_up,
                                Cache.getByKey(Constants.TOTAL_POINTS_C), Cache.getByKey(Constants.CURRENT_POINTS_C)) + "\n\n" +
                                context.getResources().getString(R.string.finish_question_message);
                        Cache.save(Constants.TOTAL_POINTS_C, Cache.getByKey(Constants.CURRENT_POINTS_C));

                    } else {
                        message = context.getResources().getString(R.string.total_points_equal,
                                Cache.getByKey(Constants.TOTAL_POINTS_C), Cache.getByKey(Constants.CURRENT_POINTS_C)) + "\n\n" +
                                context.getResources().getString(R.string.finish_question_message);

                    }*/
                    updateDataFirebase(Constants.TOTAL_POINTS_C, Integer.valueOf(Cache.getByKey(Constants.TOTAL_POINTS_C)));

                    try {
                        message = message + validateDateLastAnswer(Constants.DATE_COMPLETED_CERVIX, Constants.LAPSE_CERVIX);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        FirebaseCrash.report(e);
                    }
                    break;
                case Constants.BREAST:
                    turn = Integer.valueOf(Cache.getByKey(Constants.BREAST_TURN)) + 1;
                    Cache.save(Constants.BREAST_TURN, String.valueOf(turn));
                    // se coloca la fecha de completado de preguntas
                    Cache.save(Constants.DATE_COMPLETED_BREAST, dateCompleted);

                    updateDataFirebase(Constants.DATE_COMPLETED_BREAST, dateCompleted);
                    updateDataFirebase(Constants.BREAST_TURN, turn);


                    message = context.getResources().getString(R.string.firs_total_points, Cache.getByKey(Constants.CURRENT_POINTS_B));
                    if (Integer.valueOf(Cache.getByKey(Constants.LAPSE_BREAST)) > 1) {
                        message = context.getResources().getString(R.string.firs_total_points, Cache.getByKey(Constants.CURRENT_POINTS_B)) +
                                context.getResources().getString(R.string.turn_to_next_Oportunity, Cache.getByKey(Constants.LAPSE_BREAST));
                    }
                    Cache.save(Constants.TOTAL_POINTS_B, Cache.getByKey(Constants.CURRENT_POINTS_B));
                   /* if (Cache.getByKey(Constants.TOTAL_POINTS_B).equals("0")) {
                    } else if (Integer.valueOf(Cache.getByKey(Constants.CURRENT_POINTS_B)) >
                            Integer.valueOf(Cache.getByKey(Constants.TOTAL_POINTS_B))) {
                        message = context.getResources().getString(R.string.total_points_up,
                                Cache.getByKey(Constants.TOTAL_POINTS_B), Cache.getByKey(Constants.CURRENT_POINTS_B)) + "\n\n" +
                                context.getResources().getString(R.string.finish_question_message);
                        Cache.save(Constants.TOTAL_POINTS_B, Cache.getByKey(Constants.CURRENT_POINTS_B));
                    } else {
                        message = context.getResources().getString(R.string.total_points_equal,
                                Cache.getByKey(Constants.TOTAL_POINTS_B), Cache.getByKey(Constants.CURRENT_POINTS_B)) + "\n\n" +
                                context.getResources().getString(R.string.finish_question_message);
                    }*/
                    updateDataFirebase(Constants.TOTAL_POINTS_B, Integer.valueOf(Cache.getByKey(Constants.TOTAL_POINTS_B)));

                    try {
                        message = message + validateDateLastAnswer(Constants.DATE_COMPLETED_BREAST, Constants.LAPSE_BREAST);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        FirebaseCrash.report(e);
                    }
                    break;
            }
            updateDataFirebase(Constants.TOTAL_POINTS, Integer.valueOf(Cache.getByKey(Constants.TOTAL_POINTS_C)) +
                    Integer.valueOf(Cache.getByKey(Constants.TOTAL_POINTS_B)));

            //TODO
            questionView.tamizaje();
        } else {
            setNextQuestion(current);
            updateDataFirebase(Constants.STATE, 1);
        }
    }

    private void setPoints(String message) {
        questionView.finishActivity(message);
    }

    private String validateDateLastAnswer(String dateCancer, String lapseCancer) throws ParseException {

        String dayMessage = "";

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        cal.setTime(sdf.parse(dateCancer));
        cal.add(Calendar.DATE, Integer.valueOf(lapseCancer));

        if (!dateCancer.equals("null") && !dateCancer.equals("") && !lapseCancer.equals("0") && cal.after(Calendar.getInstance())) {
            dayMessage = "\n\n" + context.getString(R.string.finish_question_message);
        }

        return dayMessage;
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
                        questionView.showInfoTxtSecondary(value);
                    }
                    questionView.disableItemsAdapter();

                    loadInfoSnackbar("");
                } catch (Exception e) {
                    FirebaseCrash.report(e);
                    System.out.println("error " + e.getMessage());
                }

                break;
            case Constants.RIESGO:
                //verifico si hay pregunta aninada para la respuesta dada, trayendo los datos de la pregunta que corresponden a esta respuesta
                AnswersQuestion answers = ilQuestionDB.getAnswersByAnswers(Cache.getByKey(Constants.QUESTION_ID), answerId);

                if (answers != null && answers.getTxtSecondQuestion() != null &&
                        !answers.getTxtSecondQuestion().equals("")) {
                    //se guarda en cache la preguna aninada para mostrala cuando se necesite
                    getSecondAnswers(answerId);
                    Cache.save(Constants.SECOND_Q, answers.getTxtSecondQuestion());
                } else {
                    Cache.save(Constants.SECOND_Q, "");
                    questionView.disableItemsAdapter();
                    loadInfoSnackbar(context.getResources().getString(R.string.thanks_for_answers));
                }
                questionView.processAnswer(value);
                break;
            case Constants.EDUCATIVA:
                questionView.processAnswer(value);
                loadInfoSnackbar("");
                break;
            default:
                loadInfoSnackbar("");
                break;
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
                    calendar.get(Calendar.MONTH) + 1,
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

            if (current == lstQuestion.size() - 1) {
                questionView.setProgressBar(100); // si es la ultima pregunta lleno el progress bar porque en
                // algunos casos el resultado de la división da pasos menores al total necesario para llenarlo
            } else {
                questionView.setProgressBar(progress);
            }

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

    //comprobación de tamizaje después de finalizar cada ronda de preguntas.
    @Override
    public void getValidaionAppointment(String Uid) {
        questionView.showProgress();

        ValidationService service = APIService.getInstanceRetrofit(ValidationService.class);

        service.getValidation(Uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Validation>() {
                    @Override
                    public void onNext(Validation value) {

                        if (value != null) {
                            try {
                                boolean cervix = value.isCervix();
                                boolean breast = value.isBreast();

                                if (cervix && breast) {
                                    Cache.save(Constants.APPOINTMENT_TYPE, "3");
                                    message = message + "\n\n" + context.getResources().getString(R.string.message_validation_yes,
                                            context.getResources().getString(R.string.appointment_cervix_breast));
                                } else if (cervix) {
                                    Cache.save(Constants.APPOINTMENT_TYPE, "2");
                                    message = message + "\n\n" + context.getResources().getString(R.string.message_validation_yes,
                                            context.getResources().getString(R.string.appointment_cervix));
                                } else if (breast) {
                                    Cache.save(Constants.APPOINTMENT_TYPE, "1");
                                    message = message + "\n\n" + context.getResources().getString(R.string.message_validation_yes,
                                            context.getResources().getString(R.string.appointment_breast));
                                } else {
                                    Cache.save(Constants.APPOINTMENT_TYPE, "0");
                                }
                                Cache.save(Constants.TYPE_CANCER_ERROR_TAMIZAJE_CERVIX, "false");
                                Cache.save(Constants.TYPE_CANCER_ERROR_TAMIZAJE_BREAST, "false");
                                questionView.hideProgress();
                                setPoints(message);
                            } catch (Exception e) {
                                //se utiliza esta constante para poner un aviso en el inicio para que intenten sincronizar de nuevo esto debido a la desconexión de internet principalmente.
                                errorTamizaje("\n\n" + e.getMessage());
                                FirebaseCrash.report(e);
                            }
                        } else {
                            //en este caso no hay excepción
                            errorTamizaje("");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        questionView.hideProgress();
                        errorTamizaje("\n\n" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void errorTamizaje(String e) {
        switch (Cache.getByKey(Constants.TYPE_CANCER)) {
            case Constants.CERVIX:
                Cache.save(Constants.TYPE_CANCER_ERROR_TAMIZAJE_CERVIX, "true");
                break;
            case Constants.BREAST:
                Cache.save(Constants.TYPE_CANCER_ERROR_TAMIZAJE_BREAST, "true");
                break;
        }
        message = message + "\n\n" + context.getString(R.string.error_tamizaje) + e;
        setPoints(message);
    }
}
