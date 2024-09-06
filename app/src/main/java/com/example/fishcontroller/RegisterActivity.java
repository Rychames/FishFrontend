package com.example.fishcontroller;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fishcontroller.model.User;
import com.example.fishcontroller.network.ApiService;
import com.example.fishcontroller.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etEmail, etPassword, etNumberTelephone;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.editTextRegisterUsername);
        etEmail = findViewById(R.id.editTextEmail);
        etPassword = findViewById(R.id.editTextRegisterPassword);
        etNumberTelephone = findViewById(R.id.editTextPhoneNumber);
        Button btnRegister = findViewById(R.id.buttonRegister);

        apiService = RetrofitClient.getClient("http://192.168.100.142:3000/").create(ApiService.class);

        btnRegister.setOnClickListener(view -> {
            String username = etUsername.getText().toString();
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            String number_telephone = etNumberTelephone.getText().toString();

            User user = new User(username, email, password, number_telephone);
            registerUser(user);
        });
    }

    private void registerUser(User user) {
        Call<Void> call = apiService.register(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Usuário registrado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Falha ao registrar usuário", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("RegisterActivity", "Erro ao registrar usuário: " + t.getMessage());
            }
        });
    }
}