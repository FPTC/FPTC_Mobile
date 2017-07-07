package org.pfccap.education.presentation.main.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import org.pfccap.education.R;
import org.pfccap.education.presentation.main.presenters.GiftsPresenter;
import org.pfccap.education.presentation.main.presenters.IGiftsPresenter;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by USUARIO on 27/06/2017.
 */

public class MessageGetGift extends Fragment {


    @BindView(R.id.content_ms_gift)
    TextView content;

    @BindView(R.id.logo_gift)
    ImageView logo;

    @BindView(R.id.lyt_botton_gift)
    LinearLayout buttons;

    @BindView(R.id.points_gitf_message)
    TextView points;

    boolean validation = true;

    public MessageGetGift() {
        // Required empty public constructor
    }

    public static MessageGetGift newInstance() {
        MessageGetGift fragment = new MessageGetGift();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.message_get_gift, container, false);
        ButterKnife.bind(this, view);
        int totalpoint = Integer.valueOf(Cache.getByKey(Constants.TOTAL_POINTS_C)) + Integer.valueOf(Cache.getByKey(Constants.TOTAL_POINTS_B));
        points.setText(getString(R.string.points_and_thanks_gift, String.valueOf(totalpoint)));

        if (validation) {
            content.setText(getString(R.string.message_validation_yes_gift));
            buttons.setVisibility(View.VISIBLE);
            logo.setVisibility(View.GONE);
        }else{
            content.setText(getString(R.string.message_validation_no_gift));
            buttons.setVisibility(View.GONE);
            logo.setVisibility(View.VISIBLE);
        }

      //  totalPoints.setText(getString(R.string.title_points) + "¡" + totalpoint + "!");
        return view;
    }

}
