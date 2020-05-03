package com.example.nurserypolije;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SettingProfileActivity extends AppCompatActivity {
    ListView listView;
    Button btn_logout;
    ArrayAdapter<CharSequence> adapter;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_profile);

        listView = findViewById(R.id.listviewsetting);
        adapter = ArrayAdapter.createFromResource(this, R.array.settingprofile, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        btn_logout = findViewById(R.id.logout);
        sessionManager = new SessionManager(this);

        //ketika button logout di tekan
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logout();
            }
        });
    }
}
