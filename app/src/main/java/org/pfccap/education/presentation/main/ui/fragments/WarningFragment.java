package org.pfccap.education.presentation.main.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.pfccap.education.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WarningFragment extends Fragment {

    @BindView(R.id.gift_txt_warning_link)
    TextView link;

    public WarningFragment() {
        // Required empty public constructor
    }

    public static WarningFragment newInstance() {
        WarningFragment fragment = new WarningFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_warning, container, false);
        ButterKnife.bind(this, view);
        Linkify.addLinks(link, Linkify.EMAIL_ADDRESSES);

        return view;
    }

    @OnClick(R.id.gitf_btn_warning_finish)
    public void finished(){
        getActivity().onBackPressed();
    }

}
