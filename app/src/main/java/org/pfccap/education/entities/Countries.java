package org.pfccap.education.entities;

import java.util.HashMap;

public class Countries {

    private long id;
    private String name;
    private boolean state;
    private HashMap<String, Cities> ciudades = new HashMap<>();

    public Countries (){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public HashMap<String, Cities> getCiudades() {
        return ciudades;
    }

    public void setCiudades(HashMap<String, Cities> ciudades) {
        this.ciudades = ciudades;
    }
}
