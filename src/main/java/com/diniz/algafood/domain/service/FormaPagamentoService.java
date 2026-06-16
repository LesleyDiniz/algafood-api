package com.diniz.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diniz.algafood.domain.exception.EntidadeEmUsoException;
import com.diniz.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.diniz.algafood.domain.model.FormaPagamento;
import com.diniz.algafood.domain.repository.FormaPagamentoRepository;

@Service
public class FormaPagamentoService {	

	private static final String MSG_FORMA_PAGAMENTO_EM_USO = "Forma de pagamento com código %d não pode ser removida, pois está em uso!";
	
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	public Optional<FormaPagamento> buscar(Long formaPagamentoId) {
		return formaPagamentoRepository.findById(formaPagamentoId);
	}
	
	public FormaPagamento buscarOuFalhar(Long formaPagamentoId) {
		return formaPagamentoRepository.findById(formaPagamentoId)
				.orElseThrow(() -> new FormaPagamentoNaoEncontradaException(formaPagamentoId)
		);
	}
	
	public List<FormaPagamento> listar() {
		return formaPagamentoRepository.findAll();
	}
	
	@Transactional
	public FormaPagamento salvar(FormaPagamento formaPagamento) {
		return formaPagamentoRepository.save(formaPagamento);
	}
	
	@Transactional
	public void excluir(Long formaPagamentoId) {
		try {
			formaPagamentoRepository.deleteById(formaPagamentoId);
			formaPagamentoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new FormaPagamentoNaoEncontradaException(formaPagamentoId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_FORMA_PAGAMENTO_EM_USO, formaPagamentoId));
		} 
	}

}
