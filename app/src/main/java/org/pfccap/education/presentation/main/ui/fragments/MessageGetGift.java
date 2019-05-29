package org.pfccap.education.presentation.main.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import org.pfccap.education.R;
import org.pfccap.education.domain.user.IUserBP;
import org.pfccap.education.domain.user.UserBP;
import org.pfccap.education.presentation.main.presenters.IMessageGiftPresenter;
import org.pfccap.education.presentation.main.presenters.MessageGiftPresenter;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;
import org.pfccap.education.utilities.Utilities;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by USUARIO on 27/06/2017.
 */

public class MessageGetGift extends Fragment implements IMessageGiftView {

    private OnMessFragInteractionListener mListener;

    @BindView(R.id.gift_msg_intro)
    TextView intro;

    @BindView(R.id.content_ms_gift)
    TextView content;

    /*@BindView(R.id.lyt_botton_gift)
    LinearLayout buttons;

    @BindView(R.id.points_gift_message)
    TextView points;

    @BindView(R.id.gitf_btn_gift)
    Button gift;

    @BindView(R.id.scrollMessageGift)
    ScrollView scrollView;*/

    @BindView(R.id.gitf_btn_finish)
    Button finish;

    @BindView(R.id.codigoCitologia)
    EditText codigoCitologia;

    @BindView(R.id.inputCodeBreast)
    TextInputLayout inputCodeBreast;

    @BindView(R.id.inputCodeCervix)
    TextInputLayout inputCodeCervix;

    @BindView(R.id.codigoMamografia)
    EditText codigoMamografia;

