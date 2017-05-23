package org.pfccap.education.presentation.main.presenters;

import com.google.firebase.crash.FirebaseCrash;

import org.pfccap.education.dao.AnswersQuestion;
import org.pfccap.education.dao.Question;
import org.pfccap.education.dao.SecondAnswer;
import org.pfccap.education.domain.questions.ILQuestionDB;
import org.pfccap.education.domain.questions.LQuestionDB;
import org.pfccap.education.presentation.main.ui.activities.IQuestionView;
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

    public QuestionPresenter(IQuestionView questionView) {
        this.questionView = questionView;
        ilQuestionDB = new LQuestionDB();
    }

    @Override
    public List<Question> getQuestionsDB() {
        return ilQuestionDB.getAll(Cache.getByKey(Constants.TYPE_CANCER));
    }

    @Override
    public void loadQuestionCurrent(List<Question> questions, int randomQ) {
        try {
            Question currentQ = questions.get(randomQ);
            Cache.save(Constants.TYPE_Q, currentQ.getTypeQuestion());
            Cache.save(Constants.QUESTION_ID, currentQ.getIdquest());
            questionView.setPrimaryQuestion(currentQ.getTxtQuestion());
            questionView.setProgressBar(100 / questions.size());
            switch (currentQ.getTypeQuestion()) {
                case "Evaluativa":
                    //re carga las respuestas de la pregunta en el adaptador
                    questionView.loadAdapterRecycler(ilQuestionDB.getAnswers(currentQ.getIdquest()));
                    break;
                case "Riesgo":
                    //se carga los label de los botones true y false
                    for (AnswersQuestion answersQuestion : ilQuestionDB.getAnswers(currentQ.getIdquest())) {
                        if (answersQuestion.getValue()) {
                            lableTrue = answersQuestion.getDescription();
                        } else {
                            lableFalse = answersQuestion.getDescription();
                        }
                    }
                    questionView.setLabelButtonTrueFalse(lableTrue, lableFalse);

                    //se verifica si existe segunda preguna
                    if (!currentQ.getTxtSecondQuestion().equals("")) {
                        //se guarda en cache la preguna aninada para mostrala cuando se necesite
                        Cache.save(Constants.SECOND_Q, currentQ.getTxtSecondQuestion());

                        //cargo las respuestas de la pregunta aninada, se debe cambiar de tipo de lista por la clase que recibe el adaptador
                        List<SecondAnswer> secondAnswerList = ilQuestionDB.getSecondAnswers(currentQ.getIdquest());
                        List<AnswersQuestion> answersQuestionList = new ArrayList<>();
                        AnswersQuestion answersQuestion;
                        for (SecondAnswer secondAnswer : secondAnswerList) {
                            answersQuestion = new AnswersQuestion();
                            answersQuestion.setDescription(secondAnswer.getDescription());
                            answersQuestion.setPoints(0);
                            answersQuestionList.add(answersQuestion);
                        }
                        questionView.loadAdapterRecycler(answersQuestionList);
                    }
                    break;
                case "Educativa":
                    //se carga los label de los botones true y false
                    for (AnswersQuestion answersQuestion : ilQuestionDB.getAnswers(currentQ.getIdquest())) {
                        if (answersQuestion.getValue()) {
                            lableTrue = answersQuestion.getDescription();
                        } else {
                            lableFalse = answersQuestion.getDescription();
                        }
                    }
                    questionView.setLabelButtonTrueFalse(lableTrue, lableFalse);
                    //se carga la información que se muestra en el snack bar para usarla luego
                    Cache.save(Constants.INFO_SNACKBAR, currentQ.getInfo());
                    break;
            }
        }catch (Exception e){
            FirebaseCrash.report(e);
        }

    }

    @Override
    public void saveAnswerQuestionDB() {

    }

    @Override
    public void loadNextQuestion() {
     questionView.loadNextQuestion();
    }

    @Override
    public void finishAcivity() {
        questionView.finishActivity();
    }

    @Override
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
        }catch (Exception e){
            FirebaseCrash.report(e);
            return null;
        }
    }

    @Override
    public void loadInfoSnakbar(String check) {
        questionView.setInfoSnackbar(check + "\n" + Cache.getByKey(Constants.INFO_SNACKBAR));
    }

    @Override
    public void calculatePointsCheck(int points) {

        int totalPoinst;
        if (Cache.getByKey(Constants.TOTAL_POINTS).equals("")){
            totalPoinst = 0;
        }else {
            totalPoinst =  Integer.parseInt(Cache.getByKey(Constants.TOTAL_POINTS));
        }
        totalPoinst = totalPoinst + points;
        Cache.save(Constants.TOTAL_POINTS, String.valueOf(totalPoinst));

        String check = "";
        if (points>0 && Cache.getByKey(Constants.TYPE_Q).equals("Evaluativa")){
            check = "¡Correcto!";
        }else if(points == 0 && Cache.getByKey(Constants.TYPE_Q).equals("Evaluativa")) {
            check = "¡Incorrecto!";
        }else {
            check = "";
        }
        loadInfoSnakbar(check);
    }
}
