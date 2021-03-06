package org.pfccap.education.entities;

import java.util.HashMap;

public class Cities {

    private long id;
    private String idPais;
    private String name;
    private boolean state;
    private HashMap<String, ComunasEntity> comunas = new HashMap<>();
    private HashMap<String, EseEntity> ese = new HashMap<>();

    public Cities (){}


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdPais() {
        return idPais;
    }

    public void setIdPais(String idPais) {
        this.idPais = idPais;
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

    public HashMap<String, ComunasEntity> getComunas() {
        return comunas;
    }

    public void setComunas(HashMap<String, ComunasEntity> comunas) {
        this.comunas = comunas;
    }

    public HashMap<String, EseEntity> getEse() {
        return ese;
    }

    public void setEse(HashMap<String, EseEntity> ese) {
        this.ese = ese;
    }
}
