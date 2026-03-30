package com.diniz.algafood.core.jackson;

import org.springframework.stereotype.Component;

import com.diniz.algafood.api.model.mixin.CidadeMixin;
import com.diniz.algafood.api.model.mixin.CozinhaMixin;
import com.diniz.algafood.domain.model.Cidade;
import com.diniz.algafood.domain.model.Cozinha;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Component
public class JacksonMixinModule extends SimpleModule {

	private static final long serialVersionUID = 1L;
	
	public JacksonMixinModule() {
		setMixInAnnotation(Cidade.class, CidadeMixin.class);
		setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
	}

}
