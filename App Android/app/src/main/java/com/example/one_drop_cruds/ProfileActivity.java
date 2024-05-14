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
import com.example.one_drop_cruds.entities.user.FichaMedicaUsuario;
import com.example.one_drop_cruds.entities.user.LoguedUserDetails;
import com.example.one_drop_cruds.request.AuthRequests;
import com.example.one_drop_cruds.utils.AdminSQLiteOpenHelper;
import com.example.one_drop_cruds.utils.SharedPrefManager;
import com.example.one_drop_cruds.utils.UserSessionManager;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {
    UserSessionManager userSessionManager;
    SharedPrefManager sharedPrefManager;
    // AdminSQLiteOpenHelper admin;
    EditText signup_name, signup_last_name, signup_age, signup_birth, signup_weight, signup_db_type, signup_db_therapy;
    Button edit_medical_data_button, selectImageButton;
    ImageView profileImage;
    private static final int PICK_IMAGE = 100;
    private Uri selectedImageUri = null; // Para almacenar la URI de la imagen seleccionada

    LoguedUserDetails loguedUser;
    FichaMedicaUsuario fichaMedicaUsuario;
    String token;
    String baseUrl = "http://192.168.6.144:8080";// "http://192.168.18.3:8080";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sharedPrefManager = new SharedPrefManager(getApplicationContext(), "oneDrop_shared_preferences");
        token = sharedPrefManager.getUserToken();
        userSessionManager = new UserSessionManager(getApplicationContext());

        loguedUser = userSessionManager.getLoguedUserDetails();  // SI NO ESTA LOGUEADO, SE REDIRIGE A LOGIN

        signup_name = findViewById(R.id.signup_name);
        signup_last_name = findViewById(R.id.signup_last_name);
        // signup_age = findViewById(R.id.signup_age);
        signup_birth = findViewById(R.id.signup_birth);
        signup_weight = findViewById(R.id.signup_weight);
        signup_db_type = findViewById(R.id.signup_db_type);
        signup_db_therapy = findViewById(R.id.signup_db_therapy);

        setTextsForm();
    }

    private void getFichaMedicaUsuario(){
        HttpLoggingInterceptor getDetailsUserInterceptor = new HttpLoggingInterceptor();
        getDetailsUserInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(getDetailsUserInterceptor);

        // todo el interceptor para agregar el token al header
        httpClient.addInterceptor(chain -> {
            Request originalRequest = chain.request();
            Request.Builder builder = originalRequest.newBuilder();
            if (token!= null &&!token.isEmpty()) {
                builder.header("Authorization", "Bearer " + token);
            }
            return chain.proceed(builder.build());
        }); //todo el interceptor para agregar el token al header


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        AuthRequests authRequest = retrofit.create(AuthRequests.class);
        Call<FichaMedicaUsuario> call = authRequest.getFichaMedicaUsuario(loguedUser.getId());
        call.enqueue(new Callback<FichaMedicaUsuario>() {
            @Override
            public void onResponse(Call<FichaMedicaUsuario> call, Response<FichaMedicaUsuario> response) {
                if(response.isSuccessful() && response.body() != null){
                    // Obtener datos de ficha medica y guardarlo en shared
                    sharedPrefManager.setFichaMedicaUser(response.body());
                    System.out.println("******************** FICHA MEDICA *************************************************");
                    System.out.println(response.body());
                    System.out.println("******************** FICHA MEDICA ******************************************************");
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

    public void setTextsForm() {
        getFichaMedicaUsuario(); // Carga datos en fichaMedicaUsuario, o inicia activity para cargar datos de ficha medica

        // DTOmedicalRecord medicalRecord = // admin.getMedicalRecord(loguedUser.getUsername());
        signup_name.setText(loguedUser.getNombre());
        signup_last_name.setText(loguedUser.getApellido());

        List<Integer> nac = loguedUser.getNacimiento();
        signup_birth.setText(nac.get(2)+"/"+nac.get(1)+"/"+nac.get(0));

        fichaMedicaUsuario = sharedPrefManager.getFichaMedicaUser();
        signup_weight.setText(fichaMedicaUsuario.getPeso().toString());

        // TODO TRANSFORMAR A LISTA DESPLEGABLE EN ANDROID! y agregar , terapia pastillas, tipo glucometro, y tipo de sensor al perfil usuario

        String insulina = fichaMedicaUsuario.getTerapia_insulina().name();
        signup_db_therapy.setText(insulina);

        String diabetes = fichaMedicaUsuario.getTipo_diabetes().name();
        signup_db_type.setText(diabetes);

        String objetivoGluco = fichaMedicaUsuario.getObjetivo_glucosa();
        String terapiaPastillas = fichaMedicaUsuario.getTerapia_pastillas().name();
        String tipoGlucometro = fichaMedicaUsuario.getTipo_glucometro().name();
        String tipoSensor = fichaMedicaUsuario.getTipo_sensor().name();

        System.out.println("====>>>>>>>>> "+objetivoGluco + terapiaPastillas + tipoGlucometro + tipoSensor);
        // comorbilidad, obj glucosa



    }

    public void updateMedicalRecord(View v) {
        /*
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

         */
    }

    public void toHome(View v) {
        Intent home = new Intent(this, Home.class);
        startActivity(home);
    }
}