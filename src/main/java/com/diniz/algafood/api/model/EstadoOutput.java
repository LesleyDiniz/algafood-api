package com.diniz.algafood.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EstadoOutput {

	@NotNull
	@EqualsAndHashCode.Include
	private Long id;

	@NotBlank
	private String nome;
}
