package com.diniz.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diniz.algafood.domain.exception.EntidadeEmUsoException;
import com.diniz.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.diniz.algafood.domain.model.Produto;
import com.diniz.algafood.domain.model.Restaurante;
import com.diniz.algafood.domain.repository.ProdutoRepository;

@Service
public class CadastroProdutoService {

	private static final String MSG_PRODUTO_EM_USO = "Produto de código %d não pode ser removido, pois está em uso!";

	@Autowired
	private ProdutoRepository produtoRepository;
	
	public Optional<Produto> buscar(Long produtoId) {
		return produtoRepository.findById(produtoId);
	}
	
	public Produto buscarOuFalhar(Long restauranteId, Long produtoId) {
		return produtoRepository.findById(restauranteId, produtoId)
				.orElseThrow(() -> new ProdutoNaoEncontradoException(restauranteId, produtoId));
	}
	
	public List<Produto> listar() {
		return produtoRepository.findAll();
	}
	
	public List<Produto> listarPorRestaurante(Restaurante restaurante) {
		return produtoRepository.findByRestaurante(restaurante);
	}
	
	@Transactional
	public Produto salvar(Produto produto) {
		return produtoRepository.save(produto);
	}
	
	@Transactional
	public void excluir(Long restauranteId, Long produtoId) {
		try {
			produtoRepository.deleteById(produtoId);
			produtoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new ProdutoNaoEncontradoException(restauranteId, produtoId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_PRODUTO_EM_USO, produtoId));
		}
	}

}
