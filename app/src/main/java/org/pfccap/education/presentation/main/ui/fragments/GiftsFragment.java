package org.pfccap.education.presentation.main.ui.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import org.pfccap.education.R;
import org.pfccap.education.dao.Gift;
import org.pfccap.education.presentation.main.presenters.GiftsPresenter;
import org.pfccap.education.presentation.main.presenters.IGiftsPresenter;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;
import org.pfccap.education.utilities.Table;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GiftsFragment extends Fragment {

    //private OnFragmentInteractionListener mListener;
    @BindView(R.id.mainGiftLayoutTable)
    TableLayout giftTable;

    @BindView(R.id.mainGiftTxtPoints)
    TextView totalPoints;

    @BindView(R.id.appointment_gift)
    TextView appointmentGift;

    private IGiftsPresenter giftsPresenter;

    public GiftsFragment() {
        // Required empty public constructor
    }

    public static GiftsFragment newInstance() {
        GiftsFragment fragment = new GiftsFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gifts, container, false);
        ButterKnife.bind(this, view);
        giftsPresenter = new GiftsPresenter();
        int totalpoint;
        if (Cache.getByKey(Constants.TOTAL_POINTS_C).equals("") && Cache.getByKey(Constants.TOTAL_POINTS_C).equals("")) {
            totalpoint = 0;
        }else{
            totalpoint = Integer.valueOf(Cache.getByKey(Constants.TOTAL_POINTS_C)) + Integer.valueOf(Cache.getByKey(Constants.TOTAL_POINTS_B));
        }

        totalPoints.setText(getString(R.string.title_points) + "¡" + totalpoint + "!");
        initTable();
        return view;
    }

    private void initTable() {
        appointmentGift.setText(Cache.getByKey(Constants.APPOINTMENT));

        Table table = new Table(getActivity(), giftTable);
        table.addHead(R.array.head_table);
        List<Gift> giftList = giftsPresenter.getListGiftsTable();

        for (Gift gift: giftList){
            ArrayList<String> elements = new ArrayList<>();
            elements.add(gift.getPoints());
            elements.add(gift.getGift());
            table.addRowTable(elements);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
  /*  public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
*/

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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
