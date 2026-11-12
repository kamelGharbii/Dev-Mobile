package com.example.mastermind;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.graphics.Color;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText etGuess;
    private Button btnCheck, btnReplay;
    private TextView tvResult, tvAttempts;
    private LinearLayout layoutHistory;

    private String secretNumber;
    private int attemptsLeft = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        startNewGame();

        btnCheck.setOnClickListener(v -> checkGuess());
        btnReplay.setOnClickListener(v -> startNewGame());
    }

    private void initViews() {
        etGuess = findViewById(R.id.etGuess);
        btnCheck = findViewById(R.id.btnCheck);
        btnReplay = findViewById(R.id.btnReplay);
        tvResult = findViewById(R.id.tvResult);
        tvAttempts = findViewById(R.id.tvAttempts);
        layoutHistory = findViewById(R.id.layoutHistory);
    }

    private void startNewGame() {
        secretNumber = generateSecretNumber();
        attemptsLeft = 10;
        layoutHistory.removeAllViews();
        tvResult.setText("");
        tvAttempts.setText("Tentatives restantes : " + attemptsLeft);
        btnReplay.setVisibility(View.GONE);
        btnCheck.setEnabled(true);
        etGuess.setText("");
    }

    private String generateSecretNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        while (sb.length() < 4) {
            int digit = random.nextInt(10);
            if (sb.indexOf(String.valueOf(digit)) == -1) { // interdiction de doublons
                sb.append(digit);
            }
        }
        return sb.toString();
    }

    private void checkGuess() {
        String guess = etGuess.getText().toString();

        if (guess.length() != 4) {
            Toast.makeText(this, "Entrez 4 chiffres", Toast.LENGTH_SHORT).show();
            return;
        }

        attemptsLeft--;
        tvAttempts.setText("Tentatives restantes : " + attemptsLeft);

        LinearLayout line = new LinearLayout(this);
        line.setOrientation(LinearLayout.HORIZONTAL);

        for (int i = 0; i < 4; i++) {
            TextView tv = new TextView(this);
            char g = guess.charAt(i);
            char s = secretNumber.charAt(i);

            tv.setText(g + " ");
            tv.setTextSize(20);

            if (g == s) {
                tv.setTextColor(Color.GREEN); // ✅ bien placé
            } else if (secretNumber.contains(String.valueOf(g))) {
                tv.setTextColor(Color.GRAY); // ⚪ existe mais mal placé
            } else {
                tv.setTextColor(Color.RED); // ❌ absent
            }
            line.addView(tv);
        }

        layoutHistory.addView(line);
        etGuess.setText("");

        if (guess.equals(secretNumber)) {
            tvResult.setText("🎉 Bravo ! Vous avez gagné !");
            btnCheck.setEnabled(false);
            btnReplay.setVisibility(View.VISIBLE);
        } else if (attemptsLeft == 0) {
            tvResult.setText("❌ Perdu ! La solution était : " + secretNumber);
            btnCheck.setEnabled(false);
            btnReplay.setVisibility(View.VISIBLE);
        }
    }
}
