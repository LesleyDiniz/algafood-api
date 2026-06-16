package com.diniz.algafood.api.assembler;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diniz.algafood.api.model.RestauranteOutput;
import com.diniz.algafood.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public RestauranteOutput toModel(Restaurante restaurante) {
		
		return modelMapper.map(restaurante, RestauranteOutput.class);
//		var restauranteOutput = new RestauranteOutput();
//		
//		CozinhaOutput cozinhaOutput = new CozinhaOutput();
//		BeanUtils.copyProperties(restaurante.getCozinha(), cozinhaOutput);
//		
//		BeanUtils.copyProperties(restaurante, restauranteOutput);
//		
//		restauranteOutput.setCozinha(cozinhaOutput);		
//		
//		return restauranteOutput;
	}
	
	public List<RestauranteOutput> toCollectionModel(List<Restaurante> restaurantes) {
		return restaurantes.stream()
				.map(restaurante -> toModel(restaurante))
				.toList();
	}
	
}
