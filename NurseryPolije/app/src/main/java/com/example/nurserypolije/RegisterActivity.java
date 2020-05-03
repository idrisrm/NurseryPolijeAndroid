package com.example.nurserypolije;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText nama, nomor_telepon, email, password, jk, alamat;
    TextView pesan;
    Button btn_regist;
    Boolean cek;
    String namaH, nomor_teleponH, emailH, passwordH, jkH, alamatH;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    SessionManager sessionManager;
    String Url = "http://192.168.43.11/nuporyV2/Justify/rest_ci/index.php/Auth/daftar";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sessionManager = new SessionManager(RegisterActivity.this);
        requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        progressDialog = new ProgressDialog(RegisterActivity.this);
        nama = findViewById(R.id.regis_nama);
        nomor_telepon = findViewById(R.id.regis_notel);
        email = findViewById(R.id.regis_email);
        password = findViewById(R.id.regis_password);
        alamat = findViewById(R.id.regis_alamat);
        jk = findViewById(R.id.regis_jk);
        pesan = findViewById(R.id.pesan);
        btn_regist = findViewById(R.id.btn_regis);

        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cek1();
                if (cek){
                    Regist();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    pesan.setText("Harap Isi Semua Field!");
                }
            }
        });

    }

    //cek form kosong atau tidak
    public void cek1()
    {
        emailH = email.getText().toString().trim();
        namaH = nama.getText().toString().trim();
        passwordH = password.getText().toString().trim();
        nomor_teleponH = nomor_telepon.getText().toString().trim();
        jkH = jk.getText().toString().trim();
        alamatH = alamat.getText().toString().trim();
        if (TextUtils.isEmpty(emailH) || (TextUtils.isEmpty(namaH)) || (TextUtils.isEmpty(passwordH)) || (TextUtils.isEmpty(nomor_teleponH)) || (TextUtils.isEmpty(jkH)) || (TextUtils.isEmpty(alamatH)))
        {
            cek = false;
        }else{
            cek =  true;
        }
    }

    //Registrasi
    public void Regist(){

        //Menampilkan progres dialog
        progressDialog.setMessage("Tunggu sebentar");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String pesan = jsonObject.getString("message");

                            Toast.makeText(RegisterActivity.this, pesan.toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();

                params.put("nama", namaH);
                params.put("email1", emailH);
                params.put("nomor_telepon", nomor_teleponH);
                params.put("jenis_kelamin", jkH);
                params.put("alamat", alamatH);
                params.put("password", passwordH);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        requestQueue.add(stringRequest);
    }
}
