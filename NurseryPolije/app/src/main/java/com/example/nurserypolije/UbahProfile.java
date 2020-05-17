package com.example.nurserypolije;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UbahProfile extends AppCompatActivity {

    EditText nama, nomor_telepon, alamat;
    Spinner jk;
    TextView pesan, daftar;
    Button simpan;
    Boolean cek;
    String namaH, nomor_teleponH, jkH, alamatH, email;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    SessionManager sessionManager;
    String Url = "http://192.168.43.11/nuporyV2/Justify/rest_ci/index.php/Profile/ubahProfil";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_update_profile);

        sessionManager = new SessionManager(UbahProfile.this);
        requestQueue = Volley.newRequestQueue(UbahProfile.this);
        progressDialog = new ProgressDialog(UbahProfile.this);

        nama = findViewById(R.id.edit_nama);
        nomor_telepon = findViewById(R.id.edit_notel);
        alamat = findViewById(R.id.edit_alamat);
        jk = findViewById(R.id.edit_jk);
        simpan = findViewById(R.id.simpan);
        pesan = findViewById(R.id.pesan);

        //mengambil data yg login dari session manager
        HashMap<String, String> user = sessionManager.getUserDetail();
        email = user.get(sessionManager.EMAIL);
        nama.setText(user.get(sessionManager.NAMA));
        alamat.setText(user.get(sessionManager.ALAMAT));
        nomor_telepon.setText(user.get(sessionManager.NOTEL));

        //Jika tombol simpan di pencet
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cek();
                if (cek){
                    simpan();
                    Intent intent = new Intent(UbahProfile.this, SettingProfileActivity.class);
                    startActivity(intent);
                } else {
                    pesan.setText("Harap Isi Semua Field!");
                }
            }
        });
    }

    //cek form kosong atau tidak
    public void cek()
    {
        namaH = nama.getText().toString().trim();
        nomor_teleponH = nomor_telepon.getText().toString().trim();
        jkH = jk.getSelectedItem().toString().trim();
        if(jkH == "Jenis Kelamin")
        {
            jkH = null;
        }
        alamatH = alamat.getText().toString().trim();
        if (TextUtils.isEmpty(namaH)
                || (TextUtils.isEmpty(nomor_teleponH))
                || (TextUtils.isEmpty(jkH))
                || (TextUtils.isEmpty(alamatH)))
        {
            cek = false;
        }else{
            cek =  true;
        }
    }

    //function simpan
    public void simpan()
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

                            Toast.makeText(UbahProfile.this, pesan.toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(UbahProfile.this, e.toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(UbahProfile.this, pesan.toString(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                })
        {
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                params.put("nama", namaH);
                params.put("alamat", alamatH);
                params.put("notel", nomor_teleponH);
                params.put("jk", jkH);
                params.put("email", email);
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(UbahProfile.this);
        requestQueue.add(stringRequest);
    }

}
