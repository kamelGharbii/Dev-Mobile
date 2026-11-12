package com.example.conversioncf;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText inputTemp;
    private ImageButton btnToF, btnToC;
    private TextView resultText;
    private DecimalFormat df = new DecimalFormat("#.##"); // 2 décimales

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputTemp = findViewById(R.id.inputTemp);
        btnToF = findViewById(R.id.btnToF);
        btnToC = findViewById(R.id.btnToC);
        resultText = findViewById(R.id.resultText);

        btnToF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { convertToFahrenheit(); }
        });

        btnToC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { convertToCelsius(); }
        });
    }

    private void convertToFahrenheit() {
        String s = inputTemp.getText().toString().trim();
        if (!isValidNumber(s)) return;
        double c = Double.parseDouble(s);
        double f = (c * 9.0 / 5.0) + 32.0; // formule °F = (°C × 9/5) + 32
        resultText.setText("Résultat : " + df.format(f) + " °F");
        hideKeyboard();
    }

    private void convertToCelsius() {
        String s = inputTemp.getText().toString().trim();
        if (!isValidNumber(s)) return;
        double f = Double.parseDouble(s);
        double c = (f - 32.0) * 5.0 / 9.0; // formule °C = (°F − 32) × 5/9
        resultText.setText("Résultat : " + df.format(c) + " °C");
        hideKeyboard();
    }

    private boolean isValidNumber(String s) {
        if (s.isEmpty()) {
            Toast.makeText(this, "Veuillez entrer une température", Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Format invalide. Entrez un nombre (ex: 36.5)", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void hideKeyboard() {
        // cache le clavier pour une meilleure UX
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
