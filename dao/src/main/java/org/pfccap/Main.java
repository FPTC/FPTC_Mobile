package org.pfccap;


import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class Main {

    public static void main(String[] args){
        Schema schema = new Schema(1, "org.pfccap.education.dao");
        schema.enableKeepSectionsByDefault();
        createDatabase(schema);

        try {
            DaoGenerator  generator = new DaoGenerator();
            generator.generateAll(schema, args[0]);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void createDatabase(Schema schema) {

        //crea usuarios
        Entity user = schema.addEntity("User");
        user.addIdProperty().autoincrement();
        user.addStringProperty("name");
        user.addDateProperty("datelogin");

        //crea preguntas
        Entity questions = schema.addEntity("Question");
        questions.addIdProperty().autoincrement();
        questions.addStringProperty("idquest");
        questions.addStringProperty("txtQuestion");
        questions.addStringProperty("typeCancer");
        questions.addStringProperty("typeQuestion");
        questions.addIntProperty("order");
        questions.addBooleanProperty("enable");
        questions.addStringProperty("info");
        questions.addStringProperty("txtSecondQuestion");

        //crea respuestas de la pregunta
        Entity answersQuestion = schema.addEntity("AnswersQuestion");
        answersQuestion.addIdProperty().autoincrement();
        answersQuestion.addStringProperty("idQuestion");
        answersQuestion.addStringProperty("idAnswer");
        answersQuestion.addStringProperty("description");
        answersQuestion.addBooleanProperty("value");
        answersQuestion.addIntProperty("points");
        answersQuestion.addBooleanProperty("enable");


        //crea respeustas de la pregutna anidada
        Entity secondAnswer = schema.addEntity("SecondAnswer");
        secondAnswer.addIdProperty().autoincrement();
        secondAnswer.addStringProperty("idQuestion");
        secondAnswer.addStringProperty("idAnswer");
        secondAnswer.addStringProperty("description");


        //crea respuestas
        Entity answers = schema.addEntity("Answers");
        answers.addIdProperty().autoincrement();
        answers.addLongProperty("codeQuestion");
        answers.addStringProperty("answer");
        Property userProperty = answers.addLongProperty("idUser").notNull().getProperty();
        answers.addToOne(user, userProperty);

    }
}
