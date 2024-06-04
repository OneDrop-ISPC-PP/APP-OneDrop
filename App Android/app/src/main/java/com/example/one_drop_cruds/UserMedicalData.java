package com.example.one_drop_cruds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.one_drop_cruds.entities.dtos.medicalRecord.AddNewMedicalRecordDto;
import com.example.one_drop_cruds.entities.dtos.medicalRecord.MedicalRecordReadDto;
import com.example.one_drop_cruds.entities.user.LoguedUserDetails;
import com.example.one_drop_cruds.entities.user.enums.Terapia_insulina;
import com.example.one_drop_cruds.entities.user.enums.Terapia_pastillas;
import com.example.one_drop_cruds.entities.user.enums.Tipo_diabetes;
import com.example.one_drop_cruds.entities.user.enums.Tipo_glucometro;
import com.example.one_drop_cruds.entities.user.enums.Tipo_sensor;
import com.example.one_drop_cruds.request.AuthRequests;
import com.example.one_drop_cruds.request.MedicalRecordRequest;
import com.example.one_drop_cruds.utils.RetrofitHelper;
import com.example.one_drop_cruds.utils.SharedPrefManager;
import com.example.one_drop_cruds.utils.ToastHelper;
import com.example.one_drop_cruds.utils.UserSessionManager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserMedicalData extends AppCompatActivity {
    UserSessionManager userSessionManager;
    SharedPrefManager sharedPrefManager;
    String token;
    ToastHelper toastHelper;
    MedicalRecordRequest medicalRecordRequest;


    EditText signup_objetivo_glucosa, signup_comorbilidades, signup_weight;
    Spinner spinnerDiabetes, spinnerTerapiaInsulina, spinnerTerapiaPastillas, spinnerTipoGlucometro, spinnerTipoSensor;
    Button signup_medical_data_button;
    LoguedUserDetails loguedUser;
    AuthRequests authRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_medical_data);

        // user sessions
        userSessionManager = new UserSessionManager(UserMedicalData.this);
        sharedPrefManager = new SharedPrefManager(UserMedicalData.this, "oneDrop_shared_preferences");

        // listas desplegables
        spinnerDiabetes = findViewById(R.id.spinnerDiabetes);
        spinnerTerapiaInsulina = findViewById(R.id.spinnerTerapiaInsulina);
        spinnerTerapiaPastillas = findViewById(R.id.spinnerTerapiaPastillas);
        spinnerTipoGlucometro = findViewById(R.id.spinnerTipoGlucometro);
        spinnerTipoSensor = findViewById(R.id.spinnerTipoSensor);
        signup_objetivo_glucosa = findViewById(R.id.signup_objetivo_glucosa);
        signup_comorbilidades = findViewById(R.id.signup_comorbilidades);
        signup_weight = findViewById(R.id.signup_weight);

        signup_medical_data_button = findViewById(R.id.signupButton);
        toastHelper = new ToastHelper(getApplicationContext());
        // request, Crea helper con el jwt, inicializa retrofit y crea RecordsRequest, para hacer solicitudes
        token = sharedPrefManager.getUserToken();
        medicalRecordRequest = new RetrofitHelper(token).getRetrofitHelperWithToken().create(MedicalRecordRequest.class);
        authRequest = new RetrofitHelper(token).getRetrofitHelperWithToken().create(AuthRequests.class);
        getLoguedUserDetails(token);
        setSpinners();

    }

    private void getLoguedUserDetails(String token){
        Call<LoguedUserDetails> call = authRequest.getUserDetailsRequest();
        call.enqueue(new Callback<LoguedUserDetails>() {
            @Override
            public void onResponse(Call<LoguedUserDetails> call, Response<LoguedUserDetails> response) {
                if(response.isSuccessful() && response.body() != null){
                    // Obtener datos del usuario de la resp y guardarlo en shared pref como un objeto json
                    loguedUser = response.body();
                    sharedPrefManager.setLoguedUser(response.body());
                } else if (response.code()==400){
                    sharedPrefManager.clearLoguedUser();
                }
            }
            @Override
            public void onFailure(Call<LoguedUserDetails> call, Throwable t) {
                sharedPrefManager.clearLoguedUser();
            }
        });
    }
    private void setSpinners(){
        //Sets listas desplegables
        setSpinner(Tipo_diabetes.class, spinnerDiabetes);
        setSpinner(Terapia_insulina.class, spinnerTerapiaInsulina);
        setSpinner(Terapia_pastillas.class, spinnerTerapiaPastillas);
        setSpinner(Tipo_glucometro.class, spinnerTipoGlucometro);
        setSpinner(Tipo_sensor.class, spinnerTipoSensor);
    }
    public void setSpinner(Class<?> enumerable, Spinner spinner){
        List<String> opciones = new ArrayList<>();
        String primerOp =  "Elige una opcion de "+enumerable.getSimpleName().replace("_", " ").toLowerCase(Locale.ROOT);
        opciones.add(primerOp);

        Field[] opcionesEnum = enumerable.getDeclaredFields();
        for (Field field : opcionesEnum) {
            if(field.getName() != "$VALUES") opciones.add(field.getName().replace("_", " ")); // si funciona cambiar en ProfileActivity.java
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opciones.toArray(new String[0])); // SE MUESTRA EN this ACTIVITY, dentro de un SIMPLE_SPINNER
        spinner.setAdapter(adapter); // mostrar spinner con el adapatador creado
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // todo si no hubiese nada seleccionado...
            }
        });
    }
    public boolean validFields(){
        Boolean diabetes = spinnerDiabetes.getSelectedItem().toString().contains(Tipo_diabetes.class.getSimpleName().replace("_", " ").toLowerCase(Locale.ROOT));
        Boolean insulina = spinnerTerapiaInsulina.getSelectedItem().toString().contains(Terapia_insulina.class.getSimpleName().replace("_", " ").toLowerCase(Locale.ROOT));
        Boolean pastillas = spinnerTerapiaPastillas.getSelectedItem().toString().contains(Terapia_pastillas.class.getSimpleName().replace("_", " ").toLowerCase(Locale.ROOT));
        Boolean glucometro = spinnerTipoGlucometro.getSelectedItem().toString().contains(Tipo_glucometro.class.getSimpleName().replace("_", " ").toLowerCase(Locale.ROOT));
        Boolean sensor = spinnerTipoSensor.getSelectedItem().toString().contains(Tipo_sensor.class.getSimpleName().replace("_", " ").toLowerCase(Locale.ROOT));
        Boolean objetivo = signup_objetivo_glucosa.getText().toString().isEmpty();
        Boolean comorbilidades = signup_comorbilidades.getText().toString().isEmpty();
        Boolean peso = signup_weight.getText().toString().isEmpty();
        if(diabetes  || insulina|| pastillas|| glucometro || sensor|| objetivo || comorbilidades || peso ){
            toastHelper.showLong("Todos los campos son obligatorios.");
            return false;
        }
        return true;
    }
    public void submitMedicalRecord(View v){
        if(validFields()){
            Integer userId = loguedUser.getId();
            String diabetes = spinnerDiabetes.getSelectedItem().toString().replace(" ", "_");
            String insulina = spinnerTerapiaInsulina.getSelectedItem().toString().replace(" ", "_");
            String pastillas = spinnerTerapiaPastillas.getSelectedItem().toString().replace(" ", "_");
            String glucometro = spinnerTipoGlucometro.getSelectedItem().toString().replace(" ", "_");
            String sensor = spinnerTipoSensor.getSelectedItem().toString().replace(" ", "_");
            String objetivo = signup_objetivo_glucosa.getText().toString();
            String comorbilidades = signup_comorbilidades.getText().toString();
            Double peso = Double.valueOf(signup_weight.getText().toString());
            AddNewMedicalRecordDto addMedicalRecord = new AddNewMedicalRecordDto(userId, diabetes, insulina, pastillas, glucometro, sensor, objetivo, comorbilidades, peso);
            Call<MedicalRecordReadDto> call = medicalRecordRequest.addNewMedicalRecord(addMedicalRecord);
            call.enqueue(new Callback<MedicalRecordReadDto>() {
                @Override
                public void onResponse(Call<MedicalRecordReadDto> call, Response<MedicalRecordReadDto> response) {
                    if(response.isSuccessful() && response.body() != null){
                        // cleanEditTextFields();
                        Intent intent = new Intent(getApplicationContext(), Home.class);
                        startActivity(intent);
                        toastHelper.showLong("Se agrego ficha medica");
                    } else if (response.code()==400){
                        // todo pendiente de manejar
                        System.out.println(" MedicalRecordReadDto response.code()==400  *********");
                        toastHelper.showLong("Error agregando MedicalRecordReadDto response.code()==400??");
                        System.out.println(response.body());
                        System.out.println(" MedicalRecordReadDto response.code()==400  *********");
                    }
                }
                @Override
                public void onFailure(Call<MedicalRecordReadDto> call, Throwable t) {
                    // todo pendiente de manejar
                    System.out.println("********* MedicalRecordReadDto Throwable t******");
                    toastHelper.showLong("Error onFailure Throwable=> "+t);
                    System.out.println( t.getLocalizedMessage());
                    System.out.println( t.getCause());
                    System.out.println( t.getMessage());
                    System.out.println("********* MedicalRecordReadDto Throwable t******");
                }
            });

        }

    }
}