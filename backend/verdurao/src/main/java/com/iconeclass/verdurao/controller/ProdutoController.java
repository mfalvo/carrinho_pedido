package com.iconeclass.verdurao.controller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
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

import com.iconeclass.verdurao.model.Produto;
import com.iconeclass.verdurao.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping
    public Iterable<Produto> getAllProdutos() {
    	Iterable<Produto> produtos = produtoRepository.findAll();
        for (Produto produto : produtos) {
            if (produto.getImagem() != null) {
                String imagePath = produto.getImagem();
                try {
                    Path path = Paths.get(imagePath);
                    byte[] imageBytes = Files.readAllBytes(path);
                    String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                    produto.setImagem(base64Image);
                } catch (IOException e) {
                    e.printStackTrace();
                    produto.setImagem(null); // Clear the image path if an error occurs
                }
            }
        }
        return produtos;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable Long id) {
    	Optional<Produto> produto = produtoRepository.findById(id);
        if (produto.isPresent()) {
            Produto p = produto.get();
            if (p.getImagem() != null) {
                String imagePath = p.getImagem();
                try {
                    Path path = Paths.get(imagePath);
                    byte[] imageBytes = Files.readAllBytes(path);
                    String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                    p.setImagem(base64Image);
                } catch (IOException e) {
                    e.printStackTrace();
                    p.setImagem(null); // Clear the image path if an error occurs
                }
            }
            return ResponseEntity.ok(p);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Produto createProduto(@RequestBody Produto produto) {
        return produtoRepository.save(produto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> updateProduto(@PathVariable Long id, @RequestBody Produto produtoDetails) {
        Optional<Produto> optionalProduto = produtoRepository.findById(id);
        if (optionalProduto.isPresent()) {
            Produto produto = optionalProduto.get();
            produto.setNome(produtoDetails.getNome());
            produto.setPreco(produtoDetails.getPreco());
            produto.setDescricao(produtoDetails.getDescricao());
            produto.setImagem(produtoDetails.getImagem());
            // Atualize outros campos conforme necessário

            final Produto updatedProduto = produtoRepository.save(produto);
            return ResponseEntity.ok(updatedProduto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        if (produto.isPresent()) {
            produtoRepository.delete(produto.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}