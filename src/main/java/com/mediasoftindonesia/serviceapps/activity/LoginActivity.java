package com.mediasoftindonesia.serviceapps.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mediasoftindonesia.serviceapps.R;
import com.mediasoftindonesia.serviceapps.model.AccessToken;
import com.mediasoftindonesia.serviceapps.network.ApiService;
import com.mediasoftindonesia.serviceapps.network.RetrofitBuilder;
import com.mediasoftindonesia.serviceapps.token.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private LoginActivity self;

    private EditText etEmail,etPassword;
    private Button btnLogin, btnNewAccount;

    private ApiService service;
    private TokenManager tokenManager;
    private Call<AccessToken> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        self = this;

        service = RetrofitBuilder.createService(ApiService.class);
        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));

        if (tokenManager.getToken().getToken() != null) {
            startActivity(new Intent(self, MainActivity.class));
            finish();
        }

        etEmail = findViewById(R.id.edit_text_email);
        etPassword = findViewById(R.id.edit_text_password);
        btnLogin = findViewById(R.id.loginButton);
        btnNewAccount = findViewById(R.id.newAccountButton);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToHome();
            }
        });

        btnNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(self, CreateNewAccountActivity.class));
            }
        });

    }

    private void navigateToHome() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        call = service.login(email,password);
        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                Log.w(TAG, "onResponse: " + response);
                if (response.isSuccessful()) {
                    tokenManager.saveToken(response.body().getData());
                    startActivity(new Intent(self, MainActivity.class));
                    finish();
                }else {
                    Toast.makeText(self, "Akun belum terdaftar...!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
