package com.example.nurserypolije;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
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

        btn_logout = findViewById(R.id.logout);
        sessionManager = new SessionManager(this);

        listView = findViewById(R.id.listviewsetting);
        String[] setting = new String[] {
                "Ubah Profile",
                "Ubah Password"
        };
        List<String> fruits_list = new ArrayList<String>(Arrays.asList(setting));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, fruits_list);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dipilih = (String) parent.getItemAtPosition(position);

                if (dipilih == "Ubah Profile"){
//                    Intent i = new Intent(SettingProfileActivity.this, LoginActivity.class);
////                    startActivity(i);
                    Toast.makeText(SettingProfileActivity.this, "Ubah Profile", Toast.LENGTH_SHORT).show();
                }else if (dipilih == "Ubah Password"){
                    Toast.makeText(SettingProfileActivity.this, "Ubah Password", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //ketika button logout di tekan
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logout();
            }
        });
    }
}
