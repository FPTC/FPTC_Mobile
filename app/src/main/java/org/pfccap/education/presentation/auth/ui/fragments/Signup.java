package org.pfccap.education.presentation.auth.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import org.pfccap.education.R;
import org.pfccap.education.presentation.auth.presenters.ISignupPresenter;
import org.pfccap.education.presentation.auth.presenters.SignupPresenter;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;
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

    @Password(messageResId = R.string.valite_password)
    @BindView(R.id.authSignupPassword)
    EditText authSignupPassword;

    @Checked(messageResId = R.string.privacy_check_validation_message)
    @BindView(R.id.link_check_privacy)
    CheckBox link_check_privacy;

    @Checked(messageResId = R.string.terms_check_validation_message)
    @BindView(R.id.link_check_terms)
    CheckBox link_check_terms;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.authLinkLogin)
    TextView authLinkLogin;

    @BindView(R.id.authBtnSignUp)
    Button authBtnSignUp;

    @BindView(R.id.link_termsCondition)
    TextView link_termsCondition;

    @BindView(R.id.link_privacy_policy)
    TextView link_privacy_policy;

    @BindView(R.id.link_create_gmail)
    TextView link_create_gmail;

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

        signupPresenter = new SignupPresenter(this, getContext());

        initText();
        initTextLink();

        validator = new Validator(this);
        validator.setValidationListener(this);

        return view;
    }

    private void initTextLink() {
       String linkUrl;
    /*  link_termsCondition.setClickable(true);
        link_termsCondition.setMovementMethod(LinkMovementMethod.getInstance());
        linkUrl = Cache.getByKey(Constants.BASE_URL_TERMS_CONDITIONS_KEY);
        link_termsCondition.setText(Utilities.fromHtml("<a href='"+linkUrl+"'>"+getString(R.string.terms_and_conditions)+"</a>"));

        link_privacy_policy.setClickable(true);
        link_privacy_policy.setMovementMethod(LinkMovementMethod.getInstance());
        linkUrl = Cache.getByKey(Constants.BASE_URL_PRIVACY_POLICY_KEY);
        link_privacy_policy.setText(Utilities.fromHtml("<a href='"+linkUrl+"'>"+getString(R.string.privacy_policy)+"</a>"));
*/
        link_create_gmail.setClickable(true);
        link_create_gmail.setMovementMethod(LinkMovementMethod.getInstance());
        linkUrl = Cache.getByKey(Constants.BASE_URL_CREATE_EMAIL_APP);
        link_create_gmail.setText(Utilities.fromHtml("<a href='"+ linkUrl +"'>"+getString(R.string.create_gmail_acount)+"</a>"));

    }

    private void initText() {
        SpannableString content = new SpannableString(getString(R.string.tienes_cuenta));
        content.setSpan(new UnderlineSpan(), 0, getString(R.string.tienes_cuenta).length(), 0);
        authLinkLogin.setText(content);

        SpannableString terms = new SpannableString(getString(R.string.terms_and_conditions));
        terms.setSpan(new UnderlineSpan(), 0, getString(R.string.terms_and_conditions).length(), 0);
        link_termsCondition.setText(terms);

        SpannableString privacy = new SpannableString(getString(R.string.privacy_policy));
        privacy.setSpan(new UnderlineSpan(), 0, getString(R.string.privacy_policy).length(), 0);
        link_privacy_policy.setText(privacy);
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
        //se esconde el teclado despues de presionar el botón para poder ver los errores del snakbar
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(authSignupEmail.getWindowToken(), 0);
        validator.validate();
    }


    @OnClick(R.id.authLinkLogin)
    @Override
    public void navigateToLoginScreen() {
        if (mListener != null) {
            mListener.onNavigateToLoginScreen();
        }
    }

    @OnClick(R.id.link_termsCondition)
    public void link_termsCondition() {
        if (mListener != null) {
            mListener.onTermsAndCondition();
        }
    }

    @OnClick(R.id.link_privacy_policy)
    public void link_privacy_policy() {
        if (mListener != null) {
            mListener.onPrivacyPolicy();
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
        void onTermsAndCondition();
        void onPrivacyPolicy();

    }
}
