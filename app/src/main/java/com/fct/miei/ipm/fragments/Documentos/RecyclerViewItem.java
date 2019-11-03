package com.fct.miei.ipm.fragments.Documentos;

public class RecyclerViewItem {

    private int drawableId;
    private String name;
    private int estrelas;

    public RecyclerViewItem(int drawableId, String name , int estrelas) {
        this.drawableId = drawableId;
        this.name = name;
        this.estrelas = estrelas;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public int getEstrelas(){
        return this.estrelas;
    }

    public String getName() {
        return name;
    }
}