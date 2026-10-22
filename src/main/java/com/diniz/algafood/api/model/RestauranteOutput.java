package com.diniz.algafood.api.model;

import java.math.BigDecimal;

import com.diniz.algafood.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RestauranteOutput {
	
	@JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
	private Long id;

	@JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
	private String nome;
	
	@JsonView(RestauranteView.Resumo.class)
	private BigDecimal taxaFrete;
	
	@JsonView(RestauranteView.Resumo.class)
	private CozinhaOutput cozinha;
	
	private Boolean ativo;
	private Boolean aberto;
	private EnderecoOutput endereco;

}
