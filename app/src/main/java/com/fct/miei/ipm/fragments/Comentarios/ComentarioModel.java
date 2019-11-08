package com.fct.miei.ipm.fragments.Comentarios;

public class ComentarioModel {

    int idImage;
    String texto;


    public ComentarioModel(int idImage , String texto ) {
        this.idImage=idImage;
        this.texto=texto;

    }

    public String getTexto() {
        return texto;
    }

    public int getIdImage() {
        return idImage;
    }


}