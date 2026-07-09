package com.diniz.algafood.api.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UsuarioOutput {

	@EqualsAndHashCode.Include
	private Long id;
	private String nome;
	private String email;
	
}
