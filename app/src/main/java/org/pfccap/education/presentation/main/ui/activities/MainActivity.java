package org.pfccap.education.presentation.main.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.firebase.crash.FirebaseCrash;

import org.pfccap.education.R;
import org.pfccap.education.presentation.auth.ui.activities.AuthActivity;
import org.pfccap.education.presentation.main.presenters.IMainActivityPresenter;
import org.pfccap.education.presentation.main.presenters.MainActivityPresenter;
import org.pfccap.education.presentation.main.ui.fragments.GiftsFragment;
import org.pfccap.education.presentation.main.ui.fragments.IntroFragment;
import org.pfccap.education.presentation.main.ui.fragments.MainFragment;
import org.pfccap.education.presentation.main.ui.fragments.MessageGetGift;
import org.pfccap.education.presentation.main.ui.fragments.WarningFragment;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;
import org.pfccap.education.utilities.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IMainActivityView,
        MainFragment.OnMainFragInteractionListener, IntroFragment.OnIntroFragInteractionListener,
        GiftsFragment.OnFragmentInteractionListener, MessageGetGift.OnMessFragInteractionListener {

    private static final int REQUEST_INVITE = 1;
    private IMainActivityPresenter mainActivityPresenter;

    @BindView(R.id.navigation_drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.navigation_view)
    NavigationView navigationView;

    private TextView txtNavigationHeaderUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        starViews();
        initFragment();

        mainActivityPresenter = new MainActivityPresenter(this, this);
        mainActivityPresenter.setUserName();
    }

    private void starViews() {

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_36dp);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
            View headerLayout = navigationView.getHeaderView(0);
            txtNavigationHeaderUserName =
                    (TextView) headerLayout.findViewById(R.id.txtNavigationHeaderUserName);

        }

    }

    private void initFragment() {
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.content, MainFragment.newInstance()).addToBackStack(null);
        t.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.logout:
                mainActivityPresenter.logOut();
                return true;
            case R.id.whoIs:
                Utilities.initActivity(this, WhoIsActivity.class);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupNavigationDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.item_drawer_home:
                                FragmentManager fm = getSupportFragmentManager();
                                for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                                    fm.popBackStack();
                                }
                                initFragment();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_drawer_profile:
                                Utilities.initActivity(MainActivity.this, ProfileActivity.class);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_drawer_gifts:
                                if (Cache.getByKey(Constants.BREAST_TURN).equals("0") || Cache.getByKey(Constants.BREAST_TURN).equals("")) {
                                    showError(getString(R.string.have_opportunities));
                                } else if (Cache.getByKey(Constants.BREAST_TURN).equals("0") || Cache.getByKey(Constants.BREAST_TURN).equals("")) {
                                    showError(getString(R.string.have_opportunities));
                                } else if (Integer.valueOf(Cache.getByKey(Constants.BREAST_TURN)) < Integer.valueOf(Cache.getByKey(Constants.NUM_OPPORTUNITIES))) {
                                    showError(getString(R.string.have_opportunities_breast));
                                } else if (Integer.valueOf(Cache.getByKey(Constants.CERVIX_TURN)) < Integer.valueOf(Cache.getByKey(Constants.NUM_OPPORTUNITIES))) {
                                    showError(getString(R.string.have_opportunities_cervix));
                                } else {
                                    Utilities.initFragment(MainActivity.this, GiftsFragment.newInstance());
                                }
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_who_is:
                                Utilities.initActivity(MainActivity.this, WhoIsActivity.class);
                                return true;
                           /* case R.id.item_drawer_invite:
                                menuItem.setChecked(false);
                                mainActivityPresenter.invite();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;*/
                        }
                        return true;
                    }
                });
    }

    @Override
    public void onBackPressed() {
        //Al llegar al ultimo fragmento cierra la actividad al precinoar el back para evitar pantallas en blanco
        if (getSupportFragmentManager().getBackStackEntryCount() == 1){
            finish();
        }else{
            getSupportFragmentManager().popBackStack("MAIN", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    @Override
    public void setUserName(String userName) {
        txtNavigationHeaderUserName.setText(userName);
    }

    @Override
    public void showError(String error) {
        Utilities.snackbarMessageError(findViewById(android.R.id.content), error);
    }

    @Override
    public void navigateToLogin() {
        Utilities.initActivity(this, AuthActivity.class);
        finish();
    }

    @Override
    public void goInviteActivity(Intent intent) {
        startActivityForResult(intent, REQUEST_INVITE);
    }

    @Override
    public void onNavigateToBreast() {
        Utilities.initFragment(this, IntroFragment.newInstance(getString(R.string.intro_breast),
                R.drawable.cancer_seno, R.color.colorPrimary));
    }

    @Override
    public void onNavigateToCervical() {
        Utilities.initFragment(this, IntroFragment.newInstance(getString(R.string.intro_cervical),
                R.drawable.cancer_cervix, R.color.colorBlue));
    }

    @Override
    public void onNavigateToGifts() {
        Utilities.initFragment(this, GiftsFragment.newInstance());
    }

    @Override
    public void onNavigationQuestion() {
        Utilities.initActivity(this, QuestionsActivity.class);
    }

    @Override
    public void onNavigationMessageGift() {
        Utilities.initFragment(this, MessageGetGift.newInstance());
    }

    @Override
    public void onNavigateToWarnings() {
        Utilities.initFragment(this, WarningFragment.newInstance());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FirebaseCrash.log("onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

        if (requestCode == REQUEST_INVITE) {
            if (resultCode == RESULT_OK) {
                // Get the invitation IDs of all sent messages
                String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                for (String id : ids) {
                    FirebaseCrash.log("onActivityResult: sent invitation " + id);
                    FirebaseCrash.log("Result ok invitation data: "+data.getDataString());
                }
            } else {
                // Sending failed or it was canceled, show failure message to the user
                // ...
                FirebaseCrash.log("Data error invitation: "+ data.getDataString());
            }
        }
    }
}
