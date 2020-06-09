package com.example.nurserypolije;

import com.example.nurserypolije.config.restServer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagihanActivity extends AppCompatActivity {
    String url = restServer.URL_TAGIHAN;
    String email;
    TextView keterangan;
    SessionManager sessionManager;
    List<ModelTagihan> mItems;
    RecyclerView recyclerView;
    TagihanAdapter mAdapter;
    RecyclerView.LayoutManager mManager;
    private ArrayList<ModelTagihan> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagihan);

        mItems = new ArrayList<>();
        arrayList = new ArrayList<>();
        mAdapter = new TagihanAdapter(TagihanActivity.this, mItems);
        mManager = new LinearLayoutManager(TagihanActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.rvTagihan);
        recyclerView.setLayoutManager(mManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

        sessionManager = new SessionManager(TagihanActivity.this);
        //mengambil EMAIL dari session manager
        HashMap<String, String> user = sessionManager.getUserDetail();
        email = user.get(sessionManager.EMAIL);

        keterangan = findViewById(R.id.keteranganTagihan);

        Tagihan();
    }



    private void Tagihan() {
        StringRequest sendData = new StringRequest(Request.Method.GET, url+"?email="+email, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    String message = jsonObject.getString("message");
                    if (success.equals("1")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("tagihan");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            ModelTagihan md = new ModelTagihan();

                            md.setId_transaksi(object.getString("id_transaksi"));
                            md.setTanggal_transaksi(object.getString("tanggal_transaksi"));
                            md.setAlamat_pengiriman(object.getString("alamat_pengiriman"));
                            md.setTotal(object.getString("total"));
                            mItems.add(md);
                            keterangan.setVisibility(View.GONE);
                        }
                    } else {
//                        aksi.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
//                    progressDialog.dismiss();
                    Toast.makeText(TagihanActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
                Toast.makeText(TagihanActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(TagihanActivity.this);
        requestQueue.add(sendData);
    }
}
