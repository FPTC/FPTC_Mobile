package org.pfccap.education.entities;

public class SpinnerEntidad {

    private long id;
    private String item;


    public SpinnerEntidad(long _id, String _item){
        id = _id;
        item = _item;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }


    // para mostrar el item como un string en el spinner
    @Override
    public String toString(){
        return item;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof SpinnerEntidad){
            SpinnerEntidad c = (SpinnerEntidad )obj;
            if(c.getItem().equals(item) && c.getId()==id ) return true;
        }

        return false;
    }
}
