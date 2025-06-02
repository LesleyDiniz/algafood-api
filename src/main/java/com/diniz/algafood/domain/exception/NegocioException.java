package com.diniz.algafood.domain.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

public class NegocioException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NegocioException(String mensagem) {
		super(mensagem);
	}	public NegocioException(ResponseStatus status, String mensagem) {
		super(mensagem);
	}	public NegocioException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}	public NegocioException(ResponseStatus status, String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}
