package com.diniz.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diniz.algafood.domain.exception.EntidadeEmUsoException;
import com.diniz.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.diniz.algafood.domain.model.Produto;
import com.diniz.algafood.domain.model.Restaurante;
import com.diniz.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	private static final String MSG_RESTAURANTE_EM_USO = "Restaurante de código %d não pode ser removido, pois está em uso!";

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@Autowired
	private CadastroProdutoService cadastroProdutoService;
	
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;
	
	public Optional<Restaurante> buscar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId);
	}
	
	public Restaurante buscarOuFalhar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
	}
	
	private List<Restaurante> buscarOuFalhar(List<Long> restauranteIds) {		
		var restaurantes = restauranteRepository.findByIdIn(restauranteIds);
		if (restaurantes.size() != restauranteIds.size()) {
			throw new RestauranteNaoEncontradoException(getMessageRestauranteNaoEncontrado(restauranteIds, restaurantes));
		}
		return restaurantes;
	}
	
	private String getMessageRestauranteNaoEncontrado(List<Long> restauranteIds, List<Restaurante> restaurantes) {
		var idsEncontrados = restaurantes.stream()
				.map(Restaurante::getId)
				.toList();
		
		var idsNaoEncontrados = restauranteIds.stream()
				.filter(id -> !idsEncontrados.contains(id))
				.toList();
		
		return String.format("Restaurantes de código %s não encontrados", idsNaoEncontrados);
	}
	
	
	public List<Restaurante> listar() {
		return restauranteRepository.findAll();
	}
	
	@Transactional
	public void ativar(Long restauranteId) {
		var restaurante = buscarOuFalhar(restauranteId);
		restaurante.ativar();
	}
	
	@Transactional
	public void inativar(Long restauranteId) {
		var restaurante = buscarOuFalhar(restauranteId);
		restaurante.inativar();
	}
	
	@Transactional
	public void ativar(List<Long> restauranteIds) {
		var restaurantes = buscarOuFalhar(restauranteIds);
		restaurantes.forEach(Restaurante -> Restaurante.ativar());
	}
	

	@Transactional
	public void inativar(List<Long> restauranteIds) {
		var restaurantes = buscarOuFalhar(restauranteIds);
		restaurantes.forEach(Restaurante -> Restaurante.inativar());
	}
	
	@Transactional
	public void abrir(Long restauranteId) {
		var restaurante = buscarOuFalhar(restauranteId);
		restaurante.abrir();
	}
	
	@Transactional
	public void fechar(Long restauranteId) {
		var restaurante = buscarOuFalhar(restauranteId);
		restaurante.fechar();
	}
	
	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		var cozinhaId = restaurante.getCozinha().getId();
		var cidadeId = restaurante.getEndereco().getCidade().getId();
		
		var cozinha = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
		var cidade = cadastroCidadeService.buscarOuFalhar(cidadeId);
				
		restaurante.setCozinha(cozinha);
		restaurante.getEndereco().setCidade(cidade);
		
		return restauranteRepository.save(restaurante);
	}
	
	@Transactional
	public void excluir(Long restauranteId) {
		try {
			restauranteRepository.deleteById(restauranteId);
			restauranteRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new RestauranteNaoEncontradoException(restauranteId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_RESTAURANTE_EM_USO, restauranteId));
		}
	}
	
	@Transactional
	public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		var restaurante = buscarOuFalhar(restauranteId);
		var formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(formaPagamentoId);
		restaurante.removerFormaPagamento(formaPagamento);
	}
	
	@Transactional
	public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		var restaurante = buscarOuFalhar(restauranteId);
		var formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(formaPagamentoId);
		restaurante.adicionarFormaPagamento(formaPagamento);
	}
	
	@Transactional
	public void removerProduto(Long restauranteId, Long produtoId) {
		var restaurante = buscarOuFalhar(restauranteId);
		restaurante.removerProduto(produtoId);
	}
	
	@Transactional
	public void adicionarProduto(Long restauranteId, Produto produto) {
		var restaurante = buscarOuFalhar(restauranteId);
		produto.setRestaurante(restaurante);
		cadastroProdutoService.salvar(produto);
	}
	

	@Transactional
	public void associarResponsavel(Long restauranteId, Long usuarioId) {
		var restaurante = buscarOuFalhar(restauranteId);
		var usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);
		restaurante.adicionarResponsavel(usuario); 
	}
	
	@Transactional
	public void desassociarResponsavel(Long restauranteId, Long usuarioId) {
		var restaurante = buscarOuFalhar(restauranteId);
		var usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);
		restaurante.removerResponsavel(usuario);
	}
	
}
