package org.pfccap.education.presentation.auth.ui.fragments;

import android.content.Context;
import android.content.Intent;
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

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.pfccap.education.R;
import org.pfccap.education.presentation.auth.presenters.ILoginPresenter;
import org.pfccap.education.presentation.auth.presenters.LoginPresenter;
import org.pfccap.education.utilities.Utilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Login.OnLoginFragInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login extends Fragment implements ILoginView,
        Validator.ValidationListener {


    private OnLoginFragInteractionListener mListener;
    private ILoginPresenter loginPresenter;
    private CallbackManager callbackManager;
    private Validator validator;

    @NotEmpty(messageResId = R.string.field_required)
    @Email(messageResId = R.string.email_validation_msg)
    @BindView(R.id.authLoginTxtEmail)
    TextView authLoginTxtEmail;

    @NotEmpty(messageResId = R.string.field_required)
    @BindView(R.id.authLoginTxtPassword)
    TextView authLoginTxtPassword;

    @BindView(R.id.authBtnLogin)
    Button authBtnLogin;

    @BindView(R.id.authBtnLoginFacebook)
    LoginButton authBtnLoginFacebook;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.authLinkSignup)
    TextView authLinkSignup;

    @BindView(R.id.authLinkResetPassword)
    TextView authLinkResetPassword;

    public Login() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Login.
     */
    public static Login newInstance() {
        Login fragment = new Login();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);

        loginPresenter = new LoginPresenter(this, getContext());

        loginPresenter.isLogging();

        initLoginFacebook();

        initText();

        validator = new Validator(this);
        validator.setValidationListener(this);

        return view;
    }

    private void initText() {
        SpannableString content = new SpannableString(getString(R.string.sign_up));
        content.setSpan(new UnderlineSpan(), 0, getString(R.string.sign_up).length(), 0);
        authLinkSignup.setText(content);

        content = new SpannableString(getString(R.string.olvidaste_contrasena));
        content.setSpan(new UnderlineSpan(), 0, getString(R.string.olvidaste_contrasena).length(), 0);
        authLinkResetPassword.setText(content);
    }

    private void initLoginFacebook() {
        authBtnLoginFacebook.setReadPermissions("email");
        authBtnLoginFacebook.setFragment(this);
        callbackManager = loginPresenter.registerCallbackFacebook(authBtnLoginFacebook);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLoginFragInteractionListener) {
            mListener = (OnLoginFragInteractionListener) context;
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
    @OnClick(R.id.authLinkSignup)
    public void navigateToSignUp() {
        if (mListener != null) {
            mListener.onNavigateToSignUp();
        }
    }

    @Override
    @OnClick(R.id.authLinkResetPassword)
    public void navigateToResetPassword() {
        if (mListener != null) {
            mListener.onNavigateToResetPassword();
        }
    }

    @Override
    @OnClick(R.id.authBtnLogin)
    public void handleSignIn() {
        validator.validate();
    }


    @Override
    public void navigateToMainScreen() {
        if (mListener != null) {
            mListener.onNavigateToMainScreen();
        }
    }

    @Override
    public void loginError(String error) {
        Utilities.snackbarMessageError(getView(), error);
    }

    private void setInputs(boolean enabled) {
        authBtnLogin.setEnabled(enabled);
        authLoginTxtEmail.setEnabled(enabled);
        authLoginTxtPassword.setEnabled(enabled);
    }

    @Override
    public void onValidationSucceeded() {
        loginPresenter.login(
                authLoginTxtEmail.getText().toString(),
                authLoginTxtPassword.getText().toString());
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
    public interface OnLoginFragInteractionListener {
        void onNavigateToMainScreen();

        void onNavigateToSignUp();

        void onNavigateToResetPassword();
    }
}
