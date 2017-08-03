package org.pfccap.education.presentation.main.ui.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.pfccap.education.R;
import org.pfccap.education.domain.user.IUserBP;
import org.pfccap.education.domain.user.UserBP;
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

    private OnMessFragInteractionListener mListener;

    @BindView(R.id.gift_msg_intro)
    TextView intro;

    @BindView(R.id.content_ms_gift)
    TextView content;

    @BindView(R.id.lyt_botton_gift)
    LinearLayout buttons;

    @BindView(R.id.points_gift_message)
    TextView points;

    @BindView(R.id.gitf_btn_gift)
    Button gift;

    @BindView(R.id.gitf_btn_finish)
    Button finish;

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

        initTexts();
        //se muestra la introducción y el mensaje
        intro.setVisibility(View.VISIBLE);
        content.setVisibility(View.VISIBLE);
        points.setVisibility(View.GONE);
        finish.setVisibility(View.GONE);

        return view;
    }

    public void initTexts(){
        //se calcula los puntos totales
        SpannableString spanTxt;
        int totalpoint = Integer.valueOf(Cache.getByKey(Constants.TOTAL_POINTS_C)) + Integer.valueOf(Cache.getByKey(Constants.TOTAL_POINTS_B));
        spanTxt = new SpannableString(getString(R.string.points_and_thanks_gift, String.valueOf(totalpoint)));
        spanTxt.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),R.color.colorBlue)),27,30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanTxt.setSpan(new StyleSpan(Typeface.BOLD), 27, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanTxt.setSpan(new RelativeSizeSpan(2f), 27, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        points.setText(spanTxt);

        if (!Cache.getByKey(Constants.APPOINTMENT_TYPE).equals("0")) {
            if (Cache.getByKey(Constants.APPOINTMENT_TYPE).equals("3")){
                spanTxt = new SpannableString(getString(R.string.message_validation_yes_gift, getString(R.string.appointment_cervix_breast)));
                spanTxt.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),R.color.colorPrimary)),67, 98,  Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }else  if (Cache.getByKey(Constants.APPOINTMENT_TYPE).equals("1")){
                spanTxt = new SpannableString(getString(R.string.message_validation_yes_gift, getString(R.string.appointment_breast)));
                spanTxt.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),R.color.colorPrimary)),67, 78,  Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }else  if (Cache.getByKey(Constants.APPOINTMENT_TYPE).equals("2")){
                spanTxt = new SpannableString(getString(R.string.message_validation_yes_gift, getString(R.string.appointment_cervix)));
                spanTxt.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),R.color.colorPrimary)),67, 85,  Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            //se actualiza que requiere examen con las variables en el nodo de usuario en firebase en 0
            HashMap<String, Object> obj = new HashMap<>();
            obj.put(Constants.APPOINTMENT, false);
            IUserBP userBP = new UserBP();
            userBP.update(obj);
            content.setText(spanTxt);
            buttons.setVisibility(View.VISIBLE);
            gift.setVisibility(View.GONE);

        }else{
            content.setText(getString(R.string.message_validation_no_gift));
            buttons.setVisibility(View.GONE);
            gift.setVisibility(View.VISIBLE);

        }

    }

    @OnClick(R.id.gift_btn_yes)
    public void gift_btn_yes(){
        //se actualiza si requiere examen las variables en el nodo de usuario en firebase
        HashMap<String, Object> obj = new HashMap<>();
        obj.put(Constants.APPOINTMENT, true);
        IUserBP userBP = new UserBP();
        userBP.update(obj);
        intro.setVisibility(View.GONE);
        content.setVisibility(View.GONE);
        buttons.setVisibility(View.GONE);
        points.setVisibility(View.VISIBLE);
        finish.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.gift_btn_no)
    public void gift_btn_no(){
        intro.setVisibility(View.GONE);
        content.setVisibility(View.GONE);
        buttons.setVisibility(View.GONE);
        points.setVisibility(View.VISIBLE);
        gift.setVisibility(View.GONE);
        finish.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.gitf_btn_gift)
    public void gift_btn_gift(){
        intro.setVisibility(View.GONE);
        content.setVisibility(View.GONE);
        buttons.setVisibility(View.GONE);
        points.setVisibility(View.VISIBLE);
        gift.setVisibility(View.GONE);
        finish.setVisibility(View.VISIBLE);
    }


    @OnClick(R.id.gitf_btn_finish)
    public void finish(){
        if (mListener != null) {
            mListener.onNavigateToWarnings();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMessFragInteractionListener) {
            mListener = (OnMessFragInteractionListener) context;
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

    public interface OnMessFragInteractionListener {
        void onNavigateToWarnings();
    }


}
