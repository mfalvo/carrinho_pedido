package com.example.compras.utils;

import com.example.compras.model.Produto;

import java.util.List;
public class LProdutos {

    private List<Produto> listaProdutos;

    public LProdutos(List<Produto> listaProdutos) {
        this.listaProdutos = listaProdutos;
    }
    public LProdutos() {
    }

    public List<Produto> getLProdutos() {
        return listaProdutos;
    }

    public void setLProdutos(List<Produto> listaProdutos) {
        this.listaProdutos = listaProdutos;
    }
}
