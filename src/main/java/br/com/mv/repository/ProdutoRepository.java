package br.com.mv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mv.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
