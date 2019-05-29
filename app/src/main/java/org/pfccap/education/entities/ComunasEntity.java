package org.pfccap.education.entities;

public class ComunasEntity {

    private long id;
    private String idPais;
    private String idCiudad;
    private String name;
    private boolean state;

    public ComunasEntity(){}

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
}
