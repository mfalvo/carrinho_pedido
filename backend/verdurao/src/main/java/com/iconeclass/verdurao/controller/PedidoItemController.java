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

import com.iconeclass.verdurao.model.PedidoItem;
import com.iconeclass.verdurao.repository.PedidoItemRepository;

@RestController
@RequestMapping("/pedidoitens")
public class PedidoItemController {

    @Autowired
    private PedidoItemRepository pedidoItemRepository;

    @GetMapping
    public Iterable<PedidoItem> getAllPedidoItens() {
        return pedidoItemRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoItem> getPedidoItemById(@PathVariable Long id) {
        Optional<PedidoItem> pedidoItem = pedidoItemRepository.findById(id);
        if (pedidoItem.isPresent()) {
            return ResponseEntity.ok(pedidoItem.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}