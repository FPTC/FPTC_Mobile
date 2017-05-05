package org.pfccap.education.utilities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.pfccap.education.R;

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
        FragmentTransaction t = classInicio.getSupportFragmentManager().beginTransaction();
        t.replace(R.id.content, fragmentDestino);
        t.commit();
    }

    public static void dialogoInfo(String titulo, String mensaje, Context context) {
        dialogo(titulo, mensaje, tipoDialogEnum.informacion, context);
    }

    public static void dialogoError(String titulo, String mensaje, Context context) {
        dialogo(titulo, mensaje, tipoDialogEnum.error, context);
    }

    private static void dialogo(String titulo, String mensaje, tipoDialogEnum tipoDialog, Context context) {

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
        ColoredSnackbar.alert(snackbar).show();
    }

    public static void snackbarMessageInfo(View view, String info) {
        Snackbar snackbar = Snackbar.make(view, info, Snackbar.LENGTH_LONG);
        ColoredSnackbar.info(snackbar).show();
    }

    private enum tipoDialogEnum {
        error,
        informacion
    }

    public static String traslateErrorCode(String code){
        String message = "";
        switch (code){
            case "ERROR_USER_NOT_FOUND":
                message = "No existe este usuario con este email, por favor verifique.";
                break;
            case "ERROR_INVALID_EMAIL":
                message = "El email registrado no es válido, por favor verifique.";
                break;
            case "ERROR_WRONG_PASSWORD":
                message = "La contraseña es incorrecta";
                break;
            case "ERROR_USER_DISABLED":
                message = "El usuario se encuentra deshabilitado";
                break;
            case "ERROR_EMAIL_ALREADY_IN_USE":
                message = "Este usuario ya existe";
                break;
            case "ERROR_OPERATION_NOT_ALLOWED":
                message = "El email y contraseña no están habilitado. Habilítelo en la consola de Firebase";
                break;
            case "ERROR_WEAK_PASSWORD":
                message = "La contraseña es muy débil, digite una combinación más compleja";
        }
        return message;
    }
}
