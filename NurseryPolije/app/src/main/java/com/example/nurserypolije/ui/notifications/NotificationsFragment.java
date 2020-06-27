package com.example.nurserypolije.ui.notifications;
import com.example.nurserypolije.DikemasActivity;
import com.example.nurserypolije.DikirimActivity;
import com.example.nurserypolije.FrequentlyAskQuestion;
import com.example.nurserypolije.KeranjangActivity;
import com.example.nurserypolije.Kritik;
import com.example.nurserypolije.PetaLokasi;
import com.example.nurserypolije.TagihanActivity;
import com.example.nurserypolije.config.restServer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.nurserypolije.LoginActivity;
//import com.example.nurserypolije.PetaLokasi;
import com.example.nurserypolije.R;
import com.example.nurserypolije.RegisterActivity;
import com.example.nurserypolije.SessionManager;
import com.example.nurserypolije.SettingProfileActivity;
import com.example.nurserypolije.UbahProfile;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class NotificationsFragment extends Fragment {
    ImageView keranjang, ic_setting;
    ListView pesanan, fitur_lainnya;
    SessionManager sessionManager;
    RelativeLayout data;
    LinearLayout email, nohp, alamat, jk;
    Button daftar, login;
    TextView keterangan, namaprofile, emailprofile, nohpprofile, jkprofile, alamatprofile;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String Url = restServer.URL_PROFILE;
    String id, urlfoto, EMAIL;
    private NotificationsViewModel notificationsViewModel;
    @BindView(R.id.foto) com.mikhaellopez.circularimageview.CircularImageView fotoProfile;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        progressDialog = new ProgressDialog(getActivity());
        sessionManager = new SessionManager(getActivity());

        //bagian listview pesanan
        pesanan = root.findViewById(R.id.lv_pesanan);
        String[] setting = new String[] {
                "Tagihan",
                "Dikemas",
                "Dikirim"
        };
        List<String> item = new ArrayList<String>(Arrays.asList(setting));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_list_item_1, item);

        pesanan.setAdapter(arrayAdapter);
        pesanan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dipilih = (String) parent.getItemAtPosition(position);

                if (dipilih == "Tagihan")
                {
                    Intent tagihan = new Intent(getActivity(), TagihanActivity.class);
                    startActivity(tagihan);
//                    Toast.makeText( getActivity(), "Peta Lokasi Nursery Polije", Toast.LENGTH_SHORT ).show();
                }else if (dipilih == "Dikemas"){
                    Intent dikemas = new Intent(getActivity(), DikemasActivity.class);
                    startActivity(dikemas);
                }else if (dipilih == "Dikirim"){
                    Intent dikirim = new Intent(getActivity(), DikirimActivity.class);
                    startActivity(dikirim);
                }

            }
        });



        //bagian listview fitur
        fitur_lainnya = root.findViewById(R.id.lv_tambahan);
        String[] fitur = new String[] {
                "Lokasi Kami",
                "Bantuan",
                "Kritik Dan Saran"
        };


        List<String> item1 = new ArrayList<String>(Arrays.asList(fitur));

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_list_item_1, item1);


        fitur_lainnya.setAdapter(arrayAdapter1);
        fitur_lainnya.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dipilih = (String) parent.getItemAtPosition(position);

                if (dipilih == "Lokasi Kami")
                {
                    Intent pindah = new Intent(getActivity(), PetaLokasi.class);
                    startActivity(pindah);
                    Toast.makeText( getActivity(), "Peta Lokasi Nursery Polije", Toast.LENGTH_SHORT ).show();
                }else if(dipilih == "Kritik Dan Saran"){
                    Intent kritik = new Intent(getActivity(), Kritik.class);
                    startActivity(kritik);
                }else if (dipilih == "Bantuan") {
                    Intent bantu = new Intent(getActivity(), FrequentlyAskQuestion.class);
                    startActivity(bantu);
                }

            }
        });

//        fitur_lainnya.setAdapter(arrayAdapter3);
//        fitur_lainnya.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String diklik = (String) parent.getItemAtPosition(position);
//                if(diklik == "Kritik dan Saran")
//                {
//                    Intent kritik = new Intent(getActivity(), LoginActivity.class);
//                    startActivity(kritik);
//                }
//            }
//        });

        //bagian TextView dinamis dari database
        keterangan = root.findViewById(R.id.keterangan);
        namaprofile = root.findViewById(R.id.namaprofile);

        //bagian foto
        fotoProfile = root.findViewById(R.id.foto);

        //bagian tombol
        login = root.findViewById(R.id.login);
        daftar = root.findViewById(R.id.daftar);
        keranjang = root.findViewById(R.id.keranjang);
        ic_setting = root.findViewById(R.id.setting);

        //mengambil ID dari session manager
        HashMap<String, String> user = sessionManager.getUserDetail();
        id = user.get(sessionManager.ID);



        //data
        sessionManager = new SessionManager(getActivity());
        //mengambil ID dari session manager
        HashMap<String, String> cek = sessionManager.getUserDetail();
        EMAIL = cek.get(sessionManager.EMAIL);
        // jika keranjang dipencet
            keranjang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getContext(), KeranjangActivity.class);
                    startActivity(i);
                }
            });



        //jika setting di pencet
        ic_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.checkLogin();
                Intent i = new Intent(getContext(), SettingProfileActivity.class);
                startActivity(i);
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
                                    String ketdb = object.getString("waktu_pembuatan").trim();
                                    urlfoto = object.getString("foto");

                                    Picasso.get().load(restServer.URL_FOTO_PROFILE + urlfoto).into(fotoProfile);

                                    namaprofile.setText(namadb);
                                    keterangan.setText("Bergabung Sejak " + ketdb);

                                    //menghilangkan loading
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