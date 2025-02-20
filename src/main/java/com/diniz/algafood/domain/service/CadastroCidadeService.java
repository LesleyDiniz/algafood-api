package com.diniz.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.diniz.algafood.domain.exception.EntidadeEmUsoException;
import com.diniz.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.diniz.algafood.domain.model.Cidade;
import com.diniz.algafood.domain.repository.EstadoRepository;
import com.diniz.algafood.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Cidade buscar(Long cidadeId) {
		return cidadeRepository.buscar(cidadeId);
	}
	
	public List<Cidade> listar() {
		return cidadeRepository.listar();
	}
	
	
	public Cidade salvar(Cidade cidade) {
		var estadoId = cidade.getEstado().getId();
		var estado = estadoRepository.buscar(estadoId);
		if (estado == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe cadastro de estado com código %d!", estadoId));
		}
		
		cidade.setEstado(estado);
		
		return cidadeRepository.salvar(cidade);
	}
	
	
	public void excluir(Long cidadeId) {
		try {
			cidadeRepository.remover(cidadeId);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException (
					String.format("Não existe um cadastro de cidade com código %d!", cidadeId));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Cidade de código %d não pode ser removido, pois está em uso!", cidadeId));
		}
	}

}
