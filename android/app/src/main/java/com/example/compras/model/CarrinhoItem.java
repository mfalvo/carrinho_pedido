package com.example.compras.model;

import java.math.BigDecimal;

public class CarrinhoItem {

    private Long id;
    private Carrinho carrinho;
    private Produto produto;
    private int quantidade;
    private boolean selecionado = true;
    private BigDecimal preco;

    public CarrinhoItem() {
        super();
    }

    public CarrinhoItem(Carrinho carrinho, Produto produto, int quantidade, boolean selecionado, BigDecimal preco) {
        this.carrinho = carrinho;
        this.produto = produto;
        this.quantidade = quantidade;
        this.selecionado = selecionado;
        this.preco = preco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
}
