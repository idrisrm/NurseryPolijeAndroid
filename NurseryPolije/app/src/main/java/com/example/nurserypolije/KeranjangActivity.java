package com.example.nurserypolije;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeranjangActivity extends AppCompatActivity {
    String url = restServer.URL_KERANJANG_USER;
    String email, idTransaksi;
    SessionManager sessionManager;
//    ImageView fotoBunga;
//    TextView namaBunga, jumlahBeli, TotalHarga;
    TextView keteranganKeranjang;
    RecyclerView.LayoutManager mManager;
    List<ModelKeranjang> mItems;
    RecyclerView recyclerView;
    KeranjangAdapter mAdapter;
    Button checkout;
    ConstraintLayout aksi;

    private ArrayList<ModelKeranjang> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);

        mItems = new ArrayList<>();
        arrayList = new ArrayList<>();
        mAdapter = new KeranjangAdapter(KeranjangActivity.this, mItems);
        mManager = new LinearLayoutManager(KeranjangActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.rvkeranjang);
        recyclerView.setLayoutManager(mManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

        //checkout
        checkout = findViewById(R.id.btnCheckOutKeranjang);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KeranjangActivity.this, CheckOutActivity.class);
                intent.putExtra("idTransaksi", idTransaksi);
                startActivity(intent);
            }
        });

        aksi = findViewById(R.id.aksi);
        keteranganKeranjang = findViewById(R.id.keteranganKeranjang);

        sessionManager = new SessionManager(KeranjangActivity.this);
        //mengambil EMAIL dari session manager
        HashMap<String, String> user = sessionManager.getUserDetail();
        email = user.get(sessionManager.EMAIL);

        sessionManager.checkLogin();
        Keranjang();
    }


    private void Keranjang() {
        StringRequest sendData = new StringRequest(Request.Method.GET, url+"?email="+email, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    String message = jsonObject.getString("message");
                    if (success.equals("1")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("keranjang");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            ModelKeranjang md = new ModelKeranjang();


                            String nb = object.getString("nama_bunga");
                            idTransaksi = object.getString("id_transaksi");

                            md.setId_bunga(object.getString("id_bunga"));
                            md.setNama_bunga(object.getString("nama_bunga"));
                            md.setJumlah(object.getInt("jumlah"));
                            md.setFoto_bunga(object.getString("foto_bunga"));
                            mItems.add(md);
//                            Toast.makeText(KeranjangActivity.this, idTransaksi.toString(), Toast.LENGTH_SHORT).show();
//                            pb.setVisibility(View.GONE);
                            keteranganKeranjang.setVisibility(View.GONE);
                        }
                    } else {
                        aksi.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
//                    progressDialog.dismiss();
                    Toast.makeText(KeranjangActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
                Toast.makeText(KeranjangActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
//                params.put("email", email);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(KeranjangActivity.this);
        requestQueue.add(sendData);
//        AppController.getInstance().addToRequestQueue(sendData);
    }

}
