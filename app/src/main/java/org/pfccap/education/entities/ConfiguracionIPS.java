package org.pfccap.education.entities;

import java.util.HashMap;

public class ConfiguracionIPS {

    private HashMap<String, Countries> paises = new HashMap<>();

    public ConfiguracionIPS(){

    }

    public HashMap<String, Countries> getPaises() {
        return paises;
    }

    public void setPaises(HashMap<String, Countries> paises) {
        this.paises = paises;
    }
}
