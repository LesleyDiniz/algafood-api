package com.diniz.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.diniz.algafood.domain.exception.EntidadeEmUsoException;
import com.diniz.algafood.domain.exception.EstadoNaoEncontradoException;
import com.diniz.algafood.domain.model.Estado;
import com.diniz.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {
	
	private static final String MSG_ESTADO_EM_USO = "Estado de código %d não pode ser removido, pois está em uso!";
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Optional<Estado> buscar(Long estadoId) {
		return estadoRepository.findById(estadoId);
	}
	
	
	public Estado buscarOuFalhar(Long estadoId) {
		return estadoRepository.findById(estadoId)
				.orElseThrow(() -> new EstadoNaoEncontradoException(estadoId)
		);
	}
	
	public List<Estado> listar() {
		return estadoRepository.findAll();
	}
	
	
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}
	
	
	public void excluir(Long estadoId) {
		try {
			estadoRepository.deleteById(estadoId);
		} catch (EmptyResultDataAccessException e) {
			throw new EstadoNaoEncontradoException(estadoId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_ESTADO_EM_USO, estadoId));
		}
	}

}
