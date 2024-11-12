package com.example.compras.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.compras.model.Cliente;
import com.google.gson.Gson;

public class SharedPrefManager {
    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_CLIENTE = "Cliente";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;

    public SharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();
    }

    public void saveCliente(Cliente cliente) {
        String json = gson.toJson(cliente);
        editor.putString(KEY_CLIENTE, json);
        editor.apply();
    }

    public Cliente getCliente() {
        String json = sharedPreferences.getString(KEY_CLIENTE, null);
        return gson.fromJson(json, Cliente.class);
    }

    public void clear() {
        editor.clear();
        editor.apply();
    }
}
