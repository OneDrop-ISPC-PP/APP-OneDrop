package com.example.one_drop_cruds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileInputStream;

public class GaleriaAnalisis extends AppCompatActivity {
    String []archivos;
    RecyclerView rv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria_analisis);

        archivos=fileList();
        rv1=findViewById(R.id.rView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv1.setLayoutManager(linearLayoutManager);
        rv1.setAdapter(new AdaptadorFotos());

    }
    public void toHome(View v){
        Intent home = new Intent(this, RegistoAnalisis.class);
        startActivity(home);
    }
    private class AdaptadorFotos extends RecyclerView.Adapter<AdaptadorFotos.AdaptadorFotosHolder>{

        @NonNull
        @Override
        public AdaptadorFotosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AdaptadorFotosHolder(getLayoutInflater().inflate(R.layout.layout,parent, false));

        }

        @Override
        public void onBindViewHolder(@NonNull AdaptadorFotosHolder holder, int position) {
            holder.imprimir(position);
        }

        @Override
        public int getItemCount() {
            return archivos.length;
        }

        class AdaptadorFotosHolder extends RecyclerView.ViewHolder{
            ImageView ivFoto;
            TextView ivTitulo;
            public AdaptadorFotosHolder(@NonNull View itemView){
                super(itemView);
                ivFoto = itemView.findViewById(R.id.ivFoto);
                ivTitulo = itemView.findViewById(R.id.ivTitulo);

            }

            public void imprimir(int position){
                ivTitulo.setText("Nombre archivo:"+archivos[position]);

                try{
                    FileInputStream fileInputStream = openFileInput(archivos[position]);
                    Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
                    ivFoto.setImageBitmap(bitmap);
                    fileInputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }//

    }

}