package com.example.nurserypolije;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DikirimActivity extends AppCompatActivity {
    String url = restServer.URL_DIKIRIM;
    String email;
    TextView keterangan;
    SessionManager sessionManager;
    List<ModelDikirim> mItems;
    RecyclerView recyclerView;
    DikirimAdapter mAdapter;
    RecyclerView.LayoutManager mManager;
    ProgressBar progressBar;
    private ArrayList<ModelDikirim> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dikirim);

        mItems = new ArrayList<>();
        arrayList = new ArrayList<>();
        mAdapter = new DikirimAdapter(DikirimActivity.this, mItems);
        mManager = new LinearLayoutManager(DikirimActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.rvDikirim);
        recyclerView.setLayoutManager(mManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

        sessionManager = new SessionManager(DikirimActivity.this);
        //mengambil EMAIL dari session manager
        HashMap<String, String> user = sessionManager.getUserDetail();
        email = user.get(sessionManager.EMAIL);

        keterangan = findViewById(R.id.dataKosong);

        Dikirim();
    }



    private void Dikirim() {
        StringRequest sendData = new StringRequest(Request.Method.GET, url+"?email="+email, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    String message = jsonObject.getString("message");
                    if (success.equals("1")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("dikirim");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            ModelDikirim md = new ModelDikirim();

                            md.setId_transaksi(object.getString("id_transaksi"));
                            md.setTanggal_transaksi(object.getString("tanggal_transaksi"));
                            md.setAlamat_pengiriman(object.getString("alamat_pengiriman"));
                            mItems.add(md);
                            keterangan.setVisibility(View.GONE);
                        }
                    } else {
//                        aksi.setVisibility(View.GONE);
                        mAdapter.notifyDataSetChanged();
                        keterangan.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
//                    progressDialog.dismiss();
                    Toast.makeText(DikirimActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
                Toast.makeText(DikirimActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(DikirimActivity.this);
        requestQueue.add(sendData);
    }
}
