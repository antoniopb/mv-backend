package br.com.mv.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mv.dto.ReajusteProdutosDTO;
import br.com.mv.exception.ResourceNotFoundException;
import br.com.mv.model.Produto;
import br.com.mv.service.ProdutoService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@GetMapping("/produtos")
	public List<Produto> getProdutos() {
		return produtoService.findAllProdutos();
	}

	@GetMapping("/produtos/{id}")
	public ResponseEntity<Produto> getProdutoById(@PathVariable(value = "id") Long id)
			throws ResourceNotFoundException {
		return produtoService.getProdutoById(id);
	}

	@PostMapping("produtos")
	public Produto createProduto(@Validated @RequestBody Produto produto) {
		return produtoService.saveProduto(produto);
	}

	@PutMapping("/produtos/{id}")
	public ResponseEntity<Produto> updateProduto(@PathVariable(value = "id") Long id,
			@Validated @RequestBody Produto produto) throws ResourceNotFoundException {
		return produtoService.updateProduto(id, produto);
	}

	@PutMapping("produtos/reajustar")
	public Map<String, Boolean> reajustarPrecos(@RequestBody ReajusteProdutosDTO reajusteProdutosDTO)
			throws SQLException {
		return produtoService.reajustarPrecos(reajusteProdutosDTO);
	}

	@DeleteMapping("/produtos/{id}")
	public Map<String, Boolean> deleteProduto(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
		return produtoService.deleteProduto(id);
	}

}
