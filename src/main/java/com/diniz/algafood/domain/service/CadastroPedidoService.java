package com.diniz.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diniz.algafood.domain.exception.EntidadeEmUsoException;
import com.diniz.algafood.domain.exception.PedidoNaoEncontradoException;
import com.diniz.algafood.domain.model.Pedido;
import com.diniz.algafood.domain.repository.PedidoRepository;

@Service
public class CadastroPedidoService {

	private static final String MSG_PEDIDO_EM_USO = "Pedido com código %d não pode ser removida, pois está em uso!";
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	public Optional<Pedido> buscar(Long pedidoId) {
		return pedidoRepository.findById(pedidoId);
	}
	
	public List<Pedido> listar() {
		return pedidoRepository.findAll();
	}
	
	@Transactional
	public void excluir(Long pedidoId) {
		try {
			pedidoRepository.deleteById(pedidoId);
			pedidoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new PedidoNaoEncontradoException(pedidoId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_PEDIDO_EM_USO, pedidoId));
		} 
	}

}
