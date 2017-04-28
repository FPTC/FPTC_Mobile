package org.pfccap.education.presentation.main.ui.activities;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.pfccap.education.R;
import org.pfccap.education.presentation.auth.ui.fragments.Login;
import org.pfccap.education.presentation.main.ui.fragments.MainFragment;
import org.pfccap.education.presentation.main.ui.fragments.ProfileFragment;
import org.pfccap.education.utilities.Utilities;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.navigation_drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    ActionBar actionBar;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        starViews();
        initFragment();
    }

    private void starViews() {

        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
        }

        setupNavigationDrawerContent(navigationView);
    }

    private void initFragment() {
        Utilities.initFragment(this, new MainFragment());
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
                                menuItem.setChecked(true);
                                initFragment();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_drawer_profile:
                                menuItem.setChecked(true);
                                initFragmentProfile();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_drawer_gifts:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                        }
                        return true;
                    }
                });
    }

    private void initFragmentProfile() {
        Utilities.initFragment(this, new ProfileFragment());
    }
}
