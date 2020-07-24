package br.com.mv.dto;

import java.util.Set;

public class ReajusteProdutosDTO {
	
	private Set<Long> ids;
	private Double reajuste;
	
	public ReajusteProdutosDTO(Set<Long> ids, Double reajuste) {
		this.ids = ids;
		this.reajuste = reajuste;
	}

	public Set<Long> getIdProdutos() {
		return ids;
	}

	public void setIdProdutos(Set<Long> ids) {
		this.ids = ids;
	}

	public Double getReajuste() {
		return reajuste;
	}

	public void setReajuste(Double reajuste) {
		this.reajuste = reajuste;
	}

}
