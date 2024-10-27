package com.iconeclass.verdurao.controller;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iconeclass.verdurao.model.CarrinhoItem;
import com.iconeclass.verdurao.model.Carrinho;
import com.iconeclass.verdurao.model.Cliente;
import com.iconeclass.verdurao.model.Produto;
import com.iconeclass.verdurao.repository.CarrinhoItemRepository;
import com.iconeclass.verdurao.repository.ClienteRepository;
import com.iconeclass.verdurao.repository.ProdutoRepository;

@RestController
@RequestMapping("/carrinhoitem")
public class CarrinhoItemController {

    @Autowired
    private CarrinhoItemRepository carrinhoItemRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private ProdutoRepository produtoRepository;

    // Fornece um endpoint para listar todos os itens de carrinho
    @GetMapping
    public Iterable<CarrinhoItem> getAllCarrinhoItens() {
        return carrinhoItemRepository.findAll();
    }
    
    // Fornece um endpoint para listar um item de carrinho por seu Id
    @GetMapping("/{id}")
    public ResponseEntity<CarrinhoItem> getCarrinhoItemById(@PathVariable Long id) {
        Optional<CarrinhoItem> carrinhoItem = carrinhoItemRepository.findById(id);
        if (carrinhoItem.isPresent()) {
            return ResponseEntity.ok(carrinhoItem.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Fornece um endpoint para criar um item de carrinho
    @PostMapping("/{email}/{idproduto}/{quantidade}/{preco}")
    public ResponseEntity<CarrinhoItem> createCarrinhoItem(@PathVariable String email, 
    		@PathVariable Long idproduto, @PathVariable Integer quantidade, 
    		@PathVariable BigDecimal preco) 
    {
        Optional<Cliente> optionalCliente = clienteRepository.findById(email);
        if (optionalCliente.isPresent()) 
        {
        	Optional<Produto> optionalProduto = produtoRepository.findById(idproduto);
        	CarrinhoItem carrinhoitem;
        	if(optionalProduto.isPresent()) 
        	{
        		Cliente cliente = optionalCliente.get();
        		Produto produto = optionalProduto.get();
        		carrinhoitem = new CarrinhoItem();
        		
        		if(cliente.getCarrinho() == null) {
        			Carrinho carrinho = new Carrinho();
        			carrinho.setTotal(new BigDecimal(0));
        			cliente.setCarrinho(carrinho);
        		}
        		carrinhoitem.setPreco(preco);
        		carrinhoitem.setProduto(produto);
        		carrinhoitem.setQuantidade(quantidade);
        		CarrinhoItem updatedCarrinho = carrinhoItemRepository.save(carrinhoitem);    		
        		cliente.getCarrinho().getCarrinhoitems().add(carrinhoitem);
        		clienteRepository.save(cliente);
        		return ResponseEntity.ok(updatedCarrinho);
        	}
        	
        }
        return ResponseEntity.notFound().build();
    }

    
    // Fornece um endpoint para atualizar um item de carrinho
    @PutMapping("/{id}")
    public ResponseEntity<CarrinhoItem> updateCarrinhoItem(@PathVariable Long id, @RequestBody CarrinhoItem carrinhoItemDetails) {
        Optional<CarrinhoItem> optionalCarrinhoItem = carrinhoItemRepository.findById(id);
        if (optionalCarrinhoItem.isPresent()) {
            CarrinhoItem carrinhoItem = optionalCarrinhoItem.get();
            carrinhoItem.setCarrinho(carrinhoItemDetails.getCarrinho());
            carrinhoItem.setProduto(carrinhoItemDetails.getProduto());
            carrinhoItem.setQuantidade(carrinhoItemDetails.getQuantidade());
            carrinhoItem.setPreco(carrinhoItemDetails.getPreco());
            // Atualize outros campos conforme necessário

            final CarrinhoItem updatedCarrinhoItem = carrinhoItemRepository.save(carrinhoItem);
            return ResponseEntity.ok(updatedCarrinhoItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Fornece um endpoint para deletar um item de carrinho
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarrinhoItem(@PathVariable Long id) {
        Optional<CarrinhoItem> carrinhoItem = carrinhoItemRepository.findById(id);
        if (carrinhoItem.isPresent()) {
            carrinhoItemRepository.delete(carrinhoItem.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}