package com.example.compras.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.compras.model.Cliente;
import com.google.gson.Gson;

public class SharedPrefManager {
    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_CLIENTE = "Cliente";
    private static final String KEY_LPRODUTOS = "LProdutos";
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

    public void saveLProdutos(LProdutos listaProdutos){
        String json = gson.toJson(listaProdutos);
        editor.putString(KEY_LPRODUTOS, json);
        editor.apply();

    }

    public Cliente getCliente() {
        boolean clienteLogado = !(sharedPreferences.getString(KEY_CLIENTE, null).equals("Cliente"));
        if (clienteLogado){
            String json = sharedPreferences.getString(KEY_CLIENTE, null);
            return gson.fromJson(json, Cliente.class);}
        return null;
    }

    public LProdutos getLProdutos() {
        String json = sharedPreferences.getString(KEY_LPRODUTOS, null);
        return gson.fromJson(json, LProdutos.class);
    }

    public void clear() {
        editor.clear();
        editor.apply();
    }

    public void resetKeys() {
        String keyLProdutos = "LProdutos";
        String keyCliente = "Cliente";
        editor.putString(KEY_LPRODUTOS, keyLProdutos);
        editor.apply();
        editor.putString(KEY_CLIENTE, keyCliente);
        editor.apply();
    }
}
