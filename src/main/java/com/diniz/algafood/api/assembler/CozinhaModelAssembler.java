package com.diniz.algafood.api.assembler;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diniz.algafood.api.model.CozinhaOutput;
import com.diniz.algafood.domain.model.Cozinha;

@Component
public class CozinhaModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public CozinhaOutput toModel(Cozinha cozinha) {
			
		return modelMapper.map(cozinha, CozinhaOutput.class);
	}
	
	public List<CozinhaOutput> toCollectionModel(List<Cozinha> cozinhas) {
		return cozinhas.stream()
				.map(cozinha -> toModel(cozinha))
				.toList();
	}
}
