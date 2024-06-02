package com.example.one_drop_cruds;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.one_drop_cruds.databinding.ActivityUserSignupBinding;
import com.example.one_drop_cruds.entities.user.AuthResponse;
import com.example.one_drop_cruds.entities.user.RegisterRequest;
import com.example.one_drop_cruds.entities.user.enums.ErrorResponse;
import com.example.one_drop_cruds.request.AuthRequests;
import com.example.one_drop_cruds.utils.RetrofitHelper;
import com.example.one_drop_cruds.utils.SharedPrefManager;
import com.example.one_drop_cruds.utils.ToastHelper;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserSignupActivity extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    ActivityUserSignupBinding binding;
    EditText signup_email, signup_password , signup_confirm, signup_username, signup_name, signup_last_name, signup_phone, signup_sex, signup_dni ;

    AuthRequests authRequests;
    ProgressBar progressBarRequest;


    // TODO DATE PICKER
    private Button btnFecha;
    private TextView signup_birthdate;
    // TODO DATE PICKER

    ToastHelper toastHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserSignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPrefManager = new SharedPrefManager(getApplicationContext() , "oneDrop_shared_preferences");
        authRequests = new RetrofitHelper().getRetrofitHelper().create(AuthRequests.class);

        progressBarRequest = findViewById(R.id.progressBarRequest);
        progressBarRequest.setVisibility(View.GONE);

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
        btnFecha.setOnClickListener(v -> showDatePickerDialog());

        toastHelper= new ToastHelper(UserSignupActivity.this);
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
        binding.signupButton.setVisibility(View.GONE);
        progressBarRequest.setVisibility(View.VISIBLE);
        RegisterRequest registerRequest = new RegisterRequest(username, password, confirmPassword, name, lastName, dni, phone, email, sex, birth);
        Call<AuthResponse> call = authRequests.registerRequest(registerRequest);
        call.enqueue(new Callback<AuthResponse>() {
            // todo verificar error: CUANDO INTENTO REGISTRAR UN USUARIO, Y ESTE TIENE INFO QUE YA EXISTE, POR EJ EL DNI, LUEGO INTENTO EDITANDO EL DNI Y PONGO UNO QUE ESTA BIEN, INTENTO EL REGISTRO Y ESTE FUNCIONA, PERO NO ME SALE NINGUN TOAST NI CREA LA NUEVA ACTIVITY.. PERO SI QUIERO LOGUEARME LUEGO SI FUNCIONA CORRECTAMENTE.. PARECE QUE POR ALGUNA RAZON, LA LLAMADA LA EJECUTA CADA VEZ, PERO NO VUELVE A INGRESAR A "OnResponse" como si fuera una llamada exitosa, sino como que volviese siempre a caer en el "onFailure".. por?? como solucionarlo???
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                binding.signupButton.setVisibility(View.VISIBLE);
                progressBarRequest.setVisibility(View.GONE);
                if(response.isSuccessful() && response.body() != null){
                    // Obtener token de la resp y guardarlo en shared pref
                    String token = response.body().getToken();
                    sharedPrefManager.setUserToken(token);
                    Intent intent = new Intent(getApplicationContext(), UserMedicalData.class);
                    startActivity(intent);
                    toastHelper.showLong("Registro exitoso!");
                } else if (response.errorBody()!= null){
                    try {
                        Gson gson = new Gson();
                        ErrorResponse errorResponse = gson.fromJson(response.errorBody().string(), ErrorResponse.class); // obtengo el body de error, y lo serializo en ErrorResponse
                        toastHelper.showLong("Registro erroneo: "+errorResponse.getMessage());
                        System.out.println("Registro erroneo: "+errorResponse.getMessage());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                binding.signupButton.setVisibility(View.VISIBLE);
                progressBarRequest.setVisibility(View.GONE);
                System.out.println("Error, intenta nuevamente por favor"+t.getMessage());
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
