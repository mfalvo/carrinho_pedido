package com.example.compras.model;

import java.util.ArrayList;
import java.util.List;

public class Cliente {

    private String email; // Chave primaria
    private Carrinho carrinho = new Carrinho();
    private List<Pedido> pedidos = new ArrayList<>();
    private String senha;
    private String nome;
    private String imagem;
    // getters e setters

    public Cliente() {
    }

    public Cliente(String email, String senha, String nome, String imagem) {

        this.email = email;
        this.senha = senha;
        this.nome = nome;
        this.imagem = imagem;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
