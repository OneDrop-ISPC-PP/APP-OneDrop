package com.example.one_drop_cruds;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ContactoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);
    }
    /*
    // SACO EL EVENTO, PORQUE ES MAS RAPIDO ASI, ADEMAS QUE CONTINUA CON LA MISMA FORMA DEL RESTO DE LA APP
    // POR OTRO LADO, cree un layout que envuelve icono y texto, y AGREGO EL ON CLICK AL LAYOUT COMPLETO
    Button backButton = findViewById(R.id.backButton);
    backButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Agrega aquí el código para regresar a Home
            Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);
        }
    });

     */
    public void toHome(View v){
        Intent home = new Intent(this, Home.class);
        startActivity(home);
    }
}
