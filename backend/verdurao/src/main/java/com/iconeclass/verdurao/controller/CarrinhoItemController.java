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
    


}