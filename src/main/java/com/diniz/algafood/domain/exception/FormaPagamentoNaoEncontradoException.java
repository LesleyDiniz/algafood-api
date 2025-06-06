package com.diniz.algafood.domain.exception;

public class FormaPagamentoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public FormaPagamentoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public FormaPagamentoNaoEncontradoException(Long formaPagamentoId) {
		this(String.format("Não existe um cadastro de forma de pagamento com código %d", formaPagamentoId));
	}

}
