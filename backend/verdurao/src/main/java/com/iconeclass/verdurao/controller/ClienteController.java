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

import com.iconeclass.verdurao.model.Cliente;
import com.iconeclass.verdurao.repository.ClienteRepository;

@RestController //Indica que esta classe é um controlador REST.
@RequestMapping("/clientes") //Define a rota base para os endpoints deste controlador.
public class ClienteController {

    @Autowired // Injeta uma instância do repositório ClienteRepository.
    private ClienteRepository clienteRepository;

    @GetMapping // Fornece um endpoint para buscar todos os clientes.
    public Iterable<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    @GetMapping("/{email}")  // Fornece um endpoint para buscar um cliente específico pelo email.
    public ResponseEntity<Cliente> getClienteByEmail(@PathVariable String email) {
        Optional<Cliente> cliente = clienteRepository.findById(email);
        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping //Fornece um endpoint para criar um novo cliente.
    public Cliente createCliente(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @PutMapping("/{email}") //Fornece um endpoint para atualizar um cliente existente.
    public ResponseEntity<Cliente> updateCliente(@PathVariable String email, @RequestBody Cliente clienteDetails) {
        Optional<Cliente> optionalCliente = clienteRepository.findById(email);
        if (optionalCliente.isPresent()) {
            Cliente cliente = optionalCliente.get();
            cliente.setNome(clienteDetails.getNome());
            cliente.setSenha(clienteDetails.getSenha());
            cliente.setImagem(clienteDetails.getImagem());
            // Atualize os outros campos necessários

            final Cliente updatedCliente = clienteRepository.save(cliente);
            return ResponseEntity.ok(updatedCliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{email}") //Fornece um endpoint para deletar um cliente específico pelo email.
    public ResponseEntity<Void> deleteCliente(@PathVariable String email) {
        Optional<Cliente> cliente = clienteRepository.findById(email);
        if (cliente.isPresent()) {
            clienteRepository.delete(cliente.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
