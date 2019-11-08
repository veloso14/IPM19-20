package com.fct.miei.ipm.fragments.Partilhar;

public class Data {

    private String name;
    private int imageId;

    public Data() {
    }

    public Data(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}