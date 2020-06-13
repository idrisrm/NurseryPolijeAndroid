package com.example.nurserypolije;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nurserypolije.config.restServer;
import com.example.nurserypolije.ui.notifications.NotificationsFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Kritik extends AppCompatActivity {
    EditText Email, kritik;
    TextView pesan;
    Button batal, kirim;
    Boolean cek;
    String email, Kritik;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    SessionManager sessionManager;
    String Url = restServer.URL_KRITIK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kritik);

        sessionManager = new SessionManager(Kritik.this);
        requestQueue = Volley.newRequestQueue(Kritik.this);
        progressDialog = new ProgressDialog(Kritik.this);
        kritik = findViewById(R.id.kritik);
        batal = findViewById(R.id.Batal);
        kirim = findViewById(R.id.Kir);


        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent batal = new Intent(Kritik.this, NotificationsFragment.class);
                startActivity(batal);
            }
        });

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CekForm();
                if(cek){
                    KirimKritik();
                }else{
                    pesan.setText("Harap Isi Semua Field!");
                }
            }
        });
    }

    //cek form kosong atau tidak
    public void CekForm()
    {
        Kritik = kritik.getText().toString().trim();
        if (TextUtils.isEmpty(Kritik)){
            cek = false;
        }else{
            cek = true;
        }


        //mengambil email dari session manager
        HashMap<String, String> user = sessionManager.getUserDetail();
        email = user.get(sessionManager.EMAIL);
    }

    //Fungsi Kirim Kritik
    public void KirimKritik()
    {
        //menampilkan progress dialog
        progressDialog.setMessage("Tunggu Sebentar");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String pesan = jsonObject.getString("message");

                            Toast.makeText(Kritik.this, pesan.toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Kritik.this, e.toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(Kritik.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("isi_kritik", Kritik);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Kritik.this);
        requestQueue.add(stringRequest);
    }
}
