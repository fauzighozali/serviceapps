package com.mediasoftindonesia.serviceapps.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mediasoftindonesia.serviceapps.R;
import com.mediasoftindonesia.serviceapps.model.AccessToken;
import com.mediasoftindonesia.serviceapps.model.Booking;
import com.mediasoftindonesia.serviceapps.network.ApiService;
import com.mediasoftindonesia.serviceapps.network.RetrofitBuilder;
import com.mediasoftindonesia.serviceapps.token.TokenManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private BookingActivity self;

    private EditText etDeskripsi;
    private ImageView ivOpenDate;
    private TextView tvDate;
    private Button btnBooking;

    private Calendar myCalendar;

    private ApiService service;
    private TokenManager tokenManager;
    private Call<Booking> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        self = this;

        etDeskripsi = findViewById(R.id.edit_text_booking_deskripsi);
        ivOpenDate = findViewById(R.id.image_view_booking_date);
        tvDate = findViewById(R.id.text_view_booking_date);
        btnBooking = findViewById(R.id.button_booking);

        myCalendar = Calendar.getInstance();

        ivOpenDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(self, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String formatTanggal = "yyyy-MM-dd";
                        SimpleDateFormat sdf = new SimpleDateFormat(formatTanggal);
                        tvDate.setText(sdf.format(myCalendar.getTime()));
                    }
                },
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        tokenManager = TokenManager.getInstance(self.getSharedPreferences("prefs", Context.MODE_PRIVATE));
        if (tokenManager.getToken() == null) {
            startActivity(new Intent(self, LoginActivity.class));
            finish();
        }
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);

        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMenu();
            }
        });
    }

    private void navigateToMenu() {
        Intent intent = getIntent();
        String deskripsi = etDeskripsi.getText().toString();
        String tgl = tvDate.getText().toString();

        call = service.booking(intent.getIntExtra("id_store",0), tgl, deskripsi,1);
        call.enqueue(new Callback<Booking>() {
            @Override
            public void onResponse(Call<Booking> call, Response<Booking> response) {
                Log.w(TAG, "onResponse: " + response);
                if (response.isSuccessful()) {
                    startActivity(new Intent(self, MainActivity.class));
                    finish();
                    Toast.makeText(self, "Booking Berhasil...!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Booking> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
