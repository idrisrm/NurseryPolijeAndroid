package com.example.nurserypolije;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    TextView daftar, pesan;
    String EmailHolder, PasswordHolder;
    EditText email, password;
    Boolean cek;
    Button login;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    SessionManager sessionManager;
    String Url = "http://192.168.43.243/nuporyV2/Justify/rest_ci/index.php/Auth";
//    String Url = "http://192.168.43.243/yt/login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionManager = new SessionManager(LoginActivity.this);
        requestQueue = Volley.newRequestQueue(LoginActivity.this);
        progressDialog = new ProgressDialog(LoginActivity.this);
        daftar = findViewById(R.id.daftar);
        email = findViewById(R.id.etemail);
        password = findViewById(R.id.etpassword);
        login = findViewById(R.id.btnlogin);
        pesan = findViewById(R.id.pesan);

//        daftar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getBaseContext(), MainActivity.class));
//            }
//        });
//
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekField();
                if (cek){
                    loginUser();
                }else{
                    pesan.setText("Harap Isi Semua Field!");
                }
            }
        });


    }

    //cek field kosong atau tidak
    public void cekField()
    {
        EmailHolder = email.getText().toString().trim();
        PasswordHolder = password.getText().toString().trim();
        if (TextUtils.isEmpty(EmailHolder) || (TextUtils.isEmpty(PasswordHolder))){
            cek = false;
        }else {
            cek = true;
        }
    }



//    public void login()
//    {
//        progressDialog.setMessage("Tunggu Sebentar");
//        progressDialog.show();
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String ServerResponse) {
//                        progressDialog.dismiss();
//
//                        if (ServerResponse.equalsIgnoreCase("Berhasil")){
//                            Toast.makeText(MainActivity.this, "Berhasil Login", Toast.LENGTH_SHORT).show();
//                        }else {
//                            Toast.makeText(MainActivity.this, "Gagal Login", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                })
//    }

    // membuat fungsi login
    public void loginUser() {
        //menampilkan progress dialog
        progressDialog.setMessage("Tunggu Sebentar");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        try {

                            //mengambil data pada json
                            JSONObject jsonObject = new JSONObject(ServerResponse);
                            String success = jsonObject.getString("success");
                            String pesan = jsonObject.getString("message");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if (success.equals("1")){
                                for (int i = 0; i<jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String id = object.getString("id").trim();
                                    String nama = object.getString("nama").trim();

                                    //membuat session saat berhasil login
                                    sessionManager.createSession(id, nama);

                                    Toast.makeText(LoginActivity.this, "Selamat Datang "+ nama , Toast.LENGTH_SHORT).show();
                                    finish();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    progressDialog.dismiss();
                                }
                            } else{
                                Toast.makeText(LoginActivity.this, pesan.toString(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();

                        Toast.makeText(LoginActivity.this, volleyError.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("email", EmailHolder);
                params.put("password", PasswordHolder);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(stringRequest);
    }


}

