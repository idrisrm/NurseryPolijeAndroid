package com.example.nurserypolije;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class DetailBunga extends AppCompatActivity {
TextView namaBunga, Deskripsi, Stok;
ProgressBar progressBar;
ImageView fotoBunga;
String url = restServer.URL_BERANDA;
String idBunga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bunga);

        namaBunga = findViewById(R.id.tvNamaBunga);
        Deskripsi = findViewById(R.id.tvDeskripsi);
        Stok = findViewById(R.id.tvStok);
        fotoBunga = findViewById(R.id.ivBunga);
        progressBar = findViewById(R.id.progressbar);
        idBunga = getIntent().getStringExtra("ID_BUNGA");

        loadJSON();
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

                            namaBunga.setText(nama_bungadb);
                            Stok.setText("Stok Bunga : " + stokdb);
                            Deskripsi.setText(deskripsidb);
                            progressBar.setVisibility(View.GONE);
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




}
