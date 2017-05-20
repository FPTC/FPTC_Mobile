package org.pfccap.education.presentation.main.presenters;

import org.pfccap.education.dao.Question;
import org.pfccap.education.domain.questions.ILQuestionDB;
import org.pfccap.education.domain.questions.LQuestionDB;
import org.pfccap.education.presentation.main.ui.activities.IQuestionView;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;

import java.util.List;
import java.util.Random;

/**
 * Created by USUARIO on 08/05/2017.
 */

public class QuestionPresenter implements IQuestionPresenter {

    private IQuestionView questionView;
    private ILQuestionDB ilQuestionDB;


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
        Question currentQ = questions.get(randomQ);
        Cache.save(Constants.TYPE_Q, currentQ.getTypeQuestion());
        questionView.setPrimaryQuestion(currentQ.getTxtQuestion());
        switch (currentQ.getTypeQuestion()){
            case "Evaluativa":
                try {
                    Object array = currentQ.getAnswers();

                } catch (Exception e) {
                    e.printStackTrace();
                }
             //   HashMap<String, Answer> answers = obj;
                break;
            case "Riesgo":
                try {
                    Object array = currentQ.getAnswers();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Educativa":
                try {
                    Object array = currentQ.getAnswers();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    @Override
    public void saveAnswerQuestionDB() {
        questionView.setInfoSnackbar("", Cache.getByKey(Constants.NEXT_Q));
    }

    @Override
    public void loadLablesAnswer(String numQst) {
        ILQuestionDB lquestion = new LQuestionDB();
      //  Question listQuestions = lquestion.load(numQst);
    //    questionView.setInfoSnackbar(listQuestions.getInfo(), numQst);
    }

    @Override
    public void finishAcivity() {
        questionView.finishActivity();
    }

    @Override
    public int[] ramdomNumberSecuence(int num) {

        //num es el total de preguntas que hay en la lista obtenida en la base de datos
        int[] numerosAleatorios = new int[num];
        for (int i = 0; i < num; i++) {
            numerosAleatorios[i] = i;
        }
        //desordenando los elementos
        Random r = new Random();
        for (int i = numerosAleatorios.length; i > 0; i--) {
            int posicion = r.nextInt(i);
            int tmp = numerosAleatorios[i-1];
            numerosAleatorios[i - 1] = numerosAleatorios[posicion];
            numerosAleatorios[posicion] = tmp;
        }
        return numerosAleatorios;
    }
}
