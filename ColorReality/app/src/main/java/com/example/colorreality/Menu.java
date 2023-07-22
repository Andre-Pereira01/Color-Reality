package com.example.colorreality;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        /*
        Button button = findViewById(R.id.bMenu);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BotaoPerfil();
            }
        });*/

        ImageButton imageButton = findViewById(R.id.BackRegisto);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void BotaoPerfil(View v) {
        Intent intent = new Intent(Menu.this, Perfil.class);
        startActivity(intent);
    }

    public void BotaoUpload(View v){
        Intent intent = new Intent(Menu.this, Upload.class);
        startActivity(intent);
        finish();
    }

    public void BotaoFoto(View v){
        Intent intent = new Intent(Menu.this, Fotografia.class);
        startActivity(intent);
    }

    public void BotaoIdioma(View v){
        Intent intent = new Intent(Menu.this, Idioma.class);
        startActivity(intent);
    }
}
