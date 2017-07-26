package org.pfccap.education.presentation.splash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.pfccap.education.presentation.auth.ui.activities.AuthActivity;
import org.pfccap.education.utilities.Utilities;

public class Splash extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utilities.initActivity(Splash.this, AuthActivity.class);
        finish();

    }
}
