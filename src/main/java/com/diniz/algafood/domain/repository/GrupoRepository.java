package com.diniz.algafood.domain.repository;

import org.springframework.stereotype.Repository;

import com.diniz.algafood.domain.model.Grupo;

@Repository
public interface GrupoRepository extends CustomJpaRepository<Grupo, Long>  {
	
}
