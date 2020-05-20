package com.example.nurserypolije.ui.home;
import com.example.nurserypolije.config.restServer;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nurserypolije.HomeAdapter;
import com.example.nurserypolije.ModelHome;
import com.example.nurserypolije.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    String url = restServer.URL_BERANDA;
    RecyclerView.LayoutManager mManager;
    List<ModelHome> mItems;
    RecyclerView recyclerView;
    HomeAdapter mAdapter;
    ProgressBar pb;
    JSONArray arr;
    TextView dataKosong;
    ProgressDialog progressDialog;


    private ArrayList<ModelHome> arrayList;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        pb = root.findViewById(R.id.progressbar);

        progressDialog = new ProgressDialog(getActivity());
        mItems = new ArrayList<>();
        arrayList = new ArrayList<>();
        mAdapter = new HomeAdapter(getContext(), mItems);
        mManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView = root.findViewById(R.id.rvBunga);
        recyclerView.setLayoutManager(mManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

        loadJSON();

        return root;
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

                                md.setId_bunga(object.getString("id_bunga"));
                                md.setNama_bunga(object.getString("nama_bunga"));
                                md.setId_kategori(object.getString("id_kategori"));
                                md.setStok(object.getInt("stok"));
                                md.setHarga(object.getString("harga"));
                                md.setVideo_bunga(object.getString("video_bunga"));
                                md.setFoto_bunga(object.getString("foto_bunga"));
                                md.setDeskripsi(object.getString("deskripsi"));
                                md.setCara_perawatan(object.getString("cara_perawatan"));
                                mItems.add(md);
                                pb.setVisibility(View.GONE);
                        }
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), message.toString(), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(sendData);
//        AppController.getInstance().addToRequestQueue(sendData);
    }

//    @Override
//    public void onResume() {
//        loadJSON();
//        super.onResume();
//    }
}