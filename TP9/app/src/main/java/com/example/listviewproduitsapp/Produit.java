package com.example.listviewproduitsapp;

public class Produit {
    private String nom;
    private double prix;
    private int imageResId;
    private boolean selected;

    public Produit(String nom, double prix, int imageResId) {
        this.nom = nom;
        this.prix = prix;
        this.imageResId = imageResId;
        this.selected = false;
    }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }

    public int getImageResId() { return imageResId; }
    public void setImageResId(int imageResId) { this.imageResId = imageResId; }

    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { this.selected = selected; }
}
