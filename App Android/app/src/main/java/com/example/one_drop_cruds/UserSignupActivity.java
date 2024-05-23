package com.example.one_drop_cruds;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.one_drop_cruds.databinding.ActivityUserSignupBinding;
import com.example.one_drop_cruds.entities.user.AuthResponse;
import com.example.one_drop_cruds.entities.user.RegisterRequest;
import com.example.one_drop_cruds.request.AuthRequests;
import com.example.one_drop_cruds.utils.AdminSQLiteOpenHelper;
import com.example.one_drop_cruds.utils.BackendUrl;
import com.example.one_drop_cruds.utils.SharedPrefManager;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserSignupActivity extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    ActivityUserSignupBinding binding;
    AdminSQLiteOpenHelper adminBD;
    EditText signup_email, signup_password , signup_confirm, signup_username, signup_name, signup_last_name, signup_phone, signup_sex, signup_dni ;
    String baseUrl = new BackendUrl().getBackendUrl();


    // TODO DATE PICKER
    private Button btnFecha;
    private TextView signup_birthdate;
    // TODO DATE PICKER


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserSignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        adminBD = new AdminSQLiteOpenHelper(this, "bd_one_drop", null, 1); // version es para las futuras modificaciones de la estructura de la bd
        sharedPrefManager = new SharedPrefManager(getApplicationContext() , "oneDrop_shared_preferences");

        signup_email = findViewById(R.id.signup_email);
        signup_password = findViewById(R.id.signup_password);
        signup_confirm = findViewById(R.id.signup_confirm);
        signup_username = findViewById(R.id.signup_username);
        signup_name = findViewById(R.id.signup_name);
        signup_last_name = findViewById(R.id.signup_last_name);
        signup_phone = findViewById(R.id.signup_phone);
        signup_dni = findViewById(R.id.signup_dni);
        signup_sex = findViewById(R.id.signup_sex);

        // DATE PICKER
        btnFecha = findViewById(R.id.btn_fecha);
        signup_birthdate = findViewById(R.id.signup_birthdate);
        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

    }
    // DATE PICKER
    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String day = String.valueOf(dayOfMonth);
                        String month = String.valueOf(monthOfYear + 1);
                        if (dayOfMonth<10) day="0"+day;
                        if (monthOfYear<10) month="0"+month;
                        signup_birthdate.setText(year + "-" + month + "-" + day);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    public void toLogin(View v){
        Intent intent = new Intent(UserSignupActivity.this, UserLoginActivity.class);
        startActivity(intent);
    }

    public void registerUser(String email , String  password , String  confirmPassword , String  sex , String  username , String  name , String  lastName , String  phone , String  dni  , String birth){
        HttpLoggingInterceptor registerInterceptor = new HttpLoggingInterceptor();
        registerInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(registerInterceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl) // +"/auth/register/"
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        AuthRequests authRequest = retrofit.create(AuthRequests.class);

        RegisterRequest registerRequest = new RegisterRequest(username, password, confirmPassword, name, lastName, dni, phone, email, sex, birth);
        Call<AuthResponse> call = authRequest.registerRequest(registerRequest);
        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> ");
                System.out.println(response);
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> ");
                if(response.isSuccessful() && response.body() != null){
                    // Obtener token de la resp y guardarlo en shared pref
                    String token = response.body().getToken();
                    sharedPrefManager.setUserToken(token);
                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Registro exitoso!", Toast.LENGTH_LONG).show();
                } else if (response.code()==409){
                    System.out.println(response.body()); // todo => obtener message y mostrarlo en toast
                    Toast.makeText(getApplicationContext(), "Registro erroneo, revisa los datos ingresados!", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error, intenta nuevamente por favor", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void signupUser(View v){
        String email = signup_email.getText().toString();
        String password = signup_password.getText().toString();
        String confirmPassword = signup_confirm.getText().toString();

        // todo ==>>> FALTAN VALIDACIONES
        String sex = signup_sex.getText().toString();
        String username = signup_username.getText().toString(); // todo ==>>> FALTAN VALIDACIONES
        String name = signup_name.getText().toString(); // todo ==>>> FALTAN VALIDACIONES
        String lastName = signup_last_name.getText().toString(); // todo ==>>> FALTAN VALIDACIONES
        String phone = signup_phone.getText().toString(); // todo ==>>> FALTAN VALIDACIONES
        String dni = signup_dni.getText().toString(); // todo ==>>> FALTAN VALIDACIONES

        // DATE PICKER
        String birth = signup_birthdate.getText().toString(); // todo ==>>> FALTAN VALIDACIONES edad minima?
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(UserSignupActivity.this, "¡Debes completar todos los campos!", Toast.LENGTH_SHORT).show();
        } else if (!isEmailValid(email)) {
            Toast.makeText(UserSignupActivity.this, "¡Ingresa un email válido!", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 8) {
            Toast.makeText(UserSignupActivity.this, "¡La contraseña debe tener al menos 8 caracteres!", Toast.LENGTH_SHORT).show();
        } else {
            if (password.equals(confirmPassword)) {
                // todo LOGICA DE REGISTRO!!!

                // Definir el formato de fecha
                /*
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                // Parsear la cadena de texto a LocalDate usando el formato definido
                LocalDate birtDate = LocalDate.parse(birth, formatter);
*/
                registerUser(email,  password,  confirmPassword,  sex,  username,  name,  lastName,  phone,  dni,  birth);

            // todo LOGICA DE REGISTRO VIEJA, BORRAR!
                /*
                Boolean checkUserEmail = adminBD.checkEmail(email);
                if (!checkUserEmail) {
                    Boolean insert = adminBD.createUser(email, password);
                    if (insert) {
                        Toast.makeText(UserSignupActivity.this, "¡Registro exitoso!", Toast.LENGTH_SHORT).show();
                        // todo => aqui se debe guardar un user details y no solo el email!!!!!  sharedPrefManager.setLoguedUser(email);
                        Intent intent = new Intent(getApplicationContext(), UserMedicalData.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(UserSignupActivity.this, "Error durante el registro!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UserSignupActivity.this, "¡Ya estás registrado!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), UserLoginActivity.class);
                    startActivity(intent);
                }
            */
            } else {
                Toast.makeText(UserSignupActivity.this, "¡Las contraseñas no concuerdan!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isEmailValid(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
