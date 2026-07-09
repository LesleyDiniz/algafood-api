package com.diniz.algafood.domain.repository;

import org.springframework.stereotype.Repository;

import com.diniz.algafood.domain.model.Cidade;

@Repository
public interface CidadeRepository extends CustomJpaRepository<Cidade, Long>  {
	
//	List<Cidade> listar();
//	Cidade buscar(Long id);
//	Cidade salvar(Cidade cidade);
//	void remover(Long id);

}
