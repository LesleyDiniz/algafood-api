package com.diniz.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	MENSAGEM_INCOMPREENSIVEL("mensagem-incompreensivel", "Mensagem incompreensível"),
	RECURSO_NAO_ENCONTRADO("recurso-nao-encontrado", "Recurso não encontrado"),
	ENTIDADE_EM_USO("entidade-em-uso", "Entidade em uso"),
	NEGOCIO_EXCEPTION("negocio-exception", "Violação de regra de negócio"),
	PARAMETRO_INVALIDO("parametro-invalido", "Parâmetro inválido"),
	DADOS_INVALIDOS("dados-invalidos", "Dados inválidos"),
	ERRO_DE_SISTEMA("erro-de-sistema", "Erro de sistema");
	
	private String title;
	private String uri;
	
	ProblemType(String path, String title) {
		this.uri = "https://algafood.com.br/" + path;
		this.title = title;
	}
}
