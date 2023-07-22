package com.example.colorreality;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Perfil extends AppCompatActivity {
    private TextView emailEdit, passwordEdit;
    private Spinner dropdown;
    private Button b_atualizar;
    private TextView utilizador;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);

        ImageButton imageButton = findViewById(R.id.BackRegisto);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        emailEdit = findViewById(R.id.bEditaEmail);
        passwordEdit = findViewById(R.id.bEditaPass);
        dropdown = findViewById(R.id.dropdownPerfil);
        b_atualizar = findViewById(R.id.bAtualizar);
        utilizador = findViewById(R.id.username);

        //obter nome de utilizador
        String username = getIntent().getStringExtra("username");

        //exibir
        utilizador.setText(username);

        //configura dropdown
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getOpcoesDropdown());
        dropdown.setAdapter(adapter);

        b_atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //obter os valores do campo
                String novoEmail = emailEdit.getText().toString();
                String novaSenha = passwordEdit.getText().toString();
                String opDrop = dropdown.getSelectedItem().toString();

                //atualização
                atualizarPerfil(novoEmail, novaSenha, opDrop);

                Intent intent = new Intent(Perfil.this, Menu.class);
                startActivity(intent);
            }
        });
    }

    //obter as opções da dropdown
    private String[] getOpcoesDropdown(){
        return new String[]{"Protanopia", "Deuteranopia", "Tritanopia"};
    }

    //atualizar perfil com os novos valores
    private void atualizarPerfil(String novoEmail, String novaSenha, String opDrop){
        Toast.makeText(this, "Perfil atualizado com sucesso!", Toast.LENGTH_SHORT).show();
    }
}
