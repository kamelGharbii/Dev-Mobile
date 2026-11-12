package com.example.commande;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Prix des menus
    private final int PRIX_PIZZA = 10;
    private final int PRIX_BURGER = 8;
    private final int PRIX_SALADE = 6;

    // Prix des suppléments
    private final int PRIX_FROMAGE = 2;
    private final int PRIX_SAUCE = 1;
    private final int PRIX_FRITES = 3;
    private final int PRIX_BOISSON = 2;

    // Variables pour les vues
    private RadioGroup radioGroupMenu;
    private CheckBox checkFromage, checkSauce, checkFrites, checkBoisson;
    private TextView textPrixTotal;
    private Button buttonCommander;

    // Variables pour stocker les choix
    private String menuChoisi = "";
    private int prixMenu = 0;
    private int prixSupplement = 0;
    private int prixTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialiser les vues
        initialiserVues();

        // Configurer les écouteurs
        configurerEcouteurs();
    }

    private void initialiserVues() {
        radioGroupMenu = findViewById(R.id.radioGroupMenu);
        checkFromage = findViewById(R.id.checkFromage);
        checkSauce = findViewById(R.id.checkSauce);
        checkFrites = findViewById(R.id.checkFrites);
        checkBoisson = findViewById(R.id.checkBoisson);
        textPrixTotal = findViewById(R.id.textPrixTotal);
        buttonCommander = findViewById(R.id.buttonCommander);
    }

    private void configurerEcouteurs() {
        // Écouteur pour le RadioGroup (menu principal)
        radioGroupMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                gererChoixMenu(checkedId);
                calculerPrixTotal();
            }
        });

        // Écouteurs pour les CheckBox (suppléments)
        View.OnClickListener supplementListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculerPrixTotal();
            }
        };

        checkFromage.setOnClickListener(supplementListener);
        checkSauce.setOnClickListener(supplementListener);
        checkFrites.setOnClickListener(supplementListener);
        checkBoisson.setOnClickListener(supplementListener);

        // Écouteur pour le bouton Commander
        buttonCommander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commander();
            }
        });
    }

    private void gererChoixMenu(int checkedId) {
        if (checkedId == R.id.radioPizza) {
            menuChoisi = "Pizza";
            prixMenu = PRIX_PIZZA;
        } else if (checkedId == R.id.radioBurger) {
            menuChoisi = "Burger";
            prixMenu = PRIX_BURGER;
        } else if (checkedId == R.id.radioSalade) {
            menuChoisi = "Salade";
            prixMenu = PRIX_SALADE;
        } else {
            menuChoisi = "";
            prixMenu = 0;
        }
    }

    private void calculerPrixTotal() {
        // Réinitialiser le prix des suppléments
        prixSupplement = 0;

        // Ajouter le prix de chaque supplément coché
        if (checkFromage.isChecked()) {
            prixSupplement += PRIX_FROMAGE;
        }
        if (checkSauce.isChecked()) {
            prixSupplement += PRIX_SAUCE;
        }
        if (checkFrites.isChecked()) {
            prixSupplement += PRIX_FRITES;
        }
        if (checkBoisson.isChecked()) {
            prixSupplement += PRIX_BOISSON;
        }

        // Calculer le prix total
        prixTotal = prixMenu + prixSupplement;

        // Mettre à jour l'affichage
        textPrixTotal.setText("Prix Total: " + prixTotal + " DT");
    }

    private void commander() {
        // Vérifier qu'un menu a été choisi
        if (menuChoisi.isEmpty()) {
            Toast.makeText(this, "Veuillez choisir un menu principal !", Toast.LENGTH_LONG).show();
            return;
        }

        // Construire le récapitulatif
        StringBuilder recap = new StringBuilder();
        recap.append("🎉 Commande confirmée !\n\n");
        recap.append("📋 Menu principal: ").append(menuChoisi).append("\n");
        recap.append("💰 Prix menu: ").append(prixMenu).append(" DT\n\n");

        // Ajouter les suppléments
        recap.append("➕ Suppléments:\n");
        boolean hasSupplements = false;

        if (checkFromage.isChecked()) {
            recap.append("• Fromage (+").append(PRIX_FROMAGE).append(" DT)\n");
            hasSupplements = true;
        }
        if (checkSauce.isChecked()) {
            recap.append("• Sauce spéciale (+").append(PRIX_SAUCE).append(" DT)\n");
            hasSupplements = true;
        }
        if (checkFrites.isChecked()) {
            recap.append("• Frites (+").append(PRIX_FRITES).append(" DT)\n");
            hasSupplements = true;
        }
        if (checkBoisson.isChecked()) {
            recap.append("• Boisson (+").append(PRIX_BOISSON).append(" DT)\n");
            hasSupplements = true;
        }

        if (!hasSupplements) {
            recap.append("Aucun supplément\n");
        }

        recap.append("\n💵 Prix total: ").append(prixTotal).append(" DT");
        recap.append("\n\n✅ Merci pour votre commande !");

        // Afficher le récapitulatif
        Toast.makeText(this, recap.toString(), Toast.LENGTH_LONG).show();
    }
}