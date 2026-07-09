package com.diniz.algafood.domain.repository;

import org.springframework.stereotype.Repository;

import com.diniz.algafood.domain.model.Permissao;

@Repository
public interface PermissaoRepository extends CustomJpaRepository<Permissao, Long> {
	
//	List<Permissao> listar();
//	Permissao buscar(Long id);
//	Permissao salvar(Permissao permissao);
//	void remover(Permissao permissao);

}
