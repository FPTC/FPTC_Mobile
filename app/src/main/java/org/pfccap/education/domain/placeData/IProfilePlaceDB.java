package org.pfccap.education.domain.placeData;

import org.pfccap.education.dao.Ciudades;
import org.pfccap.education.dao.Comunas;
import org.pfccap.education.dao.Ese;
import org.pfccap.education.dao.IPS;
import org.pfccap.education.dao.Paises;

import java.util.List;

public interface IProfilePlaceDB {

    List<Paises> getCountryData();

    List<Ciudades> getCitiesData(long idCountry);

    List<Comunas> getComunasData(long idCity);

    List<Ese> getEseData(long idCity);

    List<IPS> getIpsData(long idEse);

}
