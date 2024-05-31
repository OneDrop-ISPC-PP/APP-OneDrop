package com.example.one_drop_cruds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.one_drop_cruds.databinding.ActivityUserLoginBinding;
import com.example.one_drop_cruds.entities.user.AuthResponse;
import com.example.one_drop_cruds.entities.user.LoginRequest;
import com.example.one_drop_cruds.request.AuthRequests;
import com.example.one_drop_cruds.utils.AdminSQLiteOpenHelper;
import com.example.one_drop_cruds.utils.BackendUrl;
import com.example.one_drop_cruds.utils.SharedPrefManager;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserLoginActivity extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    ActivityUserLoginBinding binding;
    String baseUrl = new BackendUrl().getBackendUrl();
    private void loginUserRequest(String username, String password){// logica de request
        HttpLoggingInterceptor logginInterceptor = new HttpLoggingInterceptor();
        logginInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                /*
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS);
                 */;
        httpClient.addInterceptor(logginInterceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl+"/auth/login/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        AuthRequests authRequest = retrofit.create(AuthRequests.class);

        LoginRequest loginRequest = new LoginRequest(username, password);
        Call<AuthResponse> call = authRequest.loginRequest(loginRequest);
        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    // limpiar edit texts!
                    binding.loginUsername.setText("");
                    binding.loginPassword.setText("");
                    // Obtener token de la resp y guardarlo en shared pref
                    String token = response.body().getToken();
                    sharedPrefManager.setUserToken(token);
                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Login exitoso!", Toast.LENGTH_LONG).show();
                } else if (response.code()==400){
                    Toast.makeText(getApplicationContext(), "Login erroneo, revisa las credenciales!", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error, intenta nuevamente por favor", Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPrefManager = new SharedPrefManager(getApplicationContext(), "oneDrop_shared_preferences");

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = binding.loginUsername.getText().toString();
                String password = binding.loginPassword.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(UserLoginActivity.this, "¡Debes completar todos los campos!", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 8) {
                    Toast.makeText(UserLoginActivity.this, "¡Contraseña incorrecta!", Toast.LENGTH_SHORT).show();
                } else {
                    loginUserRequest(username, password);
                }
            }
        });

        binding.signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserLoginActivity.this, UserSignupActivity.class);
                startActivity(intent);
            }
        });
        binding.restoreRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserLoginActivity.this, RestorePassword.class);
                startActivity(intent);
            }
        });
    }
    /*
    private boolean isEmailValid(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

     */
}