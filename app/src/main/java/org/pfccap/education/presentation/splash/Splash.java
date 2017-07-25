package org.pfccap.education.presentation.splash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.pfccap.education.R;
import org.pfccap.education.presentation.auth.ui.activities.AuthActivity;
import org.pfccap.education.utilities.APIService;
import org.pfccap.education.utilities.Utilities;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        APIService.getBASEURL();
        Utilities.initActivity(Splash.this, AuthActivity.class);
        finish();

    }
}
