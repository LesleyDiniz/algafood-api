package com.diniz.algafood.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public UsuarioNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public UsuarioNaoEncontradoException(Long usuarioId) {
		this(String.format("Não existe um cadastro de usuário com código %d", usuarioId));
	}

}
