//package com.diniz.algafood.infrastructure.repository;
//
//import java.util.List;
//
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.diniz.algafood.domain.model.Estado;
//import com.diniz.algafood.domain.repository.EstadoRepository;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.persistence.TypedQuery;
//
//@Repository
//public class EstadoRepositoryImpl implements EstadoRepository{
//
//	
//	@PersistenceContext
//	private EntityManager manager;
//
//	@Override
//	public List<Estado> listar() {
//		TypedQuery<Estado> query = manager.createQuery("from Estado", Estado.class);
//		
//		return query.getResultList();
//	}
//	
//	@Override
//	public Estado buscar(Long id) {
//		return manager.find(Estado.class, id);
//	}
//	
//	@Override
//	@Transactional
//	public Estado salvar(Estado estado) {
//		return manager.merge(estado);
//	}
//	
//	@Override
//	@Transactional
//	public void remover(Long id) {
//		var estado = buscar(id);
//		if(estado == null) throw new EmptyResultDataAccessException(1);
//		manager.remove(estado);
//	}
//
//}
