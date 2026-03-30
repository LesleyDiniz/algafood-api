package com.diniz.algafood.api.model.mixin;

import com.diniz.algafood.domain.model.Estado;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class CidadeMixin {
	
	@JsonIgnoreProperties(value = "nome", allowGetters = true)	
	private Estado estado;
}
