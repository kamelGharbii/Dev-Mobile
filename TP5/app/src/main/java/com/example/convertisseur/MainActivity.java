package com.example.convertisseur;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText tndInput;
    private TextView eurOutput;

    // Taux de conversion (1 TND = 0.28 EUR environ)
    private final double CONVERSION_RATE = 0.28;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialiser les vues
        initializeViews();

        // Configurer les écouteurs de clic
        setupNumberButtons();
        setupActionButtons();

        // Conversion initiale
        convertCurrency();
    }

    private void initializeViews() {
        tndInput = findViewById(R.id.tndInput);
        eurOutput = findViewById(R.id.eurOutput);
    }

    private void setupNumberButtons() {
        // Boutons numériques 0-9
        int[] numberButtonIds = {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
                R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9
        };

        for (int buttonId : numberButtonIds) {
            Button button = findViewById(buttonId);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String currentText = tndInput.getText().toString();
                    String newDigit = ((Button) v).getText().toString();

                    // Si c'est "0" et que le champ est vide ou "0", on ne fait rien
                    if (newDigit.equals("0") && (currentText.isEmpty() || currentText.equals("0"))) {
                        return;
                    }

                    // Si le champ est "0", on le remplace par le nouveau chiffre
                    if (currentText.equals("0")) {
                        tndInput.setText(newDigit);
                    } else {
                        tndInput.append(newDigit);
                    }

                    convertCurrency();
                }
            });
        }
    }

    private void setupActionButtons() {
        // Bouton point décimal
        Button buttonDot = findViewById(R.id.buttonDot);
        buttonDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentText = tndInput.getText().toString();

                // Vérifier si il y a déjà un point décimal
                if (!currentText.contains(".")) {
                    if (currentText.isEmpty()) {
                        tndInput.setText("0.");
                    } else {
                        tndInput.append(".");
                    }
                }
            }
        });

        // Bouton C (effacer)
        Button buttonC = findViewById(R.id.buttonC);
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tndInput.setText("0");
                convertCurrency();
            }
        });

        // Écouteur pour les changements de texte manuels
        tndInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    convertCurrency();
                }
            }
        });
    }

    private void convertCurrency() {
        String tndText = tndInput.getText().toString();

        if (TextUtils.isEmpty(tndText) || tndText.equals(".")) {
            eurOutput.setText("0.0");
            return;
        }

        try {
            double tndAmount = Double.parseDouble(tndText);
            double eurAmount = tndAmount * CONVERSION_RATE;

            // Formater le résultat avec 2 décimales
            String formattedResult = String.format("%.2f", eurAmount);
            eurOutput.setText(formattedResult);

        } catch (NumberFormatException e) {
            eurOutput.setText("0.0");
        }
    }
}