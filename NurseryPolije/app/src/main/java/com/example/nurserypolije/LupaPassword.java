package com.example.nurserypolije;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LupaPassword extends AppCompatActivity {
    EditText Email;
    TextView pesan;
    Button kirim, kembali;
    Boolean cek;
    String EmailHolder, PasswordHolder;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    SessionManager sessionManager;
    String Url = "http://192.168.18.18/nuporyV2/Justify/rest_ci/index.php/Auth/lupa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);

        sessionManager = new SessionManager(LupaPassword.this);
        requestQueue = Volley.newRequestQueue(LupaPassword.this);
        progressDialog = new ProgressDialog(LupaPassword.this);
        Email = findViewById(R.id.emaill);
        kirim = findViewById(R.id.btnkirim);
        kembali = findViewById(R.id.kembali);

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kembali = new Intent(LupaPassword.this, LoginActivity.class);
                startActivity(kembali);
            }
        });

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekForm();
                if (cek){
                    kirimEmail();
                }else{
                    pesan.setText("Harap Isi Semua Field!");
                }
            }
        });
    }

    //cek form kosong atau tidak
    public void cekForm()
    {
        EmailHolder = Email.getText().toString().trim();
        if (TextUtils.isEmpty(EmailHolder))
        {
            cek = false;
        }else{
            cek =  true;
        }
    }


    //fungsi kirim email
    public void kirimEmail()
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

                            Toast.makeText(LupaPassword.this, pesan.toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LupaPassword.this, e.toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();

                            Toast.makeText(LupaPassword.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("email", EmailHolder);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(LupaPassword.this);
        requestQueue.add(stringRequest);

    }

}
