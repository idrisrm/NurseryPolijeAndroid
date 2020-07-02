package com.example.nurserypolije;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nurserypolije.config.restServer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CheckOutActivity extends AppCompatActivity {
    String idTransaksi, totalHarga, email, alamat;
    String url = restServer.URL_CHECKOUT;
    TextView tvTotalHarga;
    SessionManager sessionManager;
    EditText etAlamat;
    Button kembali, CheckOut;
    Boolean cek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        //Total Harga
        tvTotalHarga = findViewById(R.id.tvTotalHargaDB);

        sessionManager = new SessionManager(CheckOutActivity.this);
        //mengambil EMAIL dari session manager
        HashMap<String, String> user = sessionManager.getUserDetail();
        email = user.get(sessionManager.EMAIL);

        //alamat
        etAlamat = findViewById(R.id.etAlamat);
//        alamat = etAlamat.getText().toString().trim();

        //kembali
        kembali = findViewById(R.id.btnKembaliCheckOut);
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kembali = new Intent(CheckOutActivity.this, KeranjangActivity.class);
                startActivity(kembali);
            }
        });

        //CheckOut
        CheckOut = findViewById(R.id.btnCheckOutCheckOut);
        CheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekField();
                if (cek){
                    CheckOut();
                }else{
                    Toast.makeText(CheckOutActivity.this, "Alamat Harus Diisi", Toast.LENGTH_SHORT).show();
                }

            }
        });

        idTransaksi = getIntent().getStringExtra("idTransaksi");
//        Toast.makeText(CheckOutActivity.this, alamat.toString(), Toast.LENGTH_SHORT).show();
        Keranjang();
    }

    public void cekField()
    {
        alamat = etAlamat.getText().toString().trim();
        if (TextUtils.isEmpty(alamat)){
            cek = false;
        }else {
            cek = true;
        }
    }


    private void Keranjang() {
        StringRequest sendData = new StringRequest(Request.Method.GET, url+"?email="+email, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    String message = jsonObject.getString("message");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (success.equals("1")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            totalHarga = object.getString("total");
                            tvTotalHarga.setText("Rp. " + totalHarga);
                        }
                    } else {
//                        progressDialog.dismiss();
                        Toast.makeText(CheckOutActivity.this, message.toString(), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
//                    progressDialog.dismiss();
                    Toast.makeText(CheckOutActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
                Toast.makeText(CheckOutActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
//                params.put("email", email);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(CheckOutActivity.this);
        requestQueue.add(sendData);
    }



    private void CheckOut() {
        StringRequest sendData = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    String message = jsonObject.getString("message");
                    if (success.equals("1")) {
                        Toast.makeText(CheckOutActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                        Intent k = new Intent(CheckOutActivity.this, TagihanActivity.class);
                        startActivity(k);
                    } else {
//                        progressDialog.dismiss();
                        Toast.makeText(CheckOutActivity.this, message.toString(), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
//                    progressDialog.dismiss();
                    Toast.makeText(CheckOutActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
                Toast.makeText(CheckOutActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_transaksi", idTransaksi);
                params.put("alamat", alamat);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(CheckOutActivity.this);
        requestQueue.add(sendData);
    }


}
