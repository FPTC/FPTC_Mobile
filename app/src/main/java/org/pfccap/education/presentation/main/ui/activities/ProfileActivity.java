package org.pfccap.education.presentation.main.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.pfccap.education.R;
import org.pfccap.education.entities.UserAuth;
import org.pfccap.education.presentation.main.presenters.IProfilePresenter;
import org.pfccap.education.presentation.main.presenters.ProfilePresenter;
import org.pfccap.education.utilities.Utilities;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity
        implements IProfileView, DatePickerDialog.OnDateSetListener {

    private IProfilePresenter profilePresenter;
    public final static int REQUEST_CODE_MAPS = 9999;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.mainProfileTxtName)
    EditText txtName;

    @BindView(R.id.mainProfileTxtBirth)
    EditText txtBirth;

    @BindView(R.id.mainProfileTxtAge)
    EditText txtAge;

    @BindView(R.id.mainProfileTxtPhone)
    EditText txtPhone;

    @BindView(R.id.mainProfileTxtAddress)
    EditText txtAddress;

    @BindView(R.id.mainProfileTxtNeighborhood)
    EditText txtNeighborhood;

    @BindView(R.id.mainProfileTxtHeight)
    EditText txtHeight;

    @BindView(R.id.mainProfileTxtWeight)
    EditText txtWeight;

    @BindView(R.id.mainProfileTxtChilds)
    EditText txtChilds;

    @BindView(R.id.textEmailUser)
    TextView textEmailUser;

    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);

        profilePresenter = new ProfilePresenter(this);

        initCalendar();

        initToolbar();

        profilePresenter.getEmailUser();

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
    public void setEmailUser(String email) {
        textEmailUser.setText(email);
    }

    @Override
    public void enableInputs() {

    }

    @Override
    public void disableInputs() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void loadData(UserAuth user) {

    }

    @OnClick(R.id.btnmaps)
    public void clickMaps() {
        Utilities.initActivityForResult(this, MapsActivity.class, REQUEST_CODE_MAPS);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_MAPS && resultCode == RESULT_OK) {
            String address = data.getStringExtra("address");
            txtAddress.setText(address);
        }
    }

    private void setInputs(boolean enabled) {
        txtBirth.setEnabled(enabled);
        txtAddress.setEnabled(enabled);
        txtAge.setEnabled(enabled);
        txtChilds.setEnabled(enabled);
        txtHeight.setEnabled(enabled);
        txtName.setEnabled(enabled);
        txtNeighborhood.setEnabled(enabled);
        txtPhone.setEnabled(enabled);
        txtWeight.setEnabled(enabled);

    }

}
