package com.example.nurserypolije;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class CheckOutActivity extends AppCompatActivity {
    String idTransaksi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        idTransaksi = getIntent().getStringExtra("idTransaksi");
    }
}
