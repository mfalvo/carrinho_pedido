package com.example.compras.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class Pedido {

    private Long id;
    private Cliente cliente;
    private List<PedidoItem> pedidoitens = new ArrayList<>();
    private Date datahora;
    private String status;
    private BigDecimal total;

    // Constructors

    public Pedido() {
        super();
    }

    public Pedido(Long id, Cliente cliente, List<PedidoItem> pedidoitens, Date datahora,
                  String status, BigDecimal total) {
        this.id = id;
        this.cliente = cliente;
        this.pedidoitens = pedidoitens;
        this.datahora = datahora;
        this.status = status;
        this.total = total;
    }

    // Getters and Setters


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

    public List<PedidoItem> getPedidoitens() {
        return pedidoitens;
    }

    public void setPedidoitens(List<PedidoItem> pedidoitens) {
        this.pedidoitens = pedidoitens;
    }

    public Date getDatahora() {
        return datahora;
    }

    public void setDatahora(Date datahora) {
        this.datahora = datahora;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
