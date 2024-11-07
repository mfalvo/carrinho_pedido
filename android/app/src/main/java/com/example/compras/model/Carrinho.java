package com.example.compras.model;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Carrinho {
    private Long id;
    private Cliente cliente;
    private List<CarrinhoItem> carrinhoitems = new ArrayList<>();
    private BigDecimal total = new BigDecimal(0);
    // Getters and Setters

    public Carrinho() {
    }

    public Carrinho(Long id, Cliente cliente, List<CarrinhoItem> carrinhoitems, BigDecimal total) {
        this.id = id;
        this.cliente = cliente;
        this.carrinhoitems = carrinhoitems;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<CarrinhoItem> getCarrinhoitems() {
        return carrinhoitems;
    }

    public void setCarrinhoitems(List<CarrinhoItem> carrinhoitems) {
        this.carrinhoitems = carrinhoitems;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
