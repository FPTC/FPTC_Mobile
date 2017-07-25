package org.pfccap.education.presentation.auth.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.pfccap.education.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TermsAndPolicyFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TITLE = "param1";
    private static final String CONTENT = "param2";

    private String title;
    private String content;

    @BindView(R.id.termsPolicyTitle)
    TextView txtTitle;

    @BindView(R.id.termsPolicyContent)
    TextView txtContent;

    public TermsAndPolicyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TermsAndPolicyFragment.
     */
    public static TermsAndPolicyFragment newInstance(String param1, String param2) {
        TermsAndPolicyFragment fragment = new TermsAndPolicyFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, param1);
        args.putString(CONTENT, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(TITLE);
            content = getArguments().getString(CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_terms_and_policy, container, false);
        ButterKnife.bind(this, view);
        txtTitle.setText(title);
        txtContent.setText(content);
        return view;
    }


}
