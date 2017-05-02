package org.pfccap.education.presentation.splash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.pfccap.education.R;
import org.pfccap.education.presentation.auth.ui.activities.AuthActivity;
import org.pfccap.education.utilities.Utilities;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {

    private static final long SPLASH_SCREEN_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                Utilities.initActivity(Splash.this, AuthActivity.class);
                finish();
            }
        };

        // Simulate a long loading process on application startup.
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }
}
