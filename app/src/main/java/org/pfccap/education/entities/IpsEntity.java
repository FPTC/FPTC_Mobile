package org.pfccap.education.entities;

public class IpsEntity {

    private long id;
    private String idPais;
    private String idCiudad;
    private String isESE;
    private String name;
    private boolean state;

    public IpsEntity(){

    }

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

    public String getIsESE() {
        return isESE;
    }

    public void setIsESE(String isESE) {
        this.isESE = isESE;
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
