package com.diniz.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.diniz.algafood.domain.model.FormaPagamento;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {
	
//	List<FormaPagamento> listar();
//	FormaPagamento buscar(Long id);
//	FormaPagamento salvar(FormaPagamento formaPagamento);
//	void remover(FormaPagamento formaPagamento);

}
