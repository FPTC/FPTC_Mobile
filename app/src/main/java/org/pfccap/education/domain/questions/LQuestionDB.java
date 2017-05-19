package org.pfccap.education.domain.questions;

import org.pfccap.education.entities.Answer;
import org.pfccap.education.entities.Question;
import org.pfccap.education.entities.SecondQuestion;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by USUARIO on 18/05/2017.
 */

public class LQuestionDB implements ILQuestionDB {

    @Override
    public Question load(String id) {

        List<Question> listQuestions = new ArrayList<Question>();

        Question question = new Question();
        question.setTypeQuestion("Educativa");
        question.setId("id123456");
        question.setEnable(true);
        question.setText("¿El cáncer de cuello uterino es común?");
        question.setOrder(1);
        question.setInfo("El cáncer de cuello uterino y el cáncer de seno son los más comunes en las mujeres colombianas.");

        Answer answer = new Answer();
        answer.setDescription("si");
        answer.setValue(true);

        Answer answer1 = new Answer();
        answer1.setDescription("no");
        answer1.setValue(false);

        HashMap<String, Answer> hasAnswer = new HashMap<>();
        hasAnswer.put("id234567", answer);
        hasAnswer.put("id234568", answer1);
        question.setAnswers(hasAnswer);
        listQuestions.add(question);

        Question question1 = new Question();
        question1.setTypeQuestion("Riesgo");
        question1.setId("id123458");
        question1.setEnable(true);
        question1.setText("¿Alguna vez te has hecho la citología vaginal?");
        question1.setOrder(3);
        question1.setInfo(null);

        Answer answer2 = new Answer();
        answer2.setDescription("si");
        answer2.setValue(true);

        Answer answer3 = new Answer();
        answer1.setDescription("no");
        answer1.setValue(false);

        HashMap<String, Answer> hasAnswer1 = new HashMap<>();
        hasAnswer1.put("id234574", answer2);
        hasAnswer1.put("id234573", answer3);
        question1.setAnswers(hasAnswer1);

        SecondQuestion secondQuestion = new SecondQuestion();
        secondQuestion.setText("¿Cuando fue tu última citología?");

        HashMap<String, String> hass = new HashMap<String, String>();
        hass.put("description1", "Hace menos de un año, description3=no recuerdo");
        hass.put("description2", "hace más de un año");
        hass.put("description3","no recuerdo");
        secondQuestion.setAnswers(hass);
        HashMap<String, SecondQuestion> secondhass = new HashMap<>();
        secondhass.put("question", secondQuestion);

        question1.setQuestion(secondhass);
        listQuestions.add(question1);

        Question question2 = new Question();
        question2.setTypeQuestion("Evaluativa");
        question2.setId("id123457");
        question2.setEnable(true);
        question2.setText("¿Cuál de los siguientes virus causa el cáncer de cuello uterino?");
        question2.setOrder(2);
        question2.setInfo(null);


        Answer answer4 = new Answer();
        answer4.setDescription("description=Virus del papiloma humano");
        answer4.setPoints(2);

        Answer answer5 = new Answer();
        answer5.setDescription("description=Virus del Zika");
        answer5.setPoints(0);

        Answer answer6 = new Answer();
        answer6.setDescription("description=Virus de la gripa");
        answer6.setPoints(0);

        HashMap<String, Answer> hasAnswer2 = new HashMap<>();
        hasAnswer2.put("id234570", answer4);
        hasAnswer2.put("id234571", answer5);
        hasAnswer2.put("id234569", answer6);
        question2.setAnswers(hasAnswer2);
        listQuestions.add(question2);
        Cache.save(Constants.PROGRESS_BAR, String.valueOf(listQuestions.size()));
        if (id.equals("0")){
            return listQuestions.get(0);
        }else {
            int q = Integer.valueOf(id);
            return listQuestions.get(q);
        }
    }
}