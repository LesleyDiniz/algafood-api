package com.diniz.algafood.api.assembler;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diniz.algafood.api.model.CidadeOutput;
import com.diniz.algafood.domain.model.Cidade;

@Component
public class CidadeModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public CidadeOutput toModel(Cidade cidade) {
		return modelMapper.map(cidade, CidadeOutput.class);
	}
	
	public List<CidadeOutput> toCollectionModel(List<Cidade> cidades) {
		return cidades.stream()
				.map(restaurante -> toModel(restaurante))
				.toList();
	}
}
