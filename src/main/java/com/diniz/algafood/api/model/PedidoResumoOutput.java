package com.diniz.algafood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.diniz.algafood.domain.model.StatusPedido;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PedidoResumoOutput {
	
	@EqualsAndHashCode.Include
	private String codigo;
	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	private OffsetDateTime dataCriacao;
	private StatusPedido status;
	private RestauranteResumoOutput restaurante;
	private UsuarioOutput cliente;

}
