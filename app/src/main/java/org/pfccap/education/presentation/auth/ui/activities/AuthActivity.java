package org.pfccap.education.presentation.auth.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.pfccap.education.R;
import org.pfccap.education.presentation.auth.ui.fragments.Login;
import org.pfccap.education.presentation.auth.ui.fragments.ResetPasswordFragment;
import org.pfccap.education.presentation.auth.ui.fragments.Signup;
import org.pfccap.education.utilities.Utilities;

public class AuthActivity extends AppCompatActivity implements Signup.OnSigUpFragmentInteractor,
        ResetPasswordFragment.OnResetPassFragInteractionListener, Login.OnLoginFragInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        initFragment();
    }

    private void initFragment() {
        Utilities.initFragment(this, new Signup());
    }

    @Override
    public void onNavigateToLoginScreen() {
        Utilities.initFragment(this, new Login());
    }

    @Override
    public void onNavigateToMainScreen() {
        // TODO : enviar a la actividad ppal
        //Utilities.initActivity(this, new Login());
    }

    @Override
    public void onNavigateToSignUp() {
        Utilities.initFragment(this, new Login());
    }
}
