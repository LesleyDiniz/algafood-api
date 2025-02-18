package com.diniz.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.diniz.algafood.AlgafoodApiApplication;
import com.diniz.algafood.domain.repository.CozinhaRepository;

public class ConsultaCozinhaMain {

	public static void main(String[] args) {
		ApplicationContext ac = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args); 
		
		var cozinhaRepository = ac.getBean(CozinhaRepository.class);
		
		var cozinhas = cozinhaRepository.listar();
		
		for(var cozinha : cozinhas) {
			System.out.println(cozinha.getNome());
		}
	}
}
