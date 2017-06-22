package org.pfccap.education.entities;

import java.util.HashMap;

/**
 * Created by USUARIO on 21/06/2017.
 */

public class ConfigurationGifts {

    String appointment;

    private HashMap<String, ItemGifts> gifts = new HashMap<>();

    public ConfigurationGifts(){}

    public String getAppointment() {
        return appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }

    public HashMap<String, ItemGifts> getGifts() {
        return gifts;
    }

    public void setGifts(HashMap<String, ItemGifts> gifs) {
        this.gifts = gifs;
    }
}
