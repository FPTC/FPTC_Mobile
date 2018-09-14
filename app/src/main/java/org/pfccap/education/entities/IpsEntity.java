package org.pfccap.education.entities;

public class IpsEntity {

    private int id;
    private int idPais;
    private int idCiudad;
    private int isESE;
    private String name;
    private boolean state;

    public IpsEntity(){

    }

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

    public int getIdESE() {
        return isESE;
    }

    public void setIdESE(int idESE) {
        this.isESE = idESE;
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
