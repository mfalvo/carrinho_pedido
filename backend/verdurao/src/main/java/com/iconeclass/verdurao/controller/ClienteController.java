package com.iconeclass.verdurao.controller;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
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
import com.iconeclass.verdurao.model.Cliente;
import com.iconeclass.verdurao.model.Pedido;
import com.iconeclass.verdurao.model.Produto;
import com.iconeclass.verdurao.repository.CarrinhoItemRepository;
import com.iconeclass.verdurao.repository.CarrinhoRepository;
import com.iconeclass.verdurao.repository.ClienteRepository;
import com.iconeclass.verdurao.repository.PedidoRepository;
import com.iconeclass.verdurao.repository.ProdutoRepository;

@RestController //Indica que esta classe é um controlador REST.
@RequestMapping("/clientes") //Define a rota base para os endpoints deste controlador.
public class ClienteController {

    @Autowired // Injeta uma instância do repositório ClienteRepository.
    private ClienteRepository clienteRepository;
    
    @Autowired
    private CarrinhoRepository carrinhoRepository;
    
    @Autowired
    private CarrinhoItemRepository carrinhoitemRepository;
    
    @Autowired
    private ProdutoRepository produtoRepository;


    // Fornece um endpoint para buscar todos os clientes.
    @GetMapping 
    public Iterable<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    // Fornece um endpoint para buscar um cliente específico pelo email.
    @GetMapping("/{email}")  
    public ResponseEntity<Cliente> getClienteByEmail(@PathVariable String email) {
        Optional<Cliente> cliente = clienteRepository.findById(email);
        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Fornece um endpoint para criar um novo cliente.
    @PostMapping 
    public Cliente createCliente(@RequestBody Cliente cliente) {
    	Optional<Cliente> optionalCliente = clienteRepository.findById(cliente.getEmail());
    	if (!(optionalCliente.isPresent())) {
    		
    		Carrinho carrinho = new Carrinho();
    		carrinho.setCliente(cliente);
    		
    		cliente.setCarrinho(carrinho);
    		
    		carrinhoRepository.save(carrinho);
    		
    		return clienteRepository.save(cliente);
    	}
    	Cliente clienteExiste = null;
    	 return clienteExiste; 
        
    }

    //Fornece um endpoint para atualizar perfil de um cliente existente
    @PutMapping("/{email}") 
    public ResponseEntity<Cliente> updateCliente(@PathVariable String email, @RequestBody Cliente clienteDetails) {
        Optional<Cliente> optionalCliente = clienteRepository.findById(email);
        if (optionalCliente.isPresent()) {
            Cliente cliente = optionalCliente.get();
            
            cliente.setNome(clienteDetails.getNome());
            cliente.setSenha(clienteDetails.getSenha());
            cliente.setImagem(clienteDetails.getImagem());
            // Atualize os outros campos necessários
            
            // CARRINHO
            // Verifique se o clienteDetails tem um carrinho associado
            if (clienteDetails.getCarrinho() != null) {
                Long carrinhoId = clienteDetails.getCarrinho().getId();
				Optional<Carrinho> optionalCarrinho = carrinhoRepository.findById(carrinhoId);

                if (optionalCarrinho.isPresent()) {
                    Carrinho carrinho = optionalCarrinho.get();
                    cliente.setCarrinho(carrinho);
                    
                    // Sincronize o relacionamento bidirecional
                    carrinho.setCliente(cliente);
                }
            }
            
            final Cliente updatedCliente = clienteRepository.save(cliente);
            return ResponseEntity.ok(updatedCliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    //Fornece um endpoint para adicionar produto no carrinho de cliente
    @PutMapping("{email}/addItemCarribo/{idproduto}/{quant}/{preco}") 
    public ResponseEntity<Cliente> insertPedidoCliente(@PathVariable String email, @PathVariable Long idproduto,  
    		@PathVariable Integer quant,  @PathVariable BigDecimal preco) {
        
    	Optional<Cliente> optionalCliente = clienteRepository.findById(email);
    	Optional<Produto> optionalProduto = produtoRepository.findById(idproduto);
        if (optionalCliente.isPresent() && optionalProduto.isPresent()) {
          Cliente cliente = optionalCliente.get();
          Produto produto = optionalProduto.get();
          
          Carrinho carrinho = cliente.getCarrinho();
          CarrinhoItem carrinhoitem = new CarrinhoItem();
          
          carrinhoitem.setCarrinho(carrinho);
          carrinhoitem.setPreco(preco);
          carrinhoitem.setProduto(produto);
          carrinhoitem.setQuantidade(quant);
          carrinhoitem.setSelecionado(true);
          
          cliente.getCarrinho().getCarrinhoitems().add(carrinhoitem);
          
          
          carrinhoitemRepository.save(carrinhoitem);
          carrinhoRepository.save(carrinho);        
          final Cliente updatedCliente = clienteRepository.save(cliente);
          return ResponseEntity.ok(updatedCliente);
        }
        
        return ResponseEntity.notFound().build();
    }   

    // Fornece um endpoint para alterar a quantidade ou remover um itemcarrinho do carrinho de um cliente. Caso a
    // quantidade (quant) fornecida é igual a zero, o item de carrinho será removido do carrinho do cliente e será deletado.
    @PutMapping("/{email}/alteraCarrinhoItem/{carrinhoitemid}/{quant}/{preco}") 
    public ResponseEntity<Cliente> alteraCarrinhoItemById(@PathVariable String email,@PathVariable Long carrinhoitemid,
    		@PathVariable Integer quant, @PathVariable BigDecimal preco) {
        
    	Optional<Cliente> optionalCliente = clienteRepository.findById(email);
    	Optional<CarrinhoItem> optionalCarrinhoItem = carrinhoitemRepository.findById(carrinhoitemid);
        if (optionalCliente.isPresent() && optionalCarrinhoItem.isPresent()) {
        	
            Cliente cliente = optionalCliente.get();
            CarrinhoItem carrinhoitem = optionalCarrinhoItem.get();
            
            if (cliente.getCarrinho() == carrinhoitem.getCarrinho()) {
            	if(carrinhoitem.getQuantidade() == 0) {
            		Carrinho carrinho = cliente.getCarrinho();
            		carrinho.getCarrinhoitems().remove(carrinhoitem);
            		carrinhoRepository.save(carrinho);
            		carrinhoitemRepository.delete(carrinhoitem);
            	}
            	else
            	{
            		carrinhoitem.setPreco(preco);
            		carrinhoitem.setQuantidade(quant);
            		carrinhoitemRepository.save(carrinhoitem);
            	}
            	final Cliente updatedCliente = clienteRepository.save(cliente);
                return ResponseEntity.ok(updatedCliente);
            }	
       }
        
       return ResponseEntity.notFound().build();
    }   

    //Fornece um endpoint para deletar um cliente específico por seu email.
    @DeleteMapping("/{email}") 
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
