package com.diniz.algafood.core.ModelMapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.diniz.algafood.api.model.EnderecoOutput;
import com.diniz.algafood.domain.model.Endereco;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
		var enderecoToEnderecoOutputTypeMap = modelMapper.createTypeMap(
				Endereco.class, EnderecoOutput.class);
		
		enderecoToEnderecoOutputTypeMap.<String>addMapping(
				src -> src.getCidade().getEstado().getNome(), 
				(dest, value) -> dest.getCidade().setEstado(value));
		
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		
		return modelMapper;
	}
}
