package com.diniz.algafood.api.assembler;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diniz.algafood.api.model.UsuarioOutput;
import com.diniz.algafood.domain.model.Usuario;

@Component
public class UsuarioModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public UsuarioOutput toModel(Usuario usuario) {
		return modelMapper.map(usuario, UsuarioOutput.class);
	}
	
	public List<UsuarioOutput> toCollectionModel(List<Usuario> usuarios) {
		return usuarios.stream()
				.map(usuario -> toModel(usuario))
				.toList();
	}
}
