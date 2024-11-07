package com.example.compras.model;

import java.math.BigDecimal;

public class CarrinhoItem {

    private Long id;
    private Carrinho carrinho;
    private Produto produto;
    private int quantidade;
    private boolean selecionado = true;
    private BigDecimal preco;
}
