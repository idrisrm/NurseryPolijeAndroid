package com.example.nurserypolije.ui.notifications;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nurserypolije.LoginActivity;
import com.example.nurserypolije.R;
import com.example.nurserypolije.RegisterActivity;
import com.example.nurserypolije.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NotificationsFragment extends Fragment {
    ImageView keranjang, setting;
    SessionManager sessionManager;
    RelativeLayout data;
    LinearLayout email, nohp, alamat, jk;
    Button daftar, login, logout;
    TextView keterangan, namaprofile, emailprofile, nohpprofile, jkprofile;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String Url = "http://192.168.43.243/nuporyV2/Justify/rest_ci/index.php/Profile";
    String id;
    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        progressDialog = new ProgressDialog(getActivity());
        sessionManager = new SessionManager(getActivity());

        //bagian data dinamis dari database
        keterangan = root.findViewById(R.id.keterangan);
        namaprofile = root.findViewById(R.id.namaprofile);
        emailprofile = root.findViewById(R.id.emailprofile);
        nohpprofile = root.findViewById(R.id.nohpprofile);
        jkprofile = root.findViewById(R.id.jkprofile);
//        alamatprofile = root.findViewById(R.id.profile_alamat);

        //bagian statis
        email = root.findViewById(R.id.email);
        nohp = root.findViewById(R.id.nohp);
        alamat = root.findViewById(R.id.alamat);
        jk = root.findViewById(R.id.jk);
        daftar = root.findViewById(R.id.daftar);

        //bagian tombol
        login = root.findViewById(R.id.login);
        logout = root.findViewById(R.id.logout);
        keranjang = root.findViewById(R.id.keranjang);
        setting = root.findViewById(R.id.setting);

        //mengambil ID dari session manager
        HashMap<String, String> user = sessionManager.getUserDetail();
        id = user.get(sessionManager.ID);

        // jika keranjang dipencet
        keranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.checkLogin();
            }
        });

        //jika tombol logout dipencet
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logout();
            }
        });

        //jika tombol login dipencet
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        //jika tombol daftar dipencet
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), RegisterActivity.class);
                startActivity(i);
            }
        });

        //pengecekan sudah login atau tidak
        if (sessionManager.isLoggin() == true)
        {
            profile();
            daftar.setVisibility(View.GONE);
            login.setVisibility(View.GONE);
        }else{
            email.setVisibility(View.GONE);
            nohp.setVisibility(View.GONE);
            alamat.setVisibility(View.GONE);
            jk.setVisibility(View.GONE);
            logout.setVisibility(View.GONE);
            keterangan.setText("Anda Belum Login");
        }

        return root;
    }

    public void profile(){
    //menampilkan progress dialog
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            JSONArray jsonArray = jsonObject.getJSONArray("profile");

                            if (success.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String namadb = object.getString("nama").trim();
                                    String emaildb = object.getString("email").trim();
                                    String nohpdb = object.getString("no_telepon").trim();
//                                    String alamatdb = object.getString("alamat").trim();
                                    String jkdb = object.getString("jenis_kelamin").trim();
                                    String ketdb = object.getString("waktu_pembuatan").trim();


                                    namaprofile.setText(namadb);
                                    emailprofile.setText(emaildb);
                                    nohpprofile.setText(nohpdb);
                                    jkprofile.setText(jkdb);
//                                    alamatprofile.setText(alamatdb);
                                    progressDialog.dismiss();
                                }
                            }else{
                                Toast.makeText(getActivity(), message.toString(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
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
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}