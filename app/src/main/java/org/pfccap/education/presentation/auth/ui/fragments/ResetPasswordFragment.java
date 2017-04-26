package org.pfccap.education.presentation.auth.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.pfccap.education.R;
import org.pfccap.education.presentation.auth.presenters.IResetPasswordPresenter;
import org.pfccap.education.presentation.auth.presenters.ResetPasswordPresenter;
import org.pfccap.education.utilities.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ResetPasswordFragment.OnResetPassFragInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ResetPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResetPasswordFragment extends Fragment implements IResetPasswordView {

    private OnResetPassFragInteractionListener mListener;
    private IResetPasswordPresenter resetPasswordPresenter;

    @BindView(R.id.authResetTxtEmail)
    EditText authResetTxtEmail;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    public ResetPasswordFragment() {
        // Required empty public constructor
    }

    public static ResetPasswordFragment newInstance() {
        ResetPasswordFragment fragment = new ResetPasswordFragment();
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
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        ButterKnife.bind(this, view);

        resetPasswordPresenter = new ResetPasswordPresenter(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnResetPassFragInteractionListener) {
            mListener = (OnResetPassFragInteractionListener) context;
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

    @OnClick(R.id.authLinkSignIn)
    @Override
    public void navigateToLoginScreen() {
        if (mListener != null) {
            mListener.onNavigateToLoginScreen();
        }
    }

    @Override
    @OnClick(R.id.authBtnLogin)
    public void handleResetPassword() {
        resetPasswordPresenter.resetPassword(
                authResetTxtEmail.getText().toString());
    }

    @Override
    public void resetError(String error) {
        Utilities.dialogoError(getString(R.string.TITULO_ERROR), error, getActivity());
    }

    private void setInputs(boolean enabled) {
        authResetTxtEmail.setEnabled(enabled);
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
    public interface OnResetPassFragInteractionListener {

        void onNavigateToLoginScreen();

    }
}
