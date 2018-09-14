package org.pfccap.education.domain.configurationIps;

import org.pfccap.education.entities.Cities;
import org.pfccap.education.entities.ComunasEntity;
import org.pfccap.education.entities.Countries;
import org.pfccap.education.entities.EseEntity;
import org.pfccap.education.entities.IpsEntity;

import java.util.List;

public interface IConfigIps {

    List<Countries> getCountries();
    List<Cities> getCities(int idCountry);
    List<ComunasEntity> getComunas (int idCity, int idCountry);
    List<EseEntity> getEse(int idCity, int idCountry);
    List<IpsEntity> getIps(int idCity, int idCountry, int idEse);
}
