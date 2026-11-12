package com.example.tp7_listview;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import java.util.ArrayList;
import android.util.SparseBooleanArray;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    EditText editTextItem;
    Button btnAdd, btnDelete;
    ArrayAdapter<String> adapter;
    ArrayList<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        editTextItem = findViewById(R.id.editTextItem);
        btnAdd = findViewById(R.id.btnAdd);
        btnDelete = findViewById(R.id.btnDelete);

        // 🔹 Liste initiale
        items = new ArrayList<>();
        items.add("Élément 1");
        items.add("Élément 2");
        items.add("Élément 3");

        // 🔹 Création de l'adapter
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, items);
        listView.setAdapter(adapter);

        // 🔹 Ajouter un élément
        btnAdd.setOnClickListener(v -> {
            String newItem = editTextItem.getText().toString().trim();
            if (!newItem.isEmpty()) {
                items.add(newItem);
                adapter.notifyDataSetChanged();
                editTextItem.setText("");
            } else {
                Toast.makeText(this, "Entrez un texte !", Toast.LENGTH_SHORT).show();
            }
        });

        // 🔹 Modifier un élément au clic simple
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String oldItem = items.get(position);
            EditText input = new EditText(this);
            input.setText(oldItem);

            new android.app.AlertDialog.Builder(this)
                    .setTitle("Modifier l’élément")
                    .setView(input)
                    .setPositiveButton("OK", (dialog, which) -> {
                        String newValue = input.getText().toString().trim();
                        if (!newValue.isEmpty()) {
                            items.set(position, newValue);
                            adapter.notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("Annuler", null)
                    .show();
        });

        // 🔹 Supprimer les éléments sélectionnés
        btnDelete.setOnClickListener(v -> {
            SparseBooleanArray checked = listView.getCheckedItemPositions();
            ArrayList<String> itemsToRemove = new ArrayList<>();

            for (int i = 0; i < items.size(); i++) {
                if (checked.get(i)) {
                    itemsToRemove.add(items.get(i));
                }
            }

            items.removeAll(itemsToRemove);
            listView.clearChoices();
            adapter.notifyDataSetChanged();
        });
    }
}