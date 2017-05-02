package org.pfccap.education.presentation.main.ui.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.pfccap.education.R;
import org.pfccap.education.presentation.main.presenters.IProfilePresenter;
import org.pfccap.education.presentation.main.presenters.ProfilePresenter;
import org.pfccap.education.presentation.main.ui.fragments.IProfileView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity
        implements IProfileView, DatePickerDialog.OnDateSetListener {

    private IProfilePresenter profilePresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.mainProfileTxtAddress)
    EditText address;

    @BindView(R.id.mainProfileTxtBirth)
    EditText txtBirth;

    @BindView(R.id.mainProfileTxtAge)
    EditText txtAge;

    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);

        profilePresenter = new ProfilePresenter(this);

        initCalendar();

        initToolbar();

    }

    private void initToolbar() {
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.title_profile));
    }

    private void initCalendar() {
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    @OnClick(R.id.mainProfileTxtBirth)
    public void clickTxtBirth() {
        datePickerDialog.show(this.getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        txtBirth.setText(date);
        profilePresenter.calculateAge(year);
    }

    public void setAge(int age) {
        txtAge.setText(String.format("%s años", String.valueOf(age)));
    }

}
