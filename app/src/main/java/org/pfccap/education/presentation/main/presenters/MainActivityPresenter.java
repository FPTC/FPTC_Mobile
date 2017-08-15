package org.pfccap.education.presentation.main.presenters;

import android.content.Intent;

import com.google.firebase.crash.FirebaseCrash;
import org.pfccap.education.domain.auth.AuthProcess;
import org.pfccap.education.domain.auth.IAuthProcess;
import org.pfccap.education.presentation.main.ui.activities.IMainActivityView;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;
import com.google.android.gms.appinvite.AppInviteInvitation;
import android.net.Uri;
/**
 * Created by jggomez on 03-May-17.
 */

public class MainActivityPresenter implements IMainActivityPresenter {

    private IMainActivityView mainActivityView;
    private IAuthProcess authProcess;

    public MainActivityPresenter(IMainActivityView mainActivityView) {
        this.mainActivityView = mainActivityView;
    }

    @Override
    public void setUserName() {
        try {

            mainActivityView.setUserName(Cache.getByKey(Constants.USER_NAME));

        } catch (Exception e) {
            FirebaseCrash.report(e);
            mainActivityView.showError(e.getMessage());
        }
    }

    @Override
    public void logOut() {
        Cache.clearAll();
        authProcess = new AuthProcess();
        authProcess.logOut();
        mainActivityView.navigateToLogin();
    }

    @Override
    public void invite() {
        Intent intent = new AppInviteInvitation.IntentBuilder(
                "¡Invita a tus amigas a probar tu conocimiento y aprender más sobre el cáncer de seno y el cáncer de cuello uterino!")
                .setEmailSubject("Ámate cuida tu salud")
                .setMessage("Hola, Te han invitado a probar tu conocimiento sobre el cáncer de seno y cáncer cuello uterino.")
                .setDeepLink(Uri.parse("http://40.71.81.33/fundacancerPruebas/public/#/login"))
                .setEmailHtmlContent("<html>\n" +
                        "<head>\n" +
                        "\t<title>Ámate cuida tu salud</title>\n" +
                        "\t<meta http-equiv=\"Content-Type\"content=\"text/html;charset=utf-8\" />\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<table>\n" +
                        "<tr><td><img \n" +
                        "src=\"https://firebasestorage.googleapis.com/v0/b/project-963864459523015283.appspot.com/o/imgs_correo%2Fekosavelogo.png?alt=media&token=dad709a7-f28f-477f-ad0b-242a4b18b076\" alt=\"Amate_logo\"border=\"0\"width=\"200\"height=\"48\"style=\"display:block;font-family:Arial;color:#000000;font-size:1em\"/></td>\n" +
                        "</tr>\n" +
                        "<tr><td><img \n" +
                        "src=\"https://firebasestorage.googleapis.com/v0/b/project-963864459523015283.appspot.com/o/imgs_correo%2Fbanner_ekosave.jpg?alt=media&token=0349e355-03e9-492c-90dd-e3f2a05484fa\" alt=\"Ámate cuida tu salud\"border=\"0\"width=\"600\"height=\"257\"style=\"display:block;font-family:Arial;color:#000000;font-size:1em\"/></td>\n" +
                        "</tr>\n" +
                        "<tr>\n" +
                        "<td align=\"right\"><a href=\"%%APPINVITE_LINK_PLACEHOLDER%%\" style=\"color:#ff00ff;\"><span style=\"color:#ff00ff;\"><img src=\"https://firebasestorage.googleapis.com/v0/b/project-963864459523015283.appspot.com/o/imgs_correo%2Fgoogleplay.jpg?alt=media&token=794697b4-96d0-4e82-9c76-f7c6f5bf3f43\" alt=\"link_google_play\"border=\"0\"width=\"188\"height=\"62\"style=\"display:block;font-family:Arial;color:#000000;font-size:1em\"/></span></a></td>\n" +
                        "</tr>\n" +
                        "</table>\n" +
                        "</body>\n" +
                        "</html>")
                .build();

        mainActivityView.goInviteActivity(intent);
    }
}
