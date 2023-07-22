package com.example.colorreality;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//VIEW ENTRAR

public class MainActivity extends AppCompatActivity {

    private EditText nomeE, passwordE;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nomeE = findViewById(R.id.nomeEntrar);
        passwordE = findViewById(R.id.passwordEntrar);

        Button button = findViewById(R.id.bEntrar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //campos obrigatórios
                if (validarCampos()) {
                    String nome = getIntent().getStringExtra("username");

                    Intent intent = new Intent(MainActivity.this, Home.class);
                    intent.putExtra("username", nome);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean validarCampos() {
        String nome = nomeE.getText().toString().trim();
        String password = passwordE.getText().toString().trim();

        // Verificar se os campos obrigatórios estão preenchidos
        if (TextUtils.isEmpty(nome)) {
            Toast.makeText(this, "Campo obrigatório", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Campo obrigatório", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void BotaoRegisto(View v) {
        Intent intent = new Intent(MainActivity.this, Registo.class);
        startActivity(intent);
    }
}