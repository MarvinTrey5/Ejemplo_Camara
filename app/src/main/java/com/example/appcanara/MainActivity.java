package com.example.appcanara;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    // Se definen los objetos.
    Button Sonrie;
    ImageView Visualizar_Foto;
    private static final int REQUEST_CODE_CAMARA = 200;
    private static final int REQUEST_CODE_CAPTURAR_iMAGEN = 300;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se referencian los objetos con sus id.
        Sonrie = findViewById(R.id.Tomar_Foto);
        Visualizar_Foto = findViewById(R.id.foto);
        Sonrie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Método de los permisos e incluye el de tomar la foto
                // que se llama Whynot();
                Procesar__Foto();
            }
        });
    }
    public void Procesar__Foto(){
        // verificar los permisos
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            // Tomar foto
            Whynot();
        }else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.CAMERA
            }, REQUEST_CODE_CAMARA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_CAMARA){
            if(permissions.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Whynot();
            }else {
                Toast.makeText(MainActivity.this, "Acceso Denegado", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    // Método para la tomar la foto.
    public void Whynot() {
        Intent picture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (picture.resolveActivity(getPackageManager()) != null){
            startActivityForResult(picture, REQUEST_CODE_CAPTURAR_iMAGEN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Si la condición se cumnple al tomar la captura de la imagen y se confirma el resultado.
        if (requestCode == REQUEST_CODE_CAPTURAR_iMAGEN && resultCode == RESULT_OK) {
            // Con la función Bundle buscaremos si exite algun tipo de dato
            Bundle Buscar_Datos= data.getExtras();
            // Utilizamos Bitmap para cargar la imagen desde cualquier archivo.
            Bitmap imagen = (Bitmap) Buscar_Datos.get("data");
            // Mostramos la imgen en el ImagenView.
            Visualizar_Foto.setImageBitmap(imagen);
        }
    }
}