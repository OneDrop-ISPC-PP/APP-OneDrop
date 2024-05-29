package com.example.one_drop_cruds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.one_drop_cruds.entities.dtos.medicalRecord.AddNewMedicalRecordDto;
import com.example.one_drop_cruds.entities.dtos.medicalRecord.MedicalRecordReadDto;
import com.example.one_drop_cruds.entities.user.FichaMedicaUsuario;
import com.example.one_drop_cruds.entities.user.LoguedUserDetails;
import com.example.one_drop_cruds.entities.user.enums.Terapia_insulina;
import com.example.one_drop_cruds.entities.user.enums.Terapia_pastillas;
import com.example.one_drop_cruds.entities.user.enums.Tipo_diabetes;
import com.example.one_drop_cruds.entities.user.enums.Tipo_glucometro;
import com.example.one_drop_cruds.entities.user.enums.Tipo_sensor;
import com.example.one_drop_cruds.request.AuthRequests;
import com.example.one_drop_cruds.request.MedicalRecordRequest;
import com.example.one_drop_cruds.utils.BackendUrl;
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

public class ProfileActivity extends AppCompatActivity {
    UserSessionManager userSessionManager;
    SharedPrefManager sharedPrefManager;
    TextView signup_name, signup_last_name, signup_birth;
    EditText signup_weight, signup_comorbilidades, signup_objetivo_glucosa;
    private Spinner spinnerDiabetes, spinnerTerapiaInsulina, spinnerTerapiaPastillas, spinnerTipoGlucometro, spinnerTipoSensor;
    Button edit_medical_data_button, selectImageButton;
    ImageView profileImage;
    private static final int PICK_IMAGE = 100;
    private Uri selectedImageUri = null; // Para almacenar la URI de la imagen seleccionada

    LoguedUserDetails loguedUser;
    FichaMedicaUsuario fichaMedicaUsuario;
    String token;
    String baseUrl = new BackendUrl().getBackendUrl();
    AuthRequests authRequests;
    MedicalRecordRequest medicalRecordRequest;
    ToastHelper toastHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sharedPrefManager = new SharedPrefManager(getApplicationContext(), "oneDrop_shared_preferences");
        token = sharedPrefManager.getUserToken();
        userSessionManager = new UserSessionManager(getApplicationContext());

        loguedUser = userSessionManager.getLoguedUserDetails();  // SI NO ESTA LOGUEADO, SE REDIRIGE A LOGIN

        // request, Crea helper con el jwt, inicializa retrofit y crea AuthRequests, para hacer solicitudes
        authRequests = new RetrofitHelper(token).getRetrofitHelperWithToken().create(AuthRequests.class);
        medicalRecordRequest = new RetrofitHelper(token).getRetrofitHelperWithToken().create(MedicalRecordRequest.class);
        toastHelper = new ToastHelper(ProfileActivity.this);

        signup_name = findViewById(R.id.signup_name);
        signup_last_name = findViewById(R.id.signup_last_name);
        signup_birth = findViewById(R.id.signup_birth);

        signup_weight = findViewById(R.id.signup_weight);
        signup_comorbilidades = findViewById(R.id.signup_comorbilidades);
        signup_objetivo_glucosa = findViewById(R.id.signup_objetivo_glucosa);

        // listas desplegables
        spinnerDiabetes = findViewById(R.id.spinnerDiabetes);
        spinnerTerapiaInsulina = findViewById(R.id.spinnerTerapiaInsulina);
        spinnerTerapiaPastillas = findViewById(R.id.spinnerTerapiaPastillas);
        spinnerTipoGlucometro = findViewById(R.id.spinnerTipoGlucometro);
        spinnerTipoSensor = findViewById(R.id.spinnerTipoSensor);

