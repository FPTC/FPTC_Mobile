package org.pfccap.education.utilities;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by jggomez on 27-Apr-17.
 */

public class ColoredSnackbar {

    private static final int COLOR_ERROR = 0xff5c007a;
    private static final int COLOR_INFO = 0xff5c007a;

    private static View getSnackBarLayout(Snackbar snackbar) {
        if (snackbar != null) {
            return snackbar.getView();
        }
        return null;
    }

    private static Snackbar colorSnackBar(Snackbar snackbar, int colorId) {
        View snackBarView = getSnackBarLayout(snackbar);
        if (snackBarView != null) {
            snackBarView.setBackgroundColor(colorId);
        }

        snackbar.setActionTextColor(Color.WHITE);

        return snackbar;
    }

    public static Snackbar info(Snackbar snackbar) {
        return colorSnackBar(snackbar, COLOR_INFO);
    }

    public static Snackbar alert(Snackbar snackbar) {
        return colorSnackBar(snackbar, COLOR_ERROR);
    }

    public static Snackbar confirm(Snackbar snackbar) {
        return colorSnackBar(snackbar, COLOR_INFO);
    }

}
