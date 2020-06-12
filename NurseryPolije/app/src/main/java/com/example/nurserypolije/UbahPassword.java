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

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class UbahPassword extends AppCompatActivity {
    EditText password, passwordbaru, konfirmasi;
    Button simpan, batal;
    Boolean cek;
    ProgressDialog progressDialog;
    String passlama, passbaru, konpass, email;
    RequestQueue requestQueue;
    SessionManager sessionManager;
    TextView pesan;
    //String url = "http://192.168.43.11/nuporyV2/Justify/rest_ci/index.php/Profile/ubah";
    String url = "http://192.168.18.18/nuporyV2/Justify/rest_ci/index.php/Profile/ubah"; //ip sayyid

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_password);

        sessionManager = new SessionManager(UbahPassword.this);
        requestQueue = Volley.newRequestQueue(UbahPassword.this);
        progressDialog = new ProgressDialog(UbahPassword.this);
        password = findViewById(R.id.pslama);
        passwordbaru = findViewById(R.id.psbaru);
        konfirmasi = findViewById(R.id.konfir);
        simpan = findViewById(R.id.simp);
        batal = findViewById(R.id.btl);
        pesan = findViewById(R.id.pesan);


        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent batal = new Intent(UbahPassword.this, SettingProfileActivity.class);
                startActivity(batal);
            }
        });


        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekform();
                if(cek)
                {
                    ubahpass();
//                    Intent pindah = new Intent(UbahPassword.this, UbahProfile.class);
//                    startActivity(pindah);
                }else{
                    pesan.setText("Harap isi Semua Field!");
                }
            }
        });


        //mengambil email dari session manager
        HashMap<String, String> user = sessionManager.getUserDetail();
        email = user.get(sessionManager.EMAIL);
    }




    //function cek form
    public void cekform()
    {
        passlama = password.getText().toString().trim();
        passbaru = passwordbaru.getText().toString().trim();
        konpass = konfirmasi.getText().toString().trim();
        if (TextUtils.isEmpty(passlama) || TextUtils.isEmpty(passbaru) || TextUtils.isEmpty(konpass))
        {
            cek = false;
        }else{
            cek = true;
        }
    }

    //function ubah password
    public void ubahpass()
    {
        //menampilkan progress dialog
        progressDialog.setMessage("Tunggu Sebentar");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String pesan = jsonObject.getString("message");

                            Toast.makeText(UbahPassword.this, pesan.toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(UbahPassword.this, e.toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(UbahPassword.this, pesan.toString(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                })
        {
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                params.put("password", passlama);
                params.put("passwordbaru", passbaru);
                params.put("konfirmasi", konpass);
                params.put("email", email);
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(UbahPassword.this);
        requestQueue.add(stringRequest);
    }
}
