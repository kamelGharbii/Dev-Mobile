package com.example.appmoyenne;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private EditText etDS, etTP, etExam;
    private Button btnCalculer;
    private TextView tvResultat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des vues
        initViews();

        // Configuration du click listener
        btnCalculer.setOnClickListener(v -> calculerMoyenne());
    }

    private void initViews() {
        etDS = findViewById(R.id.etDS);
        etTP = findViewById(R.id.etTP);
        etExam = findViewById(R.id.etExam);
        btnCalculer = findViewById(R.id.btnCalculer);
        tvResultat = findViewById(R.id.tvResultat);
    }

    private void calculerMoyenne() {
        // Récupérer les valeurs
        String dsText = etDS.getText().toString().trim();
        String tpText = etTP.getText().toString().trim();
        String examText = etExam.getText().toString().trim();

        // Vérifier si tous les champs sont remplis
        if (dsText.isEmpty() || tpText.isEmpty() || examText.isEmpty()) {
            tvResultat.setText("Veuillez remplir tous les champs");
            tvResultat.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark));
            return;
        }

        try {
            // Convertir en nombres
            double ds = Double.parseDouble(dsText);
            double tp = Double.parseDouble(tpText);
            double exam = Double.parseDouble(examText);

            // Valider les notes (entre 0 et 20)
            if (ds < 0 || ds > 20 || tp < 0 || tp > 20 || exam < 0 || exam > 20) {
                tvResultat.setText("Erreur: Les notes doivent être entre 0 et 20");
                tvResultat.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark));
                return;
            }

            // Calculer la moyenne
            double moyenne = (ds * 0.3) + (tp * 0.2) + (exam * 0.5);

            // Afficher le résultat
            String resultatText = String.format("Moyenne: %.2f", moyenne);
            tvResultat.setText(resultatText);

            if (moyenne >= 10) {
                tvResultat.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_dark));
            } else {
                tvResultat.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark));
            }

        } catch (NumberFormatException e) {
            tvResultat.setText("Erreur: Veuillez saisir des nombres valides");
            tvResultat.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark));
        }
    }
}