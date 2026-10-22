package com.diniz.algafood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

//@JsonFilter("pedidoFilter")
@Setter
@Getter
public class PedidoResumoOutput {
	
	@EqualsAndHashCode.Include
	private String codigo;
	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	private OffsetDateTime dataCriacao;
	private String status;
	private RestauranteResumoOutput restaurante;
	private UsuarioOutput cliente;

}
