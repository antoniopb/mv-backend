package br.com.mv.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import br.com.mv.dto.ReajusteProdutosDTO;
import br.com.mv.exception.ResourceNotFoundException;
import br.com.mv.model.Produto;
import br.com.mv.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private MessageSource messageSource;
	
	Logger logger = LoggerFactory.getLogger(ProdutoService.class);

	public List<Produto> findAllProdutos() {
		logger.info("ProdutoService > findAllProdutos > Buscando todos os Produtos");
		return produtoRepository.findAll();
	}

	public ResponseEntity<Produto> getProdutoById(Long id) throws ResourceNotFoundException {
		logger.info("ProdutoService > getProdutoById > Buscando produto de id : {" + id + "}");
		Optional<Produto> produto = produtoRepository.findById(id);
		if (produto.isPresent()) {
			logger.info("ProdutoService > getProdutoById > Produto de id {" + id + "} encontrado");
			return ResponseEntity.ok().body(produto.get());
		}
		else {
			logger.error("ProdutoService > getProdutoById > Erro ao resgatar produto de id: " + id);
			throw new ResourceNotFoundException(
					messageSource.getMessage("PRODUTO_NAO_ENCONTRADO", new Object[] { id }, null));
			
		}
	}

	public Produto saveProduto(Produto produto) {
		logger.info("ProdutoService > saveProduto > Salvando Produto...");
		return produtoRepository.save(produto);
	}

	public ResponseEntity<Produto> updateProduto(Long id, Produto produto) throws ResourceNotFoundException {
		logger.info("ProdutoService > updateProduto > Editando produto de id : {" + id + "}");
		Optional<Produto> produtoOld = produtoRepository.findById(id);

		if (produtoOld.isPresent()) {
			logger.info("ProdutoService > updateProduto > Produto de id {" + id + "} encontrado");
			Produto p = produtoOld.get();
			p.setDescricao(produto.getDescricao());
			p.setPreco(produto.getPreco());
			logger.info("ProdutoService > saveProduto > Salvando Produto...");
			return ResponseEntity.ok(produtoRepository.save(p));
		} else {
			logger.error("ProdutoService > Erro ao resgatar produto de id: " + id);
			throw new ResourceNotFoundException(
					messageSource.getMessage("PRODUTO_NAO_ENCONTRADO", new Object[] { id }, null));
		}
	}

	public Map<String, Boolean> reajustarPrecos(ReajusteProdutosDTO reajusteProdutosDTO) throws SQLException {
		logger.info("ProdutoService > reajustarPrecos > Fazendo reajuste nos Produtos");
		Connection connection = jdbcTemplate.getDataSource().getConnection();
		connection.setSchema("MV");
		for (Long id : reajusteProdutosDTO.getIdProdutos()) {
			logger.info("ProdutoService > reajustarPrecos > Fazendo reajuste do produto de id {" + id + "}");
			CallableStatement cs = connection.prepareCall("{call MV_PACKAGE.REAJUSTAR_PRECOS(?, ?)}");
			cs.setLong(1, id);
			cs.setDouble(2, reajusteProdutosDTO.getReajuste() / 100);
			cs.execute();
			cs.close();
		}

		connection.close();
		
		logger.info("ProdutoService > reajustarPrecos > Produtos reajustados com sucesso");
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		response.put("reajuste_executado", Boolean.TRUE);

		return response;
	}

	public Map<String, Boolean> deleteProduto(Long id) throws ResourceNotFoundException {
		logger.info("ProdutoService > deleteProduto > Deletando produto de id : {" + id + "}");
		Optional<Produto> produtoOld = produtoRepository.findById(id);

		if (produtoOld.isPresent()) {
			logger.info("ProdutoService > deleteProduto > Produto de id {" + id + "} encontrado");
			Map<String, Boolean> response = new HashMap<String, Boolean>();
			logger.info("ProdutoService > saveProduto > Deletando Produto...");
			produtoRepository.delete(produtoOld.get());
			response.put("deleted", Boolean.TRUE);
			return response;
		} else {
			logger.error("ProdutoService > Erro ao resgatar produto de id: " + id);
			throw new ResourceNotFoundException(
					messageSource.getMessage("PRODUTO_NAO_ENCONTRADO", new Object[] { id }, null));
		}
	}

}
