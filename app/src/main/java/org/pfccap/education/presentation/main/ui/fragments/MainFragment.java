package org.pfccap.education.presentation.main.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.pfccap.education.R;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;
import org.pfccap.education.utilities.Utilities;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainFragment extends Fragment implements IMainFragmentView{

    private OnMainFragInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainFragment.
     */
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
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
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    @OnClick(R.id.mainBtnBreast)
    public void navigateToBreast() {
        Cache.save(Constants.TYPE_CANCER, Constants.BREAST);
        if (Integer.valueOf(Cache.getByKey(Constants.BREAST_TURN))==2){
            Utilities.snackbarMessageError(getView(), getContext().getResources().getString(R.string.end_turn));
        }else if (mListener != null) {
            mListener.onNavigateToBreast();
        }

    }

    @Override
    @OnClick(R.id.mainBtnCervix)
    public void navigateToCervical() {
        Cache.save(Constants.TYPE_CANCER, Constants.CERVIX);
        if (Cache.getByKey(Constants.CERVIX_TURN).equals("2")){
            Utilities.snackbarMessageError(getView(),  getContext().getResources().getString(R.string.end_turn));
        }else if (mListener != null) {
            mListener.onNavigateToCervical();
        }
    }

    @Override
    @OnClick(R.id.mainBtnGift)
    public void navigateToGifts() {
        if (mListener != null) {
            mListener.onNavigateToGifts();
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMainFragInteractionListener) {
            mListener = (OnMainFragInteractionListener) context;
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
    public interface OnMainFragInteractionListener {
        void onNavigateToBreast();
        void onNavigateToCervical();
        void onNavigateToGifts();
    }
}
