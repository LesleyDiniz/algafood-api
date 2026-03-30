package com.diniz.algafood.api.model.mixin;

import java.util.ArrayList;
import java.util.List;

import com.diniz.algafood.domain.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class CozinhaMixin {
	
	@JsonIgnore
	private List<Restaurante> restaurantes = new ArrayList<>();
}
