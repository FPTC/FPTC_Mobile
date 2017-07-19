package org.pfccap.education.utilities;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by jggomez on 27-Apr-17.
 */

public class ColoredSnackbar {

    private static final int COLOR_ERROR = 0xffa30039;
    private static final int COLOR_INFO = 0xffc82c63;
    private static final int COLOR_RIGHT = 0xff0da4a6;

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

    public static Snackbar right(Snackbar snackbar) {
        return colorSnackBar(snackbar, COLOR_RIGHT);
    }

    public static Snackbar alert(Snackbar snackbar) {
        return colorSnackBar(snackbar, COLOR_ERROR);
    }

    public static Snackbar confirm(Snackbar snackbar) {
        return colorSnackBar(snackbar, COLOR_INFO);
    }

}
