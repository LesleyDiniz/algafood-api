package com.diniz.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.diniz.algafood.AlgafoodApiApplication;
import com.diniz.algafood.domain.model.Cozinha;
import com.diniz.algafood.domain.repository.CozinhaRepository;

public class InclusaoCozinhaMain {

	public static void main(String[] args) {
		ApplicationContext ac = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args); 
		
		var cozinhaRepository = ac.getBean(CozinhaRepository.class);
		
		var cozinha1 = new Cozinha();
		cozinha1.setNome("Brasileira");
		
		var cozinha2 = new Cozinha();
		cozinha2.setNome("Japonesa");
		
		cozinhaRepository.salvar(cozinha1);
		cozinhaRepository.salvar(cozinha2);
		
	}
}
