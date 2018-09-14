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
        questions.addBooleanProperty("answer");


        //crea respuestas de la pregunta
        Entity answersQuestion = schema.addEntity("AnswersQuestion");
        answersQuestion.addIdProperty().autoincrement();
        answersQuestion.addStringProperty("idQuestion");
        answersQuestion.addStringProperty("idAnswer");
        answersQuestion.addStringProperty("description");
        answersQuestion.addBooleanProperty("value");
        answersQuestion.addIntProperty("points");
        answersQuestion.addBooleanProperty("enable");
        answersQuestion.addStringProperty("txtSecondQuestion");


        //crea respeustas de la pregutna anidada
        Entity secondAnswer = schema.addEntity("SecondAnswer");
        secondAnswer.addIdProperty().autoincrement();
        secondAnswer.addStringProperty("idAnswer");
        secondAnswer.addStringProperty("idSecondAnswer");
        secondAnswer.addStringProperty("description");

        //crea tabla para datos de la tabla de puntos
        Entity gift = schema.addEntity("Gift");
        gift.addIdProperty().autoincrement();
        gift.addStringProperty("points");
        gift.addStringProperty("gift");
        gift.addIntProperty("order");

        //crea tabla paises
        Entity paises = schema.addEntity("Paises");
        paises.addIdProperty().autoincrement();
        paises.addIntProperty("idCountry");
        paises.addStringProperty("name");
        paises.addBooleanProperty("state");

        //crea tabla ciudades
        Entity ciudades = schema.addEntity("Ciudades");
        ciudades.addIdProperty().autoincrement();
        ciudades.addIntProperty("idCity");
        ciudades.addIntProperty("idPais");
        ciudades.addStringProperty("name");
        ciudades.addBooleanProperty("state");

        //crea tabla comunas
        Entity comunas = schema.addEntity("Comunas");
        comunas.addIdProperty().autoincrement();
        comunas.addIntProperty("idComuna");
        comunas.addIntProperty("idPais");
        comunas.addIntProperty("idCiudad");
        comunas.addStringProperty("name");
        comunas.addBooleanProperty("state");

        //crea tabla ese
        Entity ese = schema.addEntity("Ese");
        ese.addIdProperty().autoincrement();
        ese.addIntProperty("idEse");
        ese.addIntProperty("idPais");
        ese.addIntProperty("idCiudad");
        ese.addStringProperty("name");
        ese.addBooleanProperty("state");

        //crea tabla ips
        Entity ips = schema.addEntity("IPS");
        ips.addIdProperty().autoincrement();
        ips.addIntProperty("idIps");
        ips.addIntProperty("idPais");
        ips.addIntProperty("idCiudad");
        ips.addIntProperty("idEse");
        ips.addStringProperty("name");
        ips.addBooleanProperty("state");
    }
}