    @BindView(R.id.send_codigo)
    Button btnSend;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.content_ms_gift2)
    TextView content_gift2;

    private IMessageGiftPresenter presenter;
    String codeMamografia;
    String codeCitologia;
    private SpannableString spanTxt2 = null;
    private int codeCheck = 0; //se usa para saber cuatos codigos ha canjeado.

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
        presenter = new MessageGiftPresenter(this, getContext());
        //se muestra la introducción y el mensaje
        intro.setVisibility(View.VISIBLE);
        content.setVisibility(View.GONE);
        inputCodeBreast.setVisibility(View.GONE);
        inputCodeCervix.setVisibility(View.GONE);
        btnSend.setVisibility(View.GONE);
        initTexts();
        //    points.setVisibility(View.GONE);
        //     finish.setVisibility(View.GONE);

        return view;

    }

    public void initTexts() {
        //Se muestran los mensajes correspondientes
        SpannableString spanTxt = null;
       /*  int totalpoint = Integer.valueOf(Cache.getByKey(Constants.TOTAL_POINTS_C)) + Integer.valueOf(Cache.getByKey(Constants.TOTAL_POINTS_B));
        spanTxt = new SpannableString(getString(R.string.points_and_thanks_gift, String.valueOf(totalpoint)));
        spanTxt.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),R.color.colorBlue)),27,30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanTxt.setSpan(new StyleSpan(Typeface.BOLD), 27, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanTxt.setSpan(new RelativeSizeSpan(2f), 27, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        points.setText(spanTxt);*/
        content.setVisibility(View.VISIBLE);
        if (!Cache.getByKey(Constants.FAILEDTRIES).equals("null") &&
                !Cache.getByKey(Constants.FAILEDTRIES).equals("")
                && Integer.valueOf(Cache.getByKey(Constants.FAILEDTRIES)) >= 3) {
            spanTxt = new SpannableString(getString(R.string.end_turn_code));
            spanTxt.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
                    R.color.colorPrimary)), 0, 58, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if (!Cache.getByKey(Constants.APPOINTMENT_TYPE).equals("0") && !Cache.getByKey(Constants.APPOINTMENT_TYPE).equals("")) {

            if (Cache.getByKey(Constants.EXAMGIFTCERVIX).equals("true") &&
                    Cache.getByKey(Constants.EXAMGIFTBREAST).equals("true") ||
                    Integer.valueOf(Cache.getByKey(Constants.STATE)) == 2 && Cache.getByKey(Constants.APPOINTMENT_TYPE).equals("3")) {

                spanTxt = new SpannableString(getString(R.string.done_get_gift,
                        getString(R.string.appointment_cervix_breast)));
                spanTxt.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
                        R.color.colorPrimary)), 31, 61, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            } else if (Cache.getByKey(Constants.EXAMGIFTCERVIX).equals("true") && Cache.getByKey(Constants.APPOINTMENT_TYPE).equals("2") ||
                    Integer.valueOf(Cache.getByKey(Constants.STATE)) == 2 && Cache.getByKey(Constants.APPOINTMENT_TYPE).equals("2")) {

                spanTxt = new SpannableString(getString(R.string.done_get_gift,
                        getString(R.string.appointment_cervix)));
                spanTxt.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
                        R.color.colorPrimary)), 31, 48, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            } else if (Cache.getByKey(Constants.EXAMGIFTBREAST).equals("true") && !Cache.getByKey(Constants.EXAMGIFTBREAST).equals("") ||
                    Integer.valueOf(Cache.getByKey(Constants.STATE)) == 2 && Cache.getByKey(Constants.APPOINTMENT_TYPE).equals("1")) {

                spanTxt = new SpannableString(getString(R.string.done_get_gift,
                        getString(R.string.appointment_breast)));
                spanTxt.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
                        R.color.colorPrimary)), 31, 41, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            } else if (Cache.getByKey(Constants.APPOINTMENT_TYPE).equals("3")) {

                if (!Cache.getByKey(Constants.EXAMGIFTCERVIX).equals("true")) {
                    inputCodeCervix.setVisibility(View.VISIBLE);
                } else {

                    spanTxt2 = new SpannableString(getString(R.string.recarga_citologia_soon));
                    spanTxt2.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
                            R.color.colorPrimary)), 0, 40, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                if (!Cache.getByKey(Constants.EXAMGIFTBREAST).equals("true")) {
                    inputCodeBreast.setVisibility(View.VISIBLE);
                } else {
                    spanTxt2 = new SpannableString(getString(R.string.recarga_mamografia_soon));
                    spanTxt2.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
                            R.color.colorPrimary)), 0, 41, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                btnSend.setVisibility(View.VISIBLE);
                spanTxt = new SpannableString(getString(R.string.message_validation_yes_gift,
                        getString(R.string.appointment_cervix_breast)));
                spanTxt.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
                        R.color.colorPrimary)), 49, 79, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            } else if (Cache.getByKey(Constants.APPOINTMENT_TYPE).equals("1")) {

                inputCodeBreast.setVisibility(View.VISIBLE);
                inputCodeCervix.setVisibility(View.GONE);
                btnSend.setVisibility(View.VISIBLE);
                spanTxt = new SpannableString(getString(R.string.message_validation_yes_gift,
                        getString(R.string.appointment_breast)));
                spanTxt.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
                        R.color.colorPrimary)), 49, 59, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            } else if (Cache.getByKey(Constants.APPOINTMENT_TYPE).equals("2")) {

                inputCodeCervix.setVisibility(View.VISIBLE);
                inputCodeBreast.setVisibility(View.GONE);
                btnSend.setVisibility(View.VISIBLE);
                spanTxt = new SpannableString(getString(R.string.message_validation_yes_gift,
                        getString(R.string.appointment_cervix)));
                spanTxt.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
                        R.color.colorPrimary)), 49, 66, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            //      buttons.setVisibility(View.VISIBLE);
            //       gift.setVisibility(View.GONE);

        } else {
            spanTxt = new SpannableString(getString(R.string.no_tiene_indicacion));
            spanTxt.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.colorBlue)), 45, 65, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            inputCodeBreast.setVisibility(View.GONE);
            inputCodeCervix.setVisibility(View.GONE);
            btnSend.setVisibility(View.GONE);
            //      buttons.setVisibility(View.GONE);
            //      gift.setVisibility(View.VISIBLE);

        }
        content.setText(spanTxt);
        if (spanTxt2 != null) {
            content_gift2.setText(spanTxt2);
        }
    }

   /* @OnClick(R.id.gift_btn_yes)
    public void gift_btn_yes(){
        //se actualiza si requiere examen con 1 en las variables en el nodo de usuario en firebase
        HashMap<String, Object> obj = new HashMap<>();
        obj.put(Constants.APPOINTMENT, true);
        IUserBP userBP = new UserBP();
        userBP.update(obj);
        intro.setVisibility(View.GONE);
        content.setVisibility(View.GONE);
        buttons.setVisibility(View.GONE);
        points.setVisibility(View.VISIBLE);
        finish.setVisibility(View.VISIBLE);
        //se situa el scroll en la parte superior después de cambiar el contenido
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }

    @OnClick(R.id.gift_btn_no)
    public void gift_btn_no(){
        //se actualiza si requiere examen con 0 en las variables en el nodo de usuario en firebase
        HashMap<String, Object> obj = new HashMap<>();
        obj.put(Constants.APPOINTMENT, false);
        IUserBP userBP = new UserBP();
        userBP.update(obj);
        intro.setVisibility(View.GONE);
        content.setVisibility(View.GONE);
        buttons.setVisibility(View.GONE);
        points.setVisibility(View.VISIBLE);
        finish.setVisibility(View.VISIBLE);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }

    @OnClick(R.id.gitf_btn_gift)
    public void gift_btn_gift(){
        intro.setVisibility(View.GONE);
        content.setVisibility(View.GONE);
        buttons.setVisibility(View.GONE);
        points.setVisibility(View.VISIBLE);
        gift.setVisibility(View.GONE);
        finish.setVisibility(View.VISIBLE);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }*/


    @OnClick(R.id.gitf_btn_finish)
    public void finish() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.send_codigo)
    public void onClickSend() {
        codeMamografia = codigoMamografia.getText().toString();
        codeCitologia = codigoCitologia.getText().toString();
        if (!codeMamografia.equals("")) {
            Cache.save(Constants.TYPE_CANCER, Constants.BREAST);
            presenter.sendCode(codeMamografia, Cache.getByKey(Constants.USER_UID), String.valueOf(2));
        }
        if (!codeCitologia.equals("")) {
            Cache.save(Constants.TYPE_CANCER, Constants.CERVIX);
            presenter.sendCode(codeCitologia, Cache.getByKey(Constants.USER_UID), String.valueOf(1));
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

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String titulo, String message, String typeCancer) {
        // Utilities.snackbarMessageInfo(getView(), message);
        if (Integer.valueOf(Cache.getByKey(Constants.INTENTOS)) >= 3) {
            inputCodeCervix.setVisibility(View.GONE);
            inputCodeBreast.setVisibility(View.GONE);
            btnSend.setVisibility(View.GONE);
            content.setText(R.string.end_turn_code);
            Cache.save(Constants.FAILEDTRIES, "3");
            message = getString(R.string.fallo_tres_intentos);
        }
        giftDone(typeCancer);
        Utilities.dialogoCodeTamizaje(titulo, message, getContext());
    }

    @Override
    public void showErrorSnack(String message) {
        Utilities.snackbarMessageError(getView(), message);
    }

    public interface OnMessFragInteractionListener {
        void onNavigateToWarnings();
    }

    public void giftDone(String typeCancer) {

        if (Integer.valueOf(Cache.getByKey(Constants.STATE_CODE)) == 1 && typeCancer.equals("2")) {
            spanTxt2 = new SpannableString(getString(R.string.recarga_mamografia_soon));
            spanTxt2.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
                    R.color.colorPrimary)), 0, 41, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (!content_gift2.getText().toString().equals("")) {
                content.setText(spanTxt2);
            } else {
                content_gift2.setText(spanTxt2);
            }
            inputCodeBreast.setVisibility(View.GONE);
            Cache.save(Constants.EXAMGIFTBREAST, "true"); //indica que ya canjeo el código pero no le han dado premio
            codeCheck = codeCheck + 1;
        }
        if (Integer.valueOf(Cache.getByKey(Constants.STATE_CODE)) == 1 && typeCancer.equals("1")) {
            spanTxt2 = new SpannableString(getString(R.string.recarga_citologia_soon));
            spanTxt2.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
                    R.color.colorPrimary)), 0, 40, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (!content_gift2.getText().toString().equals("")) {
                content.setText(spanTxt2);
            } else {
                content_gift2.setText(spanTxt2);
            }
            Cache.save(Constants.EXAMGIFTCERVIX, "true");
            inputCodeCervix.setVisibility(View.GONE);
            codeCheck = codeCheck + 1;
        }
        //para poder ocultar el voton de enviar se debe estar seguro de que reclamo ambos premios
        if (Cache.getByKey(Constants.APPOINTMENT_TYPE).equals("3") && codeCheck == 2) {
            btnSend.setVisibility(View.GONE);
            //ya cajeo los dos codigos
            HashMap<String, Object> obj = new HashMap<>();
            obj.put(Constants.STATE, 2);
            IUserBP userBP = new UserBP();
            userBP.update(obj);
        } else if (Cache.getByKey(Constants.APPOINTMENT_TYPE).equals("2") || Cache.getByKey(Constants.APPOINTMENT_TYPE).equals("1")
                && codeCheck == 1) {
            btnSend.setVisibility(View.GONE);
            //ya canjeo el codigo que le correspondia cuando no tiene indicación de los dos exámenes
            HashMap<String, Object> obj = new HashMap<>();
            obj.put(Constants.STATE, 2);
            IUserBP userBP = new UserBP();
            userBP.update(obj);
        }

    }
}
