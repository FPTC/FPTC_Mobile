package org.pfccap.education.domain.configuration;

import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.pfccap.education.domain.firebase.FirebaseHelper;
import org.pfccap.education.entities.Configuration;
import org.pfccap.education.utilities.Cache;
import org.pfccap.education.utilities.Constants;

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

}
