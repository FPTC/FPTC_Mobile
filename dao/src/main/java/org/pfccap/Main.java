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
        Entity questions = schema.addEntity("Questions");
        questions.addIdProperty().autoincrement();
        questions.addStringProperty("idquest");
        questions.addStringProperty("txtQuestion");
        questions.addStringProperty("typeCancer");
        questions.addStringProperty("typeQuestion");
        questions.addIntProperty("order");
        questions.addBooleanProperty("enable");
        questions.addStringProperty("info");
        questions.addStringProperty("Answers");
        questions.addStringProperty("secondQuestion");
        questions.addStringProperty("secondAnswers");

        //crea respuestas de la preunta
        Entity answersQuestion = schema.addEntity("AnswersQuestion");
        answersQuestion.addIdProperty();
        answersQuestion.addStringProperty("description");
        answersQuestion.addStringProperty("value");
        answersQuestion.addStringProperty("points");
        Property questionProperty = answersQuestion.addLongProperty("idQuestion").notNull().getProperty();
        answersQuestion.addToOne(questions, questionProperty);

        //crea respuestas
        Entity answers = schema.addEntity("Answers");
        answers.addIdProperty().autoincrement();
        answers.addLongProperty("codeQuestion");
        answers.addStringProperty("answer");
        Property userProperty = answers.addLongProperty("idUser").notNull().getProperty();
        answers.addToOne(user, userProperty);

    }
}
