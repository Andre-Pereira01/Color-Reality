package com.example.colorreality;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;

import androidx.core.content.ContextCompat;
import android.content.ContentResolver;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.MotionEvent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

public class Upload extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    public static final int REQUEST_CODE = 101;
    private Button bImagem, bFiltro;
    public ImageView imagemView;
    public TextView textView;
    private Bitmap bitmap;
    private String daltonismoSelecionado;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload);

        ImageButton imageButton = findViewById(R.id.BackRegisto);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Recupera o valor selecionado do tipo de daltonismo do Intent
        Intent intent = getIntent();
        daltonismoSelecionado = intent.getStringExtra("tipoDaltonismo");

        // Verifica e solicita a permissão de leitura externa, se necessário
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PICK_IMAGE_REQUEST);
        }

        bFiltro = findViewById(R.id.bFiltro);
        textView = findViewById(R.id.textoCor);
        imagemView = findViewById(R.id.imagem);

        bImagem = findViewById(R.id.b_upload);
        bImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        //ANALIZAR A COR
        Button btnCor = findViewById(R.id.corUpload);
        btnCor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagemView.setDrawingCacheEnabled(true);
                imagemView.buildDrawingCache(true);

                imagemView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                            bitmap = imagemView.getDrawingCache();
                            int pixel = bitmap.getPixel((int) event.getX(), (int) event.getY());

                            int R = Color.red(pixel);
                            int G = Color.green(pixel);
                            int B = Color.blue(pixel);
                            String hex = String.format("#%02X%02X%02X", R, G, B);

                            if (hex.equals("#000000")) {
                                textView.setText(("Preto"));
                            } else if (hex.equals("#808080")) {
                                textView.setText("Cinzento");
                            } else if (hex.equals("#FFFFFF")) {
                                textView.setText("Branco");
                            } else if (hex.equals("#FF0000")) {
                                textView.setText("Vermelho");
                            } else if (hex.equals("#800080")) {
                                textView.setText("Roxo");
                            } else if (hex.equals("#008000")) {
                                textView.setText("Verde");
                            } else if (hex.equals("#FFFF00")) {
                                textView.setText("Amarelo");
                            } else if (hex.equals("#0000FF")) {
                                textView.setText("Azul");
                            } else if (hex.equals("#A52A2A")) {
                                textView.setText("Castanho");
                            } else if (hex.equals("#FFA500")) {
                                textView.setText("Laranja");
                            } else if (hex.equals("#FFC0CB")) {
                                textView.setText("Rosa");
                            } else {
                                textView.setText("R(" + R + ")" + "G(" + G + ")" + "B(" + B + ")" + "(" + hex + ")");
                                ;
                            }
                        }
                        return (true);
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // O URI da imagem selecionada da galeria
            imagemView.setImageURI(data.getData());
        }
    }

/*
        bFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imagemUri != null) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagemUri);

                        // Aplicação do filtro com base no tipo de daltonismo selecionado
                        Bitmap filteredBitmap = applyColorFilter(bitmap, daltonismoSelecionado);
                        if (filteredBitmap != null) {
                            imagem.setImageBitmap(filteredBitmap);
                            Toast.makeText(Upload.this, "Sucesso!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Upload.this, "Falha ao aplicar o filtro", Toast.LENGTH_SHORT).show();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(Upload.this, "Arquivo não encontrado", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(Upload.this, "Erro ao carregar a imagem", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Upload.this, "Nenhuma fotografia selecionada", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imagemUri = data.getData();

            if (imagemUri != null){
                imagem.setImageURI(imagemUri);
            }
        }
    }

    private Bitmap applyColorFilter(Bitmap bitmap, String tipoDaltonismo) {
        // Verifica qual tipo de daltonismo foi selecionado e aplica o filtro apropriado
        switch (tipoDaltonismo) {
            case "Protanopia":
                return aplicaProtanopia(bitmap);
            case "Deuteranopia":
                return aplicaDeuteranopia(bitmap);
            case "Tritanopia":
                return aplicaTritanopia(bitmap);
            default:
                return bitmap;
        }
    }

    private Bitmap aplicaProtanopia(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Bitmap filteredBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixel = bitmap.getPixel(x, y);
                int red = Color.red(pixel);
                int green = Color.green(pixel);
                int blue = Color.blue(pixel);

                // Ajusta os canais de cor para simular protanopia
                int newRed = (int) (green * 0.567 + blue * 0.433);
                int newGreen = (int) (green * 0.558 + blue * 0.442);
                int newBlue = (int) (green * 0.242 + blue * 0.758);

                int newPixel = Color.rgb(newRed, newGreen, newBlue);
                filteredBitmap.setPixel(x, y, newPixel);
            }
        }

        return filteredBitmap;
    }

    private Bitmap aplicaDeuteranopia(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Bitmap filteredBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixel = bitmap.getPixel(x, y);
                int red = Color.red(pixel);
                int green = Color.green(pixel);
                int blue = Color.blue(pixel);

                // Ajuste dos canais de cor para simular deuteranopia
                int newRed = (int) (red * 0.625 + blue * 0.375);
                int newGreen = (int) (red * 0.7 + blue * 0.3);
                int newBlue = (int) (red * 0 + blue * 0.7);

                int newPixel = Color.rgb(newRed, newGreen, newBlue);
                filteredBitmap.setPixel(x, y, newPixel);
            }
        }

        return filteredBitmap;
    }

    private Bitmap aplicaTritanopia(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Bitmap filteredBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixel = bitmap.getPixel(x, y);
                int red = Color.red(pixel);
                int green = Color.green(pixel);
                int blue = Color.blue(pixel);

                // Ajuste dos canais de cor para simular tritanopia
                int newRed = (int) (red * 0.95 + green * 0.05);
                int newGreen = (int) (red * 0 + green * 0.433 + blue * 0.567);
                int newBlue = (int) (green * 0.567 + blue * 0.433);

                int newPixel = Color.rgb(newRed, newGreen, newBlue);
                filteredBitmap.setPixel(x, y, newPixel);
            }
        }
        return filteredBitmap;
    }
    */
}