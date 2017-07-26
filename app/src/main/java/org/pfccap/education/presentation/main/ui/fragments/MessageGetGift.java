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
import org.pfccap.education.domain.user.IUserBP;
import org.pfccap.education.domain.user.UserBP;
import org.pfccap.education.presentation.main.presenters.GiftsPresenter;
import org.pfccap.education.presentation.main.presenters.IGiftsPresenter;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

        if (!Cache.getByKey(Constants.APPOINTMENT_TYPE).equals("0")) {
            String type_appointement = "";
            if (Cache.getByKey(Constants.APPOINTMENT_TYPE).equals("3")){
                type_appointement = "mamografía y citología vaginal";
            }else  if (Cache.getByKey(Constants.APPOINTMENT_TYPE).equals("1")){
                type_appointement = "mamografía";
            }else  if (Cache.getByKey(Constants.APPOINTMENT_TYPE).equals("2")){
                type_appointement = "citología vaginal";
            }
            content.setText(getString(R.string.message_validation_yes_gift, type_appointement));
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

    @OnClick(R.id.gitf_btn_yes)
    public void gitf_btn_yes(){
        //se actualiza si requiere examen las variables en el nodo de usuario en firebase
        HashMap<String, Object> obj = new HashMap<>();
        if (Cache.getByKey(Constants.APPOINTMENT_TYPE).equals("3")){
            obj.put(Constants.APPOINTMENT_B, 1);
            obj.put(Constants.APPOINTMENT_C, 1);
        }else  if (Cache.getByKey(Constants.APPOINTMENT_TYPE).equals("2")){
            obj.put(Constants.APPOINTMENT_B, 1);
        }else  if (Cache.getByKey(Constants.APPOINTMENT_TYPE).equals("1")){
            obj.put(Constants.APPOINTMENT_C, 1);
        }

        IUserBP userBP = new UserBP();
        userBP.update(obj);
        buttons.setVisibility(View.GONE);
        points.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.gitf_btn_no)
    public void gitf_btn_no(){
        //se actualiza si requiere examen las variables en el nodo de usuario en firebase
        HashMap<String, Object> obj = new HashMap<>();
        if (Cache.getByKey(Constants.APPOINTMENT_TYPE).equals("3")){
            obj.put(Constants.APPOINTMENT_B, 0);
            obj.put(Constants.APPOINTMENT_C, 0);
        }else  if (Cache.getByKey(Constants.APPOINTMENT_TYPE).equals("2")){
            obj.put(Constants.APPOINTMENT_B, 0);
        }else  if (Cache.getByKey(Constants.APPOINTMENT_TYPE).equals("1")){
            obj.put(Constants.APPOINTMENT_C, 0);
        }

        IUserBP userBP = new UserBP();
        userBP.update(obj);
        buttons.setVisibility(View.GONE);
        points.setVisibility(View.VISIBLE);
    }

}
