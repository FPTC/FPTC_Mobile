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
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;
import org.pfccap.education.utilities.Table;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GiftsFragment extends Fragment {

    //private OnFragmentInteractionListener mListener;
    @BindView(R.id.mainGiftLayoutTable)
    TableLayout giftTable;
    @BindView(R.id.mainGiftTxtPoints)
    TextView totalPoints;

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
        if (Cache.getByKey(Constants.TOTAL_POINTS).equals(""))
            Cache.save(Constants.TOTAL_POINTS, "0");

        totalPoints.setText(getString(R.string.title_star_have) + Cache.getByKey(Constants.TOTAL_POINTS) + getString(R.string.title_end_points));
        initTable();
        return view;
    }

    private void initTable() {

        Table table = new Table(getActivity(), giftTable);
        table.addHead(R.array.head_table);
        int r = 0;
        for (int i = 0; i < 3; i++){
            r = r + 5000;
            ArrayList<String> elements = new ArrayList<String>();
            elements.add(Integer.toString(i));
            elements.add("Recarga $" + r );
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
