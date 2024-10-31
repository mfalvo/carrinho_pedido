package com.iconeclass.verdurao.controller;

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

import com.iconeclass.verdurao.model.Carrinho;
import com.iconeclass.verdurao.model.CarrinhoItem;
import com.iconeclass.verdurao.repository.CarrinhoItemRepository;
import com.iconeclass.verdurao.repository.CarrinhoRepository;

@RestController
@RequestMapping("/carrinhos")
public class CarrinhoController {

    @Autowired
    private CarrinhoRepository carrinhoRepository;
    
    @Autowired 
    private CarrinhoItemRepository carrinhoitemRepository;

    // Fornece um endpoint para listar todos os carrinhos
    @GetMapping
    public Iterable<Carrinho> getAllCarrinhos() {
        return carrinhoRepository.findAll();
    }

    // Fornece um endpoint para lista carrinho por Id
    @GetMapping("/{id}")
    public ResponseEntity<Carrinho> getCarrinhoById(@PathVariable Long id) {
        Optional<Carrinho> carrinho = carrinhoRepository.findById(id);
        if (carrinho.isPresent()) {
            return ResponseEntity.ok(carrinho.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Fornece um endpoint para criar um carrinho
    @PostMapping
    public Carrinho createCarrinho(@RequestBody Carrinho carrinho) {
        return carrinhoRepository.save(carrinho);
    }


}
