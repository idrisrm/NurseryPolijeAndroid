package com.example.nurserypolije;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.nurserypolije.config.restServer;

public class Kritik extends AppCompatActivity {
    TextView kritik;
    Button batal, kirim;
    Boolean cek;
    String EmailHolder;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    SessionManager sessionManager;
    String Url = restServer.URL_KRITIK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kritik);
    }
}
