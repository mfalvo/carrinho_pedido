package com.iconeclass.verdurao.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Cliente {
	
	@Id
	@Column(length=50)
	private String email; // Chave primaria
	
	@OneToOne
	@JoinColumn(name="idcarrinho_fk")
    private Carrinho carrinho;
	
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
	private List<Pedido> items = new ArrayList<>();
	
	@Column(length=50)
	private String senha;
	
	@Column(length=50)
	private String nome;
	
	@Column(length=255)
	private String imagem;

	
	
	// getters e setters
	
	public Cliente() {
		super();
	}



	public Cliente(String email, Carrinho carrinho, List<Pedido> items, String senha, String nome, String imagem) {
		super();
		this.email = email;
		this.carrinho = carrinho;
		this.items = items;
		this.senha = senha;
		this.nome = nome;
		this.imagem = imagem;
	}



	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}



	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}



	/**
	 * @return the carrinho
	 */
	public Carrinho getCarrinho() {
		return carrinho;
	}



	/**
	 * @param carrinho the carrinho to set
	 */
	public void setCarrinho(Carrinho carrinho) {
		this.carrinho = carrinho;
	}



	/**
	 * @return the items
	 */
	public List<Pedido> getItems() {
		return items;
	}



	/**
	 * @param items the items to set
	 */
	public void setItems(List<Pedido> items) {
		this.items = items;
	}



	/**
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}



	/**
	 * @param senha the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}



	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}



	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}



	/**
	 * @return the imagem
	 */
	public String getImagem() {
		return imagem;
	}



	/**
	 * @param imagem the imagem to set
	 */
	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	
	
	
}