        setTextsForm();
    }
    private void getFichaMedicaUsuarioRequest(){
        Call<FichaMedicaUsuario> call = authRequests.getFichaMedicaUsuario(loguedUser.getId());
        call.enqueue(new Callback<FichaMedicaUsuario>() {
            @Override
            public void onResponse(Call<FichaMedicaUsuario> call, Response<FichaMedicaUsuario> response) {
                if(response.isSuccessful() && response.body() != null){
                    // Obtener datos de ficha medica y guardarlo en shared
                    System.out.println("******************** FICHA MEDICA ********************");
                    System.out.println(response.body());
                    setFichaMedica(response.body());
                    System.out.println("******************** FICHA MEDICA ********************");
                } else if (response.code()==400){
                    System.out.println(" FICHA MEDICA response.code()==400 SI NO ESTA CARGADA LA FICHA, SE DEBERIA REDIRIGIR A ACTIVIY DE CARGA DE FICHA MEDICA *********");
                    System.out.println(response.body());
                    // TODO SI NO ESTA CARGADA LA FICHA, SE DEBERIA REDIRIGIR A ACTIVIY DE CARGA DE FICHA MEDICA
                    // TODO SI NO ESTA CARGADA LA FICHA, SE DEBERIA REDIRIGIR A ACTIVIY DE CARGA DE FICHA MEDICA
                    // TODO SI NO ESTA CARGADA LA FICHA, SE DEBERIA REDIRIGIR A ACTIVIY DE CARGA DE FICHA MEDICA
                    System.out.println(" FICHA MEDICA response.code()==400 SI NO ESTA CARGADA LA FICHA, SE DEBERIA REDIRIGIR A ACTIVIY DE CARGA DE FICHA MEDICA *********");
                }
            }
            @Override
            public void onFailure(Call<FichaMedicaUsuario> call, Throwable t) {
                System.out.println("******************** FICHA MEDICA Throwable t*************************************************");
                System.out.println( t);
                // TODO SI NO ESTA CARGADA LA FICHA, SE DEBERIA REDIRIGIR A ACTIVIY DE CARGA DE FICHA MEDICA
                // TODO SI NO ESTA CARGADA LA FICHA, SE DEBERIA REDIRIGIR A ACTIVIY DE CARGA DE FICHA MEDICA
                // TODO SI NO ESTA CARGADA LA FICHA, SE DEBERIA REDIRIGIR A ACTIVIY DE CARGA DE FICHA MEDICA
                System.out.println("******************** FICHA MEDICA Throwable t******************************************************");
            }
        });
    }

    // todo PENDIENTE EL MANEJO DE LOS CAMBIOS DEL INPUT
    public void setSpinner(FichaMedicaUsuario ficha, Class<?> enumerable, Spinner spinner ,  String elementoPreseleccionado){
        List<String> opciones = new ArrayList<>();
        String primerOp =  "Elige una opcion de "+enumerable.getSimpleName().replace("_", " ").toLowerCase(Locale.ROOT);
        opciones.add(primerOp);

        Field[] opcionesEnum = enumerable.getDeclaredFields();
        for (Field field : opcionesEnum) {
            if(field.getName() != "$VALUES"){
                opciones.add(field.getName().replace("_", " ")); // si funciona cambiar en ProfileActivity.java
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opciones.toArray(new String[0])); // SE MUESTRA EN this ACTIVITY, dentro de un SIMPLE_SPINNER
        spinner.setAdapter(adapter); // mostrar spinner con el adapatador creado
        spinner.setSelection(adapter.getPosition(elementoPreseleccionado));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // si la opcion es distinta a la original..
                if (! spinner.getSelectedItem().toString().replace("_", " ").equals(elementoPreseleccionado)){
                    // Aquí puedes manejar la selección del usuario
                    System.out.println("USUARIO CAMBIO OPCION A "+spinner.getSelectedItem().toString());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // todo si no hubiese nada seleccionado...
            }
        });
    }
    public void setFichaMedica(FichaMedicaUsuario ficha){
        sharedPrefManager.setFichaMedicaUser(ficha);
        signup_weight.setText(ficha.getPeso().toString());
        signup_objetivo_glucosa.setText(ficha.getObjetivo_glucosa());
        signup_comorbilidades.setText(ficha.getComorbilidades());

        //Sets listas desplegables
        setSpinner(ficha, Tipo_diabetes.class, spinnerDiabetes ,  ficha.getTipo_diabetes().name().replace("_", " "));
        setSpinner(ficha, Terapia_insulina.class, spinnerTerapiaInsulina ,  ficha.getTerapia_insulina().name().replace("_", " "));
        setSpinner(ficha, Terapia_pastillas.class, spinnerTerapiaPastillas ,  ficha.getTerapia_pastillas().name().replace("_", " "));
        setSpinner(ficha, Tipo_glucometro.class, spinnerTipoGlucometro ,  ficha.getTipo_glucometro().name().replace("_", " "));
        setSpinner(ficha, Tipo_sensor.class, spinnerTipoSensor ,  ficha.getTipo_sensor().name().replace("_", " "));
    }
    public void setTextsForm() {
        getFichaMedicaUsuarioRequest(); // Carga datos en fichaMedicaUsuario, o inicia activity para cargar datos de ficha medica
        signup_name.setText(loguedUser.getNombre());
        signup_last_name.setText(loguedUser.getApellido());

        List<Integer> nac = loguedUser.getNacimiento();
        signup_birth.setText(nac.get(2)+"/"+nac.get(1)+"/"+nac.get(0));
    }
    private boolean changesInUserProfile(){
        String nombre = signup_name.getText().toString();
        Boolean sinCambioNombre = loguedUser.getNombre().equals(nombre);
        String apellido = signup_last_name.getText().toString();
        Boolean sinCambioApellido = loguedUser.getApellido().equals(apellido);
        String nacimiento = signup_birth.getText().toString();
        Boolean sinCambioNacimiento = loguedUser.getNacimiento().equals(nacimiento);
        if (sinCambioNombre && sinCambioApellido && sinCambioNacimiento){
            return false;
        }
        return true;
    }
    private boolean changesInMedicalRecords(FichaMedicaUsuario fichaGuardada){
        Double peso = Double.valueOf(signup_weight.getText().toString());
        Boolean sinCambioPeso = fichaGuardada.getPeso().equals(peso);

        String comorbilidad = signup_comorbilidades.getText().toString();
        Boolean sinCambioComorbilidad = fichaGuardada.getComorbilidades().equals(comorbilidad);

        String glucosa = signup_objetivo_glucosa.getText().toString();
        Boolean sinCambioGlucosa = fichaGuardada.getObjetivo_glucosa().equals(glucosa);

        String diabetes = spinnerDiabetes.getSelectedItem().toString();
        Boolean sinCambioDiabetes = fichaGuardada.getTipo_diabetes().toString().replace("_", " ").equals(diabetes);

        String insulina = spinnerTerapiaInsulina.getSelectedItem().toString();
        Boolean sinCambioInsulina= fichaGuardada.getTerapia_insulina().toString().replace("_", " ").equals(insulina);

        String pastillas = spinnerTerapiaPastillas.getSelectedItem().toString();
        Boolean sinCambioPastillas = fichaGuardada.getTerapia_pastillas().toString().replace("_", " ").equals(pastillas);

        String glucometro = spinnerTipoGlucometro.getSelectedItem().toString();
        Boolean sinCambioGlucometro= fichaGuardada.getTipo_glucometro().toString().replace("_", " ").equals(glucometro);

        String sensor = spinnerTipoSensor.getSelectedItem().toString();
        Boolean sinCambioSensor = fichaGuardada.getTipo_sensor().toString().replace("_", " ").equals(sensor);

        if (sinCambioPeso && sinCambioComorbilidad && sinCambioGlucosa && sinCambioDiabetes && sinCambioInsulina &&sinCambioPastillas && sinCambioGlucometro && sinCambioSensor ){
            return false;
        }
        return true;
    }

    private void updateMedicalRecordRequest(){
        Double peso = Double.valueOf(signup_weight.getText().toString());
        String comorbilidad = signup_comorbilidades.getText().toString();
        String glucosa = signup_objetivo_glucosa.getText().toString();
        String diabetes = spinnerDiabetes.getSelectedItem().toString().replace(" ", "_");
        String insulina = spinnerTerapiaInsulina.getSelectedItem().toString().replace(" ", "_");
        String pastillas = spinnerTerapiaPastillas.getSelectedItem().toString().replace(" ", "_");
        String glucometro = spinnerTipoGlucometro.getSelectedItem().toString().replace(" ", "_");
        String sensor = spinnerTipoSensor.getSelectedItem().toString().replace(" ", "_");

        AddNewMedicalRecordDto updateDto = new AddNewMedicalRecordDto(null, diabetes, insulina, pastillas, glucometro, sensor, glucosa, comorbilidad, peso);

        Call<MedicalRecordReadDto> call = medicalRecordRequest.editMedicalRecord(loguedUser.getId(), updateDto);
        call.enqueue(new Callback<MedicalRecordReadDto>() {
            @Override
            public void onResponse(Call<MedicalRecordReadDto> call, Response<MedicalRecordReadDto> response) {
                if(response.isSuccessful() && response.body() != null){
                    toastHelper.showLong("Datos modificados!");
                } else if (response.code()==400){
                    System.out.println(" updateMedicalRecord response.code()==400 SI NO ESTA CARGADA LA FICHA, SE DEBERIA REDIRIGIR A ACTIVIY DE CARGA DE FICHA MEDICA *********");
                    toastHelper.showLong("Error modificando datos!");
                    System.out.println(response.body());
                    // TODO SI NO ESTA CARGADA LA FICHA, SE DEBERIA REDIRIGIR A ACTIVIY DE CARGA DE FICHA MEDICA
                    // TODO SI NO ESTA CARGADA LA FICHA, SE DEBERIA REDIRIGIR A ACTIVIY DE CARGA DE FICHA MEDICA
                    // TODO SI NO ESTA CARGADA LA FICHA, SE DEBERIA REDIRIGIR A ACTIVIY DE CARGA DE FICHA MEDICA
                    System.out.println(" FICHA MEDICA response.code()==400 SI NO ESTA CARGADA LA FICHA, SE DEBERIA REDIRIGIR A ACTIVIY DE CARGA DE FICHA MEDICA *********");
                }
            }
            @Override
            public void onFailure(Call<MedicalRecordReadDto> call, Throwable t) {
                System.out.println("******************** updateMedicalRecord Throwable t*************************************************");
                System.out.println( t);
                toastHelper.showLong("Error modificando datos: "+t.getMessage());
                // TODO SI NO ESTA CARGADA LA FICHA, SE DEBERIA REDIRIGIR A ACTIVIY DE CARGA DE FICHA MEDICA
                // TODO SI NO ESTA CARGADA LA FICHA, SE DEBERIA REDIRIGIR A ACTIVIY DE CARGA DE FICHA MEDICA
                // TODO SI NO ESTA CARGADA LA FICHA, SE DEBERIA REDIRIGIR A ACTIVIY DE CARGA DE FICHA MEDICA
                System.out.println("******************** FICHA MEDICA Throwable t******************************************************");
            }
        });
    }
    public void updateUserData(View v) {
        FichaMedicaUsuario fichaGuardada = sharedPrefManager.getFichaMedicaUser();
        if(changesInMedicalRecords(fichaGuardada)){
            updateMedicalRecordRequest();
        }
        if(changesInUserProfile()){// solo modificable desde web, por el momento
            toastHelper.showLong("No esta permitido modificar este dato desde la App");
        }
    }

    public void toHome(View v) {
        Intent home = new Intent(this, Home.class);
        startActivity(home);
    }

    public void updateNotValid(View v){
        toastHelper.showLong("Este campo no es editable en la app movil");
    }
}