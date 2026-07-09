package com.diniz.algafood.api.model.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PermissaoInput {

	@NotBlank
	private String nome;
	
	@NotBlank
	private String descricao;
}
