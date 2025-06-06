package com.diniz.algafood.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public GrupoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public GrupoNaoEncontradoException(Long grupoId) {
		this(String.format("Não existe um cadastro de grupo com código %d", grupoId));
	}

}
