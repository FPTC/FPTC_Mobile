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

        //crea pregunta educativas
        Entity education = schema.addEntity("Education");
        education.addIdProperty().autoincrement();
        education.addStringProperty("code");
        education.addStringProperty("question");
        education.addIntProperty("typeCancer");

        //crea pregunta riesgo
        Entity risk = schema.addEntity("Risk");
        risk.addIdProperty().autoincrement();
        risk.addStringProperty("code");
        risk.addStringProperty("question");
        risk.addIntProperty("typeCancer");

        //crea pregunta evaluativas
        Entity evaluation = schema.addEntity("Evaluation");
        evaluation.addIdProperty().autoincrement();
        evaluation.addStringProperty("code");
        evaluation.addStringProperty("question");
        evaluation.addIntProperty("typeCancer");

        //crea respuestas
        Entity answers = schema.addEntity("Answers");
        answers.addIdProperty().autoincrement();
        answers.addLongProperty("codeQuestion");
        answers.addStringProperty("answer");
        Property userProperty = answers.addLongProperty("idUser").notNull().getProperty();
        answers.addToOne(user, userProperty);

    }
}
