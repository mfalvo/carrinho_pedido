package com.iconeclass.verdurao.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="idcliente_fk")
	private Cliente cliente;
	
	@OneToMany(mappedBy="pedido")
	private List<PedidoItem> itens = new ArrayList();
	
	private Date datahora;
	
	@Column(length=30)
	private String status;
	
	private BigDecimal Total;
	
	//Getters and Setters
	
	public Pedido() {
		super();
	}

	public Pedido(Long id, Cliente cliente, List<PedidoItem> itens, Date datahora, String status, BigDecimal total) {
		super();
		this.id = id;
		this.cliente = cliente;
		this.itens = itens;
		this.datahora = datahora;
		this.status = status;
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
	 * @return the itens
	 */
	public List<PedidoItem> getItens() {
		return itens;
	}

	/**
	 * @param itens the itens to set
	 */
	public void setItens(List<PedidoItem> itens) {
		this.itens = itens;
	}

	/**
	 * @return the datahora
	 */
	public Date getDatahora() {
		return datahora;
	}

	/**
	 * @param datahora the datahora to set
	 */
	public void setDatahora(Date datahora) {
		this.datahora = datahora;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
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
