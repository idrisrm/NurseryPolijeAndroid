package com.example.nurserypolije;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.nurserypolije.config.restServer;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class DetailTagihan extends AppCompatActivity {
    String id_transaksi;
    String urltagihan = restServer.URL_TAGIHAN;
    String urldetail = restServer.URL_DETAIL_TAGIHAN;
    Button pilihFoto, uploadBukti;
    ImageView ivBukti;
    TextView Tagihan;
    Bitmap bitmap;
    final int CODE_GALLERY_REQUEST = 999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tagihan);

        id_transaksi = getIntent().getStringExtra("ID_TRANSAKSI");
        pilihFoto = findViewById(R.id.btnPilihFoto);
        uploadBukti = findViewById(R.id.btnUpload);
        ivBukti = findViewById(R.id.ivBukti);
        Tagihan = findViewById(R.id.tvTagihan);

        pilihFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        DetailTagihan.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                         CODE_GALLERY_REQUEST
                );
            }
        });

        uploadBukti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tagihan();
            }
        });
        DetailTagihan();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == CODE_GALLERY_REQUEST){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Image"), CODE_GALLERY_REQUEST);
            }else {
                Toast.makeText(getApplicationContext(), "You Don't Have Permissions To Access Gallery", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CODE_GALLERY_REQUEST && resultCode == RESULT_OK && data != null){
            Uri filePath = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                ivBukti.setImageBitmap(bitmap);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    private void Tagihan() {
        StringRequest sendData = new StringRequest(Request.Method.POST, urltagihan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    String message = jsonObject.getString("message");
                    if (success.equals("1")) {
                        Toast.makeText(DetailTagihan.this, message.toString(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailTagihan.this, message.toString(), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(DetailTagihan.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailTagihan.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                String ImageData = ImageToString(bitmap);
                params.put("id_transaksi", id_transaksi);
                params.put("bukti", ImageData);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(DetailTagihan.this);
        requestQueue.add(sendData);
    }

    private String ImageToString (Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return  encodedImage;
    }


    private void DetailTagihan() {
        StringRequest sendData = new StringRequest(Request.Method.GET, urldetail + "?id_transaksi=" + id_transaksi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    String message = jsonObject.getString("message");
                    JSONArray jsonArray = jsonObject.getJSONArray("tagihan");
                    if (success.equals("1")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String tagihan = object.getString("total").trim();
                            String bukti = object.getString("bukti").trim();
                            Tagihan.setText( "Tagihan Anda sebesar : " + tagihan);
                            if(bukti.equals("")){
                                Picasso.get().load(restServer.URL_FOTO_BUKTI + "placeholder.jpg").into(ivBukti);
                            }else {
                                Picasso.get().load(restServer.URL_FOTO_BUKTI + bukti).into(ivBukti);
                            }
                        }
                    } else {
                        Toast.makeText(DetailTagihan.this, message.toString(), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(DetailTagihan.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailTagihan.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(DetailTagihan.this);
        requestQueue.add(sendData);
    }

}
