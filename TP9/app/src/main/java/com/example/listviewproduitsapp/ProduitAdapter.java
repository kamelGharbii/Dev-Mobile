package com.example.listviewproduitsapp;

import android.content.Context;
import android.view.*;
import android.widget.*;
import java.util.List;

public class ProduitAdapter extends ArrayAdapter<Produit> {
    private final List<Produit> produits;

    public ProduitAdapter(Context context, List<Produit> produits) {
        super(context, 0, produits);
        this.produits = produits;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_produit, parent, false);

        Produit produit = produits.get(position);

        ImageView image = convertView.findViewById(R.id.imageProduit);
        TextView nom = convertView.findViewById(R.id.textNom);
        TextView prix = convertView.findViewById(R.id.textPrix);
        CheckBox checkBox = convertView.findViewById(R.id.checkBox);

        image.setImageResource(produit.getImageResId());
        nom.setText(produit.getNom());
        prix.setText(String.format("%.2f DT", produit.getPrix()));
        checkBox.setChecked(produit.isSelected());

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> produit.setSelected(isChecked));

        return convertView;
    }
}
