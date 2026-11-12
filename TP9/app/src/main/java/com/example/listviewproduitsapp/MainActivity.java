package com.example.listviewproduitsapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    private List<Produit> produits = new ArrayList<>();
    private ProduitAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editSearch = findViewById(R.id.editSearch);
        Button btnAjouter = findViewById(R.id.btnAjouter);
        Button btnModifier = findViewById(R.id.btnModifier);
        Button btnSupprimer = findViewById(R.id.btnSupprimer);
        ListView listView = findViewById(R.id.listViewProduits);

        produits.add(new Produit("Téléphone", 999, R.drawable.phone));
        produits.add(new Produit("Ordinateur", 2500, R.drawable.laptop));
        produits.add(new Produit("Casque", 150, R.drawable.headphones));

        adapter = new ProduitAdapter(this, produits);
        listView.setAdapter(adapter);

        // 🔍 Filtrage en temps réel
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterList(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        // ➕ Ajouter
        btnAjouter.setOnClickListener(v -> showAddDialog());

        // ✏️ Modifier
        btnModifier.setOnClickListener(v -> showEditDialog());

        // 🗑️ Supprimer
        btnSupprimer.setOnClickListener(v -> deleteSelected());
    }

    private void filterList(String text) {
        List<Produit> filtered = new ArrayList<>();
        for (Produit p : produits) {
            if (p.getNom().toLowerCase().contains(text.toLowerCase())) {
                filtered.add(p);
            }
        }
        adapter.clear();
        adapter.addAll(filtered);
    }

    private void showAddDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_edit, null);
        EditText nomInput = dialogView.findViewById(R.id.editNom);
        EditText prixInput = dialogView.findViewById(R.id.editPrix);

        new AlertDialog.Builder(this)
                .setTitle("Ajouter un produit")
                .setView(dialogView)
                .setPositiveButton("Ajouter", (dialog, which) -> {
                    String nom = nomInput.getText().toString();
                    double prix = Double.parseDouble(prixInput.getText().toString());
                    produits.add(new Produit(nom, prix, R.drawable.phone));
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    private void showEditDialog() {
        List<Produit> selected = new ArrayList<>();
        for (Produit p : produits)
            if (p.isSelected()) selected.add(p);

        if (selected.size() != 1) {
            Toast.makeText(this, "Sélectionnez un seul produit à modifier", Toast.LENGTH_SHORT).show();
            return;
        }

        Produit produit = selected.get(0);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_edit, null);
        EditText nomInput = dialogView.findViewById(R.id.editNom);
        EditText prixInput = dialogView.findViewById(R.id.editPrix);

        nomInput.setText(produit.getNom());
        prixInput.setText(String.valueOf(produit.getPrix()));

        new AlertDialog.Builder(this)
                .setTitle("Modifier le produit")
                .setView(dialogView)
                .setPositiveButton("Modifier", (dialog, which) -> {
                    produit.setNom(nomInput.getText().toString());
                    produit.setPrix(Double.parseDouble(prixInput.getText().toString()));
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    private void deleteSelected() {
        produits.removeIf(Produit::isSelected);
        adapter.notifyDataSetChanged();
    }
}
