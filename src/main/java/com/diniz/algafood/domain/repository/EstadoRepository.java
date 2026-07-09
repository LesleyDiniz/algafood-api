package com.diniz.algafood.domain.repository;

import org.springframework.stereotype.Repository;

import com.diniz.algafood.domain.model.Estado;

@Repository
public interface EstadoRepository extends CustomJpaRepository<Estado, Long> {
	
//	List<Estado> listar();
//	Estado buscar(Long id);
//	Estado salvar(Estado estado);
//	void remover(Long id);
}
