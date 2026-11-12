package com.example.tp8_listview;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.util.SparseBooleanArray;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerCategories;
    EditText editTextItem;
    Button btnAdd, btnDelete;
    ListView listViewItems;

    HashMap<String, ArrayList<String>> categories;
    ArrayAdapter<String> listAdapter;
    ArrayAdapter<String> spinnerAdapter;

    String currentCategory;
    ArrayList<String> currentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerCategories = findViewById(R.id.spinnerCategories);
        editTextItem = findViewById(R.id.editTextItem);
        btnAdd = findViewById(R.id.btnAdd);
        btnDelete = findViewById(R.id.btnDelete);
        listViewItems = findViewById(R.id.listViewItems);

        // 🔹 Initialisation des catégories
        categories = new HashMap<>();
        categories.put("Fruits", new ArrayList<>());
        categories.put("Légumes", new ArrayList<>());
        categories.put("Boissons", new ArrayList<>());

        // 🔹 Remplissage initial (optionnel)
        categories.get("Fruits").add("Pomme");
        categories.get("Fruits").add("Banane");
        categories.get("Légumes").add("Carotte");
        categories.get("Légumes").add("Tomate");
        categories.get("Boissons").add("Eau");
        categories.get("Boissons").add("Jus d'orange");

        // 🔹 Adapter du Spinner
        spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new ArrayList<>(categories.keySet()));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategories.setAdapter(spinnerAdapter);

        // 🔹 Catégorie par défaut
        currentCategory = spinnerCategories.getSelectedItem().toString();
        currentList = categories.get(currentCategory);

        // 🔹 Adapter de la ListView
        listAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice,
                currentList);
        listViewItems.setAdapter(listAdapter);

        // 🔹 Changement de catégorie
        spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentCategory = parent.getItemAtPosition(position).toString();
                currentList = categories.get(currentCategory);
                updateListView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // 🔹 Ajout d’un élément
        btnAdd.setOnClickListener(v -> {
            String newItem = editTextItem.getText().toString().trim();
            if (!newItem.isEmpty()) {
                categories.get(currentCategory).add(newItem);
                updateListView();
                editTextItem.setText("");
            } else {
                Toast.makeText(this, "Veuillez entrer un texte", Toast.LENGTH_SHORT).show();
            }
        });

        // 🔹 Suppression des éléments sélectionnés
        btnDelete.setOnClickListener(v -> {
            SparseBooleanArray checked = listViewItems.getCheckedItemPositions();
            ArrayList<String> toRemove = new ArrayList<>();

            for (int i = 0; i < currentList.size(); i++) {
                if (checked.get(i)) {
                    toRemove.add(currentList.get(i));
                }
            }

            currentList.removeAll(toRemove);
            listViewItems.clearChoices();
            updateListView();
        });

        // 🔹 Clic simple sur un élément → afficher un Toast
        listViewItems.setOnItemClickListener((parent, view, position, id) -> {
            String item = currentList.get(position);
            Toast.makeText(this, "Élément : " + item, Toast.LENGTH_SHORT).show();
        });
    }

    // 🔁 Méthode pour rafraîchir la ListView
    private void updateListView() {
        listAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice,
                currentList);
        listViewItems.setAdapter(listAdapter);
    }
}