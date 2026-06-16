package com.diniz.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diniz.algafood.api.model.EstadoOutput;
import com.diniz.algafood.domain.model.Estado;

@Component
public class EstadoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public EstadoOutput toModel(Estado estado) {
			
			return modelMapper.map(estado, EstadoOutput.class);
		}
}
