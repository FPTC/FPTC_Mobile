package org.pfccap.education.presentation.auth.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.pfccap.education.R;
import org.pfccap.education.presentation.auth.presenters.ISignupPresenter;
import org.pfccap.education.presentation.auth.presenters.SignupPresenter;
import org.pfccap.education.utilities.Utilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Signup.OnSigUpFragmentInteractor} interface
 * to handle interaction events.
 * Use the {@link Signup#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Signup extends Fragment implements ISignupView,
        Validator.ValidationListener {

    private OnSigUpFragmentInteractor mListener;
    private ISignupPresenter signupPresenter;
    private Validator validator;

    @NotEmpty(messageResId = R.string.field_required)
    @BindView(R.id.authSignupName)
    EditText authSignupName;

    @NotEmpty(messageResId = R.string.field_required)
    @Email(messageResId = R.string.email_validation_msg)
    @BindView(R.id.authSignupEmail)
    EditText authSignupEmail;

    @NotEmpty(messageResId = R.string.field_required)
    @BindView(R.id.authSignupPassword)
    EditText authSignupPassword;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.authLinkLogin)
    TextView authLinkLogin;

    @BindView(R.id.authBtnSignUp)
    Button authBtnSignUp;

    public Signup() {
        // Required empty public constructor
    }

    public static Signup newInstance() {
        Signup fragment = new Signup();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.bind(this, view);

        signupPresenter = new SignupPresenter(this);

        initText();

        validator = new Validator(this);
        validator.setValidationListener(this);

        return view;
    }

    private void initText() {
        SpannableString content = new SpannableString(getString(R.string.tienes_cuenta));
        content.setSpan(new UnderlineSpan(), 0, getString(R.string.tienes_cuenta).length(), 0);
        authLinkLogin.setText(content);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSigUpFragmentInteractor) {
            mListener = (OnSigUpFragmentInteractor) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    @OnClick(R.id.authBtnSignUp)
    public void handleSignUp() {
        validator.validate();
    }


    @OnClick(R.id.authLinkLogin)
    @Override
    public void navigateToLoginScreen() {
        if (mListener != null) {
            mListener.onNavigateToLoginScreen();
        }
    }

    @Override
    public void signUpError(String error) {
        Utilities.snackbarMessageError(getView(), error);
    }

    @Override
    public void signUpSuccessful() {
        Utilities.snackbarMessageInfo(getView(), getString(R.string.sigUpSuccessful));
    }

    private void setInputs(boolean enabled) {
        authSignupName.setEnabled(enabled);
        authSignupEmail.setEnabled(enabled);
        authSignupPassword.setEnabled(enabled);
        authBtnSignUp.setEnabled(enabled);
    }

    @Override
    public void onValidationSucceeded() {
        signupPresenter.signUp(authSignupName.getText().toString(),
                authSignupEmail.getText().toString(),
                authSignupPassword.getText().toString());
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());

            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Utilities.snackbarMessageError(getActivity().findViewById(android.R.id.content), message);
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnSigUpFragmentInteractor {

        void onNavigateToLoginScreen();

    }
}
