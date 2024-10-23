package com.iconeclass.verdurao.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Carrinho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name="idcliente_fk")
    private Cliente cliente;
    
    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL)
    private List<CarrinhoItem> items = new ArrayList<>();
    
    private BigDecimal Total;
    
    // getters e setters
    
    public Carrinho() {
		super();
	}

	public Carrinho(Long id, Cliente cliente, List<CarrinhoItem> items, BigDecimal total) {
		super();
		this.id = id;
		this.cliente = cliente;
		this.items = items;
		Total = total;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	/**
	 * @return the items
	 */
	public List<CarrinhoItem> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(List<CarrinhoItem> items) {
		this.items = items;
	}

	/**
	 * @return the total
	 */
	public BigDecimal getTotal() {
		return Total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(BigDecimal total) {
		Total = total;
	}
    
    
}