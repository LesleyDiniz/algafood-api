package com.diniz.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public PedidoNaoEncontradoException(Long pedidoId) {
		super(String.format("Não existe um cadastro de pedido com id %d", pedidoId));
	}
	
	public PedidoNaoEncontradoException(String codigoPedido) {
		super(String.format("Não existe um cadastro de pedido com código %s", codigoPedido));
	}

}
