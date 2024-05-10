package com.example.one_drop_cruds;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.one_drop_cruds.entities.DTOmedicalRecord;
import com.example.one_drop_cruds.utils.AdminSQLiteOpenHelper;
import com.example.one_drop_cruds.utils.UserSessionManager;

public class ProfileActivity extends AppCompatActivity {
    UserSessionManager userSessionManager;
    AdminSQLiteOpenHelper admin;
    EditText signup_name, signup_last_name, signup_age, signup_birth, signup_weight, signup_db_type, signup_db_therapy;
    Button edit_medical_data_button, selectImageButton;
    ImageView profileImage;
    private static final int PICK_IMAGE = 100;
    private Uri selectedImageUri = null; // Para almacenar la URI de la imagen seleccionada

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        admin = new AdminSQLiteOpenHelper(this, "bd_one_drop", null, 1);
        userSessionManager = new UserSessionManager(getApplicationContext());
        userSessionManager.validateLoguedUser();

        signup_name = findViewById(R.id.signup_name);
        signup_last_name = findViewById(R.id.signup_last_name);
        signup_age = findViewById(R.id.signup_age);
        signup_birth = findViewById(R.id.signup_birth);
        signup_weight = findViewById(R.id.signup_weight);
        signup_db_type = findViewById(R.id.signup_db_type);
        signup_db_therapy = findViewById(R.id.signup_db_therapy);



        setTextsForm();


    }





    public void setTextsForm() {
        DTOmedicalRecord medicalRecord = admin.getMedicalRecord(userSessionManager.getLoguedUsername());
        signup_name.setText(medicalRecord.getName());
        signup_last_name.setText(medicalRecord.getLast_name());
        signup_age.setText(String.valueOf(medicalRecord.getAge()));
        signup_birth.setText(medicalRecord.getBirth());
        signup_weight.setText(String.valueOf(medicalRecord.getWeight()));
        signup_db_type.setText(medicalRecord.getDbType());
        signup_db_therapy.setText(medicalRecord.getDbTherapy());
    }

    public void updateMedicalRecord(View v) {
        String name = signup_name.getText().toString();
        String last_name = signup_last_name.getText().toString();
        int age = Integer.parseInt(signup_age.getText().toString());
        String birth = signup_birth.getText().toString();
        double weight = Double.parseDouble(signup_weight.getText().toString());
        String db_type = signup_db_type.getText().toString();
        String username = userSessionManager.getLoguedUsername();
        String db_therapy = signup_db_therapy.getText().toString();

        if (name.isEmpty() || last_name.isEmpty() || birth.isEmpty() || age <= 0 || weight <= 0 || db_type.isEmpty() || db_therapy.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios. Edad y peso deben ser mayor a 0", Toast.LENGTH_SHORT).show();
        } else {
            DTOmedicalRecord updateDtoMedical = new DTOmedicalRecord(username, name, last_name, age, birth, weight, db_type, db_therapy);

            // Luego de actualizar los datos médicos, verifica si hay una imagen seleccionada
            if (selectedImageUri != null) {
                // Convierte la URI a un String
                String imageUriString = selectedImageUri.toString();
                // Guarda la URI de la imagen en la base de datos local
                if (admin.updateImageUri(username, imageUriString)) {
                    Toast.makeText(this, "URI de imagen guardada con éxito", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Error al guardar la URI de imagen", Toast.LENGTH_SHORT).show();
                }
            }

            boolean insertResult = admin.updateMedicalRecord(updateDtoMedical);
            if (insertResult) {
                Toast.makeText(this, "Datos modificados exitosamente", Toast.LENGTH_SHORT).show();
                Intent homeAct = new Intent(this, Home.class);
                startActivity(homeAct);
            } else {
                Toast.makeText(this, "Error actualizando datos ficha medica", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void toHome(View v) {
        Intent home = new Intent(this, Home.class);
        startActivity(home);
    }
}