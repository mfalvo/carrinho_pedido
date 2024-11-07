package com.example.compras.model;

import java.math.BigDecimal;

public class PedidoItem {
    private Long id;
    private Pedido pedido;
    private Produto produto;
    private Integer quantidade;
    private BigDecimal preco;
    // Constructors
    public PedidoItem() {
        super();
    }
    public PedidoItem(Long id, Pedido pedido, Produto produto, Integer quantidade,
                      BigDecimal preco) {
        this.id = id;
        this.pedido = pedido;
        this.produto = produto;
        this.quantidade = quantidade;
        this.preco = preco;
    }
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
}
