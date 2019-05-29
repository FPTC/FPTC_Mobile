package org.pfccap.education.presentation.main.presenters;

import android.content.Context;

import com.google.firebase.crash.FirebaseCrash;

import org.pfccap.education.R;
import org.pfccap.education.domain.services.SendCodeService;
import org.pfccap.education.entities.RespSendCode;
import org.pfccap.education.presentation.main.ui.fragments.IMessageGiftView;
import org.pfccap.education.utilities.APIService;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class MessageGiftPresenter implements IMessageGiftPresenter {

    private Context context;
    private IMessageGiftView view;

    public MessageGiftPresenter(IMessageGiftView view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void sendCode(String code, String uid, final String typeCancer) {
        view.showProgress();

        SendCodeService service = APIService.getInstanceRetrofit(SendCodeService.class);

        Map<String, String> data = new HashMap<>();
        data.put("t", typeCancer);
        data.put("c", code);
        data.put("uid", uid);



        service.sendCode(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Response<RespSendCode>>() {

                    @Override
                    public void onNext(Response<RespSendCode> value) {
                        String url = value.raw().request().url().toString();
                        int respCode = value.body().getState();
                        String msgResponse = value.body().getMessage();
                        String titulo = "";
                        Cache.save(Constants.INTENTOS, String.valueOf(value.body().getIntentos()));
                        Cache.save(Constants.STATE_CODE, String.valueOf(respCode));
                        int intentos = value.body().getIntentos();
                        switch (respCode) {
                            case 0:
                                titulo = "Error";
                                break;
                            case 1:
                                titulo = "Felicidades";
                                break;
                            case -1:
                                titulo = "Error - " + "Codigo no valido Intentos: "+intentos;
                                break;
                            default:
                                msgResponse = "El código no se reconoce";
                                titulo = "Atención";
                                break;
                        }

                        view.hideProgress();
                        view.showMessage(titulo, msgResponse, typeCancer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.hideProgress();
                        showErrorView(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void showErrorView(Throwable e) {
        FirebaseCrash.report(e);
        view.hideProgress();
        FirebaseCrash.report(e);
        if (e.getMessage().equals("Unable to resolve host \"us-central1-fptc-dev.cloudfunctions.net\": No address associated with hostname")) {
            view.showErrorSnack(context.getResources().getString(R.string.error_with_network_spanish));
        } else {
            view.showErrorSnack(e.getMessage());
        }
    }
}
