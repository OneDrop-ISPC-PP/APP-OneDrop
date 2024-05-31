package com.example.one_drop_cruds;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.one_drop_cruds.utils.ToastHelper;

public class RestorePassword extends AppCompatActivity implements View.OnClickListener {

    EditText restore_password_email;
    Button restore_password_btn;
    ToastHelper toastHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_password);


        restore_password_email = findViewById(R.id.restore_password_email);
        restore_password_btn = findViewById(R.id.restore_password_btn);
/*
        restore_password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restorePasswordRequest();
            }
        });
*/

        toastHelper= new ToastHelper(RestorePassword.this);

    }

    public void restorePasswordRequest(View v){
        toastHelper.showLong("SE ENVIA SOLICITUD PARA RESTAURAR PASSWORD");
    }

    public void toLogin(View v){
        Intent i = new Intent(this, UserLoginActivity.class);
        startActivity(i);
    }
    public void toSingup(View v){
        Intent i = new Intent(this, UserSignupActivity.class);
        startActivity(i);
    }

    @Override
    public void onClick(View v) {

    }
}