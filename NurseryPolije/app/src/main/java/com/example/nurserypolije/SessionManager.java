package com.example.nurserypolije;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String NAMA = "NAMA";
    public static final String EMAIL = "EMAIL";
    public static final String ALAMAT = "ALAMAT";
    public static final String NOTEL = "NOTEL";
    public static final String JK = "JK";
    public static final String FOTO = "FOTO";
    public static final String ID = "ID";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String id ,String nama, String email, String alamat, String notel, String jk, String foto){

        editor.putBoolean(LOGIN, true);
        editor.putString(ID, id);
        editor.putString(NAMA, nama);
        editor.putString(EMAIL, email);
        editor.putString(ALAMAT, alamat);
        editor.putString(NOTEL, notel);
        editor.putString(JK, jk);
        editor.putString(FOTO, foto);
        editor.apply();

    }

    public boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLogin(){

        if (!this.isLoggin()){
            Intent i = new Intent(context, LoginActivity.class);
            context.startActivity(i);
            ((MainActivity) context).finish();
        }
    }

    public HashMap<String, String> getUserDetail(){

        HashMap<String, String> user = new HashMap<>();
        user.put(NAMA, sharedPreferences.getString(NAMA, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(ALAMAT, sharedPreferences.getString(ALAMAT, null));
        user.put(NOTEL, sharedPreferences.getString(NOTEL, null));
        user.put(JK, sharedPreferences.getString(JK, null));
        user.put(FOTO, sharedPreferences.getString(FOTO, null));
        user.put(ID, sharedPreferences.getString(ID, null));

        return user;
    }

    public void logout(){

        editor.clear();
        editor.commit();
        Intent i = new Intent(context, MainActivity.class);
        context.startActivity(i);
        ((MainActivity) context).finish();

    }

}
