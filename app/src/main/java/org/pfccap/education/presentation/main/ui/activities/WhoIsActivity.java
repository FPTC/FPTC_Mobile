package org.pfccap.education.presentation.main.ui.activities;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.widget.ImageButton;
import android.widget.TextView;

import org.pfccap.education.R;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;
import org.pfccap.education.utilities.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.championswimmer.libsocialbuttons.FabSocial;

public class WhoIsActivity extends AppCompatActivity implements IWhoIsView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_is_who);

        ButterKnife.bind(this);

        uri = Uri.parse("http://pfccap.org/espanol/inicio.html");
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.who_is));
        }
    }

    @OnClick(R.id.btnInts)
    public void clicInts() {
        startActivity(newInstagramProfileIntent(getPackageManager(), "https://www.instagram.com/fptc_colombia/"));
    }

    @OnClick(R.id.btnFab)
    public void clicFab() {
        startActivity(newFacebookIntent(getPackageManager(), "https://www.facebook.com/FPTCColombia/"));
    }

    @OnClick(R.id.btnLinkWeb)
    public void clicLinkWeb() {
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @OnClick(R.id.btnLinkWeb2)
    public void clicLinkWeb2() {
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @OnClick(R.id.webAmate)
    public void clicLinkWebAmate() {
        uri = Uri.parse("http://amatecuidatusalud.pfccap.org/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }


    public static Intent newFacebookIntent(PackageManager pm, String url) {
        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    public static Intent newInstagramProfileIntent(PackageManager pm, String url) {
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        try {
            if (pm.getPackageInfo("com.instagram.android", 0) != null) {
                if (url.endsWith("/")) {
                    url = url.substring(0, url.length() - 1);
                }
                final String username = url.substring(url.lastIndexOf("/") + 1);
                intent.setData(Uri.parse("http://instagram.com/_u/" + username));
                intent.setPackage("com.instagram.android");
                return intent;
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        intent.setData(Uri.parse(url));
        return intent;
    }

}
