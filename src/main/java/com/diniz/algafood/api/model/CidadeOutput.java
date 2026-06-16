package com.diniz.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeOutput {

	private Long id;
	private String nome;
	private EstadoOutput estado;
}
