package com.diniz.algafood;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.diniz.algafood.domain.exception.EntidadeEmUsoException;
import com.diniz.algafood.domain.model.Cozinha;
import com.diniz.algafood.domain.service.CadastroCozinhaService;

import jakarta.validation.ConstraintViolationException;

@SpringBootTest
public class CadastroCozinhaIT {
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Test
	public void deveAtribuirId_QuandoCadastrarCozinhaComDadosCorretos() {
		
		var novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");
		
		
		novaCozinha = cadastroCozinha.salvar(novaCozinha);
		
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}
	

	@Test
	public void deveFalhar_QaundoCadastrarCozinhaSemNome() {
	
		var novaCozinha = new Cozinha();
		novaCozinha.setNome(null);
		
		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			cadastroCozinha.salvar(novaCozinha);
		});
	}
	
	@Test
	public void deveFalhar_QuandoExcluirCozinhaEmUso() {
		var cozinhaEmUso = new Cozinha();
		cozinhaEmUso.setId(1L); // Supondo que a cozinha com ID 1 está em uso
		
		Assertions.assertThrows(EntidadeEmUsoException.class, () -> {
			cadastroCozinha.excluir(cozinhaEmUso.getId());
		});
	}
	
	@Test
	public void deveDarSucesso_QuandoExcluirCozinhaInexistente() {
		
			cadastroCozinha.excluir(9999L); // ID inexistente
	
//			Assertions.assertTrue(true, "Exceção esperada não foi lançada");
	}

}
