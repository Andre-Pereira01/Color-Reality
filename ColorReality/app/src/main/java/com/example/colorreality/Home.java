package com.example.colorreality;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
    }

    public void BotaoMenu(View v) {
        Intent intent = new Intent(Home.this, Menu.class);
        startActivity(intent);
    }

    public void BotaoLogout(View v){
        Intent intent = new Intent(Home.this, MainActivity.class);
        startActivity(intent);
    }
}
