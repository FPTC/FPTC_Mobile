package org.pfccap.education.presentation.auth.ui.activities;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import org.pfccap.education.R;
import org.pfccap.education.presentation.auth.ui.fragments.Login;
import org.pfccap.education.presentation.auth.ui.fragments.ResetPasswordFragment;
import org.pfccap.education.presentation.auth.ui.fragments.Signup;
import org.pfccap.education.presentation.auth.ui.fragments.TermsAndPolicyFragment;
import org.pfccap.education.presentation.main.ui.activities.MainActivity;
import org.pfccap.education.utilities.APIService;
import org.pfccap.education.utilities.Utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class AuthActivity extends AppCompatActivity implements Signup.OnSigUpFragmentInteractor,
        ResetPasswordFragment.OnResetPassFragInteractionListener, Login.OnLoginFragInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        APIService.getBASEURL();
        FragmentManager fm = getSupportFragmentManager();

        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    finish();
                }
            }
        });

       /* try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "org.pfccap.education",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }*/
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
    public void onTermsAndCondition() {
        Utilities.initFragment(this, TermsAndPolicyFragment.newInstance(getResources().getString(R.string.terms_and_conditions),
                getResources().getString(R.string.terms_conditions_content)));
    }

    @Override
    public void onPrivacyPolicy() {
        Utilities.initFragment(this, TermsAndPolicyFragment.newInstance(getResources().getString(R.string.privacy_policy),
                getResources().getString(R.string.privacy_policy_content)));
    }

    @Override
    public void onNavigateToMainScreen() {
        Utilities.initActivity(this, MainActivity.class);
        finish();
    }

    @Override
    public void onNavigateToSignUp() {
        Utilities.initFragment(this, Signup.newInstance());
    }

    @Override
    public void onNavigateToResetPassword() {
        Utilities.initFragment(this, ResetPasswordFragment.newInstance());
    }

    @Override
    public void onBackPressed() {
        getSupportFragmentManager().popBackStack();
    }

}
