package com.example.nurserypolije.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.nurserypolije.LoginActivity;
import com.example.nurserypolije.R;
import com.example.nurserypolije.SessionManager;

public class NotificationsFragment extends Fragment {
ImageView keranjang, setting;
SessionManager sessionManager;
RelativeLayout data;
LinearLayout email, nohp, alamat, jk;
Button daftar, login, logout;
TextView keterangan, nama;
    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        keterangan = root.findViewById(R.id.keterangan);
        nama = root.findViewById(R.id.nama);
        email = root.findViewById(R.id.email);
        nohp = root.findViewById(R.id.nohp);
        alamat = root.findViewById(R.id.alamat);
        jk = root.findViewById(R.id.jk);
        daftar = root.findViewById(R.id.daftar);
        login = root.findViewById(R.id.login);
        logout = root.findViewById(R.id.logout);
        sessionManager = new SessionManager(getActivity());
        keranjang = root.findViewById(R.id.keranjang);
        keranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.checkLogin();
            }
        });


        setting = root.findViewById(R.id.setting);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logout();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
            }
        });

//        daftar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getContext(), LoginActivity.class);
//                startActivity(i);
//            }
//        });

        if (sessionManager.isLoggin() == true)
        {
            daftar.setVisibility(View.GONE);
            login.setVisibility(View.GONE);
        }else{
            keterangan.setText("Anda Belum Login");
            nama.setText("");
            email.setVisibility(View.GONE);
            nohp.setVisibility(View.GONE);
            alamat.setVisibility(View.GONE);
            jk.setVisibility(View.GONE);
            logout.setVisibility(View.GONE);
        }



//        data = root.findViewById(R.id.data);
//        data.setVisibility(View.GONE);
//        final TextView textView = root.findViewById(R.id.text_notifications);
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText("");
//            }
//        });
        return root;
    }
}