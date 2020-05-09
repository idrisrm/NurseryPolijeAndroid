package com.example.nurserypolije;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

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
    String Url = "http://192.168.43.11/nuporyV2/Justify/rest_ci/index.php/Auth/daftar";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_update_profile);

        sessionManager = new SessionManager(UbahProfile.this);
        requestQueue = Volley.newRequestQueue(UbahProfile.this);
        progressDialog = new ProgressDialog(UbahProfile.this);

        nama = findViewById(R.id.regis_nama);
        nomor_telepon = findViewById(R.id.regis_notel);
        alamat = findViewById(R.id.regis_alamat);
        jk = findViewById(R.id.regis_jk);
        pesan = findViewById(R.id.pesan);
        simpan = findViewById(R.id.simpan);

        //mengambil email dari session manager
        HashMap<String, String> user = sessionManager.getUserDetail();
        email = user.get(sessionManager.EMAIL);


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
