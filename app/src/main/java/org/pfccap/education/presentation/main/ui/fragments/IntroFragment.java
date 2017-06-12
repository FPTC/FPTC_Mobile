package org.pfccap.education.presentation.main.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class IntroFragment extends Fragment implements IIntroView {

    private OnIntroFragInteractionListener mListener;

    @BindView(R.id.mainIntroTxt)
    TextView textIntro;

    @BindView(R.id.mainIntroBtnGo)
    Button btnGoAnswerQuestion;

    @BindView(R.id.mainIntroImage)
    ImageView introImage;

    @BindView(R.id.progressBarF)
    ProgressBar progressBar;

    private static final String TEXT = "text";
    private static final String IMAGE = "image";
    private static final String BACK_COLOR = "param3";
    private IIntroFragPresenter iIntroFragPresenter;

    private String text;
    private int image;
    private int backcolor;

    public IntroFragment() {
        // Required empty public constructor
    }

    public static IntroFragment newInstance(String param1, int param2, int param3) {
        IntroFragment fragment = new IntroFragment();
        Bundle args = new Bundle();
        args.putString(TEXT, param1);
        args.putInt(IMAGE, param2);
        args.putInt(BACK_COLOR, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            text = getArguments().getString(TEXT);
            image = getArguments().getInt(IMAGE);
            backcolor = getArguments().getInt(BACK_COLOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_intro, container, false);
        ButterKnife.bind(this, view);
        textIntro.setText(text);
        introImage.setImageResource(image);
        btnGoAnswerQuestion.setBackgroundColor(ContextCompat.getColor(getContext(), backcolor));
        iIntroFragPresenter = new IntroFragPresenter(this, getContext());
        return view;
    }

    @OnClick(R.id.mainIntroBtnGo)
    public void goAnswersQuestion(){
        switch (Cache.getByKey(Constants.TYPE_CANCER)){
            case Constants.CERVIX: //TODO validar si puede contestar por segunda o más veces
                if (Cache.getByKey(Constants.CERVIX_TURN).equals("2")){
                    showTurnError();
                }else{
                    load();
                }

                break;
            case Constants.BREAST:
                if (Cache.getByKey(Constants.BREAST_TURN).equals("2")){
                    showTurnError();
                }else {
                    load();
                }

                break;
        }
    }

    private void showTurnError(){
        Utilities.snackbarMessageError(getView(),  getContext().getResources().getString(R.string.end_turn));
    }

    private void load(){
        if (iIntroFragPresenter.IIntroFragPresenter()) { // se comprueba si existen preguntas de lo contrario se descargan
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
