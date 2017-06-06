package org.pfccap.education.presentation.main.ui.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.pfccap.education.R;
import org.pfccap.education.presentation.main.presenters.IIntroFragPresenter;
import org.pfccap.education.presentation.main.presenters.IntroFragPresenter;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;
import org.pfccap.education.utilities.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by USUARIO on 08/05/2017.
 */

public class IntroFragment extends Fragment implements IIntroView{

    private OnIntroFragInteractionListener mListener;

    @BindView(R.id.mainIntroTxt)
    TextView textIntro;

    @BindView(R.id.mainIntroBtnGo)
    Button btnGoAnswerQuestion;

    @BindView(R.id.mainIntroImage)
    ImageView introImage;

    @BindView(R.id.progressBarF)
    ProgressBar progressBar;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private IIntroFragPresenter iIntroFragPresenter;

    private String mParam1;
    private int mParam2;
    private int mParam3;

    public IntroFragment() {
        // Required empty public constructor
    }

    public static IntroFragment newInstance(String param1, int param2, int param3) {
        IntroFragment fragment = new IntroFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        args.putInt(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
            mParam3 = getArguments().getInt(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_intro, container, false);
        ButterKnife.bind(this, view);
        textIntro.setText(mParam1);
        introImage.setImageResource(mParam2);
        btnGoAnswerQuestion.setBackgroundColor(ContextCompat.getColor(getContext(), mParam3));
        iIntroFragPresenter = new IntroFragPresenter(this, getContext());
        return view;
    }

    @OnClick(R.id.mainIntroBtnGo)
    public void goAnswersQuestion(){
        if (Integer.valueOf(Cache.getByKey(Constants.CERVIX_TURN))==2){
            Utilities.snackbarMessageError(getView(),  getContext().getResources().getString(R.string.end_turn));
        }else if (iIntroFragPresenter.IIntroFragPresenter()) { // se comprueba si existen preguntas de lo contrario se descargan
            if (mListener != null) {
                mListener.onNavigationQuestion();
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnIntroFragInteractionListener) {
            mListener = (OnIntroFragInteractionListener) context;
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
    public void errorDBQuestion(String error) {
        Utilities.snackbarMessageError(getView(), error);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    public interface OnIntroFragInteractionListener {
        void onNavigationQuestion();
    }
}
