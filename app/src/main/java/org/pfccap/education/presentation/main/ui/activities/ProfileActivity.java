package org.pfccap.education.presentation.main.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.crash.FirebaseCrash;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.DecimalMax;
import com.mobsandgeeks.saripaar.annotation.DecimalMin;
import com.mobsandgeeks.saripaar.annotation.Max;
import com.mobsandgeeks.saripaar.annotation.Min;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.pfccap.education.R;
import org.pfccap.education.entities.UserAuth;
import org.pfccap.education.presentation.main.presenters.IProfilePresenter;
import org.pfccap.education.presentation.main.presenters.ProfilePresenter;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;
import org.pfccap.education.utilities.Utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity
        implements IProfileView, DatePickerDialog.OnDateSetListener, Validator.ValidationListener {

    private IProfilePresenter profilePresenter;
    private Validator validator;
    public final static int REQUEST_CODE_MAPS = 9999;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @NotEmpty
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

    @BindView(R.id.mainProfileTxtLongitude)
    EditText txtLongitude;

    @BindView(R.id.mainProfileTxtLatitude)
    EditText txtLatitude;

    @BindView(R.id.mainProfileTxtNeighborhood)
    EditText txtNeighborhood;

    @DecimalMin(value = 1.30, messageResId = R.string.height_min_msg)
    @DecimalMax(value = 2.20, messageResId = R.string.height_max_msg)
    @BindView(R.id.mainProfileTxtHeight)
    EditText txtHeight;

    @DecimalMin(value = 30, messageResId = R.string.weight_min_msg)
    @DecimalMax(value = 200, messageResId = R.string.weight_max_msg)
    @BindView(R.id.mainProfileTxtWeight)
    EditText txtWeight;

    @Min(value = 0)
    @Max(value = 20, messageResId = R.string.child_max_msg)
    @BindView(R.id.mainProfileTxtChilds)
    EditText txtChilds;

    @BindView(R.id.textEmailUser)
    TextView textEmailUser;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);

        profilePresenter = new ProfilePresenter(this, this);

        initCalendar();

        initToolbar();

        profilePresenter.getEmailUser();
        profilePresenter.getUserData();

        validator = new Validator(this);
        validator.setValidationListener(this);

    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.title_profile));
        }
    }

    private void initCalendar() {
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        Calendar minCal = Calendar.getInstance();
        minCal.set(Calendar.YEAR, minCal.get(Calendar.YEAR) - 90);
        Calendar maxCal = Calendar.getInstance();
        maxCal.set(Calendar.YEAR, maxCal.get(Calendar.YEAR) - 14);
        datePickerDialog.setMaxDate(maxCal);
        datePickerDialog.setMinDate(minCal);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                validator.validate();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveUserData() {
        UserAuth user = new UserAuth();

        user.setFirstLastName(txtName.getText().toString());
        user.setAddress(txtAddress.getText().toString());
        user.setDateBirthday(txtBirth.getText().toString());
        user.setHasChilds(Integer.parseInt(txtChilds.getText().toString()));
        user.setHeight(Double.parseDouble(txtHeight.getText().toString()));
        user.setWeight(Double.parseDouble(txtWeight.getText().toString()));
        user.setLatitude(Double.parseDouble(txtLatitude.getText().toString()));
        user.setLongitude(Double.parseDouble(txtLongitude.getText().toString()));
        user.setNeighborhood(txtNeighborhood.getText().toString());
        user.setPhoneNumber(txtPhone.getText().toString());
        user.setProfileCompleted(1);
        Cache.save(Constants.PROFILE_COMPLETED, "1");

        profilePresenter.saveUserData(user);
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
        setInputs(true);
    }

    @Override
    public void disableInputs() {
        setInputs(false);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String error) {
        Utilities.snackbarMessageError(findViewById(android.R.id.content), error);
    }

    @Override
    public void loadData(UserAuth user) {
        txtBirth.setText(user.getDateBirthday());
        txtAddress.setText(user.getAddress());
        txtLongitude.setText(String.valueOf(user.getLongitude()));
        txtLatitude.setText(String.valueOf(user.getLatitude()));
        txtChilds.setText(String.valueOf(user.getHasChilds()));
        txtHeight.setText(String.valueOf(user.getHeight()));
        txtName.setText(user.getFirstLastName());
        txtNeighborhood.setText(user.getNeighborhood());
        txtPhone.setText(user.getPhoneNumber());
        txtWeight.setText(String.valueOf(user.getWeight()));

        if (user.getDateBirthday() != null && !user.getDateBirthday().equals("")) {

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

            try {
                cal.setTime(sdf.parse(user.getDateBirthday()));
                profilePresenter.calculateAge(cal.get(Calendar.YEAR));
            } catch (ParseException e) {
                Utilities.dialogoError(getString(R.string.title_error_dialog), e.getMessage(), this);
            }

        }
    }

    @Override
    public void finishActivity() {
        finish();
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
        try {
            if (requestCode == REQUEST_CODE_MAPS) {
                try {
                    String address = data.getStringExtra("address");
                    String latitude = data.getStringExtra("latitude");
                    String longitude = data.getStringExtra("longitude");
                    txtAddress.setText(address);
                    txtLatitude.setText(latitude);
                    txtLongitude.setText(longitude);
                } catch (Exception e) {
                    FirebaseCrash.report(e);
                }

            }
        } catch (Exception e) {
            FirebaseCrash.report(e);
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

    @Override
    public void onValidationSucceeded() {
        saveUserData();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Utilities.snackbarMessageError(findViewById(android.R.id.content), message);
            }
        }
    }
}
