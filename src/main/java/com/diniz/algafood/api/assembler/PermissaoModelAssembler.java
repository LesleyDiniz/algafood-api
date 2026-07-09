package com.diniz.algafood.api.assembler;

import java.util.Collection;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diniz.algafood.api.model.PermissaoOutput;
import com.diniz.algafood.domain.model.Permissao;

@Component
public class PermissaoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public PermissaoOutput toModel(Permissao permissao) {
		
		return modelMapper.map(permissao, PermissaoOutput.class);
	}
	
	public List<PermissaoOutput> toCollectionModel(Collection<Permissao> permissaos) {
		return permissaos.stream()
				.sorted((f1, f2) -> f1.getId().compareTo(f2.getId()))
				.map(permissao -> toModel(permissao))
				.toList();
	}
}
