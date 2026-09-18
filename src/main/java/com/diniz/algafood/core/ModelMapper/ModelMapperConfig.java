package com.diniz.algafood.core.ModelMapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.diniz.algafood.api.model.EnderecoOutput;
import com.diniz.algafood.api.model.input.ItemPedidoInput;
import com.diniz.algafood.domain.model.Endereco;
import com.diniz.algafood.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
		modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
			.addMappings(mapper -> mapper.skip(ItemPedido::setId));
		
		var enderecoToEnderecoOutputTypeMap = modelMapper.createTypeMap(
				Endereco.class, EnderecoOutput.class);
		
		enderecoToEnderecoOutputTypeMap.<String>addMapping(
				src -> src.getCidade().getEstado().getNome(), 
				(dest, value) -> dest.getCidade().setEstado(value));
		
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
				
		return modelMapper;
	}
}
