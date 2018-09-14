package org.pfccap.education.presentation.main.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.crash.FirebaseCrash;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.DecimalMax;
import com.mobsandgeeks.saripaar.annotation.DecimalMin;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Max;
import com.mobsandgeeks.saripaar.annotation.Min;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Select;
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
import java.util.HashMap;
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

    @NotEmpty(messageResId = R.string.field_required)
    @BindView(R.id.mainProfileTxtName)
    EditText txtName;

    @NotEmpty(messageResId = R.string.field_required)
    @BindView(R.id.mainProfileTxtLastName)
    EditText txtLastName;

    @NotEmpty(messageResId = R.string.field_required)
    @BindView(R.id.mainProfileTxtEmail)
    EditText txtEmail;

    @NotEmpty(messageResId = R.string.dateBirthday)
    @BindView(R.id.mainProfileTxtBirth)
    EditText txtBirth;

    @BindView(R.id.mainProfileTxtAge)
    TextView txtAge;

    @NotEmpty(messageResId = R.string.phone_no_empty_msg)
    @Length(min = 7, max = 7, messageResId = R.string.limits_phone_number)
    @BindView(R.id.mainProfileTxtPhone)
    EditText txtPhone;

    @NotEmpty(messageResId = R.string.phone_no_empty_msg)
    @Length(min = 10, max = 10, messageResId = R.string.limits_phone_number_cel)
    @BindView(R.id.mainProfileTxtPhoneCel)
    EditText txtPhoneCel;

    @NotEmpty(messageResId = R.string.field_required)
    @BindView(R.id.mainProfileTxtAddress)
    EditText txtAddress;

    @BindView(R.id.mainProfileTxtLongitude)
    EditText txtLongitude;

    @BindView(R.id.mainProfileTxtLatitude)
    EditText txtLatitude;

    @NotEmpty(messageResId = R.string.neighborhood_no_empty_msg)
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

    /*@BindView(R.id.textEmailUser)
    TextView textEmailUser;*/

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.spCity)
    AppCompatSpinner spCity;

    @BindView(R.id.spCountry)
    AppCompatSpinner spCountry;

    @BindView(R.id.spComuna)
    AppCompatSpinner spComuna;

    @BindView(R.id.spEse)
    AppCompatSpinner spEse;

    @BindView(R.id.spIps)
    AppCompatSpinner spIps;

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
        if (!Utilities.isNetworkAvailable(ProfileActivity.this)) {
            loadInfoLocal();
        } else {
            profilePresenter.getUserData();
        }

        validator = new Validator(this);
        validator.setValidationListener(this);

        initCountry();
        initListeners();

    }

    private void initListeners() {
    }

    private void initCountry() {

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

        Cache.save(Constants.USER_NAME, txtName.getText().toString());
        Cache.save(Constants.LASTNAME, txtLastName.getText().toString());
        Cache.save(Constants.ADDRESS, txtAddress.getText().toString());
        Cache.save(Constants.DATEBIRDARY, txtBirth.getText().toString());
        Cache.save(Constants.HASCHILDS, txtChilds.getText().toString());
        Cache.save(Constants.HEIGHT, txtHeight.getText().toString());
        Cache.save(Constants.WEIGHT, txtWeight.getText().toString());
        Cache.save(Constants.LATITUDE, txtLatitude.getText().toString());
        Cache.save(Constants.LONGITUDE, txtLongitude.getText().toString());
        Cache.save(Constants.NEIGHVORHOOD, txtNeighborhood.getText().toString());
        Cache.save(Constants.PHONENUMBER, txtPhone.getText().toString());
        Cache.save(Constants.PHONENUMBERCEL, txtPhoneCel.getText().toString());
        Cache.save(Constants.PROFILE_COMPLETED, "1");
        //se agrega esta campo debido a que algunos usuarios de facebook ingresan con número de
        if(Cache.getByKey(Constants.EMAIL).equals("")) {
            Cache.save(Constants.EMAIL, txtEmail.getText().toString());
        }

        HashMap<String, Object> user = new HashMap<>();
        user.put(Constants.NAME, txtName.getText().toString());
        user.put(Constants.LASTNAME, txtLastName.getText().toString());
        user.put(Constants.EMAIL, txtEmail.getText().toString());
        user.put(Constants.ADDRESS, txtAddress.getText().toString());
        user.put(Constants.DATEBIRDARY, txtBirth.getText().toString());
        user.put(Constants.HASCHILDS, Integer.parseInt(txtChilds.getText().toString()));
        user.put(Constants.NEIGHVORHOOD, txtNeighborhood.getText().toString());
        user.put(Constants.PHONENUMBER, txtPhone.getText().toString());
        user.put(Constants.PHONENUMBERCEL, txtPhoneCel.getText().toString());
        user.put(Constants.PROFILE_COMPLETED, 1);

        if(txtHeight.getText().toString().equals("")){
            user.put(Constants.HEIGHT, 0);
        }else{
            user.put(Constants.HEIGHT, Double.parseDouble(txtHeight.getText().toString()));
        }
        if (txtWeight.getText().toString().equals("")){
            user.put(Constants.WEIGHT, 0);
        }else {
            user.put(Constants.WEIGHT, Double.parseDouble(txtWeight.getText().toString()));
        }
        if (txtLatitude.getText().toString().equals("")) {
            user.put(Constants.LATITUDE, 0);
        } else {
            user.put(Constants.LATITUDE, Double.parseDouble(txtLatitude.getText().toString()));
        }
        if (txtLongitude.getText().toString().equals("")) {
            user.put(Constants.LONGITUDE, 0);
        } else {
            user.put(Constants.LONGITUDE, Double.parseDouble(txtLongitude.getText().toString()));
        }

        Cache.save(Constants.PROFILE_COMPLETED, "1");

        profilePresenter.updateUserData(user);
    }

    @Override
    @OnClick(R.id.mainProfileTxtBirth)
    public void clickTxtBirth() {
        datePickerDialog.show(this.getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void setEmailUser(String email) {
      //  textEmailUser.setText(email);
        txtEmail.setText(email);
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

    private void loadInfoLocal() {

        txtName.setText(Cache.getByKey(Constants.USER_NAME));
        txtLastName.setText(Cache.getByKey(Constants.LASTNAME));
        txtEmail.setText(Cache.getByKey(Constants.EMAIL));
        txtAddress.setText(Cache.getByKey(Constants.ADDRESS));
        txtBirth.setText(Cache.getByKey(Constants.DATEBIRDARY));
        txtChilds.setText(Cache.getByKey(Constants.HASCHILDS));
        txtHeight.setText(Cache.getByKey(Constants.HEIGHT));
        txtWeight.setText(Cache.getByKey(Constants.WEIGHT));
        txtLatitude.setText(Cache.getByKey(Constants.LATITUDE));
        txtLongitude.setText(Cache.getByKey(Constants.LONGITUDE));
        txtNeighborhood.setText(Cache.getByKey(Constants.NEIGHVORHOOD));
        txtPhone.setText(Cache.getByKey(Constants.PHONENUMBER));
        txtPhoneCel.setText(Cache.getByKey(Constants.PHONENUMBERCEL));

        if (!Cache.getByKey(Constants.DATEBIRDARY).equals("")) {

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

            try {
                cal.setTime(sdf.parse(Cache.getByKey(Constants.DATEBIRDARY)));
                profilePresenter.calculateAge(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar
                        .DAY_OF_MONTH));
            } catch (ParseException e) {
                Utilities.dialogoError(getString(R.string.title_error_dialog), e.getMessage(), this);
            }
        }
    }

    @Override
    public void loadData(UserAuth user) {

        //se guarda localmente los datos para garantizar que este actualizado la información
        Cache.save(Constants.USER_NAME, user.getName());
        Cache.save(Constants.LASTNAME, user.getLastName());
        Cache.save(Constants.ADDRESS, user.getAddress());
        Cache.save(Constants.DATEBIRDARY, user.getDateBirthday());
        Cache.save(Constants.HASCHILDS, String.valueOf(user.getHasChilds()));
        Cache.save(Constants.HEIGHT, String.valueOf(user.getHeight()));
        Cache.save(Constants.WEIGHT, String.valueOf(user.getWeight()));
        Cache.save(Constants.LATITUDE, String.valueOf(user.getLatitude()));
        Cache.save(Constants.LONGITUDE, String.valueOf(user.getLongitude()));
        Cache.save(Constants.NEIGHVORHOOD, user.getNeighborhood());
        Cache.save(Constants.PHONENUMBER, user.getPhoneNumber());
        Cache.save(Constants.EMAIL, user.getEmail());
        Cache.save(Constants.PHONENUMBERCEL, user.getPhoneNumberCel());

        txtName.setText(user.getName());
        txtLastName.setText(user.getLastName());
        txtAddress.setText(user.getAddress());
        txtBirth.setText(user.getDateBirthday());
        txtLatitude.setText(String.valueOf(user.getLatitude()));
        txtLongitude.setText(String.valueOf(user.getLongitude()));
        txtNeighborhood.setText(user.getNeighborhood());
        txtPhone.setText(user.getPhoneNumber());
        txtPhoneCel.setText(user.getPhoneNumberCel());
        txtEmail.setText(user.getEmail());

        if (user.getHeight() == 0) {
            txtHeight.setText("");
        } else {
            txtHeight.setText(String.valueOf(user.getHeight()));
        }
        if (user.getWeight() == 0) {
            txtWeight.setText("");
        } else {
            txtWeight.setText(String.valueOf(user.getWeight()));
        }
        if (Cache.getByKey(Constants.PROFILE_COMPLETED).equals("0")) {
            txtChilds.setText("");
        } else {
            txtChilds.setText(String.valueOf(user.getHasChilds()));
        }


        if (user.getDateBirthday() != null && !user.getDateBirthday().equals("")) {

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

            try {
                cal.setTime(sdf.parse(user.getDateBirthday()));
                profilePresenter.calculateAge(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar
                        .DAY_OF_MONTH));
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
        profilePresenter.calculateAge(year, monthOfYear + 1, dayOfMonth);
    }

    public void setAge(int age) {
        txtAge.setText(getString(R.string.years, String.valueOf(age)));
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
        txtChilds.setEnabled(enabled);
        txtHeight.setEnabled(enabled);
        txtName.setEnabled(enabled);
        txtNeighborhood.setEnabled(enabled);
        txtPhone.setEnabled(enabled);
        txtPhoneCel.setEnabled(enabled);
        txtWeight.setEnabled(enabled);
        txtEmail.setEnabled(enabled);

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

            ((EditText) view).setError(message);

            Utilities.snackbarMessageError(findViewById(android.R.id.content), message);
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
