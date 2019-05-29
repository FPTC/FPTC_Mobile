package org.pfccap.education.entities;

import java.util.HashMap;

public class EseEntity {

    private long id;
    private String idPais;
    private String idCiudad;
    private String name;
    private boolean state;

    private HashMap<String, IpsEntity> ips = new HashMap<>();

    public EseEntity(){}

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

    public String getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(String idCiudad) {
        this.idCiudad = idCiudad;
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

    public HashMap<String, IpsEntity> getIps() {
        return ips;
    }

    public void setIps(HashMap<String, IpsEntity> ips) {
        this.ips = ips;
    }
}
