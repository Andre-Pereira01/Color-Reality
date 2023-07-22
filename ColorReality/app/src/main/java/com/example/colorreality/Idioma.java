package com.example.colorreality;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class Idioma extends AppCompatActivity {
    private Spinner dropdown;
    private String selecionar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.idioma);

        ImageButton imageButton = findViewById(R.id.BackRegisto);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //dropdown
        dropdown = findViewById(R.id.dropdownIdioma);
        String[] itens = new String[]{"Português", "English"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itens);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selecionar = parent.getItemAtPosition(position).toString();
                //falta fazer: atualizar a configuração do idioma da aplicação
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //nada a ser feito quando nada for selecionado
            }
        });

        //botão guardar
        Button guardar = findViewById(R.id.bIdioma);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//vai voltar para onde estava antes
            }
        });
    }
}
