package org.pfccap.education.domain.configurationIps;

import org.pfccap.education.entities.Cities;
import org.pfccap.education.entities.ComunasEntity;
import org.pfccap.education.entities.Countries;
import org.pfccap.education.entities.EseEntity;
import org.pfccap.education.entities.IpsEntity;

import java.util.List;

public class ConfigIps implements IConfigIps {


    @Override
    public List<Countries> getCountries() {
        return null;
    }

    @Override
    public List<Cities> getCities(int idCountry) {
        return null;
    }

    @Override
    public List<ComunasEntity> getComunas(int idCity, int idCountry) {
        return null;
    }

    @Override
    public List<EseEntity> getEse(int idCity, int idCountry) {
        return null;
    }

    @Override
    public List<IpsEntity> getIps(int idCity, int idCountry, int idEse) {
        return null;
    }
}
