package org.pfccap.education.domain.configuration;

import org.pfccap.education.entities.Configuration;
import org.pfccap.education.entities.ConfigurationGifts;

import io.reactivex.Observable;

/**
 * Created by jggomez on 08-Jun-17.
 */

public interface IConfigurationBP {

    Observable<Configuration> getConfiguration();

    Observable<ConfigurationGifts> getConfigurationGifts();

    Observable<Boolean> getPaises();

}
