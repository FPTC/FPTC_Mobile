package org.pfccap.education.utilities;

/**
 * Created by jggomez on 25-Apr-17.
 */

public class Constants {

    //para remote config firebase
    public static final String BASE_URL_SERVICE_KEY = "serviceAppointment";
    public static final String BASE_URL_TERMS_CONDITIONS_KEY = "urlTermCondition";
    public static final String BASE_URL_PRIVACY_POLICY_KEY = "urlPrivacyPolicy";
    public static final String BASE_URL_CREATE_EMAIL_APP = "urlCreateEmailApp";
    public static final int CACHE_EXPIRATION = 3600;
    public static final String FAILEDTRIES = "failedTries";
    public static final String EXAMGIFTBREAST = "examGiftBreast";
    public static final String EXAMGIFTCERVIX = "examGiftCervix";


    //autenticación de usuarios
    public static String USER_UID = "useruid";
    public static String USER_NAME = "username";
    public static String EMAIL = "email";
    public static String IS_LOGGGIN = "islogging";

    //cargue de preguntas
    public static final String TYPE_Q = "typeq";
    public static String SECOND_Q = "secondq"; //almacena el texto de la pregunta anidada
    public static final String TYPE_CANCER = "typecancer";
    public static final String QUESTION_ID = "questionid";
    public static final String INFO_TEACH = "infosnackbar";
    public static final String CERVIX = "cervixCancer";
    public static final String BREAST = "breastCancer";

    //tipos pregunta
    public static final String EVALUATIVA = "Evaluativa";
    public static final String EDUCATIVA = "Educativa";
    public static final String RIESGO = "Riesgo";
    public static final String CERVIX_TURN = "repetitionsAnswersCervix";
    public static final String BREAST_TURN = "repetitionsAnswersBreast";
    public static final String DATE_COMPLETED_BREAST = "dateCompletedBreast";
    public static final String DATE_COMPLETED_CERVIX = "dateCompletedCervix";
    public static final String PROFILE_COMPLETED = "profileCompleted";

    //puntos
    public static final String TOTAL_POINTS_C = "pointsCervix";
    public static final String TOTAL_POINTS_B = "pointsBreast";
    public static final String CURRENT_POINTS_C = "currentPointsCervix";
    public static final String CURRENT_POINTS_B = "currentPointsBreast";
    public static final String TOTAL_POINTS = "pointsTotal";

    //almacenmiento de respuestas
    public static final String TURN_ANSWER = "turnanswer";

    //configuracion
    public static String LAPSE_BREAST = "lapsebreast";
    public static String LAPSE_CERVIX = "laspsecervix";
    public static String NUM_OPPORTUNITIES = "numOpportunities";

    //tabla de puntos, es el valor del premio que dan por sacar la cita
    public static String APPOINTMENT_GIFT = "appointmentgift";
    //el para el tipo de examen, 0 quiere decir queno requiere examane, 1 para mamografía, 2 para citología y 3 para los dos
    public static String APPOINTMENT_TYPE = "appointmenttype";

    //constantes para actualizar los datos de usuario en firebase
    public static final String NAME = "name";
    public static final String LASTNAME = "lastName";
    public static final String ADDRESS = "address";
    public static final String DATEBIRDARY = "dateBirthday";
    public static final String HASCHILDS = "hasChilds";
    public static final String HEIGHT = "height";
    public static final String WEIGHT = "weight";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String NEIGHVORHOOD = "neighborhood";
    public static final String PHONENUMBER = "phoneNumber";
    public static final String PHONENUMBERCEL = "phoneNumberCel";
    public static final String STATE = "state";
    public static final String APPOINTMENT = "appointment";

    public static final String DATE_CREATE = "dateCreate";

    //se agregan nuevos campos al sisteme 21/09/2018
    public static final String COUNTRY = "pais" ;
    public static final String CITY = "ciudad";
    public static final String COMUNA = "comuna";
    public static final String ESE = "ese";
    public static final String IPS = "ips";
    public static final String ERROR_TAMIZAJE = "errorTamisaje";
    public static final String TYPE_CANCER_ERROR_TAMIZAJE_CERVIX = "tamizaje_error_cervix";
    public static final String TYPE_CANCER_ERROR_TAMIZAJE_BREAST = "tamizaje_error_breast";
    public static final String INTENTOS = "intentos";
    public static final String BREAST_INDICATION = "breastIndication";
    public static final String CERVIX_INDICATION = "cervixIndication";
    public static final String STATE_CODE = "stateCade";
}
