package com.fct.miei.ipm.fragments.Documentos;

import java.util.Comparator;

public class RecyclerViewItem {

    private int drawableId;
    private String name;
    private int estrelas;
    private int day;
    private boolean isClassified;
    private int classifi;

    public RecyclerViewItem(int drawableId, String name, int estrelas, int day) {
        this.drawableId = drawableId;
        this.name = name;
        this.estrelas = estrelas;
        this.day = day;
        this.isClassified = false;
        this.classifi = 0;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public int getEstrelas() {
        return this.estrelas + this.classifi;
    }

    public int getDay() { return this.day; }

    public String getName() {
        return name;
    }

    public void incEstrealas(int estrelas){
        this.classifi = estrelas;
    }

    public static Comparator<RecyclerViewItem> DocNameComp = new Comparator<RecyclerViewItem>() {

        public int compare(RecyclerViewItem doc1, RecyclerViewItem doc2) {
            String docName1 = doc1.getName().toUpperCase();
            String docName2 = doc2.getName().toUpperCase();

            //alphabetic order
            return docName1.compareTo(docName2);

        }};

    public static Comparator<RecyclerViewItem> DocRecentComp = new Comparator<RecyclerViewItem>() {

        public int compare(RecyclerViewItem doc1, RecyclerViewItem doc2) {
            int docDate1 = doc1.getDay();
            int docDate2 = doc2.getDay();

            //alphabetic order
            return docDate1 - docDate2;

        }};

    public static Comparator<RecyclerViewItem> DocOlderComp = new Comparator<RecyclerViewItem>() {

        public int compare(RecyclerViewItem doc1, RecyclerViewItem doc2) {
            int docDate1 = doc1.getDay();
            int docDate2 = doc2.getDay();

            //alphabetic order
            return docDate2 - docDate1;

        }};

    public static Comparator<RecyclerViewItem> DocClassificationComp = new Comparator<RecyclerViewItem>() {

        public int compare(RecyclerViewItem doc1, RecyclerViewItem doc2) {
            int docEstrelas1 = doc1.getEstrelas();
            int docEstrelas2 = doc2.getEstrelas();

            //alphabetic order
            return docEstrelas2 - docEstrelas1;

        }};

}