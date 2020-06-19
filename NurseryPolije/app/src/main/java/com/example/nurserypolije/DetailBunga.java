package com.example.nurserypolije;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.example.nurserypolije.config.restServer;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class DetailBunga extends AppCompatActivity {
TextView namaBunga, Deskripsi, Stok, hargaBunga;
ProgressBar progressBar;
ImageView fotoBunga;
SessionManager sessionManager;
String url = restServer.URL_BERANDA;
String urlkeranjang = restServer.URL_KERANJANG;
String idBunga, email, id_status_transaksi, jumlah, totalharga;
Button keranjang, beli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bunga);

        keranjang = findViewById(R.id.btnKeranjang);
        namaBunga = findViewById(R.id.tvNamaBunga);
        Deskripsi = findViewById(R.id.tvDeskripsi);
        Stok = findViewById(R.id.tvStok);
        fotoBunga = findViewById(R.id.ivBunga);
        hargaBunga = findViewById(R.id.tvHargaBunga);
        progressBar = findViewById(R.id.progressbar);
        idBunga = getIntent().getStringExtra("ID_BUNGA");

        //data keranjang
        sessionManager = new SessionManager(DetailBunga.this);
        //mengambil ID dari session manager
        HashMap<String, String> user = sessionManager.getUserDetail();
        email = user.get(sessionManager.EMAIL);
        id_status_transaksi = "1";
        jumlah = "1";

        //detail bunga
        loadJSON();

        keranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keranjang();
            }
        });
    }


    private void loadJSON() {
        StringRequest sendData = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    String message = jsonObject.getString("message");
                    JSONArray jsonArray = jsonObject.getJSONArray("bunga");
                    if (success.equals("1")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            ModelHome md = new ModelHome();

                            String nama_bungadb = object.getString("nama_bunga").trim();
                            String stokdb = object.getString("stok").trim();
                            String deskripsidb = object.getString("deskripsi").trim();
                            String urlfoto = object.getString("foto_bunga").trim();
                            String harga = object.getString("harga").trim();

                            hargaBunga.setText("Rp." + harga);
                            namaBunga.setText(nama_bungadb);
                            Stok.setText("Stok Bunga : " + stokdb);
                            Deskripsi.setText(deskripsidb);
                            progressBar.setVisibility(View.GONE);
                            totalharga = harga;
                            Picasso.get().load(restServer.URL_FOTO_BUNGA + urlfoto).into(fotoBunga);
                        }
                    } else {
                        Toast.makeText(DetailBunga.this, message.toString(), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(DetailBunga.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailBunga.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_bunga", idBunga);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(DetailBunga.this);
        requestQueue.add(sendData);
    }



    //volley keranjang
    private void keranjang() {
        StringRequest sendData = new StringRequest(Request.Method.POST, urlkeranjang, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    String message = jsonObject.getString("message");
                    if (success.equals("1")) {
                        Toast.makeText(DetailBunga.this, message.toString(), Toast.LENGTH_SHORT).show();
                        Intent k = new Intent(DetailBunga.this, KeranjangActivity.class);
                        startActivity(k);
                    } else {
                        Toast.makeText(DetailBunga.this, message.toString(), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(DetailBunga.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailBunga.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_bunga", idBunga);
                params.put("email", email);
                params.put("id_status_transaksi", id_status_transaksi);
                params.put("jumlah", jumlah);
                params.put("total_harga", totalharga);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(DetailBunga.this);
        requestQueue.add(sendData);
    }




}
