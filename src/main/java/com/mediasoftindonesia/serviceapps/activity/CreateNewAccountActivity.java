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
import com.mediasoftindonesia.serviceapps.model.InfoUser;
import com.mediasoftindonesia.serviceapps.network.ApiService;
import com.mediasoftindonesia.serviceapps.network.RetrofitBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateNewAccountActivity extends AppCompatActivity {

    private static final String TAG = "NewAccountActivity";
    private CreateNewAccountActivity self;

    private EditText etNama,etEmail,etPassword,etNoTelp,etAlamat;
    private Button btnSignUp;

    private ApiService service;
    private Call<InfoUser> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);
        self = this;

        etNama = findViewById(R.id.edit_text_nama);
        etEmail = findViewById(R.id.edit_text_email);
        etPassword = findViewById(R.id.edit_text_password);
        etNoTelp = findViewById(R.id.edit_text_noTelp);
        etAlamat = findViewById(R.id.edit_text_alamat);
        btnSignUp = findViewById(R.id.signUpButton);

        service = RetrofitBuilder.createService(ApiService.class);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToLogin();
            }
        });
    }

    private void navigateToLogin() {
        String nama = etNama.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String no_telp = etNoTelp.getText().toString();
        String alamat = etAlamat.getText().toString();

        call = service.register(nama,email,password,no_telp,alamat);
        call.enqueue(new Callback<InfoUser>() {
            @Override
            public void onResponse(Call<InfoUser> call, Response<InfoUser> response) {
                Log.w(TAG, "onResponse: " + response);
                if (response.isSuccessful()) {
                    startActivity(new Intent(self, LoginActivity.class));
                    finish();
                    Toast.makeText(self, "Daftar Berhasil...!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InfoUser> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
            }
        });

    }
}
