package com.example.one_drop_cruds;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class RegistoAnalisis extends AppCompatActivity {

    ImageView iv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo_analisis);

        iv1 = findViewById(R.id.iv1);


    }
    // VARIABLES
    final int CAPTURA_IMAGEN=1;

    // METODO PARA CAPTURAR IMAGEN
    public void tomarFoto(View v){
        // este intent nos devuelve la imagen que se toma
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,CAPTURA_IMAGEN);
    }


    // METODO PARA EXTRAER LA IMAGEN, ESTE SE EJECUTA CUANDO SE CIERRA LA CAMARA.
    @Override
    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode,data);
        // si devuelve la imagen y esta to do bien entonces se imprime la imagen en el ImageView
        if(requestCode==CAPTURA_IMAGEN && resultCode==RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap bitmap1=(Bitmap)extras.get("data");
            iv1.setImageBitmap(bitmap1);
            // ahora para grabar la imagen en la memoria interna hacemos
            try{
                // la foto va a tener como nombre la fecha y hora
                FileOutputStream fos=openFileOutput(crearNombreArchivoJPG(), Context.MODE_PRIVATE);

                // guardamos la captura de la imagen
                bitmap1.compress(Bitmap.CompressFormat.JPEG,100,fos);
                fos.close();

            }catch (Exception e){}
        }
    } //Termina metodo

    // METODO crearNombreArchivoJPG
    private String crearNombreArchivoJPG(){
        String fecha= new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return fecha+".jpg";
    }


    // METODO PARA IR A LA GALERIA


    public void irAGaleria(View v){
        Intent siguiente = new Intent(this, GaleriaAnalisis.class);
        startActivity(siguiente);
    }

    public void toHome(View v){
        Intent siguiente = new Intent(this, Home.class);
        startActivity(siguiente);
    }


    public void volver(View v){
        finish();
    }
}