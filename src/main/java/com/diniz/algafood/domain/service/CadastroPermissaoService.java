package com.diniz.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diniz.algafood.domain.exception.EntidadeEmUsoException;
import com.diniz.algafood.domain.exception.PermissaoNaoEncontradaException;
import com.diniz.algafood.domain.model.Permissao;
import com.diniz.algafood.domain.repository.PermissaoRepository;

@Service
public class CadastroPermissaoService {

	private static final String MSG_PERMISSAO_EM_USO = "Permissão com código %d não pode ser removida, pois está em uso!";
	
	@Autowired
	private PermissaoRepository permissaoRepository;
	
	public Optional<Permissao> buscar(Long permissaoId) {
		return permissaoRepository.findById(permissaoId);
	}
	
	public Permissao buscarOuFalhar(Long permissaoId) {
		return permissaoRepository.findById(permissaoId)
				.orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId)
		);
	}
	
	public List<Permissao> listar() {
		return permissaoRepository.findAll();
	}
	
	@Transactional
	public Permissao salvar(Permissao permissao) {
		return permissaoRepository.save(permissao);
	}
	
	@Transactional
	public void excluir(Long permissaoId) {
		try {
			permissaoRepository.deleteById(permissaoId);
			permissaoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new PermissaoNaoEncontradaException(permissaoId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_PERMISSAO_EM_USO, permissaoId));
		} 
	}

}
