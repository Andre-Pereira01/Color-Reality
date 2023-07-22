package com.example.colorreality;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Registo extends AppCompatActivity {
    private Spinner dropdown;
    private EditText nomeR, emailR, passwordR;
    private CheckBox checkBoxR;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registo);

        nomeR = findViewById(R.id.nomeRegisto);
        emailR = findViewById(R.id.emailRegisto);
        passwordR = findViewById(R.id.passwordRegisto);
        checkBoxR = findViewById(R.id.checkBoxR);

        ImageButton BackRegisto = findViewById(R.id.BackRegisto);
        BackRegisto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //dropdown
        dropdown = findViewById(R.id.dropdownR);
        String[] itens = new String[]{"--Selecione--","Protanopia", "Deuteranopia", "Tritanopia"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itens);
        dropdown.setAdapter(adapter);

        Button button = findViewById(R.id.bEntrar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //campos obrigatórios
                if (validarCampos()) {
                    String nome = getIntent().getStringExtra("username");

                    String daltonismoSelecionado = dropdown.getSelectedItem().toString();

                    Intent intent = new Intent(Registo.this, Home.class);
                    intent.putExtra("username", nome);
                    intent.putExtra("tipoDaltonismo", daltonismoSelecionado);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean validarCampos() {
        String nome = nomeR.getText().toString().trim();
        String email = emailR.getText().toString().trim();
        String password = passwordR.getText().toString().trim();
        String daltonismo = dropdown.getSelectedItem().toString().trim();
        boolean aceitarTermos = checkBoxR.isChecked();

        // Verificar se os campos obrigatórios estão preenchidos
        if (TextUtils.isEmpty(nome)) {
            Toast.makeText(this, "Campo obrigatório", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Campo obrigatório", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Campo obrigatório", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (daltonismo.equals("--Selecione--")) {
            Toast.makeText(this, "Selecione um tipo de daltonismo", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!aceitarTermos) {
            Toast.makeText(this, "Você precisa aceitar os termos e condições", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        // Adicione qualquer lógica adicional antes de voltar à atividade anterior
        super.onBackPressed();
    }
}
