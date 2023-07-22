package com.example.colorreality;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

//PARA TIRAR FOTOGRAFIAS
public class Fotografia extends AppCompatActivity {

    public static final int REQUEST_CODE = 101;
    public static final int REQUEST_CODE2 = 102;
    public static final int REQUEST_CODE3 = 103;
    String currentPhotoPath;
    private Button bFiltro, bFoto;
    ImageView imagem;
    Button cor;

    String CurrentPhotoPath;
    public TextView textView;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fotografia);

        ImageButton imageButton = findViewById(R.id.BackRegisto);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        imagem = findViewById(R.id.imageViewFoto);
        bFiltro = findViewById(R.id.filtroFoto);

        bFoto = findViewById(R.id.fotografia);
        bFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCamaraPermissions();
            }
        });

        //ANALIZAR A COR
        cor = findViewById(R.id.corFoto);
        cor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagem.setDrawingCacheEnabled(true);
                imagem.buildDrawingCache(true);

                imagem.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                            bitmap = imagem.getDrawingCache();
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
                                textView.setText("Cor de laranja");
                            } else if (hex.equals("#FFC0CB")) {
                                textView.setText("Cor de rosa");
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

        private void askCamaraPermissions() {
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA}, REQUEST_CODE);
            }

            else {
                //openCamera();
                dispatchTakePictureIntent();
            }
        }

       @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_CODE2){
                /** Bitmap image = (Bitmap) data.getExtras().get("data");
                 imageView.setImageBitmap(image); */
                if(resultCode == Activity.RESULT_OK) {
                    File f = new File(currentPhotoPath);
                    imagem.setImageURI(Uri.fromFile(f));
                    Log.d("tag","Absolute Url of Image is " + Uri.fromFile(f));

                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri contentUri = Uri.fromFile(f);
                    mediaScanIntent.setData(contentUri);
                    this.sendBroadcast(mediaScanIntent);
                }
            }

            if (requestCode == REQUEST_CODE3){
                if(resultCode == Activity.RESULT_OK) {
                    Uri uri = data.getData();
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    String imageFileName = "JPEG_" + timeStamp + "." + getFileExt(uri);
                    Log.d("tag","onActivityResult: Gallery Image Uri: " + imageFileName);
                    imagem.setImageURI(uri);
                }
            }
        }

        private String getFileExt(Uri uri) {
            ContentResolver c = getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            return mime.getExtensionFromMimeType(c.getType(uri));

        }

        private File createImageFile() throws IOException {
            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            //File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );

            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = image.getAbsolutePath();
            return image;
        }


        static final int REQUEST_TAKE_PHOTO = 1;

        private void dispatchTakePictureIntent() {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.example.android.fileprovider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent,REQUEST_CODE2);
                }
            }
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == REQUEST_CODE) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //openCamera();
                    dispatchTakePictureIntent();

                } else {
                    Toast.makeText(this, "Necessita de autorizar a utilização da câmara para usar esta funcionalidade.", Toast.LENGTH_LONG).show();
                }
            }

    }
}
