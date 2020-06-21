package com.example.nurserypolije;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class FrequentlyAskQuestion extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Version> versionList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frequently_ask_question);
        recyclerView = findViewById(R.id.recyle);
        initData();
        setRecyclerView();
    }

    private void setRecyclerView() {
        VersionAdapter versionAdapter = new VersionAdapter(versionList);
        recyclerView.setAdapter(versionAdapter);
        recyclerView.setHasFixedSize(true);
    }

    private void initData() {

        versionList = new ArrayList<>();
        versionList.add(new Version("1. Kok saya tidak bisa login?","jangan stres dulu... jadi gini... kemungkinan kamu tidak bisa login karena salah memasukkan username dan password. Jika sudah yakin username dan password yang kamu masukkan benar, namun tetep tidak bisa hubungi kami."));
        versionList.add(new Version("2. Gimana sih cara beli bunga?","jika sudah punya akun maka langsung login, jika belum punya akun maka regestrasi lalu masuk ke home untuk melihat produk bunga. selanjutnya kalian dapat langsung meng-klik produk bunga yang anda inginkan maka akan langsung masuk ke halaman transaksi silakan masukkan jumlah bunga yang anda inginkan selesai"));
        versionList.add(new Version("3. Gimana sih cara buat akun?","Gampang banget caranya! Coba ikutin langkah-langkah di bawah ini ya: masukkan nama anda masukkan alamat anda masukkan no telepon anda masukkan email anda. pastikan email yang aktif masukkan username yang anda inginkan masukkan password. buatlah seunik mungkin agar orang lain tidak bisa mengatahuinya konfirmasi password anda pastikan semuanaya terisi Ya! selesai! Butuh bantuan lebih lanjut? chat kami"));

    }
}
