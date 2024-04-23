package com.example.one_drop_cruds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void irARegistro(View view) {
        // Abre la actividad de registro al hacer clic en el botón "Registrarse"
        Intent intent = new Intent(this, UserSignupActivity.class);
        startActivity(intent);
    }

    public void irAIngreso(View view) {
        // Abre la actividad de ingreso al hacer clic en el botón "Ingresar"
        Intent intent = new Intent(this, UserLoginActivity.class);
        startActivity(intent);
    }
}
