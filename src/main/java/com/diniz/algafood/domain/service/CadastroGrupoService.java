package com.diniz.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diniz.algafood.domain.exception.EntidadeEmUsoException;
import com.diniz.algafood.domain.exception.GrupoNaoEncontradoException;
import com.diniz.algafood.domain.model.Grupo;
import com.diniz.algafood.domain.repository.GrupoRepository;

@Service
public class CadastroGrupoService {

	private static final String MSG_CIDADE_EM_USO = "Grupo de código %d não pode ser removido, pois está em uso!";

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private CadastroPermissaoService cadastroPermissaoService;
	
	public Optional<Grupo> buscar(Long grupoId) {
		return grupoRepository.findById(grupoId);
	}
	
	public Grupo buscarOuFalhar(Long grupoId) {
		return grupoRepository.findById(grupoId)
				.orElseThrow(() -> new GrupoNaoEncontradoException(grupoId));
	}
	
	public List<Grupo> listar() {
		return grupoRepository.findAll();
	}
	
	@Transactional
	public Grupo salvar(Grupo grupo) {		
		return grupoRepository.save(grupo);
	}
	
	@Transactional
	public void excluir(Long grupoId) {
		try {
			grupoRepository.deleteById(grupoId);
			grupoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new GrupoNaoEncontradoException(grupoId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_CIDADE_EM_USO, grupoId));
		}
	}
	
	@Transactional
	public void desassociarPermissao(Long restauranteId, Long permissaoId) {
		var restaurante = buscarOuFalhar(restauranteId);
		var permissao = cadastroPermissaoService.buscarOuFalhar(permissaoId);
		restaurante.removerPermissao(permissao);
	}
	
	@Transactional
	public void associarPermissao(Long restauranteId, Long permissaoId) {
		var restaurante = buscarOuFalhar(restauranteId);
		var permissao = cadastroPermissaoService.buscarOuFalhar(permissaoId);
		restaurante.adicionarPermissao(permissao);
	}

}
