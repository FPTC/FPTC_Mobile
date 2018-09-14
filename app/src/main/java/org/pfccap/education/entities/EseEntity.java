package org.pfccap.education.entities;

import java.util.HashMap;

public class EseEntity {

    private int id;
    private int idPais;
    private int idCiudad;
    private String name;
    private boolean state;

    private HashMap<String, IpsEntity> ips = new HashMap<>();

    public EseEntity(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
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
