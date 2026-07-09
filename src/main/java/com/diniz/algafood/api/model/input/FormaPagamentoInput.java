package com.diniz.algafood.api.model.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FormaPagamentoInput {
	
	@NotBlank
	private String descricao;
}
