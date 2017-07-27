package org.pfccap.education.utilities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import org.pfccap.education.R;
import org.pfccap.education.presentation.main.presenters.IQuestionPresenter;
import org.pfccap.education.presentation.main.presenters.QuestionPresenter;
import org.pfccap.education.presentation.main.ui.activities.QuestionsActivity;

/**
 * Created by jggomez on 05-Apr-17.
 */

public class Utilities {

    public static <T extends AppCompatActivity> void initActivity(T classInicio,
                                                                  Class classDestino) {
        Intent intent = new Intent(classInicio, classDestino);
        classInicio.startActivity(intent);
    }

    public static <T extends AppCompatActivity> void initActivityForResult
            (T classInicio, Class classDestino, int requestCode) {
        Intent intent = new Intent(classInicio, classDestino);
        classInicio.startActivityForResult(intent, requestCode);
    }

    public static <T extends FragmentActivity> void initActivityFromFragment(T classInicio,
                                                                             Class classDestino) {
        Intent intent = new Intent(classInicio, classDestino);
        classInicio.startActivity(intent);
    }


    public static <T extends FragmentActivity> void initFragmentFromFragment(T classInicio,
                                                                             Fragment fragmentDestino) {
        FragmentTransaction t = classInicio.getSupportFragmentManager().beginTransaction();
        t.replace(R.id.content, fragmentDestino);
        t.commit();
    }

    public static <T extends AppCompatActivity> void initFragment(T classInicio,
                                                                  Fragment fragmentDestino) {

        if (Build.VERSION.SDK_INT >= 21) {
            Fade fadeTransition = new Fade();
            fadeTransition.setDuration(1000);

            fragmentDestino.setEnterTransition(fadeTransition);

            fadeTransition = new Fade();
            fadeTransition.setDuration(1000);

            fragmentDestino.setExitTransition(fadeTransition);
            fragmentDestino.setAllowEnterTransitionOverlap(true);
            fragmentDestino.setAllowReturnTransitionOverlap(true);
        }

        FragmentTransaction t = classInicio.getSupportFragmentManager().beginTransaction();
        t.replace(R.id.content, fragmentDestino).addToBackStack(null);
        t.commit();
    }

    public static void dialogoInfoAndIntent(String titulo, String mensaje, final Context context,
                                            final Intent intent) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(context, R.style.DialogInfo);
        dialog.setTitle(titulo);
        dialog.setMessage(mensaje);
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                context.startActivity(intent);
            }
        });

        dialog.show();
    }

    public static void dialogoInfo(String titulo, String mensaje, Context context) {
        dialogo(titulo, mensaje, tipoDialogEnum.informacion, context);
    }

    public static void dialogoError(String titulo, String mensaje, Context context) {
        dialogo(titulo, mensaje, tipoDialogEnum.error, context);
    }

    private static void dialogo(String titulo, String mensaje,
                                tipoDialogEnum tipoDialog,
                                final Context context) {

        AlertDialog.Builder dialog = null;

        switch (tipoDialog) {
            case informacion:
                dialog = new AlertDialog.Builder(context, R.style.DialogInfo);
                break;
            case error:
                dialog = new AlertDialog.Builder(context, R.style.DialogError);
                break;
        }


        dialog.setTitle(titulo);
        dialog.setMessage(mensaje);
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });

        dialog.show();
    }

    public static void snackbarMessageError(View view, String error) {
        Snackbar snackbar = Snackbar.make(view, error, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(5);
        textView.setTextSize(20);
        ColoredSnackbar.alert(snackbar).show();
    }

    public static void snackbarMessageInfo(View view, String info) {
        Snackbar snackbar = Snackbar.make(view, info, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(5);
        textView.setTextSize(20);
        ColoredSnackbar.info(snackbar).show();
    }

    public static void snackbarNextAnswer(View view, String info, final QuestionsActivity context) {
        final Snackbar snackbar = Snackbar.make(view, info, Snackbar.LENGTH_INDEFINITE);
        View snackbarView = snackbar.getView();
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(5);
        textView.setTextSize(20);
        switch (Cache.getByKey(Constants.TYPE_CANCER)){
            case Constants.CERVIX:
                ColoredSnackbar.right(snackbar);
                break;
            case Constants.BREAST:
                ColoredSnackbar.info(snackbar);
                break;
        }
        snackbar.setAction("Siguiente", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IQuestionPresenter iQuestionPresenter = new QuestionPresenter(context, context);
                iQuestionPresenter.loadNextQuestion();
                snackbar.dismiss();
            }
        }).show();
    }

    private enum tipoDialogEnum {
        error,
        informacion
    }

    public static boolean isNetworkAvailable(Context context) {
        //este metodo verifica si esta habilitado el wifi y los datos y luego si hay acceso a internet
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting() && networkInfo.isAvailable();
    }

    public static String traslateErrorCode(String code, Context context) {
        String message = "";
        switch (code) {
            case "ERROR_USER_NOT_FOUND":
                message = context.getString(R.string.error_user_not_found);
                break;
            case "ERROR_INVALID_EMAIL":
                message = context.getString(R.string.error_invalid_email);
                break;
            case "ERROR_WRONG_PASSWORD":
                message = context.getString(R.string.error_wrong_password);
                break;
            case "ERROR_USER_DISABLED":
                message = context.getString(R.string.error_user_disabled);
                break;
            case "ERROR_EMAIL_ALREADY_IN_USE":
                message = context.getString(R.string.error_email_already_in_use);
                break;
            case "ERROR_OPERATION_NOT_ALLOWED":
                message = context.getString(R.string.error_operation_not_allowed);
                break;
            case "ERROR_WEAK_PASSWORD":
                message = context.getString(R.string.error_weak_password);
                break;
            case "WEAK_PASSWORD":
                message = context.getString(R.string.weak_password_second);
        }
        return message;
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html){
        Spanned result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }
}
