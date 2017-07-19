package org.pfccap.education.domain.configuration;

import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.pfccap.education.application.AppDao;
import org.pfccap.education.dao.Gift;
import org.pfccap.education.dao.GiftDao;
import org.pfccap.education.domain.firebase.FirebaseHelper;
import org.pfccap.education.entities.Configuration;
import org.pfccap.education.entities.ConfigurationGifts;
import org.pfccap.education.entities.ItemGifts;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by jggomez on 08-Jun-17.
 */

public class ConfigurationBP implements IConfigurationBP {

    @Override
    public Observable<Configuration> getConfiguration() {
        try {

            return Observable.create(new ObservableOnSubscribe<Configuration>() {
                @Override
                public void subscribe(final ObservableEmitter<Configuration> e) throws Exception {

                    FirebaseHelper.getInstance().getConfigurationReference()
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Configuration configuration =
                                            dataSnapshot.getValue(Configuration.class);

                                    Cache.save(Constants.LAPSE_BREAST,
                                            String.valueOf(configuration.getLapseBreast()));
                                    Cache.save(Constants.LAPSE_CERVIX,
                                            String.valueOf(configuration.getLapseCervix()));
                                    Cache.save(Constants.NUM_OPPORTUNITIES,
                                            String.valueOf(configuration.getNumOpportunities()));
                                    if(Cache.getByKey(Constants.NUM_OPPORTUNITIES).equals("")){
                                        //Esto es porque luego se hace la comparación de valores haciendo la conversión a
                                        // enteros y si el valor viene vació sale una excepción cerrando la app.
                                        Cache.save(Constants.NUM_OPPORTUNITIES, "0");
                                    }
                                    e.onNext(configuration);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Exception error = new Exception(databaseError.getMessage());
                                    FirebaseCrash.report(error);
                                    e.onError(error);
                                }
                            });
                }
            });

        } catch (Exception e) {
            FirebaseCrash.report(e);
            throw e;
        }
    }

    @Override
    public Observable<ConfigurationGifts> getConfigurationGifts() {
        try {

            return Observable.create(new ObservableOnSubscribe<ConfigurationGifts>() {
                @Override
                public void subscribe(final ObservableEmitter<ConfigurationGifts> e) throws Exception {

                    FirebaseHelper.getInstance().getGiftsReference()
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    ConfigurationGifts gifts =
                                            dataSnapshot.getValue(ConfigurationGifts.class);
                                    saveGiftTable(gifts);
                                    e.onNext(gifts);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Exception error = new Exception(databaseError.getMessage());
                                    FirebaseCrash.report(error);
                                    e.onError(error);
                                }
                            });
                }
            });

        } catch (Exception e) {
            FirebaseCrash.report(e);
            throw e;
        }
    }

    private void saveGiftTable(ConfigurationGifts listGift) {

        try {
            HashMap<String, ItemGifts> gifts = listGift.getGifts();

            GiftDao giftDao = AppDao.getGiftDao();
            Gift gift;
            for (Map.Entry<String, ItemGifts> entry : gifts.entrySet()) {
                gift = new Gift();
                gift.setPoints(entry.getValue().getPoints());
                gift.setGift(entry.getValue().getGift());
                giftDao.insert(gift);
            }
        } catch (Exception e) {
            FirebaseCrash.report(e);
        }

    }

}
