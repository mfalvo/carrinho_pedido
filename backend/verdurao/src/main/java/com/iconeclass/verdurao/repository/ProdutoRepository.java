package com.iconeclass.verdurao.repository;

import org.springframework.data.repository.CrudRepository;

import com.iconeclass.verdurao.model.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Long> {

}
