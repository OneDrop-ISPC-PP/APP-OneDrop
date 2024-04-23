package com.example.one_drop_cruds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.one_drop_cruds.databinding.ActivityUserLoginBinding;
import com.example.one_drop_cruds.utils.AdminSQLiteOpenHelper;
import com.example.one_drop_cruds.utils.SharedPrefManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserLoginActivity extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    ActivityUserLoginBinding binding;
    AdminSQLiteOpenHelper adminBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adminBD = new AdminSQLiteOpenHelper(this, "bd_one_drop", null, 1);
        sharedPrefManager = new SharedPrefManager(getApplicationContext(), "oneDrop_shared_preferences");

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.loginEmail.getText().toString();
                String password = binding.loginPassword.getText().toString();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(UserLoginActivity.this, "¡Debes completar todos los campos!", Toast.LENGTH_SHORT).show();
                } else if (!isEmailValid(email)) {
                    Toast.makeText(UserLoginActivity.this, "¡Ingresa un email válido!", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 8) {
                    Toast.makeText(UserLoginActivity.this, "¡Contraseña incorrecta!", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkCredentials = adminBD.checkEmailPassword(email, password);
                    if (checkCredentials) {
                        sharedPrefManager.setLoguedUser(email);
                        Toast.makeText(UserLoginActivity.this, "Login exitoso!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Home.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(UserLoginActivity.this, "¡Credenciales erróneas!", Toast.LENGTH_SHORT).show();
                    }
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
    }
    private boolean isEmailValid(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
