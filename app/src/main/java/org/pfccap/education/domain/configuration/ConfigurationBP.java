package org.pfccap.education.domain.configuration;

import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.pfccap.education.application.AppDao;
import org.pfccap.education.dao.Ciudades;
import org.pfccap.education.dao.CiudadesDao;
import org.pfccap.education.dao.Comunas;
import org.pfccap.education.dao.ComunasDao;
import org.pfccap.education.dao.Ese;
import org.pfccap.education.dao.EseDao;
import org.pfccap.education.dao.Gift;
import org.pfccap.education.dao.GiftDao;
import org.pfccap.education.dao.IPS;
import org.pfccap.education.dao.IPSDao;
import org.pfccap.education.dao.Paises;
import org.pfccap.education.dao.PaisesDao;
import org.pfccap.education.domain.firebase.FirebaseHelper;
import org.pfccap.education.entities.Cities;
import org.pfccap.education.entities.ComunasEntity;
import org.pfccap.education.entities.ConfiguracionIPS;
import org.pfccap.education.entities.Configuration;
import org.pfccap.education.entities.ConfigurationGifts;
import org.pfccap.education.entities.Countries;
import org.pfccap.education.entities.EseEntity;
import org.pfccap.education.entities.IpsEntity;
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

    //se obtiene la respuesta de la consulta de la configuración cada cuantos días puede responder el cuestionario
    // y número de oportunidades para contestar el cuestionario
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

    //se obtiene los datos para llenar la tabla de puntos y premios
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

    //se guardan los datos obtenidos de la consulta a firebase en la base de datos local
    private void saveGiftTable(ConfigurationGifts listGift) {

        try {
            HashMap<String, ItemGifts> gifts = listGift.getGifts();

            GiftDao giftDao = AppDao.getGiftDao();
            Gift gift;
            for (Map.Entry<String, ItemGifts> entry : gifts.entrySet()) {
                gift = new Gift();
                gift.setPoints(entry.getValue().getPoints());
                gift.setGift(entry.getValue().getGift());
                gift.setOrder(entry.getValue().getOrder());
                giftDao.insert(gift);
            }
        } catch (Exception e) {
            FirebaseCrash.report(e);
        }

    }

    //se obtienen los datos para solicitar la ips de cada usuario en el perfil, trae paises, cuiudades, comunas, eses y ips
    //se obtiene los datos para llenar la tabla de puntos y premios
    @Override
    public Observable<Boolean> getPaises() {
        try {

            return Observable.create(new ObservableOnSubscribe<Boolean>() {
                @Override
                public void subscribe(final ObservableEmitter<Boolean> e) throws Exception {

                    FirebaseHelper.getInstance().getPaisesReference()
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    ConfiguracionIPS countries =
                                            dataSnapshot.getValue(ConfiguracionIPS.class);
                                    saveCountries(countries);
                                    e.onNext(true);
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

    //guarda los datos de pais, ciudad, comuna, ips en la base de datos
    private void saveCountries(ConfiguracionIPS configuracionIPS){
        try{

            HashMap<String, Countries> paises = configuracionIPS.getPaises();

            PaisesDao paisesDao = AppDao.getCountriesDao();
            CiudadesDao ciudadesDao = AppDao.getCitiesDao();
            ComunasDao comunasDao = AppDao.getComunasDao();
            EseDao eseDao = AppDao.getEseDao();
            IPSDao ipsDao = AppDao.getIpsDao();

            Paises pais;
            for (Map.Entry<String, Countries> entry: paises.entrySet()) {
                pais = new Paises();
                pais.setIdCountry(entry.getValue().getId());
                pais.setName(entry.getValue().getName());
                pais.setState(entry.getValue().isState());
                paisesDao.insert(pais);

                HashMap<String, Cities> ciudades = entry.getValue().getCiudades();
                Ciudades ciudad;
                for (Map.Entry<String, Cities> entry1: ciudades.entrySet()){
                    ciudad = new Ciudades();
                    ciudad.setIdCity(entry1.getValue().getId());
                    ciudad.setIdPais(entry1.getValue().getIdPais());
                    ciudad.setName(entry1.getValue().getName());
                    ciudad.setState(entry1.getValue().isState());
                    ciudadesDao.insert(ciudad);

                    HashMap<String, ComunasEntity> comunas = entry1.getValue().getComunas();
                    Comunas comuna;
                    for (Map.Entry<String, ComunasEntity> entry2: comunas.entrySet()){
                        comuna = new Comunas();
                        comuna.setIdComuna(entry2.getValue().getId());
                        comuna.setIdCiudad(entry2.getValue().getIdCiudad());
                        comuna.setIdPais(entry2.getValue().getIdPais());
                        comuna.setName(entry2.getValue().getName());
                        comuna.setState(entry2.getValue().isState());
                        comunasDao.insert(comuna);
                    }

                    HashMap<String, EseEntity> eses = entry1.getValue().getEse();
                    Ese ese;
                    for (Map.Entry<String, EseEntity> entry3: eses.entrySet()){
                        ese = new Ese();
                        ese.setIdEse(entry3.getValue().getId());
                        ese.setIdCiudad(entry3.getValue().getIdCiudad());
                        ese.setIdPais(entry3.getValue().getIdPais());
                        ese.setName(entry3.getValue().getName());
                        ese.setState(entry3.getValue().isState());
                        eseDao.insert(ese);

                        HashMap<String, IpsEntity> ipses = entry3.getValue().getIps();
                        IPS ips;
                        for (Map.Entry<String, IpsEntity> entry4: ipses.entrySet()){
                            ips = new IPS();
                            ips.setIdIps(entry4.getValue().getId());
                            ips.setIdCiudad(entry4.getValue().getIdCiudad());
                            ips.setIdPais(entry4.getValue().getIdPais());
                            ips.setIdEse(entry4.getValue().getIdESE());
                            ips.setName(entry4.getValue().getName());
                            ips.setState(entry4.getValue().isState());
                            ipsDao.insert(ips);
                        }
                    }
                }
            }

        }catch (Exception e){
            FirebaseCrash.report(e);
            throw e;
        }
    }
}
