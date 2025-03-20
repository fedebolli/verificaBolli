package com.example.verificabolli;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText budgetEditText;
    private Button setBudgetButton;
    private Button ciboButton;
    private Button trasportiButton;
    private Button altroButton;
    private Button ricavoButton;
    private EditText descrizioneEditText;
    private EditText importoEditText;
    private Button inviaButton;

    private TextView budgetInizialeTextView;
    private TextView speseCiboTextView;
    private TextView speseTrasportiTextView;
    private TextView speseAltroTextView;
    private TextView totaleRicaviTextView;
    private TextView totaleComplessivoTextView;
    private TextView speseCiboPercentualeTextView;
    private TextView speseTrasportiPercentualeTextView;
    private TextView speseAltroPercentualeTextView;

    private double budgetIniziale = 0.0;
    private double speseCibo = 0.0;
    private double speseTrasporti = 0.0;
    private double speseAltro = 0.0;
    private double totaleRicavi = 0.0;
    private Button lastClickedCategoryButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inizializzazione delle viste
        budgetEditText = findViewById(R.id.budgetEditText);
        setBudgetButton = findViewById(R.id.setBudgetButton);
        ciboButton = findViewById(R.id.ciboButton);
        trasportiButton = findViewById(R.id.trasportiButton);
        altroButton = findViewById(R.id.altroButton);
        ricavoButton = findViewById(R.id.ricavoButton);
        descrizioneEditText = findViewById(R.id.descrizioneEditText);
        importoEditText = findViewById(R.id.importoEditText);
        inviaButton = findViewById(R.id.inviaButton);

        budgetInizialeTextView = findViewById(R.id.budgetInizialeTextView);
        speseCiboTextView = findViewById(R.id.speseCiboTextView);
        speseTrasportiTextView = findViewById(R.id.speseTrasportiTextView);
        speseAltroTextView = findViewById(R.id.speseAltroTextView);
        totaleRicaviTextView = findViewById(R.id.totaleRicaviTextView);
        totaleComplessivoTextView = findViewById(R.id.totaleComplessivoTextView);
        speseCiboPercentualeTextView = findViewById(R.id.speseCiboPercentualeTextView);
        speseTrasportiPercentualeTextView = findViewById(R.id.speseTrasportiPercentualeTextView);
        speseAltroPercentualeTextView = findViewById(R.id.speseAltroPercentualeTextView);

        // Disabilita i pulsanti delle categorie all'avvio
        ciboButton.setEnabled(false);
        trasportiButton.setEnabled(false);
        altroButton.setEnabled(false);
        ricavoButton.setEnabled(false);
        ciboButton.setAlpha(0.5f);
        trasportiButton.setAlpha(0.5f);
        altroButton.setAlpha(0.5f);
        ricavoButton.setAlpha(0.5f);

        // Listener per il pulsante "Imposta Budget"
        setBudgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Blocca l'EditText e il Button
                budgetEditText.setEnabled(false);
                setBudgetButton.setEnabled(false);
                budgetEditText.setAlpha(0.5f);
                setBudgetButton.setAlpha(0.5f);

                // Abilita i pulsanti delle categorie
                ciboButton.setEnabled(true);
                trasportiButton.setEnabled(true);
                altroButton.setEnabled(true);
                ricavoButton.setEnabled(true);
                ciboButton.setAlpha(1f);
                trasportiButton.setAlpha(1f);
                altroButton.setAlpha(1f);
                ricavoButton.setAlpha(1f);

                // Imposta il budget iniziale
                String budgetString = budgetEditText.getText().toString();
                if (!budgetString.isEmpty()) {
                    budgetIniziale = Double.parseDouble(budgetString);
                    budgetInizialeTextView.setText("Budget Iniziale: " + String.format("%.2f", budgetIniziale));
                }
            }
        });

        // Listener per i pulsanti delle categorie
        View.OnClickListener categoriaClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostra gli EditText e il pulsante "Invia"
                descrizioneEditText.setVisibility(View.VISIBLE);
                importoEditText.setVisibility(View.VISIBLE);
                inviaButton.setVisibility(View.VISIBLE);
                lastClickedCategoryButton = (Button) v; // Salva il pulsante cliccato
            }
        };

        ciboButton.setOnClickListener(categoriaClickListener);
        trasportiButton.setOnClickListener(categoriaClickListener);
        altroButton.setOnClickListener(categoriaClickListener);
        ricavoButton.setOnClickListener(categoriaClickListener);

        // Listener per il pulsante "Invia"
        inviaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Recupera i dati
                String descrizione = descrizioneEditText.getText().toString();
                String importoString = importoEditText.getText().toString();

                // Verifica che l'importo sia stato inserito
                if (importoString.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Inserisci un importo", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Converte l'importo in un numero (double)
                double importo = Double.parseDouble(importoString);

                // Aggiorna i totali in base alla categoria
                if (lastClickedCategoryButton != null) {
                    if (lastClickedCategoryButton == ciboButton) {
                        speseCibo += importo;
                        speseCiboTextView.setText("Spese Cibo: " + String.format("%.2f", speseCibo));
                    } else if (lastClickedCategoryButton == trasportiButton) {
                        speseTrasporti += importo;
                        speseTrasportiTextView.setText("Spese Trasporti: " + String.format("%.2f", speseTrasporti));
                    } else if (lastClickedCategoryButton == altroButton) {
                        speseAltro += importo;
                        speseAltroTextView.setText("Spese Altro: " + String.format("%.2f", speseAltro));
                    } else if (lastClickedCategoryButton == ricavoButton) {
                        totaleRicavi += importo;
                        totaleRicaviTextView.setText("Totale Ricavi: " + String.format("%.2f", totaleRicavi));
                    }
                }

                // Calcola e aggiorna il totale complessivo
                double totaleComplessivo = budgetIniziale - speseCibo - speseTrasporti - speseAltro + totaleRicavi;
                totaleComplessivoTextView.setText("Totale Complessivo: " + String.format("%.2f", totaleComplessivo));

                // Calcola e aggiorna le percentuali
                if (budgetIniziale > 0) {
                    double percentualeCibo = (speseCibo / budgetIniziale) * 100;
                    double percentualeTrasporti = (speseTrasporti / budgetIniziale) * 100;
                    double percentualeAltro = (speseAltro / budgetIniziale) * 100;

                    speseCiboPercentualeTextView.setText("(" + String.format("%.1f", percentualeCibo) + "%)");
                    speseTrasportiPercentualeTextView.setText("(" + String.format("%.1f", percentualeTrasporti) + "%)");
                    speseAltroPercentualeTextView.setText("(" + String.format("%.1f", percentualeAltro) + "%)");
                } else {
                    speseCiboPercentualeTextView.setText("(0%)");
                    speseTrasportiPercentualeTextView.setText("(0%)");
                    speseAltroPercentualeTextView.setText("(0%)");
                }

                // Resetta i campi
                descrizioneEditText.setText("");
                importoEditText.setText("");
                descrizioneEditText.setVisibility(View.GONE);
                importoEditText.setVisibility(View.GONE);
                inviaButton.setVisibility(View.GONE);
            }
        });
    }
}