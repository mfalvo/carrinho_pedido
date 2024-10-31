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

    // Fornece um endpoint para atualizar um carrinho
    @PutMapping("/{id}")
    public ResponseEntity<Carrinho> updateCarrinho(@PathVariable Long id, @RequestBody Carrinho carrinhoDetails) {
        Optional<Carrinho> optionalCarrinho = carrinhoRepository.findById(id);
        if (optionalCarrinho.isPresent()) {
            Carrinho carrinho = optionalCarrinho.get();
            carrinho.setCliente(carrinhoDetails.getCliente());
            carrinho.setCarrinhoitems(carrinhoDetails.getCarrinhoitems());
            // Atualize outros campos conforme necessário

            final Carrinho updatedCarrinho = carrinhoRepository.save(carrinho);
            return ResponseEntity.ok(updatedCarrinho);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Fornece um endpoint para adicionar um item de carrinho no carrinho
    @PutMapping("/{idcarrinho}/addItemCarrinho/{idItemCarrinho}")
    public ResponseEntity<Carrinho> addItemCarrinho(@PathVariable Long idcarrinho, @PathVariable Long idItemCarrinho) {
        Optional<Carrinho> optionalCarrinho = carrinhoRepository.findById(idcarrinho);
        if (optionalCarrinho.isPresent()) {
        	Optional<CarrinhoItem> optionalCarrinhoItem = carrinhoitemRepository.findById(idItemCarrinho);
        	if(optionalCarrinhoItem.isPresent()) {
        		Carrinho carrinho = optionalCarrinho.get();
        		CarrinhoItem carrinhoitem = optionalCarrinhoItem.get();
        		carrinho.getCarrinhoitems().add(carrinhoitem);
        		carrinhoitem.setCarrinho(carrinho);
        		final Carrinho updatedCarrinho = carrinhoRepository.save(carrinho);
        		return ResponseEntity.ok(updatedCarrinho);
        	}else
        		return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    
 // Fornece um endpoint para remover um item de carrinho do carrinho
    @PutMapping("/{idcarrinho}/removeItemCarrinho/{idItemCarrinho}")
    public ResponseEntity<Carrinho> removeItemCarrinho(@PathVariable Long idcarrinho, @PathVariable Long idItemCarrinho) {
        Optional<Carrinho> optionalCarrinho = carrinhoRepository.findById(idcarrinho);
        if (optionalCarrinho.isPresent()) {
        	Optional<CarrinhoItem> optionalCarrinhoItem = carrinhoitemRepository.findById(idItemCarrinho);
        	if(optionalCarrinhoItem.isPresent()) {
        		Carrinho carrinho = optionalCarrinho.get();
        		CarrinhoItem carrinhoitem = optionalCarrinhoItem.get();
        		carrinho.getCarrinhoitems().remove(carrinhoitem);
        		carrinhoitem.setCarrinho(null);
        		final Carrinho updatedCarrinho = carrinhoRepository.save(carrinho);
        		carrinhoitemRepository.deleteById(idItemCarrinho);
        		return ResponseEntity.ok(updatedCarrinho);
        	}else
        		return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }
        	

    // Fornece um endpoint para deletar um carrinho
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarrinho(@PathVariable Long id) {
        Optional<Carrinho> optionalcarrinho = carrinhoRepository.findById(id);
        if (optionalcarrinho.isPresent()) {
        	Carrinho carrinho = optionalcarrinho.get();
            carrinhoRepository.delete(carrinho);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
