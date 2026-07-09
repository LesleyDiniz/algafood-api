package com.diniz.algafood.api.assembler;

import java.util.Collection;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diniz.algafood.api.model.ProdutoOutput;
import com.diniz.algafood.domain.model.Produto;

@Component
public class ProdutoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public ProdutoOutput toModel(Produto produto) {
		
		return modelMapper.map(produto, ProdutoOutput.class);
	}
	
	public List<ProdutoOutput> toCollectionModel(Collection<Produto> produtos) {
		return produtos.stream()
				.sorted((f1, f2) -> f1.getId().compareTo(f2.getId()))
				.map(produto -> toModel(produto))
				.toList();
	}
}
