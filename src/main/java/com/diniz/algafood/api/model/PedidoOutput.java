package com.diniz.algafood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import com.diniz.algafood.domain.model.StatusPedido;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PedidoOutput {
	
	@EqualsAndHashCode.Include
	private String codigo;
	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	private OffsetDateTime dataCriacao;
	private OffsetDateTime dataConfirmacao;
	private OffsetDateTime dataEntrega;
	private OffsetDateTime dataCancelamento;
	private StatusPedido status;
	private FormaPagamentoOutput formaPagamento;
	private RestauranteResumoOutput restaurante;
	private UsuarioOutput cliente;
	private EnderecoOutput enderecoEntrega;
	private List<ItemPedidoOutput> itens;

}
