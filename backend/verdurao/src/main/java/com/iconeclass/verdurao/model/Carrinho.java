package com.iconeclass.verdurao.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
    
    @OneToOne(mappedBy="carrinho")
    @JsonBackReference
    private Cliente cliente;
    
    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CarrinhoItem> carrinhoitems = new ArrayList<>();
    
    private BigDecimal total = new BigDecimal(0);
    // getters e setters

	public Carrinho() {
		super();
	}
	public Carrinho(Long id, Cliente cliente, List<CarrinhoItem> carrinhoitems, BigDecimal total) {
		super();
		this.id = id;
		this.cliente = cliente;
		this.carrinhoitems = carrinhoitems;
		this.total = total;
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
	 * @return the carrinhoitems
	 */
	public List<CarrinhoItem> getCarrinhoitems() {
		return carrinhoitems;
	}
	/**
	 * @param carrinhoitems the carrinhoitems to set
	 */
	public void setCarrinhoitems(List<CarrinhoItem> carrinhoitems) {
		this.carrinhoitems = carrinhoitems;
	}
	/**
	 * @return the total
	 */
	public BigDecimal getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
    
   
    
}