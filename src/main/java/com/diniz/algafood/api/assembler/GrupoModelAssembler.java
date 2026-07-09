package com.diniz.algafood.api.assembler;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diniz.algafood.api.model.GrupoOutput;
import com.diniz.algafood.domain.model.Grupo;

@Component
public class GrupoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public GrupoOutput toModel(Grupo grupo) {
		return modelMapper.map(grupo, GrupoOutput.class);
	}
	
	public List<GrupoOutput> toCollectionModel(List<Grupo> grupos) {
		return grupos.stream()
				.map(grupo -> toModel(grupo))
				.toList();
	}
}
