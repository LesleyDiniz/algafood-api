package com.diniz.algafood.api.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaIdInput {
	
	@NotNull
	private Long id;
}
