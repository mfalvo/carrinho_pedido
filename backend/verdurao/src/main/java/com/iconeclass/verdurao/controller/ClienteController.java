package com.iconeclass.verdurao.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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
import com.iconeclass.verdurao.model.PedidoItem;
import com.iconeclass.verdurao.model.Produto;
import com.iconeclass.verdurao.repository.CarrinhoItemRepository;
import com.iconeclass.verdurao.repository.CarrinhoRepository;
import com.iconeclass.verdurao.repository.ClienteRepository;
import com.iconeclass.verdurao.repository.PedidoItemRepository;
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
    
    @Autowired
    private PedidoItemRepository pedidoitemRepository;
    
    @Autowired
    private PedidoRepository pedidoRepository;


    // AÇÕES NO CLIENTE /////////////////////////////////
    // Fornece um endpoint para buscar todos os clientes.
    
    
    
    //Fornece um endpoint para relizar um login.
    @GetMapping("/login")
    public Cliente loginCliente(@RequestBody Cliente cliente) {
    	Cliente clienteNaoExiste = null;
    	// tenta localizar um cliente com mesmo email e password
    	Optional<Cliente> optionalCliente = clienteRepository.findById(cliente.getEmail());
    	if (optionalCliente.isPresent()) {
    		Cliente clienteExiste = optionalCliente.get();
    		if (clienteExiste.getSenha() == cliente.getSenha())
    		{
    			clienteExiste.setSenha("");
    			return clienteExiste;
    		}
    	}
    	return clienteNaoExiste; 
    } 
    
    
    
    // Retorna lista de todos os clientes
    @GetMapping 
    public Iterable<Cliente> getAllClientes() {
    	// lista todos os clientes do repositório
        return clienteRepository.findAll();
    }
    
    

    // Fornece um endpoint para buscar um cliente específico pelo email(Id).
    @GetMapping("/{email}")  
    public ResponseEntity<Cliente> getClienteByEmail(@PathVariable String email) {
    	//tenta localizar cliente por seu Id (email)
        Optional<Cliente> cliente = clienteRepository.findById(email);
        if (cliente.isPresent()) { // retorna objeto seo encontrar
            return ResponseEntity.ok(cliente.get()); 
        } else { // caso contrário retorna vazio
            return ResponseEntity.notFound().build();
        }
    }

    
    
    //Fornece um endpoint para criar um novo cliente.
    @PostMapping 
    public Cliente createCliente(@RequestBody Cliente cliente) {
    	// tenta localizar um cliente com mesmo email antes de criá-lo
    	Optional<Cliente> optionalCliente = clienteRepository.findById(cliente.getEmail());
    	// se não existe outro cliente com o mesmo email segue com criação de cliente
    	if (!(optionalCliente.isPresent())) {
    		
    		Cliente updatedCliente = clienteRepository.save(cliente);
    		Carrinho carrinho = new Carrinho(); // cria um carrinho vazio
    		carrinho.setCliente(updatedCliente); // atribui Cliente  ao carrinho criado
    		Carrinho updatedCarrino = carrinhoRepository.save(carrinho);
    		updatedCliente.setCarrinho(updatedCarrino);// e atribui carrinho ao Cliente    		
    		return clienteRepository.save(updatedCliente); // salva cliente no repositório e o retorna ao solicitante e sai
    	}
    	// o trecho de código seguinte somente é executado se já existe um cliente
    	// então não será criado um novo Cliente e será retornado null no lugar de um objeto Cliente e sai
    	Cliente clienteExiste = null;
    	return clienteExiste; 
        
    }

    
    
    //Fornece um endpoint para atualizar perfil de um cliente existente 
    @PutMapping("/{email}") 
    public ResponseEntity<Cliente> atualizaPerfilCliente(@PathVariable String email, @RequestBody Cliente clienteDetails) {
    	// localiza cliente por seu Id (email) para ser alterado
        Optional<Cliente> optionalCliente = clienteRepository.findById(email);
        // Se for encontrad, inicia bloco de código para sual alteração
        if (optionalCliente.isPresent()) {
        	// obtem objeto cliente
            Cliente cliente = optionalCliente.get();
            
            cliente.setNome(clienteDetails.getNome()); // atribui novo nome informado para o objeto
            cliente.setSenha(clienteDetails.getSenha()); // atribui nova senha  para objeto cliente 
            cliente.setImagem(clienteDetails.getImagem()); // atribui path imagem para objeto cliente
            
            final Cliente updatedCliente = clienteRepository.save(cliente);// Salva atualização
            return ResponseEntity.ok(updatedCliente); // retorna objeto cliente atualizado
        } else {
            return ResponseEntity.notFound().build(); // retorna aviso se cliente não foi encontrado
        }
    }
    
    
    
    //Fornece um endpoint para deletar um cliente específico por seu email.
    @DeleteMapping("/{email}") 
    public ResponseEntity<Void> deleteCliente(@PathVariable String email) {
    	// localiza objeto cliente por seu Id (email)
        Optional<Cliente> cliente = clienteRepository.findById(email);
        if (cliente.isPresent()) {// Se encontrado executa deleção
            clienteRepository.delete(cliente.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build(); // se não, retorna aviso de não encontrado
        }
    }

    
    
    
    // AÇÕES NO CARRINHO E CARRINHO ITEM /////////////////////////////////
    //Fornece um endpoint para adicionar produto no carrinho de cliente
    @PutMapping("{email}/addItemCarribo/{idproduto}/{quant}") 
    public ResponseEntity<Cliente> Cliente(@PathVariable String email, @PathVariable Long idproduto,  
    		@PathVariable Integer quant) {
        // tenta localizar Cliente
    	Optional<Cliente> optionalCliente = clienteRepository.findById(email);
    	// tenta localizar Produto
    	Optional<Produto> optionalProduto = produtoRepository.findById(idproduto);
    	
    	// Se Cliente e Produto são econtrados, inicia-se a adição do produto como item de carrinho
        if (optionalCliente.isPresent() && optionalProduto.isPresent()) {
          Cliente cliente = optionalCliente.get(); // obtem objeto Cliente
          Produto produto = optionalProduto.get(); // obtem obejto Produto
          
          Carrinho carrinho = cliente.getCarrinho(); // obtem carrinho de cliente 
          CarrinhoItem carrinhoitem = new CarrinhoItem(); // cria um item de carrinho
          
          carrinhoitem.setCarrinho(carrinho); // atribui carrinho a item de carrinho criado
          carrinhoitem.setPreco(produto.getPreco()); // atribui preço de produto ao item criado
          carrinhoitem.setProduto(produto);// atribui o produto ao item criado
          carrinhoitem.setQuantidade(quant); // atribui quantidade ao item criado
          carrinhoitem.setSelecionado(true); // atribui true como selecioando ao item criado
          
          cliente.getCarrinho().getCarrinhoitems().add(carrinhoitem); // adiciona item carrinho criado ao carrinho de cliente
          
          // Recalcula total do carrinho
          BigDecimal total_carrinho = carrinho.getTotal();
          BigDecimal preco = carrinhoitem.getPreco();
          BigDecimal quantidade = BigDecimal.valueOf(quant);
          
          BigDecimal subtotal = preco.multiply(quantidade);
          total_carrinho = total_carrinho.add(subtotal);
          
          carrinho.setTotal(total_carrinho);
          
          carrinhoitemRepository.save(carrinhoitem); //  salva item de carrinho no respectivo repositório
          carrinhoRepository.save(carrinho);  // salva carrinho no respectivo repositório 
          final Cliente updatedCliente = clienteRepository.save(cliente); // salva cliente em seu repositório
          return ResponseEntity.ok(updatedCliente); //retorna Cliente atualizado com novo item de carrinho
        }
        
        return ResponseEntity.notFound().build(); // retorna aviso de Cliente ou Produto não encontrados
    }   

    
    
    // Fornece um endpoint para alterar a quantidade ou remover um itemcarrinho do carrinho de um cliente. Caso a
    // quantidade (quant) fornecida é igual a zero, o item de carrinho será removido do carrinho do cliente e será deletado.
    @PutMapping("/{email}/alteraCarrinhoItem/{carrinhoitemid}/{quant}") 
    public ResponseEntity<Cliente> alteraCarrinhoItemById(@PathVariable String email,@PathVariable Long carrinhoitemid,
    		@PathVariable Integer quant) {
        // tenta localizar cliente por seu Id
    	Optional<Cliente> optionalCliente = clienteRepository.findById(email);
    	// tenta localizar item de carrinho por seu Id (Long)
    	Optional<CarrinhoItem> optionalCarrinhoItem = carrinhoitemRepository.findById(carrinhoitemid);
    	// Caso consiga localizar Cliente e CarrinhoItem executa o bloco do if...
        if (optionalCliente.isPresent() && optionalCarrinhoItem.isPresent()) {
        	
            Cliente cliente = optionalCliente.get(); //  obtem objeto Cliente
            CarrinhoItem carrinhoitem = optionalCarrinhoItem.get(); // obtem objeto CarrinhoItem
            
            //verifica se CarrinhoItem informado pertence ao carrinho do cliente informado
            if (cliente.getCarrinho() == carrinhoitem.getCarrinho()) {
            	
            	// Atualiza valor total do carrinho pela variação da quantidade do carrinhoitem
            	// se quant for menor que o valor da quantidade atual de carrinhoitem
            	// a diferença será um número negativo, caso contrário será uma quantidade positva.
            	// Isso se refletirá no valor de ajuste que pode ser positivo - se a quantidade for maior ou
            	// negativo se quantidade for menor.
            	BigDecimal total_carrinho = carrinhoitem.getCarrinho().getTotal();
            	BigDecimal diferenca_quant = BigDecimal.valueOf(quant - carrinhoitem.getQuantidade());
            	BigDecimal subtotal = diferenca_quant.multiply(carrinhoitem.getProduto().getPreco());
            	BigDecimal novo_total_carrinho = total_carrinho.add(subtotal);
            	// Atualiza carrinho
            	carrinhoitem.getCarrinho().setTotal(novo_total_carrinho);    		
        		BigDecimal preco_item = carrinhoitem.getPreco();
            	
            	if(quant == 0) { // Se nova quantidade de carrinhoitem informado é zero carrinhoitem é removido
            		Carrinho carrinho = cliente.getCarrinho(); //obtem carrinho de cliente 
            		carrinho.getCarrinhoitems().remove(carrinhoitem); // remove carrinhoitem de carrinho
            		carrinhoRepository.save(carrinho); // salva carrinho no respectivo repositório
            		carrinhoitemRepository.delete(carrinhoitem); // deleta carrinhoitem do respectivo repositório
            	}
            	else // caso quantidade seja diferente de zero é realizada uma atualização
            	{
            		carrinhoitem.setPreco(carrinhoitem.getProduto().getPreco()); // atualiza preço unitário
            		carrinhoitem.setQuantidade(quant); // atualiza quantidade
            		carrinhoitemRepository.save(carrinhoitem); // salva atualização de carrinhoitem no repositório
            	}
            	final Cliente updatedCliente = clienteRepository.save(cliente); // salva atualização no repositório cliente
                return ResponseEntity.ok(updatedCliente); // retorna cliente atualizado
            }
       }
       return ResponseEntity.notFound().build(); // retorna aviso de cliente ou carrinho item não encontrado
    }

    
    
    // MUDA ESTADO DO ITEM DE CARRINHO PARA TRUE/FALSE UTILIZANDO RESPECTIVAMENTE  1/0 
    // Quando um item de carrinho tem seu atributo Selecao em false ele continua na lista mas não 
    // compõem o preco total do carrinho.  E caso seja executado como pedido, ele não fará parte do
    // pedido, mas continuará na lista de carrinho. 
    @PutMapping("/{email}/alteraSelecaoCarrinhoItem/{carrinhoitemid}/{selecao}") 
    public ResponseEntity<Cliente> alteraSelecaoCarrinhoItemById(@PathVariable String email,@PathVariable Long carrinhoitemid,
    		@PathVariable Integer selecao) {
    	
    	// Verifica se valor de selecao está dentro do intervalo aceitável
    	if (selecao < 0 && selecao > 1 ) return ResponseEntity.notFound().build();
    	
        // tenta localizar cliente por seu Id
    	Optional<Cliente> optionalCliente = clienteRepository.findById(email);
    	// tenta localizar item de carrinho por seu Id (Long)
    	Optional<CarrinhoItem> optionalCarrinhoItem = carrinhoitemRepository.findById(carrinhoitemid);
    	// Caso consiga localizar Cliente e CarrinhoItem executa o bloco do if...
        if (optionalCliente.isPresent() && optionalCarrinhoItem.isPresent()) {
        	
            Cliente cliente = optionalCliente.get(); //  obtem objeto Cliente
            CarrinhoItem carrinhoitem = optionalCarrinhoItem.get(); // obtem objeto CarrinhoItem
            
            //verifica se CarrinhoItem informado pertence ao carrinho do cliente informado
            if (cliente.getCarrinho() == carrinhoitem.getCarrinho()) {
            	
            	// Atualiza valor total do carrinho pela variação da quantidade do carrinhoitem
            	// se quant for menor que o valor da quantidade atual de carrinhoitem
            	// a diferença será um número negativo, caso contrário será uma quantidade positva.
            	// Isso se refletirá no valor de ajuste que pode ser positivo - se a quantidade for maior ou
            	// negativo se quantidade for menor.
            	BigDecimal total_carrinho = carrinhoitem.getCarrinho().getTotal();
            	BigDecimal novo_total_carrinho = total_carrinho;
            	
            	BigDecimal diferenca_quant = BigDecimal.valueOf(carrinhoitem.getQuantidade());
            	BigDecimal subtotal = diferenca_quant.multiply(carrinhoitem.getProduto().getPreco());
            	
            	if (carrinhoitem.getSelecionado()==true && selecao == 0) {
                	carrinhoitem.setSelecionado(false);
            		novo_total_carrinho = total_carrinho.subtract(subtotal);
            	}
            	if (carrinhoitem.getSelecionado()==false && selecao == 1) {
                	carrinhoitem.setSelecionado(true);
            		novo_total_carrinho = total_carrinho.add(subtotal);
            	}
            	// Atualiza carrinho
            	Carrinho carrinho = carrinhoitem.getCarrinho();
            	carrinho.setTotal(novo_total_carrinho);
        		
            	carrinhoitemRepository.save(carrinhoitem); // salva atualização de carrinhoitem no repositório
            	carrinhoRepository.save(carrinho); // salva carrinho no respectivo repositório
            	
            }
            	final Cliente updatedCliente = clienteRepository.save(cliente); // salva atualização no repositório cliente
                return ResponseEntity.ok(updatedCliente); // retorna cliente atualizado
            }
       
       return ResponseEntity.notFound().build(); // retorna aviso de cliente ou carrinho item não encontrado
    }
    
    // AÇÕES EM PEDIDOS E PEDIDOS ITEM /////////////////////////////////
    //Fornece um endpoint para criar um pedido e transferir os itens selecionados do carrinho no pedido criado
    @PutMapping("/criaPedidoDeCarrinho/{email}") 
    public ResponseEntity<Cliente> insertPedidoCliente(@PathVariable String email) {
        //localiza cliente por Id email
    	Optional<Cliente> optionalCliente = clienteRepository.findById(email);
        if (optionalCliente.isPresent()) { // cliente localizado	
          Cliente cliente = optionalCliente.get();   // Obptem objeto Cliente
          Carrinho carrinho = cliente.getCarrinho(); // Obtem objeto Carrinho de Cliente
          List<CarrinhoItem> carrinhoitens = carrinho.getCarrinhoitems(); // Obtem Lista de itens do Carrinho 
          List<PedidoItem> listaItemPedidos = new ArrayList(); // Cria lista de itens de pedido localmente
          Pedido pedido = null; // Declara variavel para receber novo objeto Pedido
          BigDecimal total_pedido = new BigDecimal(0); // Define variável local para guardar o valor total do pedido
          
          Iterator<CarrinhoItem> iterator = carrinhoitens.iterator(); // usa iterator para varrer a lista carrinhoitens
          while (iterator.hasNext()) // enquanto houver objetos ele continua o laço
          { 
        	  CarrinhoItem item = iterator.next(); // obtem o objeto CarrinhoItem da lista carrinhoitens
        	  if(item.getSelecionado()) { // verifica se o retorno do método getSelecionado é true  -  se sim esse objeto fará parte do pedido
        		  
        		  if (pedido == null) // aqui é criado/aberto um pedido somente se for encontrado um item selecionado e somente uma vez!
        		  {
        			  pedido = new Pedido(); // cria o objeto Pedido
        			  pedido.setCliente(cliente); // Atribui a ele o respectivo cliente
        			  pedido.setDatahora(new Date()); // Atribui a data e hora da abertura
        			  pedido.setStatus("ABERTO"); // Define o status do pedido - inicialmente ABERTO
        			  pedidoRepository.save(pedido); // Salva pedido no repositório
        		  }
        		  
        		  PedidoItem pedidoitem = new PedidoItem(); // cria um item de pedido
        		  pedidoitem.setProduto(item.getProduto()); // atribui o produto do item de carrinho para item de pedido
        		  pedidoitem.setPedido(pedido); // atribui pedido ao item pedido
        		  pedidoitem.setQuantidade(item.getQuantidade()); // atribui quatidade do item de carrinho para item de pedido
        		  pedidoitem.setPreco(item.getPreco()); // atribui preco do ite de carrinho para item de pedido
        		  pedidoitemRepository.save(pedidoitem); // salva item de pedido
        		  
        		  iterator.remove(); // remove item de carrinho da lista de itens de carrinho
        		  carrinhoitemRepository.delete(item);

        		  Integer quant = item.getQuantidade();
        		  BigDecimal subtotal = item.getPreco().multiply(BigDecimal.valueOf(quant)); // calcula subtotal do item do pedido
        		  total_pedido = total_pedido.add(subtotal); // adiciona subtotal calculado e soma à variável total pedido
        		  
        		  listaItemPedidos.add(pedidoitem); // adiciona item pedido em lista de itens de pedido local
        	  } 
          }
          if(pedido != null ) { // Verifica se há um pedido realmente criado para concluir sua abertura
        	  carrinho.setCarrinhoitems(carrinhoitens);
        	  carrinhoRepository.save(carrinho);
        	  pedido.setPedidoitens(listaItemPedidos); //  atribui a lista local de items de pedido para pedido criado
        	  pedido.setTotal(total_pedido); // atribui o valor total do pedido calculado
        	  pedidoRepository.save(pedido); //  salva pedido

        	  cliente.getPedidos().add(pedido); // adiciona o novo pedido na lista de Pedidos do Cliente
 
        	  final Cliente updatedCliente = clienteRepository.save(cliente); // Salva atualização de Cliente com novo pedido
              return ResponseEntity.ok(updatedCliente); // retorna Cliente atualizado
          }
          return ResponseEntity.ok(cliente); // retorna Cliente encontrado mas sem itens carrinho 
          									 // para abrir pedido -> carrinho vazio ou sem itens selecionados para pedido
        }
        return ResponseEntity.notFound().build(); // retorno de Cliente não encontrado
    }   

 
    
}
