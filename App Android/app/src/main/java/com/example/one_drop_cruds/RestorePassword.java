package com.example.one_drop_cruds;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.one_drop_cruds.request.AuthRequests;
import com.example.one_drop_cruds.utils.BackendUrl;
import com.example.one_drop_cruds.utils.ToastHelper;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestorePassword extends AppCompatActivity implements View.OnClickListener {

    EditText restore_password_email;
    Button restore_password_btn;
    ToastHelper toastHelper;
    String backendUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_password);

        restore_password_email = findViewById(R.id.restore_password_email);
        restore_password_btn = findViewById(R.id.restore_password_btn);

        toastHelper= new ToastHelper(RestorePassword.this);
        backendUrl = new BackendUrl().getBackendUrl();

    }

    public void restorePasswordRequest(View v){
        String email = restore_password_email.getText().toString();
        // logica de request
        HttpLoggingInterceptor logginInterceptor = new HttpLoggingInterceptor();
        logginInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logginInterceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(backendUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        AuthRequests authRequest = retrofit.create(AuthRequests.class);

        Call<Boolean> call = authRequest.getRestorePassword(email);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                // responde true o false
                if(response.isSuccessful() && response.body() != null && response.body()){
                    Intent intent = new Intent(getApplicationContext(), UserLoginActivity.class);
                    startActivity(intent);
                    toastHelper.showLong("Se enviaron mas instrucciones al email!");
                } else if(! response.isSuccessful()){
                    toastHelper.showLong("Error enviando email, revisa el email!");
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                toastHelper.showLong("Error : "+t.getMessage());
            }
        });
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