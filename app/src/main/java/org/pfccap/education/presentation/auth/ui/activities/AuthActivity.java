package org.pfccap.education.presentation.auth.ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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

        FragmentManager fm = getSupportFragmentManager();

        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if(getSupportFragmentManager().getBackStackEntryCount() == 0){
                    finish();
                }
            }
        });

        initFragment();
    }

    private void initFragment() {
        Utilities.initFragment(this, Login.newInstance());
    }

    @Override
    public void onNavigateToLoginScreen() {
        Utilities.initFragment(this, Login.newInstance());
    }

    @Override
    public void onNavigateToMainScreen() {
        // TODO : enviar a la actividad ppal
        //Utilities.initActivity(this, new Login());
    }

    @Override
    public void onNavigateToSignUp() {
        Utilities.initFragment(this, Signup.newInstance());
    }

    @Override
    public void onNavigateToResetPassword() {
        Utilities.initFragment(this, ResetPasswordFragment.newInstance());
    }
}
