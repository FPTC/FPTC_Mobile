package org.pfccap.education.domain.placeData;

import com.google.firebase.crash.FirebaseCrash;

import org.pfccap.education.application.AppDao;
import org.pfccap.education.dao.Ciudades;
import org.pfccap.education.dao.CiudadesDao;
import org.pfccap.education.dao.Comunas;
import org.pfccap.education.dao.ComunasDao;
import org.pfccap.education.dao.Ese;
import org.pfccap.education.dao.EseDao;
import org.pfccap.education.dao.IPS;
import org.pfccap.education.dao.IPSDao;
import org.pfccap.education.dao.Paises;
import org.pfccap.education.dao.PaisesDao;

import java.util.List;

public class ProfilePlaceDB implements IProfilePlaceDB {

    private PaisesDao paisesDao;
    private CiudadesDao ciudadesDao;
    private ComunasDao comunasDao;
    private EseDao eseDao;
    private IPSDao ipsDao;

    public ProfilePlaceDB(){
        paisesDao = AppDao.getCountriesDao();
        ciudadesDao = AppDao.getCitiesDao();
        comunasDao = AppDao.getComunasDao();
        eseDao = AppDao.getEseDao();
        ipsDao = AppDao.getIpsDao();
    }

    @Override
    public List<Paises> getCountryData() {
        try {
            return paisesDao.loadAll();
        }catch (Exception e){
            FirebaseCrash.report(e);
            return null;
        }
    }

    @Override
    public List<Ciudades> getCitiesData(long idCountry) {
        try {
            String idpais = String.valueOf(idCountry);
            return ciudadesDao.queryBuilder()
                    .where(CiudadesDao.Properties.IdPais.eq(idpais))
                    .orderAsc()
                    .list();
        }catch (Exception e){
            FirebaseCrash.report(e);
            return null;
        }
    }

    @Override
    public List<Comunas> getComunasData(long idCity) {
        try {
            String idciudad = String.valueOf(idCity);
            return comunasDao.queryBuilder()
                    .where(ComunasDao.Properties.IdCiudad.eq(idciudad))
                    .orderAsc()
                    .list();
        }catch (Exception e){
            FirebaseCrash.report(e);
            return null;
        }
    }

    @Override
    public List<Ese> getEseData(long idCity) {
        try {
            String idciudad = String.valueOf(idCity);
            return eseDao.queryBuilder()
                    .where(EseDao.Properties.IdCiudad.eq(idciudad))
                    .orderAsc()
                    .list();
        }catch (Exception e){
            FirebaseCrash.report(e);
            return null;
        }
    }

    @Override
    public List<IPS> getIpsData(long idEse) {
        try {
            String idese = String.valueOf(idEse);
            return ipsDao.queryBuilder()
                    .where(IPSDao.Properties.IdEse.eq(idese))
                    .orderAsc()
                    .list();
        }catch (Exception e){
            FirebaseCrash.report(e);
            return null;
        }
    }


}
