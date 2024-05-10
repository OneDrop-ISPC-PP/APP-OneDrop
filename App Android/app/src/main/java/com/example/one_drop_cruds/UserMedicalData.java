package com.example.one_drop_cruds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.one_drop_cruds.entities.DTOmedicalRecord;
import com.example.one_drop_cruds.utils.AdminSQLiteOpenHelper;
import com.example.one_drop_cruds.utils.UserSessionManager;

public class UserMedicalData extends AppCompatActivity {
    UserSessionManager userSessionManager;
    AdminSQLiteOpenHelper admin;
    EditText signup_name, signup_last_name, signup_age, signup_birth , signup_weight, signup_db_type, signup_db_therapy;
    Button signup_medical_data_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_medical_data);

        admin = new AdminSQLiteOpenHelper(this, "bd_one_drop", null, 1); // version es para las futuras modificaciones de la estructura de la bd
        userSessionManager = new UserSessionManager(getApplicationContext());
        userSessionManager.validateLoguedUser(); // SI NO ESTA LOGUEADO, SE REDIRIGE A LOGIN

        signup_name = findViewById(R.id.signup_name);
        signup_last_name = findViewById(R.id.signup_last_name);
        signup_age = findViewById(R.id.signup_age);
        signup_birth = findViewById(R.id.signup_birthdate);
        signup_weight = findViewById(R.id.signup_weight);
        signup_db_type = findViewById(R.id.signup_diabetes_type);
        signup_db_therapy = findViewById(R.id.signup_diabetes_therapy);
        signup_medical_data_button = findViewById(R.id.signupButton);

    }

    public void submitMedicalRecord(View v){
        String name = signup_name.getText().toString();
        String last_name = signup_last_name.getText().toString();
        String birth = signup_birth.getText().toString();
        Integer age = Integer.valueOf(signup_age.getText().toString());
        Double weight = Double.valueOf(signup_weight.getText().toString());
        String db_type = signup_db_type.getText().toString();
        String db_therapy = signup_db_therapy.getText().toString();
        String username = userSessionManager.getLoguedUsername();


        if(name.equals("") || last_name.equals("")|| birth.equals("")|| age <= 0 || weight <= 0|| db_type.equals("") || db_therapy.equals("")){
            Toast.makeText(this, "Todos los campos son obligatorios. Edad y peso deben ser mayor a 0", Toast.LENGTH_SHORT).show();
        } else {
            DTOmedicalRecord inputDtoMedical = new DTOmedicalRecord(username, name, last_name, age, birth, weight, db_type, db_therapy);
            Boolean insertResult = admin.createMedicalRecord(inputDtoMedical);
            if (insertResult){
                Toast.makeText(this, "Datos guardados exitosamente", Toast.LENGTH_SHORT).show();
                Intent weightAct = new Intent(this, Home.class);
                startActivity(weightAct);
                // toHome(View v);
            }else{
                Toast.makeText(this, "Error guardando datos ficha medica", Toast.LENGTH_SHORT).show();
            }
        }

    }
    public void toHome(View v){
        Intent weight = new Intent(this, Home.class);
        startActivity(weight);
    }
}