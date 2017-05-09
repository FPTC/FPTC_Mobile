package org.pfccap.education.presentation.main.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.pfccap.education.R;
import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by USUARIO on 08/05/2017.
 */

public class IntroFragment extends Fragment {

    @BindView(R.id.mainIntroTxt)
    TextView textIntro;

    @BindView(R.id.mainIntroBtnGo)
    Button btnGoAnswerQuestion;

    public IntroFragment() {
        // Required empty public constructor
    }

    public static IntroFragment newInstance() {
        IntroFragment fragment = new IntroFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_intro, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

}
