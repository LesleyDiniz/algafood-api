package com.diniz.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.diniz.algafood.domain.exception.EntidadeEmUsoException;
import com.diniz.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.diniz.algafood.domain.model.Cozinha;
import com.diniz.algafood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	
	public Cozinha buscar(Long cozinhaId) {
		return cozinhaRepository.findById(cozinhaId).get();
	}
	
	public List<Cozinha> listar() {
		return cozinhaRepository.findAll();
	}
	
	
	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}
	
	
	public void excluir(Long cozinhaId) {
		try {
			cozinhaRepository.deleteById(cozinhaId);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException (
					String.format("Não existe um cadastro de cozinha com código %d!", cozinhaId));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Cozinha de código %d não pode ser removida, pois está em uso!", cozinhaId));
		}
	}

}
